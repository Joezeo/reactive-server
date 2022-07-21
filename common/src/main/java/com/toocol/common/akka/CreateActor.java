package com.toocol.common.akka;

/**
 * @author ZhaoZhe (joezane.cn@gmail.com)
 * @date 2022/7/21 20:27
 */
public final class CreateActor {
    private static final CreateActor INSTANCE = new CreateActor();

    public static CreateActor of() {
        return INSTANCE;
    }

    private CreateActor() {

    }

}
