package com.toocol.common.functional;

/**
 * @author Joezeo
 * @date 2020/12/6 16:27
 */
public interface Castable {
    /**
     * Automatically deduces type conversions, ignoring "unchecked" warnings
     *
     * @param obj the object need to cast
     * @param <T> generic type
     * @return casted result
     */
    @SuppressWarnings("all")
    default <T> T cast(Object obj) {
        if (obj == null) {
            return null;
        }
        return (T) obj;
    }
}
