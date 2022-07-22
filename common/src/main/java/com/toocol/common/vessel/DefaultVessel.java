package com.toocol.common.vessel;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.toocol.common.akka.ActorInitializer;
import com.toocol.common.events.AsyncEventDispatcher;
import com.toocol.common.events.EventPublisher;
import com.toocol.common.events.EventSystemInitializer;
import com.toocol.common.events.SyncEventDispatcher;
import com.toocol.common.web.HttpActionRouter;
import com.toocol.common.web.HttpActionRouterInitializer;
import org.springframework.stereotype.Component;

/**
 * every subproject should have its own Vessel and annotated {@link org.springframework.context.annotation.Primary},
 * if it does not, we just use this DefaultVessel
 *
 * @author Joezeo
 * @date 2021/7/31 23:47
 */
@Component
public class DefaultVessel extends AbstractVessel {

    /**
     * the akka base actor system, created by {@link com.toocol.common.akka.AkkaSystemConfig}.
     */
    public ActorSystem actorSystem;

    /**
     * the initializer of actors in akka system.
     */
    public ActorInitializer actorInitializer;

    /**
     * the initializer of {@link HttpActionRouter}
     */
    public HttpActionRouterInitializer routerInitializer;

    /**
     * the initializer of async/sync event system.
     */
    public EventSystemInitializer eventSystemInitializer;

    /**
     * the actor ref of {@link com.toocol.common.akka.ActorTickScheduler}
     */
    public ActorRef actorTickScheduler;

    /**
     * the actor ref of {@link AsyncEventDispatcher}
     */
    public ActorRef asyncEventDispatcherRef;

    /**
     * the dispatcher of sync event.
     */
    public SyncEventDispatcher syncEventDispatcher;

    /**
     * publish the async/sync event.
     */
    public EventPublisher eventPublisher;

}
