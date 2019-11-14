package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.vo.ImportFromExcelVo;
import com.uptang.cloud.score.dto.RequestParameter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-11 11:39
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @Summary : FIXME
 */
@Mapper
public interface ImportFromExcelConverter {

    ImportFromExcelConverter INSTANCE = Mappers.getMapper(ImportFromExcelConverter.class);

    /**
     * 将附件实体转换成VO
     *
     * @param excel 公示数据
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    ImportFromExcelVo toVo(RequestParameter excel);

    /**
     * 将附件VO转换成实体
     *
     * @param excel 公示数据
     * @return 转换后实体
     */
    RequestParameter toModel(ImportFromExcelVo excel);
}
