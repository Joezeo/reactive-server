package com.toocol.common.functional;

/**
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/7/20 1:40
 * @version: 0.0.1
 */
public interface Asable {

    /**
     * Automatically deduces type conversions, ignoring "unchecked" warnings
     *
     * @param <T>
     * @return
     */
    @SuppressWarnings("all")
    default <T> T as() {
        return (T) this;
    }

}
