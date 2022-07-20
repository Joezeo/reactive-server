package com.toocol.common.utils;

/**
 * @author ：JoeZane (joezane.cn@gmail.com)
 * @date: 2022/7/19 23:30
 * @version: 0.0.1
 */
public class OsUtil {

    public static final int THREADS_PER_PROCESSOR_NET40_64 = 32768;
    public static final int THREADS_PER_PROCESSOR_NET40_32 = 1023;
    public static final int THREADS_PER_PROCESSOR_NET30 = 250;
    public static final int THREADS_PER_PROCESSOR_NET20 = 23;

    public static int availableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

}
