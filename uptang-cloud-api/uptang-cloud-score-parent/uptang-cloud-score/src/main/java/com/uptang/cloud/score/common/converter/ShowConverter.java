package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.model.Show;
import com.uptang.cloud.score.common.vo.ShowVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 16:06
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Mapper
public interface ShowConverter {


    ShowConverter INSTANCE = Mappers.getMapper(ShowConverter.class);

    /**
     * 将附件实体转换成VO
     *
     * @param show 公示数据
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "showStat", source = "showStat.code")
    @Mapping(target = "showStatText", source = "showStat.desc")
    @Mapping(target = "objection", source = "objection.code")
    @Mapping(target = "objectionText", source = "objection.desc")
    @Mapping(target = "archive", source = "archive.code")
    @Mapping(target = "archiveText", source = "archive.desc")
    @Mapping(target = "semesterCode", source = "semesterCode.code")
    @Mapping(target = "semesterCodeText", source = "semesterCode.desc")
    @Mapping(target = "scoreType", source = "scoreType.code")
    @Mapping(target = "scoreTypeText", source = "scoreType.desc")
    @Mapping(target = "gender", source = "gender.code")
    @Mapping(target = "genderText", source = "gender.desc")
    ShowVO toVo(Show show);

    /**
     * 将附件VO转换成实体
     *
     * @param show 公示数据
     * @return 转换后实体
     */
    @Mappings({
            @Mapping(target = "archive", expression = "java(ArchiveEnum.code(show.getArchive()))"),
            @Mapping(target = "showStat", expression = "java(ShowStatEnum.code(show.getShowStat()))"),
            @Mapping(target = "objection", expression = "java(ObjectionEnum.code(show.getObjection()))"),
            @Mapping(target = "semesterCode", expression = "java(SemesterEnum.code(show.getSemesterCode()))"),
            @Mapping(target = "scoreType", expression = "java(ScoreTypeEnum.code(show.getScoreType()))"),
            @Mapping(target = "gender", expression = "java(GenderEnum.parse(show.getGender()))"),
    })
    Show toModel(ShowVO show);
}