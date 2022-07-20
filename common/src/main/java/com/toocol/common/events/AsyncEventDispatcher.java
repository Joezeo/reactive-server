package com.toocol.common.events;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/7/21 0:02
 * @version: 0.0.1
 */
public class AsyncEventDispatcher extends AbstractActor {

    protected static Map<Class<? extends AsyncEvent>, List<AbstractEventListener<?>>> listenerMap = null;

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(AsyncEvent.class, this::dispatch)
                .build();
    }

    private void dispatch(AsyncEvent event) {
        if (listenerMap == null) {
            return;
        }
        Optional.ofNullable(listenerMap.get(event.getClass()))
                .ifPresent(eventListeners -> eventListeners.forEach(listener -> listener.handler(event.as())));
    }
}
