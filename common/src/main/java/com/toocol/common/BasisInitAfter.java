package com.toocol.common;

import com.toocol.common.utils.SpringGetter;
import com.toocol.common.vessel.AbstractVessel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Joezeo
 * @date 2021/8/1 0:28
 */
@Component
public class BasisInitAfter implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        SpringGetter.getBean(AbstractVessel.class).ifPresent(AbstractVessel::init);
    }
}
