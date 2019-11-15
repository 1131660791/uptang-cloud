package com.uptang.cloud.score.template;

import com.alibaba.fastjson.JSON;
import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.score.common.dto.ExcelDto;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.ScoreStatus;
import com.uptang.cloud.score.common.model.Subject;
import com.uptang.cloud.score.common.util.Calculator;
import com.uptang.cloud.score.dto.GradeCourseDTO;
import com.uptang.cloud.score.handler.PrimitiveResolver;
import com.uptang.cloud.score.util.CharacterConvert;
import com.uptang.cloud.score.util.IntervalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-13 17:49
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Slf4j
class Utils {

    /**
     * @param rawData     Excel数据
     * @param lineNumber  行号
     * @param startIndex  开始下标
     * @param gradeCourse 课程信息
     * @return
     */
    public static List<Subject> getSubjects(Map<Integer, Object> rawData,
                                            Integer lineNumber,
                                            int startIndex,
                                            List<GradeCourseDTO> gradeCourse,
                                            ScoreTypeEnum scoreType) {

        List<Subject> subjects = new ArrayList<>();

        List<GradeCourseDTO> newGradeCourses = gradeCourse.stream()
                .filter(course -> course.getScoreType() == scoreType)
                .collect(Collectors.toList());

        int size = newGradeCourses.size();
        StringBuilder errorMessage = new StringBuilder();
        for (int i = startIndex; i < rawData.size(); i++) {
            for (GradeCourseDTO course : newGradeCourses) {
                // 成绩类型等同并且科目顺序下标相同[确保是同一个科目]
                Integer orderNumber = size == 1 ? startIndex : course.getOrderNumber();
                if (orderNumber.compareTo(i) == 0) {
                    Subject subject = new Subject();
                    subject.setScoreType(scoreType);
                    Object value = rawData.get(orderNumber);

                    // 数值类型
                    String rule = course.getRule();
                    if (course.getDataType() == GradeCourseDTO.DataTypeEnum.NUMBER) {
                        String errMsg = numberValue(lineNumber, scoreType, i, subject, value, rule);
                        Optional.ofNullable(errMsg).ifPresent(errorMessage::append);
                    }
                    // 字符串类型
                    else {
                        String convert = (String) PrimitiveResolver.String_.convert(value);
                        subject.setScoreText(CharacterConvert.toHalf(convert));
                        subject.setScoreNumber(Calculator.UNSIGNED_SMALLINT_MAX_VALUE);
                    }

                    subject.setCode(course.getId());
                    subject.setName(course.getSubjectName());
                    subjects.add(subject);
                }
            }
        }

        // Excel列校验解析出错
        if (StringUtils.isNotBlank(errorMessage)) {
            throw new BusinessException(errorMessage.toString());
        }

        if (subjects.size() == 0) {
            log.error("导入Excel。插入Subject失败，原因没有Subject需要被插入 年级课程信息 ==> {}", newGradeCourses);
            throw new BusinessException("系统异常");
        }
        return subjects;
    }

    /**
     * 数值类型
     * 艺术成绩等级划分：90分以上为优秀，75-89分为良好，60-74分为合格，60分以下为不合格
     *
     * @param lineNumber Excel行号
     * @param scoreType  当前录入成绩类型
     * @param cell       Excel列下标
     * @param subject    科目信息
     * @param scoreValue 科目成绩
     * @param rule       分数校验规则
     */
    private static String numberValue(Integer lineNumber,
                                      ScoreTypeEnum scoreType,
                                      int cell,
                                      Subject subject,
                                      Object scoreValue,
                                      String rule) {

        double doubleValue = (double) PrimitiveResolver.Double_.convert(scoreValue);

        // {"[90,100]": "优秀","[75,89.9]": "良好","[60,74.9]": "合格","[0,59.9]": "不合格"}
        if (JSON.isValidObject(rule) && scoreType == ScoreTypeEnum.ART) {
            subject.setScoreNumber(Calculator.defaultNumberScore(doubleValue));

            Map<String, String> map = JSON.parseObject(rule, Map.class);
            Set<Map.Entry<String, String>> entries = map.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                if (IntervalUtil.inInterval(doubleValue + "", entry.getKey())) {
                    subject.setScoreText(entry.getValue());
                }
            }

            if (StringUtils.isBlank(subject.getScoreText())) {
                return String.format("第%d行第%d列数据{%.1f}未通过校验.校验规则:\n", lineNumber, cell, doubleValue, rule);
            }
        } else {
            boolean interval = IntervalUtil.inInterval(doubleValue + "", rule);
            if (!interval) {
                String message =
                        String.format("第%d行第%d列数据{%.1f}未通过校验.校验规则:\n", lineNumber, cell, doubleValue, rule);
                if (log.isDebugEnabled()) {
                    log.debug("{}类成绩校验失败规则:{}。成绩:{}", scoreType, rule, message);
                }
                return message;
            } else {
                subject.setScoreNumber(Calculator.defaultNumberScore(doubleValue));
                subject.setScoreText(Strings.EMPTY);
            }
        }

        return null;
    }

    /**
     * @param data
     * @return
     */
    public static List<Subject> convert2List(List<ExcelDto> data) {
        return data.stream()
                .map(ExcelDto::getSubjects)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    /**
     * @param maps
     * @return
     */
    public static List<ScoreStatus> getScoreStatuses(List<Map<Long, Long>> maps) {
        List<ScoreStatus> statuses = new ArrayList<>();
        for (Map<Long, Long> map : maps) {
            Set<Map.Entry<Long, Long>> entries = map.entrySet();
            for (Map.Entry<Long, Long> entry : entries) {
                statuses.add(new ScoreStatus(entry.getValue()));
            }
        }
        return statuses;
    }
}
