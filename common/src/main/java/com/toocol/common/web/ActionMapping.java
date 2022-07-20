package com.toocol.common.web;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The corresponded uri is formatted as "module/action"
 *
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/3/12 16:12
 * @version: 0.0.1
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionMapping {
    /**
     * @return the module value
     */
    String module();

    /**
     * @return the action value
     */
    String action();
}
