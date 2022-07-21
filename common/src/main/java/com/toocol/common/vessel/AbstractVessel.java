package com.toocol.common.vessel;

import akka.actor.ActorRef;
import com.toocol.common.functional.Asable;
import com.toocol.common.functional.OnceCheck;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * this abstract vessel is provided for product side.
 *
 * @author Joezeo
 * @date 2021/7/31 23:41
 */
@Slf4j
public abstract class AbstractVessel implements ApplicationContextAware, Asable, OnceCheck {
    private static final AtomicBoolean checker = new AtomicBoolean();
    private static final Set<Class<?>> ignoreInject = new HashSet<Class<?>>() {
        {
            add(ApplicationContext.class);
            add(ActorRef.class);
            add(AtomicBoolean.class);
            add(Logger.class);
            add(Set.class);
        }
    };

    protected static ApplicationContext applicationContext;

    public static AbstractVessel get() {
        return applicationContext.getBean(AbstractVessel.class);
    }

    @PostConstruct
    public void init() {
        if (checkOnce()) {
            return;
        }
        Class<?> superClass = this.getClass();
        List<Field> fields = new ArrayList<>();
        do {
            fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
            superClass = superClass.getSuperclass();
        } while (superClass != null);

        fields.forEach(field -> {
            try {
                if (ignoreInject.contains(field.getType())) {
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
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        AbstractVessel.applicationContext = applicationContext;
    }

    @Override
    public AtomicBoolean provideChecker() {
        return checker;
    }
}
