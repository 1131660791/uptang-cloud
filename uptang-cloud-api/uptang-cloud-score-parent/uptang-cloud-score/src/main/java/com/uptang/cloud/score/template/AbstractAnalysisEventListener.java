package com.uptang.cloud.score.template;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.uptang.cloud.score.common.enums.SemesterEnum;
import com.uptang.cloud.score.exceptions.ExcelExceptions;
import com.uptang.cloud.score.handler.PrimitiveResolver;
import com.uptang.cloud.score.strategy.ExcelProcessorStrategy;
import com.uptang.cloud.score.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-08 20:55
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME
 * final Map<Integer, List<T>> groupMapList = Collections.groupList(sheetData);
 * resumeService.batchSave(groupMapList, scoreTypeEnum);
 */
@Slf4j
public abstract class AbstractAnalysisEventListener<T> extends AnalysisEventListener<Map<Integer, Object>> {

    /**
     * 当前用户ID
     */
    private Long userId;

    /**
     * 年级ID
     */
    private Long gradeId;

    /**
     * 班级ID
     */
    private Long classId;

    /**
     * 学校ID
     */
    private Long schoolId;

    /**
     * 学期编码
     */
    private SemesterEnum semesterCode;

    /**
     * 子类泛型
     */
    private Class<T> generic;

    private void init() {
        Type type = this.getClass().getGenericSuperclass();
        Type clazz = (type instanceof ParameterizedType) ? ((ParameterizedType) type).getActualTypeArguments()[0] : type;
        this.generic = (Class<T>) clazz;
    }

    public AbstractAnalysisEventListener
            (Long userId, Long gradeId, Long classId, Long schoolId, SemesterEnum semesterCode) {
        this.userId = userId;
        this.gradeId = gradeId;
        this.classId = classId;
        this.schoolId = schoolId;
        this.semesterCode = semesterCode;
        init();
    }


    @Override
    public final void invoke(Map<Integer, Object> rawData, AnalysisContext context) {
        T newInstance = (T) ReflectUtils.newInstance(this.generic);
        ReflectionUtils.doWithFields(this.generic, field -> {
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (annotation != null) {
                ReflectionUtils.makeAccessible(field);
                Object originalValue = rawData.get(annotation.index());
                Object value = PrimitiveResolver.getConverter(field.getType()).convert(originalValue);
                field.set(newInstance, value);
            }
        });


        final ExcelProcessorStrategy strategy = getStrategy();
        if (strategy.check(newInstance, context, userId, gradeId, classId, schoolId, semesterCode)) {
            doInvoke(newInstance, context);
        } else {
            String message = String.format("第%d页，第%d行解析异常",
                    context.readSheetHolder().getSheetNo(),
                    context.readRowHolder().getRowIndex());

            String rawMessageData = JSON.toJSONString(newInstance);
            message = String.format("%s。该行数据为：%s", message, rawMessageData);
            throw new ExcelExceptions(message);
        }
    }

    /**
     * 数据处理
     *
     * @param data
     * @param context
     */
    public abstract void doInvoke(T data, AnalysisContext context);

    /**
     * 分批次/分组 100条一组
     *
     * @return
     */
    public <R> Map<Integer, List<R>> getGroupList(List<R> data) {
        return Collections.groupList(data);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        if (log.isDebugEnabled()) {
            log.debug("用户{}导入{}学校{}年级{}班{}学期的{}成绩 Header ==> {}",
                    userId, schoolId, gradeId, classId, semesterCode, semesterCode, headMap);
        }
        getStrategy().headMap(headMap);
    }

    /**
     * 获取Excel数据处理策略
     *
     * @return
     */
    public abstract ExcelProcessorStrategy getStrategy();

    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        exception.printStackTrace();

        String message = String.format("第%d页，第%d行解析异常",
                context.readSheetHolder().getSheetNo(),
                context.readRowHolder().getRowIndex());

        Object currentRowAnalysisResult = context.readRowHolder().getCurrentRowAnalysisResult();
        if (currentRowAnalysisResult != null) {
            String rawData = JSON.toJSONString(currentRowAnalysisResult);
            message = String.format("%s。该行数据为：%s", message, rawData);
        }

        log.error("用户{}导入{}学校{}年级{}班{}学期 error ==> {}",
                userId, schoolId, gradeId, classId, semesterCode, message);
        throw new ExcelExceptions(message);
    }

    public Long getUserId() {
        return userId;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public Long getClassId() {
        return classId;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public SemesterEnum getSemesterCode() {
        return semesterCode;
    }
}
