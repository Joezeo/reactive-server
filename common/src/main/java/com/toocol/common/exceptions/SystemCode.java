package com.toocol.common.exceptions;

import lombok.AllArgsConstructor;

/**
 * @author Joezeo
 * @date 2021/5/3 18:16
 */
@AllArgsConstructor
public enum SystemCode implements ICode {
    /**
     * An unknown error occurred on the server
     */
    SERVER_UNKNOWN_ERROR(5001, "服务器发生未捕获异常", "System error."),
    /**
     * The required parameters of the request are incomplete
     */
    NECESSARY_PARAM_IS_MISSING(5002, "传来的请求必要参数不全", "Param missing."),
    /**
     * Illegal request address
     */
    ILLEGAL_REQUEST_ADDRESS(5003, "非法的请求", "Param missing."),
    ;

    public final int code;

    public final String desc;

    public final String message;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
