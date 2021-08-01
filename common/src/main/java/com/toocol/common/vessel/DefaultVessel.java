package com.toocol.common.vessel;

import akka.actor.ActorSystem;
import org.springframework.stereotype.Component;

/**
 * every sub server should has its own Vessel and annotated {@link org.springframework.context.annotation.Primary},
 * if it doesnt, we just use this DefaultVessel
 *
 * @author Joezeo
 * @date 2021/7/31 23:47
 */
@Component
public class DefaultVessel extends AbstractVessel{

    /**
     * this field default is null,
     * if the sub project need use akka system, please use annotation {@link org.springframework.context.annotation.Import} to
     * import the {@link com.toocol.common.akka.AkkaSystemConfig} manual.
     */
    public ActorSystem actorSystem;

}
