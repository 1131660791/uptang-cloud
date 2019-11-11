package com.uptang.cloud.score.strategy;

import com.alibaba.excel.context.AnalysisContext;
import com.uptang.cloud.score.common.dto.AcademicScoreDTO;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.dto.ImportFromExcelDTO;
import com.uptang.cloud.score.service.IRestCallerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 18:01
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Slf4j
@Component
public class AcademicScoreExcelProcessorStrategy implements ExcelProcessorStrategy<AcademicScoreDTO>, InitializingBean {

    @Autowired
    private IRestCallerService restCallerService;

    /**
     * Excel 表头数据校验
     * <p>
     * {0=年级, 1=班级, 2=学籍号, 3=姓名, 4=道德与法治, 5=语文, 6=数学, 7=英语, 8=物理, 9=化学, 10=历史,
     * 11=地理, 12=生物, 13=体育, 14=信息技术, 15=音乐, 16=美术, 17=物理实验, 18=化学实验,
     * 19=生物实验, 20=劳动与技术教育, 21=地方及校本课程}
     *
     * @param headMap 表头数据
     */
    @Override
    public void headMap(Map<Integer, String> headMap) {
        log.info("Academic header map ==> {}", headMap);
    }

    /**
     * Excel数据行校验
     *
     * @param rawData      Excel行数据
     * @param context      Excel 解析上下文
     * @param userId       用户ID
     * @param gradeId      年级ID
     * @param classId      班级ID
     * @param schoolId     学校ID
     * @param semesterCode 学期编码
     * @return
     */
    @Override
    public boolean check(AcademicScoreDTO rawData, AnalysisContext context, ImportFromExcelDTO excel) {
        log.info("学业 check {}", rawData);
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ExcelProcessorStrategyFactory.register(ScoreTypeEnum.ACADEMIC, this);
    }
}
