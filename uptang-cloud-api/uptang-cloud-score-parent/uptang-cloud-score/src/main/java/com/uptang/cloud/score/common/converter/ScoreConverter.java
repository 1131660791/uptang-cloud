package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.model.Score;
import com.uptang.cloud.score.common.vo.ScoreVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-11-05
 */
@Mapper
public interface ScoreConverter {
    ScoreConverter INSTANCE = Mappers.getMapper(ScoreConverter.class);

    /**
     * 将附件实体转换成VO
     *
     * @param score 成绩
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "levelCode", source = "level.code")
    @Mapping(target = "levelDesc", source = "level.desc")
    ScoreVO toVo(Score score);

    /**
     * 将附件VO转换成实体
     *
     * @param score 成绩
     * @return 转换后实体
     */
    Score toModel(ScoreVO score);
}
