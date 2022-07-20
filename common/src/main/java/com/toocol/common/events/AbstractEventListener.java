package com.toocol.common.events;

/**
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/7/21 0:26
 * @version: 0.0.1
 */
public abstract class AbstractEventListener<E extends AbstractEvent> {

    private final Class<? extends AbstractEvent> clazz;

    protected AbstractEventListener(Class<? extends AbstractEvent> clazz) {
        this.clazz = clazz;
    }

    public abstract void handler(E event);

}
