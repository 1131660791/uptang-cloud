package com.uptang.cloud.base.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 横拼:horizontally, 竖拼:vertically, 两者:both
 *
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-18
 */
@Getter
@AllArgsConstructor
public enum ImageCropModeEnum {
    /**
     * 横拼
     */
    HORIZONTALLY,

    /**
     * 竖拼
     */
    VERTICALLY,

    /**
     * 两者
     */
    BOTH;
}
