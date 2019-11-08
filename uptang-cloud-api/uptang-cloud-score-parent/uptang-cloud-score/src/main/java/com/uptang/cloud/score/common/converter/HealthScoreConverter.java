package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.model.AcademicResume;
import com.uptang.cloud.score.common.vo.AcademicResumeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 9:25
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Mapper
public interface HealthScoreConverter {

    HealthScoreConverter INSTANCE = Mappers.getMapper(HealthScoreConverter.class);

    /**
     * 将附件实体转换成VO
     *
     * @param resume 履历表
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "semesterCode", source = "semesterCode.code")
    @Mapping(target = "semesterCodeText", source = "semesterCode.desc")
    @Mapping(target = "scoreType", source = "scoreType.code")
    @Mapping(target = "scoreTypeText", source = "scoreType.desc")
    @Mapping(target = "gender", source = "gender.code")
    @Mapping(target = "genderText", source = "gender.desc")
    AcademicResumeVO toVo(AcademicResume resume);

    /**
     * 将附件VO转换成实体
     *
     * @param resume 履历表
     * @return 转换后实体
     */
    @Mappings({
            @Mapping(target = "semesterCode", expression = "java(SemesterEnum.code(resume.getSemesterCode()))"),
            @Mapping(target = "scoreType", expression = "java(ScoreTypeEnum.code(resume.getScoreType()))"),
            @Mapping(target = "gender", expression = "java(GenderEnum.parse(resume.getGender()))"),
    })
    AcademicResume toModel(AcademicResumeVO resume);
}
