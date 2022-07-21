package com.toocol.common.events;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/7/21 0:52
 * @version: 0.0.1
 */
@Component
public class SyncEventDispatcher {

    protected static Map<Class<? extends SyncEvent>, List<IEventListener<?>>> listenerMap = null;

    public void dispatch(SyncEvent event) {
        if (listenerMap == null) {
            return;
        }
        Optional.ofNullable(listenerMap.get(event.getClass()))
                .ifPresent(eventListeners -> eventListeners.forEach(listener -> listener.handler(event.as())));
    }

}
