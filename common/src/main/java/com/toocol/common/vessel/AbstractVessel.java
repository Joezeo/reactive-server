package com.toocol.common.vessel;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * this abstract vessel is provided for product side.
 *
 * @author Joezeo
 * @date 2021/7/31 23:41
 */
@Slf4j
public abstract class AbstractVessel implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    public static AbstractVessel get() {
        return applicationContext.getBean(AbstractVessel.class);
    }

    public void init() {
        Class<?> superClass = this.getClass();
        List<Field> fields = new ArrayList<>();
        do {
            fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
            superClass = superClass.getSuperclass();
        } while (superClass != null);

        fields.forEach(field -> {
            try {
                if (field.getType().equals(ApplicationContext.class)) {
                    return;
                }
                if (field.getType().equals(Logger.class)) {
                    return;
                }

                field.setAccessible(true);
                field.set(this, applicationContext.getBean(field.getType()));
            } catch (BeansException e) {
                log.warn("Bean: {} not exist, skip", field.getType().getName());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                log.error("inject field:{} into vessel failed, stop the server.", field.getName());
                System.exit(-1);
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AbstractVessel.applicationContext = applicationContext;
    }

}
