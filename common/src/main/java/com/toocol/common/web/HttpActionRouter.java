package com.toocol.common.web;

import com.alibaba.fastjson.JSONObject;
import com.toocol.common.exceptions.LogicException;
import com.toocol.common.exceptions.SystemCode;
import com.toocol.common.utils.IpUtils;
import com.toocol.common.utils.StringUtils;
import com.toocol.common.utils.TimeRecord;
import com.toocol.common.vessel.BeanProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

import static com.toocol.common.constants.StringConstants.SLASH;

/**
 * To receive all the patterned web requests and reroute them to corresponded action handler.
 *
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/3/12 13:15
 * @version: 0.0.1
 */
@Slf4j
@RestController
public final class HttpActionRouter {
    /**
     * Long time operation detection time.
     */
    private static final int LONG_TIME_EXECUTION_DETECT = 2000;

    static Map<String, AbstractHttpAction<?>> uriActionMap = null;
    static Map<String, String[]> uriParamChecksMap = null;

    private final HttpActionExecutorService executorService;

    public HttpActionRouter(HttpActionExecutorService executorService) {
        this.executorService = executorService;
    }

    @PostMapping(
            value = "/service/{module}/{action}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<String> post(
            @PathVariable String module, @PathVariable String action,
            @RequestBody JSONObject param, ServerHttpRequest request
    ) {
        HttpRequestResult.RequestResultBuilder builder = HttpRequestResult.builder();

        if (uriActionMap == null) {
            return Mono.just(builder.code(SystemCode.SERVER_UNKNOWN_ERROR.getCode()).message("Server error.").toString());
        }
        if (StringUtils.isEmpty(module) || StringUtils.isEmpty(action)) {
            return Mono.just(builder.code(SystemCode.ILLEGAL_REQUEST_ADDRESS.getCode()).message("Illegal request.").toString());
        }
        String uri = StringUtils.reviseUri(module + SLASH + action);
        if (!uriActionMap.containsKey(uri)) {
            return Mono.just(builder.code(SystemCode.ILLEGAL_REQUEST_ADDRESS.getCode()).message("Illegal request.").toString());
        }

        return Mono.create(monoSink -> {
            Runnable runnable = () -> {
                try {
                    final AbstractHttpAction<?> abstractHttpAction = uriActionMap.get(uri);
                    SystemCode.SERVER_UNKNOWN_ERROR.requireNonNull(abstractHttpAction);
                    TimeRecord timeRecord = new TimeRecord(abstractHttpAction.getClass()).start();

                    param.put("ip", IpUtils.ipAddr(request));

                    abstractHttpAction.doAction(param, result -> {
                        if (timeRecord.stop().timeConsume() > LONG_TIME_EXECUTION_DETECT) {
                            log.warn("long-time action execution detected, {}", timeRecord);
                        }

                        EncryptResponse encrypt = abstractHttpAction.getClass().getAnnotation(EncryptResponse.class);
                        if (encrypt == null) {
                            monoSink.success(builder.code(200).success(true).data(result).toString());
                            return;
                        }

                        IResponseEncryptor encryptor = BeanProvider.getBean(IResponseEncryptor.class);
                        if (encryptor == null) {
                            log.warn("Enable response data encrypt should implement IResponseEncryptor and add it to spring context.");
                            monoSink.success(builder.code(200).success(true).data(result).toString());
                            return;
                        }

                        monoSink.success(builder.code(200).success(true).encode(true).data(encryptor.encrypt(request)).toString());
                    });

                } catch (LogicException e) {
                    monoSink.success(builder.code(e.getCode()).message(e.getMessage()).toString());
                } catch (Exception e) {
                    monoSink.success(builder.code(SystemCode.SERVER_UNKNOWN_ERROR.getCode()).message("Server error.").toString());
                }
            };
            executorService.submit(runnable);
        });
    }
}
