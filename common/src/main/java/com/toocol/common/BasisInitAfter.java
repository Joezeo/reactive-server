package com.toocol.common;

import com.toocol.common.vessel.AbstractVessel;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;

/**
 * @author Joezeo
 * @date 2021/8/1 0:28
 */
@AllArgsConstructor
public abstract class BasisInitAfter implements CommandLineRunner {

    public final AbstractVessel vessel;

    @Override
    public void run(String... args) throws Exception {
        vessel.init();
        init();
    }

    /**
     * initial the server
     */
    public abstract void init();
}
