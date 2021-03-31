package com.toocol.server.common.utils;

/**
 * @author Joezeo
 * @date 2020/12/6 16:27
 */
public class CastUtil {
    /**
     * Automatically deduces type conversions, ignoring "unchecked" warnings
     *
     * @param obj
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        if (obj == null) {
            return null;
        }
        return (T)obj;
    }
}
