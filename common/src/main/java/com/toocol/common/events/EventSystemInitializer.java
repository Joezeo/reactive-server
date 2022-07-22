package com.toocol.common.events;

import akka.actor.ActorRef;
import com.toocol.common.akka.ActorCreate;
import com.toocol.common.functional.OnceCheck;
import com.toocol.common.vessel.AbstractVessel;
import com.toocol.common.vessel.DefaultVessel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/7/21 0:47
 * @version: 0.0.1
 */
@Slf4j
@Component
public class EventSystemInitializer implements OnceCheck {
    private static final AtomicBoolean checker = new AtomicBoolean();

    public void initialize() {
        if (checkOnce()) {
            log.info("EventSystemInitializer can only run once.");
            return;
        }

        DefaultVessel vessel = AbstractVessel.get().as();
        if (vessel.asyncEventDispatcherRef != null) {
            vessel.asyncEventDispatcherRef.tell(ActorCreate.of, ActorRef.noSender());
        }
    }

    @Override
    public AtomicBoolean provideChecker() {
        return checker;
    }
}
