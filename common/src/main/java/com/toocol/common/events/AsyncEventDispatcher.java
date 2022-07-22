package com.toocol.common.events;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.google.common.collect.ImmutableMap;
import com.toocol.common.akka.ActorAddTicker;
import com.toocol.common.akka.ActorCreate;
import com.toocol.common.utils.ClassScanner;
import com.toocol.common.vessel.AbstractVessel;
import com.toocol.common.vessel.DefaultVessel;

import java.util.*;

/**
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/7/21 0:02
 * @version: 0.0.1
 */
public class AsyncEventDispatcher extends AbstractActor {
    public static final int ACTOR_COUNT_OF_DISPATCHER = 20;

    private static Map<Class<? extends AsyncEvent>, List<ActorRef>> listenerMap;

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(ActorCreate.class, create -> createAsyncListeners())
                .match(AsyncEvent.class, this::dispatch)
                .build();
    }

    private void createAsyncListeners() {
        Map<Class<? extends AsyncEvent>, List<ActorRef>> map = new HashMap<>();
        DefaultVessel vessel = AbstractVessel.get().as();
        new ClassScanner("com.toocol", clazz -> Optional.ofNullable(clazz.getSuperclass()).map(clz -> clz.equals(AsyncEventListener.class)).orElse(false))
                .scan()
                .forEach(listenerClass -> {
                    AsyncEventListen annotation = listenerClass.getAnnotation(AsyncEventListen.class);
                    if (annotation == null) {
                        return;
                    }
                    map.compute(annotation.value(), (k, v) -> {
                        if (v == null) {
                            v = new ArrayList<>();
                        }
                        ActorRef ref = getContext().actorOf(Props.create(listenerClass), listenerClass.getSimpleName());
                        v.add(ref);
                        vessel.actorTickScheduler.tell(new ActorAddTicker(ref), ActorRef.noSender());
                        return v;
                    });
                });
        listenerMap = ImmutableMap.copyOf(map);
    }

    private void dispatch(AsyncEvent event) {
        Optional.ofNullable(listenerMap.get(event.getClass()))
                .ifPresent(eventListeners -> eventListeners.forEach(listener -> listener.tell(event.as(), ActorRef.noSender())));
    }

}
