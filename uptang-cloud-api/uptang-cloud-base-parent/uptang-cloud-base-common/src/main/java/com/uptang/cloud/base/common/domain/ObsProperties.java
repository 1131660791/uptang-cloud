package com.uptang.cloud.base.common.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-30
 */
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "obs")
public class ObsProperties {
    /**
     * 华为云配置
     */
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String endPoint;

    /**
     * 自定义域名
     */
    private String domain;

    /**
     * 图片裁切/拼接模式:  横拼:horizontally, 竖拼:vertically, 两者:both
     */
    private String cropMode;
}