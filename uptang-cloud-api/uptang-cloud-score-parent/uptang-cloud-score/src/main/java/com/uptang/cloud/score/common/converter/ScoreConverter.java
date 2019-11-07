package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.model.Score;
import com.uptang.cloud.score.common.vo.ScoreVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 13:48
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Mapper
public interface ScoreConverter {

    ScoreConverter INSTANCE = Mappers.getMapper(ScoreConverter.class);

    /**
     * 将附件实体转换成VO
     *
     * @param score 学业成绩
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "subject", source = "subject.code")
    @Mapping(target = "subjectText", source = "subject.desc")
    ScoreVO toVo(Score score);

    /**
     * 将附件VO转换成实体
     *
     * @param score 学业成绩
     * @return 转换后实体
     */
    Score toModel(ScoreVO score);

}
