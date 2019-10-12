package com.uptang.cloud.pojo.domain.context;

import com.uptang.cloud.pojo.domain.user.UserContext;

/**
 * @author Jiang Chuan
 * @version 4.0.0
 * @date 2019-09-18
 */
public class UserContextThreadLocal {
    private static final InheritableThreadLocal<UserContext> THREAD_LOCAL = new InheritableThreadLocal<UserContext>() {
        @Override
        protected UserContext initialValue() {
            return UserContext.builder().build();
        }
    };

    /**
     * 为每个请求设置用户信息
     *
     * @return 用户信息
     */
    public static UserContext get() {
        return THREAD_LOCAL.get();
    }

    /**
     * 为每个请求设置用户信息
     */
    public static void set(UserContext userContext) {
        THREAD_LOCAL.set(userContext);
    }

    /**
     * 释放用户信息，防止内存泄露
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
