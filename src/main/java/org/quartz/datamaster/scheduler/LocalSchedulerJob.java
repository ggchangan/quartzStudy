package org.quartz.datamaster.scheduler;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by magneto on 17-3-13.
 */
public class LocalSchedulerJob implements Job {
    private static final Logger logger = Logger.getLogger(LocalSchedulerJob.class);

    public LocalSchedulerJob(){}

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //任务被执行，需要转发相应任务信息
        String taskId= context.getJobDetail().getJobDataMap().getString(TaskBuilder.EXECUTOR_KEY);
        logger.info(String.format("执行任务:%s", taskId));
        Sender sender = new LocalSender(taskId);
        sender.send();
    }
}
