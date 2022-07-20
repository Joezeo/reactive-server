package com.toocol.common.akka;

import akka.actor.Props;
import akka.routing.SmallestMailboxPool;
import com.toocol.common.events.AsyncEventDispatcher;
import com.toocol.common.functional.OnceCheck;
import com.toocol.common.vessel.AbstractVessel;
import com.toocol.common.vessel.DefaultVessel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

        vessel.asyncEventDispatcherRef = vessel.actorSystem.actorOf(
                new SmallestMailboxPool(Runtime.getRuntime().availableProcessors())
                        .props(Props.create(AsyncEventDispatcher.class)),
                "async-event-system"
        );
        log.info("Create the async event system.");
    }

    @Override
    public AtomicBoolean provideChecker() {
        return checker;
    }
}
