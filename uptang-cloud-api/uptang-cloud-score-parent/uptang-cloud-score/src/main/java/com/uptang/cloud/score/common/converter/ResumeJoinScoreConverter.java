package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.vo.ResumeJoinScoreVO;
import com.uptang.cloud.score.dto.ResumeJoinScoreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-07 16:03
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Mapper
public interface ResumeJoinScoreConverter {

    ResumeJoinScoreConverter INSTANCE = Mappers.getMapper(ResumeJoinScoreConverter.class);

    /**
     * 将附件实体转换成VO
     *
     * @param resumeJoinScore 归档与履历
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "semesterCode", source = "semesterCode.code")
    @Mapping(target = "semesterCodeText", source = "semesterCode.desc")
    @Mapping(target = "scoreType", source = "scoreType.code")
    @Mapping(target = "scoreTypeText", source = "scoreType.desc")
    @Mapping(target = "gender", source = "gender.code")
    @Mapping(target = "genderText", source = "gender.desc")
    @Mapping(target = "subject", source = "subject.code")
    @Mapping(target = "scoreNumber", expression = "java(com.uptang.cloud.score.util.Calculator.dev10(resumeJoinScore.getScoreNumber()))")
    ResumeJoinScoreVO toVo(ResumeJoinScoreDTO resumeJoinScore);

    /**
     * 将附件VO转换成实体
     *
     * @param resumeJoinScore 归档与履历
     * @return 转换后实体
     */
    @Mappings({
            @Mapping(target = "semesterCode", expression = "java(SemesterEnum.code(resumeJoinScore.getSemesterCode()))"),
            @Mapping(target = "scoreType", expression = "java(ScoreTypeEnum.code(resumeJoinScore.getScoreType()))"),
            @Mapping(target = "gender", expression = "java(GenderEnum.parse(resumeJoinScore.getGender()))"),
            @Mapping(target = "subject", expression = "java(SubjectEnum.code(resumeJoinScore.getSubject()))"),
            @Mapping(target = "scoreNumber", expression = "java(com.uptang.cloud.score.util.Calculator.x10(resumeJoinScore.getScoreNumber()))")
    })
    ResumeJoinScoreDTO toModel(ResumeJoinScoreVO resumeJoinScore);
}
