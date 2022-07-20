package com.toocol.common.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The response data of all actions with this annotation need to be encrypted
 *
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/7/20 0:32
 * @version: 0.0.1
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptResponse {

}
