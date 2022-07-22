package com.toocol.common.web;

import com.alibaba.fastjson.JSONObject;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

/**
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/3/12 15:05
 * @version: 0.0.1
 */
public abstract class AbstractHttpAction<R> {

    /**
     * @param param    the request param
     * @param consumer the consumer of subscriber
     */
    void doAction(JSONObject param, Consumer<R> consumer) throws Exception {
        action(param).subscribe(consumer);
    }

    /**
     * @param param the request param
     * @return mono result
     * @throws Exception exception
     */
    protected abstract Mono<R> action(JSONObject param) throws Exception;

}
