package com.toocol.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author JoeZane
 */
@Component
public class SpringGetter implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static <T> Optional<T> getBean(Class<T> type) {
        if (applicationContext == null) {
            return Optional.empty();
        }
        return Optional.of(applicationContext.getBean(type));
    }

    public static <T> Optional<T> getBean(String name, Class<T> type) {
        if (applicationContext == null) {
            return Optional.empty();
        }
        return Optional.of(applicationContext.getBean(name, type));
    }
}
