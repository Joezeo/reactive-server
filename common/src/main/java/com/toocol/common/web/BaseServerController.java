package com.toocol.common.web;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


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
    public abstract String execute(HttpServletRequest request);
}
