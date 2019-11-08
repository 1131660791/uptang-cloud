package com.uptang.cloud.score.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @Author : Lee
 * @CreateTime : 2017-06-10 18:32
 * @Mailto: webb.lee.cn@gmail.com / lmyboblee@gmail.com
 * @Summary : FIXME
 */
public class Http {

    /**
     * @Author : Lee
     * @CreateTime : 2017-05-10 19:38
     * @Mailto: webb.lee.cn@gmail.com / lmyboblee@gmail.com
     * @Summary : HttpServletRequest
     */
    public static class Request {
        public static Optional<HttpServletRequest> getCurrent() {
            return Optional
                    .ofNullable(RequestContextHolder.getRequestAttributes())
                    .filter(requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
                    .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
                    .map(ServletRequestAttributes::getRequest);
        }
    }

    /**
     * @author : lee
     * @createtime : 2017-05-10 19:38
     * @mailto: webb.lee.cn@gmail.com / lmyboblee@gmail.com
     * @summary : httpservletresponse
     */
    public static class Response {
        public static Optional<HttpServletResponse> getCurrent() {
            return Optional
                    .ofNullable(RequestContextHolder.getRequestAttributes())
                    .filter(servletWebRequest -> ServletWebRequest.class.isAssignableFrom(servletWebRequest.getClass()))
                    .map(requestAttributes -> ((ServletWebRequest) requestAttributes))
                    .map(ServletWebRequest::getResponse);
        }
    }

    /**
     * @author : lee
     * @createtime : 2017-05-10 19:38
     * @mailto: webb.lee.cn@gmail.com / lmyboblee@gmail.com
     * @summary : cookie
     */
    public static class Cookie {
        public static void create(String name, String value, Boolean secure, Integer maxAge, String domain) {
            javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie(name, value);
            cookie.setSecure(secure);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(maxAge);
            cookie.setDomain(domain);
            cookie.setPath("/");
            Response.getCurrent().get().addCookie(cookie);
        }

        public static void clear(String name) {
            javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie(name, null);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            cookie.setDomain("localhost");
            Response.getCurrent().get().addCookie(cookie);
        }

        public static String getValue(String name) {
            javax.servlet.http.Cookie cookie = WebUtils.getCookie(Request.getCurrent().get(), name);
            return cookie != null ? cookie.getValue() : null;
        }
    }
}