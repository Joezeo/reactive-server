package com.toocol.common.system;

import lombok.AllArgsConstructor;

/**
 * @author Joezeo
 * @date 2021/5/3 18:16
 */
@AllArgsConstructor
public enum ErrorCode {
    /**
     * 传来的请求必要参数不全
     */
    NECESSARY_PARAM_IS_MISSING(1001, "传来的请求必要参数不全")
    ;

    public int code;

    public String desc;
}
