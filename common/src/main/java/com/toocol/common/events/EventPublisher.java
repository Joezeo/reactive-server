package com.toocol.common.events;

import akka.actor.ActorRef;
import com.toocol.common.vessel.AbstractVessel;
import com.toocol.common.vessel.DefaultVessel;
import org.springframework.stereotype.Component;

/**
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/7/21 0:37
 * @version: 0.0.1
 */
@Component
public class EventPublisher {

    public void publish(AbstractEvent event) {
        DefaultVessel vessel = AbstractVessel.get().as();
        if (event instanceof SyncEvent) {
            vessel.syncEventDispatcher.dispatch(event.as());
        } else if (event instanceof AsyncEvent) {
            vessel.asyncEventDispatcherRef.tell(event, ActorRef.noSender());
        }
    }

}
