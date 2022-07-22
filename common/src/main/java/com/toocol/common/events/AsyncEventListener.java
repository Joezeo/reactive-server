package com.toocol.common.events;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import com.toocol.common.akka.ActorTick;
import com.toocol.common.akka.IActorTicker;

/**
 * @author ZhaoZhe (joezane.cn@gmail.com)
 * @date 2022/7/21 19:56
 */
public abstract class AsyncEventListener<T extends AsyncEvent> extends AbstractActor implements IEventListener<T>, IActorTicker {

    private final Class<T> clazz;

    protected AsyncEventListener(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(clazz, this::handler)
                .match(ActorTick.class, actorTick -> tick())
                .build();
    }

    @Override
    public void tick() {

    }
}
