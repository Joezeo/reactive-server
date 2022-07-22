package com.toocol.common.events;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Each synchronization listener needs to add this annotation.
 *
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/7/22 1:04
 * @version: 0.0.1
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SyncEventListen {

    /**
     * The execution weight of the listener. The larger the value, the earlier the execution.
     */
    int weight() default 0;

}
