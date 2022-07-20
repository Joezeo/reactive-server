package com.toocol.common.utils;

/**
 * @author ZhaoZhe (joezane.cn@gmail.com)
 * @date 2022/7/20 11:33
 */
public class TimeRecord {
    private final Class<?> clazz;

    private long startTime;

    private long endTime;

    public TimeRecord(Class<?> clazz) {
        this.clazz = clazz;
    }

    public TimeRecord start() {
        this.startTime = System.currentTimeMillis();
        return this;
    }

    public TimeRecord stop() {
        this.endTime = System.currentTimeMillis();
        return this;
    }

    public long timeConsume() {
        return this.endTime - this.startTime;
    }

    @Override
    public String toString() {
        return clazz.getName() + "execute time: " + timeConsume() + "ms";
    }
}
