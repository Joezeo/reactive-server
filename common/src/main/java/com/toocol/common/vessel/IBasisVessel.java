package com.toocol.common.vessel;

import org.springframework.context.ApplicationContext;

/**
 * to put and manage the spring injected object.
 * this interface is provided for the consume side.
 *
 * @author Joezeo
 * @date 2021/7/31 23:39
 */
public interface IBasisVessel<T extends AbstractVessel> {

    /**
     * get the vessel object
     *
     * @return
     */
    T vessel();

    /**
     * get the spring's ApplicationContext
     *
     * @return
     */
    default ApplicationContext applicationContext() {
        return AbstractVessel.applicationContext;
    }
}
