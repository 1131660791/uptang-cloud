package com.uptang.cloud.score.service.impl;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.enums.SemesterEnum;
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

    /**
     * 处理Excel数据
     *
     * @param token        当前用户登录凭证
     * @param userId       当前用户ID
     * @param type         成绩类型
     * @param gradeId      年级ID
     * @param classId      班级ID
     * @param schoolId     学校ID
     * @param semesterCode 学期编码
     * @return
     */
    @Override
    public void processor(String token, Long userId, Integer type, Long gradeId,
                          Long classId, Long schoolId, SemesterEnum semesterCode) {

        StudentRequestDTO build =
                StudentRequestDTO.builder().classId(classId).schoolId(schoolId).gradeId(gradeId).build();
        final ListOperations<String, String> listOperations = redisTemplate.opsForList();
        String cacheKey = CacheKeys.scoreStudetInfoList(schoolId, gradeId);
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

        // 放拦截器
//        ModuleSwitchDto moduleSwitch = ModuleSwitchDto.builder().gradeId(gradeId).build();
//        moduleSwitch.setToken(token);
//        ModuleSwitchResponseDto moduleSwitchResponse = restCallerService.moduleSwitch(moduleSwitch);
//        if (moduleSwitchResponse != null && moduleSwitchResponse.getSwitched()) {
//            log.info("任务是否开放 ==> {}", moduleSwitch);
//        }

        final ScoreTypeEnum code = ScoreTypeEnum.code(type);
        if (code != null) {
            super.analysis(ExcelTypeEnum.XLS, code, userId, gradeId, classId, schoolId, semesterCode);
        }
    }
}
