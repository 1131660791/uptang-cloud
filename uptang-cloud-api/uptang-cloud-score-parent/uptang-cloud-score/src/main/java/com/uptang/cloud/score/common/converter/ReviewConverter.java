package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.model.Review;
import com.uptang.cloud.score.common.vo.ReviewVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 13:53
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Mapper
public interface ReviewConverter {


    ReviewConverter INSTANCE = Mappers.getMapper(ReviewConverter.class);

    /**
     * 将附件实体转换成VO
     *
     * @param review 审议进度
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "type", source = "type.code")
    @Mapping(target = "typeText", source = "type.desc")
    @Mapping(target = "showStat", source = "showStat.code")
    @Mapping(target = "showStatText", source = "showStat.desc")
    @Mapping(target = "objection", source = "objection.code")
    @Mapping(target = "objectionText", source = "objection.desc")
    @Mapping(target = "archive", source = "archive.code")
    @Mapping(target = "archiveText", source = "archive.desc")
    ReviewVO toVo(Review review);

    /**
     * 将附件VO转换成实体
     *
     * @param review 审议进度
     * @return 转换后实体
     */
    Review toModel(ReviewVO review);
}
