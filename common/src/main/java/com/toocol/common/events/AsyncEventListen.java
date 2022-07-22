package com.toocol.common.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Each asynchronous listener needs to add this annotation.
 *
 * @author ZhaoZhe (joezane.cn@gmail.com)
 * @date 2022/7/21 20:37
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AsyncEventListen {

    Class<? extends AsyncEvent> value();

}
