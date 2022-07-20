package com.toocol.common.exceptions;

/**
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/7/20 0:01
 * @version: 0.0.1
 */
public interface ICode {

    int getCode();

    String message();

    default void throwIf(boolean judge) {
        if (judge) {
            throw new LogicException(getCode(), message());
        }
    }

    default <T> void requireNonNull(T val) {
        if (val == null) {
            throw new LogicException(getCode(), message());
        }
    }
}
