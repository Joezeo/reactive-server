package com.toocol.common;

import com.toocol.common.vessel.AbstractVessel;
import com.toocol.common.vessel.DefaultVessel;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;

/**
 * @author Joezeo
 * @date 2021/8/1 0:28
 */
@AllArgsConstructor
public abstract class BasisInitAfter implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        DefaultVessel vessel = AbstractVessel.get().as();
        vessel.routerInitializer.initialize();
        vessel.actorInitializer.initialize();
        vessel.eventSystemInitializer.initialize();
        init();
    }

    /**
     * sub project's initialize process
     */
    public abstract void init();
}
