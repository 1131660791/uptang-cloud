package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.vo.HealthScoreVO;
import com.uptang.cloud.score.dto.HealthScoreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
     * @param healthScore 履历表
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "gender", source = "gender.code")
    @Mapping(target = "height", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getHeight()))")
    @Mapping(target = "bodyWeight", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getBodyWeight()))")
    @Mapping(target = "vitalCapacity", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getVitalCapacity()))")
    @Mapping(target = "vitalCapacityScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getVitalCapacityScore()))")
    @Mapping(target = "fiftyMetersRun", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getFiftyMetersRun()))")
    @Mapping(target = "fiftyMetersScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getFiftyMetersScore()))")
    @Mapping(target = "standingLongJump", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getStandingLongJump()))")
    @Mapping(target = "standingLongJumpScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getStandingLongJumpScore()))")
    @Mapping(target = "flexion", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getFlexion()))")
    @Mapping(target = "flexionScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getFlexionScore()))")
    @Mapping(target = "eightHundredMetersScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getEightHundredMetersScore()))")
    @Mapping(target = "additionalPoints800", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getAdditionalPoints800()))")
    @Mapping(target = "oneKilometerMetersScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getOneKilometerMetersScore()))")
    @Mapping(target = "additionalPoints1000", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getAdditionalPoints1000()))")
    @Mapping(target = "sitUpScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getSitUpScore()))")
    @Mapping(target = "sitUpAdditionalPoints", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getSitUpAdditionalPoints()))")
    @Mapping(target = "pullUpScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getPullUpScore()))")
    @Mapping(target = "pullUpAdditionalPoints", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getPullUpAdditionalPoints()))")
    @Mapping(target = "standardScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getStandardScore()))")
    @Mapping(target = "additionalPoints", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getAdditionalPoints()))")
    @Mapping(target = "totalScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(healthScore.getTotalScore()))")
    @Mapping(target = "bodyWeightLevel", source = "bodyWeightLevel.code")
    @Mapping(target = "vitalCapacityLevel", source = "vitalCapacityLevel.desc")
    @Mapping(target = "fiftyMetersLevel", source = "fiftyMetersLevel.desc")
    @Mapping(target = "standingLongJumpLevel", source = "standingLongJumpLevel.desc")
    @Mapping(target = "flexionLevel", source = "flexionLevel.desc")
    @Mapping(target = "eightHundredMetersLevel", source = "eightHundredMetersLevel.desc")
    @Mapping(target = "oneKilometerMetersLevel", source = "oneKilometerMetersLevel.desc")
    @Mapping(target = "sitUpLevel", source = "sitUpLevel.desc")
    @Mapping(target = "pullUpLevel", source = "pullUpLevel.desc")
    HealthScoreVO toVo(HealthScoreDTO healthScore);

    /**
     * 将附件VO转换成实体
     *
     * @param healthScore 履历表
     * @return 转换后实体
     */
    @Mapping(target = "gender", expression = "java(GenderEnum.parse(healthScore.getGender()))")
    @Mapping(target = "height", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getHeight()))")
    @Mapping(target = "bodyWeight", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getBodyWeight()))")
    @Mapping(target = "vitalCapacity", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getVitalCapacity()))")
    @Mapping(target = "vitalCapacityScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getVitalCapacityScore()))")
    @Mapping(target = "fiftyMetersRun", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getFiftyMetersRun()))")
    @Mapping(target = "fiftyMetersScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getFiftyMetersScore()))")
    @Mapping(target = "standingLongJump", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getStandingLongJump()))")
    @Mapping(target = "standingLongJumpScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getStandingLongJumpScore()))")
    @Mapping(target = "flexion", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getFlexion()))")
    @Mapping(target = "flexionScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getFlexionScore()))")
    @Mapping(target = "eightHundredMetersScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getEightHundredMetersScore()))")
    @Mapping(target = "additionalPoints800", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getAdditionalPoints800()))")
    @Mapping(target = "oneKilometerMetersScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getOneKilometerMetersScore()))")
    @Mapping(target = "additionalPoints1000", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getAdditionalPoints1000()))")
    @Mapping(target = "sitUpScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getSitUpScore()))")
    @Mapping(target = "sitUpAdditionalPoints", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getSitUpAdditionalPoints()))")
    @Mapping(target = "pullUpScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getPullUpScore()))")
    @Mapping(target = "pullUpAdditionalPoints", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getPullUpAdditionalPoints()))")
    @Mapping(target = "standardScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getStandardScore()))")
    @Mapping(target = "additionalPoints", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getAdditionalPoints()))")
    @Mapping(target = "totalScore", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(healthScore.getTotalScore()))")
    @Mapping(target = "bodyWeightLevel", expression = "java(LevelEnum.text(healthScore.getBodyWeightLevel()))")
    @Mapping(target = "vitalCapacityLevel", expression = "java(LevelEnum.text(healthScore.getVitalCapacityLevel()))")
    @Mapping(target = "fiftyMetersLevel", expression = "java(LevelEnum.text(healthScore.getFiftyMetersLevel()))")
    @Mapping(target = "standingLongJumpLevel", expression = "java(LevelEnum.text(healthScore.getStandingLongJumpLevel()))")
    @Mapping(target = "flexionLevel", expression = "java(LevelEnum.text(healthScore.getFlexionLevel()))")
    @Mapping(target = "eightHundredMetersLevel", expression = "java(LevelEnum.text(healthScore.getEightHundredMetersLevel()))")
    @Mapping(target = "oneKilometerMetersLevel", expression = "java(LevelEnum.text(healthScore.getOneKilometerMetersLevel()))")
    @Mapping(target = "sitUpLevel", expression = "java(LevelEnum.text(healthScore.getSitUpLevel()))")
    @Mapping(target = "pullUpLevel", expression = "java(LevelEnum.text(healthScore.getPullUpLevel()))")
    HealthScoreDTO toModel(HealthScoreVO healthScore);
}
