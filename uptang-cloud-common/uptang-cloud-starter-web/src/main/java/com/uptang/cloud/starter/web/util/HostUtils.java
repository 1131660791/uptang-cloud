package com.uptang.cloud.starter.web.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
@Slf4j
public class HostUtils extends com.uptang.cloud.core.util.HostUtils {
    private static final String[] IP_HEADER_NAMES = {
            "X-Real-IP",
            "X-Forward-For",
            "x-forward-for",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP"};

    /**
     * 获取本机 IP.
     *
     * @return 本机IP, 如果异常则返回 null
     */
    public static String getLocalIp() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (Exception ex) {
            log.error("Can not get ip of local machine", ex);
            return null;
        }
    }

    /**
     * 获取客户端IP.
     *
     * @param request HTTP Request
     * @return 客户端IP
     */
    public static String getRequestIp(HttpServletRequest request) {
        String unknown = "unknown", ip = "";
        for (String header : IP_HEADER_NAMES) {
            ip = request.getHeader(header);
            if (StringUtils.isNotBlank(ip) && !StringUtils.equalsIgnoreCase(unknown, ip)) {
                log.info("根据请求头:{}, 获取到IP:{}", header, ip);
                break;
            }
        }

        if (StringUtils.isBlank(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip)) {
                ip = getLocalIp();
            }
        }

        if (StringUtils.isNotBlank(ip) && ip.length() > 15 && ip.indexOf(',') > -1) {
            ip = ip.substring(0, ip.indexOf(','));
        }

        return ip;
    }
}
