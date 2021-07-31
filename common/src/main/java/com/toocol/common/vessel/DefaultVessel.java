package com.toocol.common.vessel;

import akka.actor.ActorSystem;
import org.springframework.stereotype.Component;

/**
 * every son server should has its own Vessel and annotated {@link org.springframework.context.annotation.Primary},
 * if it doesnt, we just use this DefaultVessel
 *
 * @author Joezeo
 * @date 2021/7/31 23:47
 */
@Component
public class DefaultVessel extends AbstractVessel{

    public ActorSystem actorSystem;

}
