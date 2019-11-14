package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.vo.ArchiveScoreVO;
import com.uptang.cloud.score.dto.ArchiveScoreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-13 17:13
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Mapper
public interface ArchiveScoreConverter {

    ArchiveScoreConverter INSTANCE = Mappers.getMapper(ArchiveScoreConverter.class);

    /**
     * 将附件实体转换成VO
     *
     * @param resume 履历表
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    ArchiveScoreVO toVo(ArchiveScoreDTO resume);

    /**
     * 将附件VO转换成实体
     *
     * @param resume 履历表
     * @return 转换后实体
     */
    ArchiveScoreDTO toModel(ArchiveScoreVO resume);
}
