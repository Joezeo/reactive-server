package com.toocol.common.functional;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/7/20 1:52
 * @version: 0.0.1
 */
public interface OnceCheck {

    AtomicBoolean provideChecker();

    default boolean checkOnce() {
         return provideChecker().getAndSet(true);
    }

}
