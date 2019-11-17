package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.model.Subject;
import com.uptang.cloud.score.common.vo.ShowScoreVO;
import com.uptang.cloud.score.common.vo.SubjectVO;
import com.uptang.cloud.score.dto.ShowScoreDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-15 20:10
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
public interface ShowScoreConverter {

    /**
     * 将附件实体转换成VO
     *
     * @param score
     * @return 转换后的VO
     */
    static ShowScoreVO toVo(ShowScoreDTO score) {
        ShowScoreVO showScoreVO = new ShowScoreVO();
        showScoreVO.setStudentId(score.getStudentId());
        showScoreVO.setGradeId(score.getGradeId());
        showScoreVO.setClassId(score.getClassId());
        showScoreVO.setSchoolId(score.getSchoolId());
        showScoreVO.setSemesterId(score.getSemesterId());
        showScoreVO.setGradeName(score.getGradeName());
        showScoreVO.setClassName(score.getClassName());
        showScoreVO.setSchoolName(score.getSchoolName());
        showScoreVO.setState(score.getState());
        showScoreVO.setGender(score.getGender());
        showScoreVO.setScoreType(score.getScoreType());
        showScoreVO.setStartedTime(score.getStartedTime());
        showScoreVO.setFinishTime(score.getFinishTime());
        showScoreVO.setSemesterName(score.getSemesterName());
        showScoreVO.setStudentId(score.getStudentId());
        showScoreVO.setStudentName(score.getStudentName());
        showScoreVO.setStudentCode(score.getStudentCode());
        showScoreVO.setCreatedTime(score.getCreatedTime());
        showScoreVO.setModifiedTime(score.getModifiedTime());
        showScoreVO.setCreatorId(score.getCreatorId());
        showScoreVO.setId(score.getId());

        List<Subject> subjects = score.getSubjects();
        SubjectConverter instance = SubjectConverter.INSTANCE;
        List<SubjectVO> subs = new ArrayList<>(subjects.size());
        for (Subject subject : subjects) {
            subs.add(instance.toVO(subject));
        }
        showScoreVO.setSubjects(subs);
        return showScoreVO;
    }


    /**
     * 将附件VO转换成实体
     *
     * @param score
     * @return 转换后实体
     */
    static ShowScoreDTO toModel(ShowScoreVO score) {
        ShowScoreDTO showScoreDTO = new ShowScoreDTO();
        showScoreDTO.setStudentId(score.getStudentId());
        showScoreDTO.setGradeId(score.getGradeId());
        showScoreDTO.setClassId(score.getClassId());
        showScoreDTO.setSchoolId(score.getSchoolId());
        showScoreDTO.setSemesterId(score.getSemesterId());
        showScoreDTO.setGradeName(score.getGradeName());
        showScoreDTO.setClassName(score.getClassName());
        showScoreDTO.setSchoolName(score.getSchoolName());
        showScoreDTO.setState(score.getState());
        showScoreDTO.setGender(score.getGender());
        showScoreDTO.setScoreType(score.getScoreType());
        showScoreDTO.setStartedTime(score.getStartedTime());
        showScoreDTO.setFinishTime(score.getFinishTime());
        showScoreDTO.setSemesterName(score.getSemesterName());
        showScoreDTO.setStudentId(score.getStudentId());
        showScoreDTO.setStudentCode(score.getStudentCode());
        showScoreDTO.setStudentName(score.getStudentName());
        showScoreDTO.setCreatedTime(score.getCreatedTime());
        showScoreDTO.setModifiedTime(score.getModifiedTime());
        showScoreDTO.setCreatorId(score.getCreatorId());
        showScoreDTO.setId(score.getId());

        List<SubjectVO> subjects = score.getSubjects();
        SubjectConverter instance = SubjectConverter.INSTANCE;
        List<Subject> subs = new ArrayList<>(subjects.size());
        for (SubjectVO subjectVO : subjects) {
            subs.add(instance.toModel(subjectVO));
        }
        showScoreDTO.setSubjects(subs);
        return showScoreDTO;
    }
}
