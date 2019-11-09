package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.vo.ArtScoreVO;
import com.uptang.cloud.score.dto.ArtScoreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-08 10:29
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Mapper
public interface ArtScoreConverter {

    ArtScoreConverter INSTANCE = Mappers.getMapper(ArtScoreConverter.class);

    /**
     * 将附件实体转换成VO
     *
     * @param artScore 履历表
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "score", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(artScore.getScore()))")
    ArtScoreVO toVo(ArtScoreDTO artScore);


    /**
     * 将附件VO转换成实体
     *
     * @param artScore 履历表
     * @return 转换后实体
     */
    @Mapping(target = "score", expression = "java(com.uptang.cloud.score.common.util.Calculator.x10(artScore.getScore()))")
    ArtScoreDTO toModel(ArtScoreVO artScore);
}
