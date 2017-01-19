package org.rss.shedule;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTaskProcessor implements ApplicationListener<ContextClosedEvent> {

    @Autowired
private ThreadPoolTaskScheduler scheduler;
    @Autowired
private ThreadPoolTaskExecutor executor;


    public void onApplicationEvent(ContextClosedEvent event) {
        shutdownExecutorService(scheduler.getScheduledExecutor());
        shutdownExecutorService(executor.getThreadPoolExecutor());
    }

    private void shutdownExecutorService(ExecutorService executorService) {
    executorService.shutdown();
    try {
    if (!executorService.awaitTermination(10L, TimeUnit.SECONDS)) {
    executorService.shutdownNow();
    }
    } catch (InterruptedException e) {
        e.printStackTrace();
        executorService.shutdownNow();
    }
     }

}
