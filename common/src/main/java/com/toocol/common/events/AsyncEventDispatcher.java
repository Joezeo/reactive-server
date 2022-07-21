package com.toocol.common.events;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.toocol.common.akka.CreateActor;
import com.toocol.common.utils.ClassScanner;

import java.util.*;

/**
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/7/21 0:02
 * @version: 0.0.1
 */
public class AsyncEventDispatcher extends AbstractActor {

    protected static final Map<Class<? extends AsyncEvent>, List<ActorRef>> listenerMap = new HashMap<>();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(CreateActor.class, create -> createAsyncListeners())
                .match(AsyncEvent.class, this::dispatch)
                .build();
    }

    private void createAsyncListeners() {
        new ClassScanner("com.toocol", clazz -> Optional.ofNullable(clazz.getSuperclass()).map(clz -> clz.equals(AsyncEventListener.class)).orElse(false))
                .scan()
                .forEach(listenerClass -> {
                    AsyncEventListen annotation = listenerClass.getAnnotation(AsyncEventListen.class);
                    if (annotation == null) {
                        return;
                    }
                    listenerMap.compute(annotation.listen(), (k, v) -> {
                        if (v == null) {
                            v = new ArrayList<>();
                        }
                        v.add(getContext().actorOf(Props.create(AsyncEventListener.class)));
                        return v;
                    });
                });
    }

    private void dispatch(AsyncEvent event) {
        Optional.ofNullable(listenerMap.get(event.getClass()))
                .ifPresent(eventListeners -> eventListeners.forEach(listener -> listener.tell(event.as(), ActorRef.noSender())));
    }
}
