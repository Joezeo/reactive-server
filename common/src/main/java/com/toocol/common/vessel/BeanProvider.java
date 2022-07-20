package com.toocol.common.vessel;

import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/3/12 20:42
 * @version: 0.0.1
 */
public class BeanProvider {

    private static final ApplicationContext context = AbstractVessel.applicationContext;

    public static <R> R getBean(String beanName, Class<R> clz) {
        R bean;
        try {
            bean = context.getBean(clz, beanName);
        } catch (Exception e) {
            return null;
        }
        return bean;
    }

    public static <R> R getBean(Class<R> clz) {
        R bean;
        try {
            bean = context.getBean(clz);
        } catch (Exception e) {
            return null;
        }
        return bean;
    }

    public static <R> Map<String, R> getBeans(Class<R> clz) {
        Map<String, R> map;
        try {
            map = context.getBeansOfType(clz);
        } catch (Exception e) {
            return null;
        }
        return map;
    }

}
