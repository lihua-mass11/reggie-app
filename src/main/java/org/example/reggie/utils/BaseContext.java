package org.example.reggie.utils;

import io.swagger.annotations.Api;

/**
 * @author 唐三
 * description: 同步线程获取用户上下文
 */
public class BaseContext {

    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static Long getCurrentId() {
        return threadLocal.get();
    }

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }
}
