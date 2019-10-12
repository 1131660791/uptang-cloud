package com.uptang.cloud.base.common.converter;

import com.uptang.cloud.base.common.model.Attachment;
import com.uptang.cloud.base.common.vo.AttachmentVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-30
 */
@Mapper
public interface AttachmentConverter {
    AttachmentConverter INSTANCE = Mappers.getMapper(AttachmentConverter.class);

    /**
     * 将附件实体转换成VO
     *
     * @param attachment 附件
     * @return 转换后的VO
     */
    @Mapping(target = "children", ignore = true)
    @Mapping(target = "typeCode", source = "type.code")
    @Mapping(target = "typeDesc", source = "type.desc")
    @Mapping(target = "stateCode", source = "state.code")
    @Mapping(target = "stateDesc", source = "state.desc")
    AttachmentVO toVo(Attachment attachment);

    /**
     * 将附件VO转换成实体
     *
     * @param attachment 附件
     * @return 转换后实体
     */
    Attachment toModel(AttachmentVO attachment);
}
