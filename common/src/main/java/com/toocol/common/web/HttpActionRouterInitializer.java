package com.toocol.common.web;

import com.google.common.collect.ImmutableMap;
import com.toocol.common.functional.OnceCheck;
import com.toocol.common.utils.StringUtils;
import com.toocol.common.vessel.BeanProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.toocol.common.constants.StringConstants.SLASH;

/**
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/3/12 16:11
 * @version: 0.0.1
 */
@Slf4j
@Component
public class HttpActionRouterInitializer implements OnceCheck {
    private static final AtomicBoolean checker = new AtomicBoolean();

    public void initialize() {
        if (checkOnce()) {
            log.info("HttpActionRouter can only check once.");
            return;
        }

        HashMap<String, AbstractHttpAction<?>> uriActionMap = new HashMap<>();
        HashMap<String, String[]> paramCheckMap = new HashMap<>();

        Optional.ofNullable(BeanProvider.getBeans(AbstractHttpAction.class)).ifPresent(map -> {
            map.values().forEach(action -> {
                ActionMapping actionMapping = action.getClass().getAnnotation(ActionMapping.class);
                if (actionMapping == null) {
                    log.warn("Action register skipped, please add annotation @ActionHandler, action = {}", action.getClass().getName());
                    return;
                }

                String uri = StringUtils.reviseUri(actionMapping.module() + SLASH + actionMapping.action());
                uriActionMap.compute(uri, (k, v) -> {
                    if (v != null) {
                        log.warn("Repeated uri = {} between {} and {}, skip action = {}",
                                uri, v.getClass().getName(), action.getClass().getName(), action.getClass().getName());
                        return v;
                    }
                    log.info("Action register success, uri = {}, action = {}", uri, action.getClass().getName());
                    return action;
                });

                ParamCheck paramCheck = action.getClass().getAnnotation(ParamCheck.class);
                if (paramCheck == null || paramCheck.params() == null || paramCheck.params().length == 0) {
                    return;
                }
                paramCheckMap.compute(uri, (k, v) -> {
                    if (v != null) {
                        log.warn("Repeated uri = {} between {} and {}, skip action = {}",
                                uri, v.getClass().getName(), action.getClass().getName(), action.getClass().getName());
                        return v;
                    }
                    log.info("Action's params-check register success, uri = {}, action = {}", uri, action.getClass().getName());
                    return paramCheck.params();
                });
            });

            HttpActionRouter.uriActionMap = ImmutableMap.copyOf(uriActionMap);
            HttpActionRouter.uriParamChecksMap = ImmutableMap.copyOf(paramCheckMap);
        });
    }

    @Override
    public AtomicBoolean provideChecker() {
        return checker;
    }
}
