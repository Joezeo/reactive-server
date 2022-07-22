package com.toocol.common.web;

import com.toocol.common.utils.OsUtil;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.*;

/**
 * @author ZhaoZhe (joezane.cn@gmail.com)
 * @date 2022/7/20 10:44
 */
@Component
@SuppressWarnings("all")
public final class HttpActionExecutorService {
    /**
     * Assumed average CPU cost in second per task.
     */
    private static final double ASSUMED_CPU_COST = 0.1;
    /**
     * Assumed average IO cost in second per task.
     */
    private static final double ASSUMED_IO_COST = 0.3;
    /**
     * Assumed average time cost in second per task.
     */
    private static final double ASSUMED_AVERAGE_TASK_COST = ASSUMED_CPU_COST + ASSUMED_IO_COST;
    /**
     * On average, each core thread can have this number of tasks waiting for it to process in one second.
     */
    private static final double WAITING_TASK_PER_THREAD = 1 / ASSUMED_AVERAGE_TASK_COST;
    /**
     * Core thread number of this executor service.
     */
    private static final int CORE_POOL_SIZE = (int) (OsUtil.availableProcessors() * (1 + ASSUMED_IO_COST / ASSUMED_CPU_COST));
    /**
     * Cache thread keep alive time.
     */
    private static final int KEEP_ALIVE_TIME = 600000;
    /**
     * The time interval of rejected task put back in the executor service.
     */
    private static final int REJECT_RETRIVAL_TIME_INTERVAL = 100;
    /**
     * The size of waiting queue, queueSize = corePoolSize / taskCost * responseTime
     */
    private static final int WAITING_QUEUE_SEIZE = (int) (CORE_POOL_SIZE * WAITING_TASK_PER_THREAD);
    /**
     * The maximum thread number of this executor service.
     */
    private static final int MAXIMUM_POOL_SIZE = OsUtil.availableProcessors() * (OsUtil.THREADS_PER_PROCESSOR_NET40_64 - WAITING_QUEUE_SEIZE);

    /**
     * handler the task that be rejected by biz executorService.
     */
    private static final Queue<Runnable> rejectedTask = new ConcurrentLinkedDeque<>();
    private static final ThreadFactory rejectTaskFactory = new DefaultThreadFactory("rejected-task-pool", false);

    /**
     * the thread pool that handler the biz task
     */
    private static final ThreadFactory factory = new DefaultThreadFactory("http-action", false);
    private static final ExecutorService executorService = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(WAITING_QUEUE_SEIZE), factory, (runnable, executorService) -> {
        rejectedTask.offer(runnable);
    });

    static {
        rejectTaskFactory.newThread(() -> {
            while (true) {
                try {
                    while (!rejectedTask.isEmpty()) {
                        executorService.submit(rejectedTask.poll());
                    }
                    Thread.sleep(REJECT_RETRIVAL_TIME_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void submit(Runnable runnable) {
        executorService.submit(runnable);
    }
}
