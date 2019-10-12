package com.uptang.cloud.task.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uptang.cloud.base.common.domain.PaperImageFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-10
 */
@Slf4j
public final class PaperFormatParser {
    private static final String FORMAT_ATTRIBUTE_NAME = "area";

    /**
     * 解析试卷的格式文件
     * <pre>
     * {
     *   "pic_num": "24",
     *   "area": {
     *     "guid": "7ff921f0-c90c-c636-b179-83cb36511a01",
     *     "x1": 1541,
     *     "di": 2,
     *     "y1": 1773,
     *     "y": 641,
     *     "x": 111,
     *     "pi": "1a"
     *   },
     *   "item_name": "24",
     *   "choice_flag": true,
     *   "subject_code": "",
     *   "item_num": "24",
     *   "choice_num": "24"
     * }
     * </pre>
     *
     * @param json 格式文件
     * @return 解析后的格式文件
     */
    public static PaperImageFormat parse(String json) {
        if (StringUtils.isBlank(json) || json.indexOf(FORMAT_ATTRIBUTE_NAME) <= 0) {
            log.warn("格式文件({})不正确!", json);
            return null;
        }

        try {
            JSONObject rootObject = JSON.parseObject(json);
            JSONObject areaObject = rootObject.getJSONObject(FORMAT_ATTRIBUTE_NAME);
            int x = areaObject.getIntValue("x");
            int y = areaObject.getIntValue("y");

            return PaperImageFormat.builder()
                    .x(x).y(y)
                    .itemNum(rootObject.getString("item_num"))
                    .width(areaObject.getIntValue("x1") - x)
                    .height(areaObject.getIntValue("y1") - y)
                    .page(Optional.ofNullable(areaObject.getInteger("di")).orElse(1))
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return null;
    }

    /**
     * 设置试卷图片路径的后缀名
     *
     * @param formats  解析后的格式文件
     * @param suffixes 文件后缀字符
     */
    public static void updateFilenameSuffix(List<PaperImageFormat> formats, String... suffixes) {
        if (CollectionUtils.isEmpty(formats) || ArrayUtils.isEmpty(suffixes)) {
            return;
        }
        final int maxIndex = suffixes.length - 1;
        formats.forEach(format -> format.setFilenameSuffix(suffixes[Math.min(maxIndex, format.getPage() - 1)]));
    }
}
