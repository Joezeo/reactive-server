package com.toocol.common.events;

/**
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/7/21 0:26
 * @version: 0.0.1
 */
interface IEventListener<E extends AbstractEvent> {

    void handler(E event);

}
