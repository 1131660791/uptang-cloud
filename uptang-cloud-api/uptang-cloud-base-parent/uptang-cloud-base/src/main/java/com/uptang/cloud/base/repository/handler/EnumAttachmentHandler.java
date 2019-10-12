package com.uptang.cloud.base.repository.handler;

import com.uptang.cloud.base.common.enums.AttachmentEnum;
import com.uptang.cloud.starter.data.mybaits.handler.BaseObjectHandler;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-30
 */
public class EnumAttachmentHandler extends BaseObjectHandler<AttachmentEnum> {
    @Override
    public int getCode(AttachmentEnum enumeration) {
        return enumeration.getCode();
    }

    @Override
    public AttachmentEnum getObject(int code) {
        return AttachmentEnum.parse(code);
    }
}
