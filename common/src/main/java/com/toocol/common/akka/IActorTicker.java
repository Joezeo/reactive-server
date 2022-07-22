package com.toocol.common.akka;

/**
 * Perform some tasks periodically(per second).
 * Only support the actor in akka system.
 *
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/7/22 22:51
 * @version: 0.0.1
 */
public interface IActorTicker {

    void tick();

}
