package org.quartz.datamaster.scheduler;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by magneto on 17-3-13.
 */
public class SchedulerJob implements Job {
    private static final Logger logger = Logger.getLogger(SchedulerJob.class);
    private Sender sender;

    public SchedulerJob(){}

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //任务被执行，需要转发相应任务信息
        String executor = context.getJobDetail().getJobDataMap().getString(TaskBuilder.EXECUTOR_KEY);
        logger.info(executor);
        String senderClass = context.getJobDetail().getJobDataMap().getString(TaskBuilder.SENDER_KEY);
        sender.send();
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }
}
