package com.toocol.common.events;

/**
 * @author ZhaoZhe (joezane.cn@gmail.com)
 * @date 2022/7/21 19:55
 */
public abstract class SyncEventListener<T extends SyncEvent> implements IEventListener<T> {

    public final Class<T> clazz;

    protected SyncEventListener(Class<T> clazz) {
        this.clazz = clazz;
    }

}
