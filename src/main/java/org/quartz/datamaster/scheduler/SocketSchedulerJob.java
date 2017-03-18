package org.quartz.datamaster.scheduler;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by magneto on 17-3-13.
 */
public class SocketSchedulerJob implements Job {
    private static final Logger logger = Logger.getLogger(SocketSchedulerJob.class);

    public SocketSchedulerJob(){}

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //任务被执行，需要转发相应任务信息
        String executor = context.getJobDetail().getJobDataMap().getString(TaskBuilder.EXECUTOR_KEY);
        logger.info(executor);
        Sender sender = new SocketSender();
        sender.send();
    }
}
