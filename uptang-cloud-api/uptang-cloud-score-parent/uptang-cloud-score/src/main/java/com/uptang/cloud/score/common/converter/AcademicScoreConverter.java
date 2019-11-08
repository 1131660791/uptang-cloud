package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.vo.AcademicScoreVO;
import com.uptang.cloud.score.dto.AcademicScoreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 9:47
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Mapper
public interface AcademicScoreConverter {

    AcademicScoreConverter INSTANCE = Mappers.getMapper(AcademicScoreConverter.class);

    /**
     * 将附件实体转换成VO
     *
     * @param academicScore 履历表
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "technology", expression = "java(academicScore.getTechnology().name())")
    @Mapping(target = "music", source = "music.desc")
    @Mapping(target = "art", source = "art.desc")
    @Mapping(target = "physicalExperiment", source = "physicalExperiment.desc")
    @Mapping(target = "chemistryExperiment", source = "chemistryExperiment.desc")
    @Mapping(target = "biologicalExperiments", source = "biologicalExperiments.desc")
    @Mapping(target = "labor", source = "labor.desc")
    @Mapping(target = "localCourse", source = "localCourse.desc")
    AcademicScoreVO toVo(AcademicScoreDTO academicScore);


    /**
     * 将附件VO转换成实体
     *
     * @param academicScore 履历表
     * @return 转换后实体
     */
    @Mappings({
            @Mapping(target = "technology", expression = "java(com.uptang.cloud.score.common.enums.InformationTechnologyEnum.level(academicScore.getTechnology()))"),
            @Mapping(target = "music", expression = "java(LevelEnum.text(academicScore.getMusic()))"),
            @Mapping(target = "art", expression = "java(LevelEnum.text(academicScore.getArt()))"),
            @Mapping(target = "physicalExperiment", expression = "java(LevelEnum.text(academicScore.getPhysicalExperiment()))"),
            @Mapping(target = "chemistryExperiment", expression = "java(LevelEnum.text(academicScore.getChemistryExperiment()))"),
            @Mapping(target = "biologicalExperiments", expression = "java(LevelEnum.text(academicScore.getBiologicalExperiments()))"),
            @Mapping(target = "labor", expression = "java(LevelEnum.text(academicScore.getLabor()))"),
            @Mapping(target = "localCourse", expression = "java(LevelEnum.text(academicScore.getLocalCourse()))")
    })
    AcademicScoreDTO toModel(AcademicScoreVO academicScore);
}
