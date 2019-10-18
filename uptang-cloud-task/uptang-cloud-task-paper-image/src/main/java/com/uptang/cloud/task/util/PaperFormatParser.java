package com.uptang.cloud.task.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uptang.cloud.base.common.domain.PaperImageFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-10-10
 */
@Slf4j
public final class PaperFormatParser {
    private static final String FORMAT_ATTRIBUTE_NAME = "dot";

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
     * @deprecated 老格式解析
     */
    @Deprecated
    public static PaperImageFormat parse2(String json) {
        if (StringUtils.isBlank(json) || json.indexOf("area") <= 0) {
            log.warn("格式文件({})不正确!", json);
            return null;
        }

        try {
            JSONObject rootObject = JSON.parseObject(json);
            JSONObject areaObject = rootObject.getJSONObject("area");
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
     * 解析试卷的格式文件
     * <pre>
     * {
     *   "num": "2",  #题号
     *   "dot": {
     *     "x": 2211,
     *     "y": 213,
     *     "w": 992,  #宽
     *     "h": 1981, #高
     *     "card_idx": 1, #答题卡序号, 1:第一张，N:第N张
     *     "front": 1     #正反面，1:正面， 0:反面
     *   }
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
            JSONObject dotObject = rootObject.getJSONObject(FORMAT_ATTRIBUTE_NAME);
            return PaperImageFormat.builder()
                    .itemNum(rootObject.getString("num"))
                    .x(dotObject.getIntValue("x"))
                    .y(dotObject.getIntValue("y"))
                    .width(dotObject.getIntValue("w"))
                    .height(dotObject.getIntValue("h"))
                    .page(Math.max(1, Optional.ofNullable(dotObject.getInteger("card_idx")).orElse(1)))
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
    public static void updateFilenameSuffix(Collection<PaperImageFormat> formats, String... suffixes) {
        if (CollectionUtils.isEmpty(formats) || ArrayUtils.isEmpty(suffixes)) {
            return;
        }
        final int maxIndex = suffixes.length - 1;
        formats.forEach(format -> format.setFilenameSuffix(suffixes[Math.min(maxIndex, format.getPage() - 1)]));
    }
}
