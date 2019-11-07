package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.vo.ResumeJoinArchiveVO;
import com.uptang.cloud.score.dto.ResumeJoinArchiveDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-07 15:41
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Mapper
public interface ResumeJoinArchiveConverter {

    ResumeJoinArchiveConverter INSTANCE = Mappers.getMapper(ResumeJoinArchiveConverter.class);

    /**
     * 将附件实体转换成VO
     *
     * @param resumeJoinArchive 归档与履历
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "semesterCode", source = "semesterCode.code")
    @Mapping(target = "semesterCodeText", source = "semesterCode.desc")
    @Mapping(target = "scoreType", source = "scoreType.code")
    @Mapping(target = "scoreTypeText", source = "scoreType.desc")
    @Mapping(target = "gender", source = "gender.code")
    @Mapping(target = "genderText", source = "gender.desc")
    ResumeJoinArchiveVO toVo(ResumeJoinArchiveDTO resumeJoinArchive);

    /**
     * 将附件VO转换成实体
     *
     * @param resumeJoinArchive 归档与履历
     * @return 转换后实体
     */
    @Mappings({
            @Mapping(target = "semesterCode", expression = "java(SemesterEnum.code(resumeJoinArchive.getSemesterCode()))"),
            @Mapping(target = "scoreType", expression = "java(ScoreTypeEnum.code(resumeJoinArchive.getScoreType()))"),
            @Mapping(target = "gender", expression = "java(GenderEnum.parse(resumeJoinArchive.getGender()))"),
    })
    ResumeJoinArchiveDTO toModel(ResumeJoinArchiveVO resumeJoinArchive);
}
