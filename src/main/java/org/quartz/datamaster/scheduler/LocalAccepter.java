package org.quartz.datamaster.scheduler;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.SchedulerException;

/**
 * Created by magneto on 17-3-15.
 */
public class LocalAccepter<T extends Task> implements Accepter<T> {
    //TODO 对于接收异常地处理
    private static final Logger logger = Logger.getLogger(LocalAccepter.class);
    private Class<? extends Job> executor;

    @Override public void accept(T job) {
        try {
            if (executor == null) executor = SchedulerJob.class;
            SchedulerService.accept(job, executor);
            logger.info(String.format("接收到任务:%s.", job));
        } catch (SchedulerException e) {
            logger.warn("接收任务失败！", e);
        }
    }

    public void setExecutor(Class<? extends Job> executor) {
        this.executor = executor;
    }
}
