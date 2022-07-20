package com.toocol.common.exceptions;

import lombok.Getter;

/**
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/7/20 0:02
 * @version: 0.0.1
 */
@Getter
public class LogicException extends RuntimeException{
    private final int code;

    public LogicException(int code, String message) {
        super(message);
        this.code = code;
    }
}
