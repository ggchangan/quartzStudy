package org.quartz.datamaster.scheduler.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.datamaster.scheduler.TaskBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by magneto on 17-3-14.
 */
public class SchedulerJobMock implements Job {
    private static final Logger logger = Logger.getLogger(SchedulerJobMock.class);
    private static List<String> jobs = new ArrayList<>();

    @Override public void execute(JobExecutionContext context) throws JobExecutionException {
        //任务被执行，需要转发相应任务信息
        String executor = context.getJobDetail().getJobDataMap().getString(TaskBuilder.EXECUTOR_KEY);
        logger.info(String.format("%s............", executor));
        jobs.add(executor);
    }

    public static List<String> getJobs() {
        return jobs;
    }
}
