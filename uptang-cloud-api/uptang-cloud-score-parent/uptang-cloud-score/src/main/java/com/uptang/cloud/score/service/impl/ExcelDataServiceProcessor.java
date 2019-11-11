package com.uptang.cloud.score.service.impl;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.uptang.cloud.score.dto.ImportFromExcelDTO;
import com.uptang.cloud.score.dto.StudentDTO;
import com.uptang.cloud.score.dto.StudentRequestDTO;
import com.uptang.cloud.score.service.IExcelDataServiceProcessor;
import com.uptang.cloud.score.service.IRestCallerService;
import com.uptang.cloud.score.template.ExcelTemplate;
import com.uptang.cloud.score.util.CacheKeys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 10:39
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Slf4j
@Service
public class ExcelDataServiceProcessor extends ExcelTemplate implements IExcelDataServiceProcessor {

    @Autowired
    @Qualifier("subjectExecutor")
    private ThreadPoolTaskExecutor subjectExecutor;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final IRestCallerService restCallerService;

    public ExcelDataServiceProcessor(IRestCallerService restCallerService) {
        this.restCallerService = restCallerService;
    }


    @Override
    public void processor(ImportFromExcelDTO excel) {
        StudentRequestDTO build = StudentRequestDTO.builder()
                .classId(excel.getClassId())
                .schoolId(excel.getSchoolId())
                .gradeId(excel.getGradeId()).build();

        final ListOperations<String, String> listOperations = redisTemplate.opsForList();
        String cacheKey = CacheKeys.scoreStudetInfoList(excel.getClassId(), excel.getGradeId());
        subjectExecutor.execute(() -> {
            int offset = 20;
            do {
                build.setOffset(offset);
                build.setRows(100);
                List<StudentDTO> students = restCallerService.studentList(build);
                if (students != null && students.size() > 0) {
                    listOperations.leftPush(cacheKey, JSON.toJSONString(students));
                }
                offset--;
            } while (offset != 0);
        });



        if (excel.getScoreType() != null) {
            super.analysis(ExcelTypeEnum.XLS, excel);
        }
    }
}
