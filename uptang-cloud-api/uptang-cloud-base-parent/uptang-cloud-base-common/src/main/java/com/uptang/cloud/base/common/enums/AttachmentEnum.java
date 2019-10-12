package com.uptang.cloud.base.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.uptang.cloud.pojo.enums.IEnumType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-30
 */
@Getter
@AllArgsConstructor
public enum AttachmentEnum implements IEnumType {
    /**
     * 用户头像
     */
    USER_AVATAR(11, "用户头像"),

    /**
     * 考试试卷
     */
    EXAM_PAPER(21, "考试试卷"),

    /**
     * 其它
     */
    OTHER(99, "其它");

    private final static Map<Integer, AttachmentEnum> BY_CODE_MAP =
            Arrays.stream(AttachmentEnum.values())
                    .collect(Collectors.toMap(AttachmentEnum::getCode, type -> type));

    private final static Map<String, AttachmentEnum> BY_NAME_MAP
            = Arrays.stream(AttachmentEnum.values())
            .collect(Collectors.toMap(type -> type.name().toLowerCase(), type -> type));

    @EnumValue
    private final int code;
    private final String desc;

    /**
     * 将代码转成枚举
     *
     * @param code 代码
     * @return 转换出来的附件类型
     */
    public static AttachmentEnum parse(Integer code) {
        return BY_CODE_MAP.get(code);
    }

    /**
     * 将名字转成枚举
     *
     * @param name 名字
     * @return 转换出来的附件类型
     */
    public static AttachmentEnum parse(String name) {
        if (StringUtils.isBlank(name)) {
            return OTHER;
        }
        return BY_NAME_MAP.getOrDefault(name.trim().toLowerCase(), OTHER);
    }
}
