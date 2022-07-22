package com.toocol.common.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/7/23 0:22
 * @version: 0.0.1
 */
public class ActorTickScheduler extends AbstractActor {

    private final List<ActorRef> tickList = new ArrayList<>();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(ActorAddTicker.class, actorAddTicker -> addTicker(actorAddTicker.ticker))
                .match(ActorTick.class, tick -> tick())
                .build();
    }

    protected void addTicker(ActorRef ticker) {
        tickList.add(ticker);
    }

    private void tick() {
        tickList.forEach(actorRef -> actorRef.tell(ActorTick.of, ActorRef.noSender()));
    }

}
