package com.uptang.cloud.score.template;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uptang.cloud.score.exceptions.ExcelExceptions;
import com.uptang.cloud.score.util.Http;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-02 23:15
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME
 */
@Slf4j
public abstract class ExcelTemplate implements Excel {

    /**
     * 读取Excel文件
     *
     * @param excelType Excel类型
     */
    @Override
    public final void analysis(ExcelTypeEnum excelType) {
        Http.Request.getCurrent().ifPresent(request -> {
            try (InputStream inputStream = request.getInputStream()) {
                ExcelReaderBuilder excelReader = EasyExcelFactory.read(inputStream);
                excelReader.autoCloseStream(true);
                excelReader.excelType(excelType);
                excelReader.autoTrim(true);
                excelReader.registerReadListener(new AnalysisEventListener<Object>() {

                    // excel sheet data
                    private final LinkedList<Object> excelSheetData = new LinkedList<>();

                    @Override
                    public void invoke(Object data, AnalysisContext context) {
                        try {
                            if (check(data, context)) {
                                excelSheetData.add(data);
                            }
                        } catch (Exception e) {
                            throw new ExcelExceptions(e);
                        }
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                        try {
                            post(excelSheetData);
                        } catch (Exception e) {
                            throw new ExcelExceptions(e);
                        } finally {
                            excelSheetData.clear();
                        }
                    }

                    /**
                     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
                     *
                     * @param exception
                     * @param context
                     * @throws Exception
                     */
                    @Override
                    public void onException(Exception exception, AnalysisContext context) {
                        String message = String.format("第%d页，第%d行解析异常", context.readSheetHolder().getSheetNo(), context.readRowHolder().getRowIndex());
                        Object currentRowAnalysisResult = context.readRowHolder().getCurrentRowAnalysisResult();
                        if (currentRowAnalysisResult != null) {
                            try {
                                //FIXME
                                message += new ObjectMapper().writeValueAsString(currentRowAnalysisResult);
                            } catch (JsonProcessingException e) {
                                throw new ExcelExceptions(exception);
                            }
                        }
                        log.error(message);
                        throw new ExcelExceptions(message);
                    }

                    /**
                     * 如果要获取头的信息
                     * @param headMap
                     * @param context
                     */
                    @Override
                    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
                        log.info("{}", headMap);
                        headMap(headMap);
                    }
                });
            } catch (Exception e) {
                throw new ExcelExceptions(e);
            }
        });
    }

    /**
     * Excel Header 表头
     *
     * @param headMap 表头数据
     */
    public abstract void headMap(Map<Integer, String> headMap);

    /**
     * 数据检查
     *
     * @param rawData 当前行数据
     * @param context Excel 解析上下文
     *                <p>
     *                System.out.println("当前:" + context.readSheetHolder().getSheetNo() + ",当前行:" + context.readRowHolder());
     */
    public abstract boolean check(Object rawData, AnalysisContext context);

    /**
     * @param data Excel sheet data
     */
    public abstract void post(List<Object> data);

}
