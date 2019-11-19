package com.uptang.cloud.score.template;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.uptang.cloud.score.common.model.Subject;
import com.uptang.cloud.score.dto.GradeCourseDTO;
import com.uptang.cloud.score.dto.RequestParameter;
import com.uptang.cloud.score.dto.StudentRequestDTO;
import com.uptang.cloud.score.exception.ExcelException;
import com.uptang.cloud.score.handler.PrimitiveResolver;
import com.uptang.cloud.score.service.IRestCallerService;
import com.uptang.cloud.score.strategy.ExcelProcessorStrategy;
import com.uptang.cloud.score.util.ApplicationContextHolder;
import com.uptang.cloud.score.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-08 20:55
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME 烂
 */
@Slf4j
public abstract class AbstractAnalysisEventListener<T> extends AnalysisEventListener<Map<Integer, Object>> {

    /**
     * 子类泛型
     */
    private Class<T> generic;

    /**
     * 客户端参数
     */
    private RequestParameter excel;

    /**
     * 数据校验信息
     */
    private List<GradeCourseDTO> gradeCourse;

    /**
     * data
     */
    private final List<T> DATA = new ArrayList<>();

    /**
     * 先解决有的问题
     * 表头
     */
    protected Map<Integer, String> headMap;

    /**
     * Rest接口调用Service
     */
    protected final IRestCallerService restCallerService =
            ApplicationContextHolder.getBean(IRestCallerService.class);

    /**
     * 获取泛型
     *
     * @return
     */
    private void init() {
        Type type = this.getClass().getGenericSuperclass();
        Type clazz = (type instanceof ParameterizedType)
                ? ((ParameterizedType) type).getActualTypeArguments()[0] : type;
        this.generic = (Class<T>) clazz;
    }

    public AbstractAnalysisEventListener(RequestParameter excel) {
        this.excel = excel;
        init();
    }

    @Override
    public final void invoke(Map<Integer, Object> rawData, AnalysisContext context) {
        // 当前行
        Integer lineNumber = context.readRowHolder().getRowIndex();
        try {
            // 实例化子类泛型
            T newInstance = (T) ReflectUtils.newInstance(this.generic);
            {
                // 固定列数
                final int[] fixedCell = new int[]{0};
                // 设置Subject
                final boolean[] setSubject = new boolean[]{true};
                // 设置固定值
                ReflectionUtils.doWithFields(this.generic, field -> {
                    fixedCell[0]++;
                    ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
                    if (annotation != null) {
                        ReflectionUtils.makeAccessible(field);
                        Object originalValue = rawData.get(annotation.index());

                        Class<?> clazz = field.getType();
                        PrimitiveResolver converter = PrimitiveResolver.getConverter(clazz);
                        field.set(newInstance, converter.convert(originalValue));
                    } else {
                        if (setSubject[0]) {
                            ReflectionUtils.makeAccessible(field);
                            int startIndex = fixedCell[0] - 1;
                            field.set(newInstance, getSubjects(rawData, lineNumber, startIndex, gradeCourse));
                            setSubject[0] = false;
                        }
                    }
                });
            }

            DATA.add(newInstance);
        } catch (Exception e) {
            this.DATA.clear();
            this.headMap.clear();
            this.gradeCourse = null;
            log.error(e.getMessage());
            throw new ExcelException(e);
        }
    }

    @Override
    public final void doAfterAllAnalysed(AnalysisContext context) {
        try {
            getStrategy().check(DATA, context, excel);
            doInvoke(DATA, excel, context);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ExcelException(e);
        } finally {
            this.gradeCourse = null;
            this.DATA.clear();
            this.headMap.clear();
        }
    }

    /**
     * 获取科目列表
     *
     * @param rawData     Sheet数据
     * @param lineNumber  当前行
     * @param startIndex  开始下标
     * @param gradeCourse 科目校验信息
     * @return
     */
    public abstract List<Subject> getSubjects(Map<Integer, Object> rawData,
                                              Integer lineNumber,
                                              int startIndex,
                                              List<GradeCourseDTO> gradeCourse);

    /**
     * 数据处理
     *
     * @param data
     * @param context
     * @param excel
     */
    public abstract void doInvoke(List<T> data, RequestParameter excel, AnalysisContext context);

    /**
     * 分批次/分组 100条一组
     *
     * @return
     */
    public <R> Map<Integer, List<R>> getGroupList(List<R> data) {
        return Collections.groupList(data);
    }

    /**
     * 处理表头
     *
     * @param headMap 表头<下标，表头名称>
     * @param context Excel上下文
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        if (log.isDebugEnabled()) {
            log.debug("用户{}导入{}学校{}年级{}班{}学期的{}成绩 Header ==> {}",
                    excel.getUserId(), excel.getSchoolId(), excel.getGradeId(),
                    excel.getClassId(), excel.getSemesterId(), headMap);
        }

        StudentRequestDTO studentRequestDTO = new StudentRequestDTO();
        studentRequestDTO.setToken(excel.getToken());
        studentRequestDTO.setGradeId(excel.getGradeId());
        this.gradeCourse = restCallerService.gradeInfo(studentRequestDTO);
        this.headMap = headMap;
        getStrategy().headMap(headMap, gradeCourse);
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
        if (DATA.size() != 0) {
            this.DATA.clear();
            this.headMap.clear();
            this.gradeCourse = null;
        }

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
                excel.getUserId(), excel.getSchoolId(), excel.getGradeId(),
                excel.getClassId(), excel.getSemesterId(), message);
        throw new ExcelException(message);
    }

    public RequestParameter getExcel() {
        return excel;
    }
}
