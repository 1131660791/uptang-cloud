package com.uptang.cloud.score.common.converter;

import com.uptang.cloud.score.common.model.ObjectionRecord;
import com.uptang.cloud.score.common.vo.ObjectionRecordVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author : Lee.m.yin
 * @createtime : 2019-11-13 18:45
 * @mailto: webb.lee.cn@gmail.com lmy@uptong.com.cn
 * @summary : FIXME
 */
@Mapper
public interface ObjectionRecordConverter {


    ObjectionRecordConverter INSTANCE = Mappers.getMapper(ObjectionRecordConverter.class);

    /**
     * 将附件实体转换成VO
     *
     * @param resume 履历表
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    ObjectionRecordVO toVo(ObjectionRecord resume);

    /**
     * 将附件VO转换成实体
     *
     * @param resume 履历表
     * @return 转换后实体
     */
    ObjectionRecord toModel(ObjectionRecordVO resume);
}
