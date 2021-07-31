package com.toocol.common.akka;

import akka.actor.ActorSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Joezeo
 * @date 2021/8/1 0:26
 */
@Configuration
public class AkkaSystemConfig {

    @Bean("actorSystem")
    public ActorSystem actorSystem() {
        return ActorSystem.apply("basis-actor-system");
    }

}
