package com.toocol.common.web;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Optional;


/**
 * @author Joezeo
 * @date 2020/11/28 22:58
 */
@RequestMapping("/service")
public abstract class BaseServerController implements IController {

    /**
     * execute http request
     *
     * @param request
     * @return
     */
    public abstract String execute(@RequestBody JSONObject request);

    /**
     * check the param
     * @param request
     * @return true:all exist   false:param miss
     */
    public Boolean paramCheck(JSONObject request) {
        return Optional.ofNullable(this.getClass().getAnnotation(ParamCheck.class))
                .filter(annotation -> Arrays.stream(annotation.params()).allMatch(request::containsKey))
                .isPresent();
    }
}
