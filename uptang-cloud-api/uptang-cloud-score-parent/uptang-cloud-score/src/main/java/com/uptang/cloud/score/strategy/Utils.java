package com.uptang.cloud.score.strategy;

import com.uptang.cloud.core.exception.BusinessException;
import com.uptang.cloud.score.common.dto.Excel;
import com.uptang.cloud.score.common.dto.ExcelDTO;
import com.uptang.cloud.score.common.dto.HealthScoreDTO;
import com.uptang.cloud.score.common.enums.ScoreTypeEnum;
import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-11 10:30
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Slf4j
class Utils {

    /**
     * 学生信息条数阈值
     */
    private static final Long STU_COUNT_THRESHOLD = 1000L;

    /**
     * 设置学生信息
     *
     * @param sheetData Excel数据
     * @param excel     前端请求参数
     * @param supplier
     * @param function
     */
    public static void setUserInfo(List<ExcelDTO> sheetData,
                                   RequestParameter excel,
                                   Supplier<StuListDTO> supplier,
                                   BiFunction<Long, Long, StuListDTO> function,
                                   Function<ExcelDTO, StuListDTO> queryByStudentCode) {
        StuListDTO stuInfo = supplier.get();
        if (stuInfo != null) {
            Utils.setStudentInfo(sheetData, excel, stuInfo);

            // 总条数小于阈值，则一次全部查询出来
            if (stuInfo.getCount().compareTo(STU_COUNT_THRESHOLD) != 1) {
                Utils.setStudentInfo(sheetData, excel, function.apply(1L, STU_COUNT_THRESHOLD));
            } else {
                // 总页数
                long pageSize = 300;
                long totalPage = (stuInfo.getCount() + pageSize - 1) / pageSize;
                for (long i = 0; i < totalPage; i++) {
                    Utils.setStudentInfo(sheetData, excel, function.apply(i, pageSize));
                }
            }

            // 检查
            StringBuilder errMessage = new StringBuilder();
            for (ExcelDTO sheetDatum : sheetData) {
                AcademicResume resume = sheetDatum.getResume();
                if (resume == null || resume.getStudentId() == null) {
                    StuListDTO apply = queryByStudentCode.apply(sheetDatum);
                    if (apply == null || apply.getList() == null || apply.getList().size() == 0) {
                        String message = sheetDatum.getStudentName() + " 学籍:" + sheetDatum.getStudentCode()
                                + " 学校:" + excel.getSchoolId() + " 年级:" + excel.getGradeId()
                                + " 班级: " + excel.getClassId() + "\n";
                        errMessage.append(message);
                        if (log.isDebugEnabled()) {
                            log.debug(message);
                        }
                    } else {
                        Utils.setStudentInfo(sheetData, excel, apply);
                    }
                }
            }

            if (StringUtils.isNotBlank(errMessage)) {
                throw new BusinessException("未找到下列学生信息\n" + errMessage.toString());
            }
        }
    }


    /**
     * 设置学生信息
     *
     * @param sheetData          Excel数据
     * @param excel              前端请求参数
     * @param supplier
     * @param function
     * @param queryByStudentCode
     */
    public static void setHealthUserInfo(List<HealthScoreDTO> sheetData,
                                         RequestParameter excel,
                                         Supplier<StuListDTO> supplier,
                                         BiFunction<Long, Long, StuListDTO> function,
                                         Function<HealthScoreDTO, StuListDTO> queryByStudentCode) {
        StuListDTO stuInfo = supplier.get();
        if (stuInfo != null) {
            Utils.setHealthStudentInfo(sheetData, excel, stuInfo);

            // 总条数小于阈值，则一次全部查询出来
            if (stuInfo.getCount().compareTo(STU_COUNT_THRESHOLD) != 1) {
                Utils.setHealthStudentInfo(sheetData, excel, function.apply(1L, STU_COUNT_THRESHOLD));
            } else {
                // 总页数
                long pageSize = 300;
                long totalPage = (stuInfo.getCount() + pageSize - 1) / pageSize;
                for (long i = 0; i < totalPage; i++) {
                    Utils.setHealthStudentInfo(sheetData, excel, function.apply(i, pageSize));
                }
            }

            // 检查
            StringBuilder errMessage = new StringBuilder();
            for (HealthScoreDTO sheetDatum : sheetData) {
                AcademicResume resume = sheetDatum.getResume();
                if (resume == null || resume.getStudentId() == null) {
                    StuListDTO apply = queryByStudentCode.apply(sheetDatum);
                    if (apply == null || apply.getList() == null || apply.getList().size() == 0) {
                        String message = String.format("%s 学籍:%s 学校:%d 年级:%d 班级:%d\n", sheetDatum.getStudentName(),
                                sheetDatum.getStudentCode(),
                                excel.getSchoolId(),
                                excel.getGradeId(),
                                excel.getClassId());
                        errMessage.append(message);
                        if (log.isDebugEnabled()) {
                            log.debug("{}类成绩。{}", ScoreTypeEnum.HEALTH.getDesc(), message);
                        }
                    } else {
                        Utils.setHealthStudentInfo(sheetData, excel, apply);
                    }
                }
            }

            if (StringUtils.isNotBlank(errMessage)) {
                throw new BusinessException("未找到下列学生信息:\n" + errMessage.toString());
            }
        }
    }


    /**
     * 检查用户数据
     */
    public static void checkUserInfo(AcademicResume resume) {
        // 检查
        if (resume == null || resume.getStudentId() == null) {
            throw new BusinessException("学生信息不存在");
        }
    }

    /**
     * 检查Excel头部
     *
     * @param headMap     Excel头部
     * @param gradeCourse 年级科目信息
     * @param mustCell    固定列
     * @param scoreType   成绩类型
     */
    public static void headCheck(Map<Integer, String> headMap,
                                 List<GradeCourseDTO> gradeCourse,
                                 int mustCell,
                                 ScoreTypeEnum scoreType) {

        List<GradeCourseDTO> newGradeCourses = gradeCourse.stream()
                .filter(course -> course.getScoreType() == scoreType)
                .collect(Collectors.toList());

        int cell = 0;
        Iterator<Map.Entry<Integer, String>> iterator = headMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> map = iterator.next();
            for (GradeCourseDTO course : newGradeCourses) {
                if (map.getValue().equals(course.getSubjectName())) {
                    cell++;
                }
            }
        }

        // 出去非固定列剩余的就是固定列
        int fixedCellNum = headMap.size() - cell;
        // 若"固定列"不相同则说明Excel文件要么多写了要么少写了
        if (mustCell != fixedCellNum || newGradeCourses.size() != cell) {
            List<String> collect = gradeCourse.stream()
                    .filter(grade -> grade.getScoreType() == scoreType)
                    .map(GradeCourseDTO::getSubjectName).collect(Collectors.toList());
            throw new BusinessException("Excel列名对应不上，正确的列名 【" + collect + "】");
        }
    }

    /**
     * 设置学生信息
     *
     * @param data    Excel数据
     * @param excel   前端请求参数
     * @param stuInfo 学生信息
     */
    public static void setStudentInfo(List<ExcelDTO> data,
                                      RequestParameter excel,
                                      StuListDTO stuInfo) {

        if (stuInfo != null && stuInfo.getList() != null) {
            for (StudentDTO student : stuInfo.getList()) {
                for (ExcelDTO datum : data) {
                    //  同一个人
                    if (student.getStudentCode().equals(datum.getStudentCode())) {
                        setResume(excel, student, datum);
                    }
                }
            }
        }
    }

    /**
     * 设置学生信息
     *
     * @param data    Excel数据
     * @param excel   前端请求参数
     * @param stuInfo 学生信息
     */
    public static void setHealthStudentInfo(List<HealthScoreDTO> data, RequestParameter excel, StuListDTO stuInfo) {
        if (stuInfo != null && stuInfo.getList() != null) {
            for (StudentDTO student : stuInfo.getList()) {
                data.stream()
                        // 同一个人
                        .filter(excelDto -> student.getStudentCode().equals(excelDto.getStudentCode()))
                        .forEach(excelDto -> setResume(excel, student, excelDto));
            }
        }
    }

    /**
     * 设置履历信息
     *
     * @param excel
     * @param student
     * @param excelDto
     */
    private static void setResume(RequestParameter excel, StudentDTO student, Excel excelDto) {
        AcademicResume resume = new AcademicResume();
        resume.setCreatorId(excel.getUserId());
        resume.setCreatedTime(new Date());
        resume.setSemesterId(excel.getSemesterId());
        resume.setSemesterName(excel.getSemesterName());
        resume.setSchoolId(excel.getSchoolId() == null ? student.getSchoolId() : excel.getSchoolId());
        resume.setGradeId(excel.getGradeId() == null ? student.getGradeId() : excel.getGradeId());
        resume.setClassId(excel.getClassId() == null ? student.getClassId() : excel.getClassId());
        resume.setScoreType(excel.getScoreType());

        // APi 获取
        resume.setStudentId(student.getGuid());
        resume.setGender(student.getSex());
        resume.setStudentCode(student.getStudentCode());
        resume.setStudentName(student.getStudentName());
        resume.setSchoolName(student.getSchoolName());
        resume.setGradeName(student.getGradeName());
        resume.setClassName(student.getClassName());

        // Subject
        excelDto.getSubjects().forEach(sub -> sub.setStudentId(student.getGuid()));
        excelDto.setResume(resume);
    }


    /**
     * @param excel
     * @param excelDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    public static StudentRequestDTO getStudentRequestDTO(RequestParameter excel,
                                                         ExcelDTO excelDto,
                                                         long pageNum,
                                                         long pageSize) {
        StudentRequestDTO student = new StudentRequestDTO();
        student.setToken(excel.getToken());
        student.setGradeId(excel.getGradeId());
        student.setSchoolId(excel.getSchoolId());

        if (excel.getClassId() != null) {
            student.setClassId(excel.getClassId());
        }

        if (excelDto != null) {
            student.setStudentName(excelDto.getStudentName());
            student.setStudentCode(excelDto.getStudentCode());
        } else {
            student.setOffset(pageNum);
            student.setRows(pageSize);
        }
        return student;
    }
}
