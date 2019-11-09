package com.uptang.cloud.score.template;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.enums.SemesterEnum;
import com.uptang.cloud.score.exceptions.ExcelExceptions;
import com.uptang.cloud.score.util.Http;

import java.io.InputStream;
import java.util.List;

/**
 * @author : Lee.m.yin
 * @createTime : 2019-11-02 23:15
 * @mailTo: webb.lee.cn@gmail.com
 * @summary: FIXME
 */
public abstract class ExcelTemplate implements Excel {

    @Override
    public final void analysis(ExcelTypeEnum excelType,
                               ScoreTypeEnum type,
                               Long userId,
                               Long gradeId,
                               Long classId,
                               Long schoolId,
                               SemesterEnum semesterCode) {
        Http.Request.getCurrent().ifPresent(request -> {
            try (InputStream inputStream = request.getInputStream()) {
                ExcelReaderBuilder excelReader = EasyExcelFactory.read(inputStream);
                excelReader.autoCloseStream(true);
                excelReader.excelType(excelType);
                excelReader.autoTrim(true);
                AnalysisEventListener readListener =
                        ListenerFactory.newListener(type, userId, gradeId, classId, schoolId, semesterCode);
                excelReader.registerReadListener(readListener);

                ExcelReader reader = excelReader.build();
                List<ReadSheet> readSheets = reader.excelExecutor().sheetList();
                if (readSheets == null || readSheets.size() == 0) {
                    throw new ExcelExceptions("该Excel中并没有数据");
                }

                // 只读第一页
                try {
                    reader.read(readSheets.get(0));
                } finally {
                    reader.finish();
                }
            } catch (Exception e) {
                throw new ExcelExceptions(e);
            }
        });
    }
}
