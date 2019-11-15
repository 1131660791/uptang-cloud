package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.vo.ShowScoreVO;
import com.uptang.cloud.score.dto.ShowScoreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-15 20:10
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Mapper
public interface ShowScoreConverter {

    ShowScoreConverter INSTANCE = Mappers.getMapper(ShowScoreConverter.class);

    /**
     * 将附件实体转换成VO
     *
     * @param score
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "scoreNumber", expression = "java(com.uptang.cloud.score.common.util.Calculator.dev10(score.getScoreNumber()))")
    ShowScoreVO toVo(ShowScoreDTO score);


    /**
     * 将附件VO转换成实体
     *
     * @param score
     * @return 转换后实体
     */
    @Mapping(target = "scoreNumber", expression = "java(com.uptang.cloud.score.common.util.Calculator.defaultNumberScore(score.getScoreNumber()))")
    ShowScoreDTO toModel(ShowScoreVO score);
}
