package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.model.Archive;
import com.uptang.cloud.score.common.vo.ArchiveVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-06 13:50
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Mapper
public interface ArchiveConverter {


    ArchiveConverter INSTANCE = Mappers.getMapper(ArchiveConverter.class);

    /**
     * 将附件实体转换成VO
     *
     * @param archive 归档
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "type", source = "type.code")
    @Mapping(target = "typeText", source = "type.desc")
    ArchiveVO toVo(Archive archive);

    /**
     * 将附件VO转换成实体
     *
     * @param archive 归档
     * @return 转换后实体
     */
    Archive toModel(ArchiveVO archive);
}
