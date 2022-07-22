package com.toocol.common.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.routing.SmallestMailboxPool;
import com.toocol.common.events.AsyncEventDispatcher;
import com.toocol.common.functional.OnceCheck;
import com.toocol.common.vessel.AbstractVessel;
import com.toocol.common.vessel.DefaultVessel;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/7/20 1:47
 * @version: 0.0.1
 */
@Slf4j
@Component
public class ActorInitializer implements OnceCheck {

    private static final AtomicBoolean checker = new AtomicBoolean();

    public void initialize() {
        if (checkOnce()) {
            log.info("ActorInitializer can only check once.");
            return;
        }
        DefaultVessel vessel = AbstractVessel.get().as();
        if (vessel.actorSystem == null) {
            return;
        }

        vessel.actorTickScheduler = vessel.actorSystem.actorOf(Props.create(ActorTickScheduler.class), "actor-tick-scheduler");
        log.info("Create the actor tick scheduler success.");

        vessel.asyncEventDispatcherRef = vessel.actorSystem.actorOf(
                new SmallestMailboxPool(AsyncEventDispatcher.ACTOR_COUNT_OF_DISPATCHER).props(Props.create(AsyncEventDispatcher.class)),
                "async-event-system"
        );
        log.info("Create the async event system success.");

        ThreadFactory factory = new DefaultThreadFactory("actor-tick", false);
        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(8), factory);
        vessel.actorSystem.scheduler()
                .schedule(FiniteDuration.apply(10, TimeUnit.SECONDS), FiniteDuration.apply(1, TimeUnit.SECONDS),
                        vessel.actorTickScheduler, ActorTick.of, ExecutionContext.fromExecutor(executorService), ActorRef.noSender());
        log.info("Initialize the actor tick scheduler success.");
    }

    @Override
    public AtomicBoolean provideChecker() {
        return checker;
    }
}
