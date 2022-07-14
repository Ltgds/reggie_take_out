package com.item.reggie.common;

/**
 * @author Li Guotng
 * @create 2022-07-09 21:13
 * 基于ThreadLocal封装工具类,用于保存和获取当前登录用户id
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
