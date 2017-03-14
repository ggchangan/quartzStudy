package org.quartz.examples.datamaster.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.examples.datamaster.common.JobInfo;
import org.quartz.examples.datamaster.scheduler.CleaningJob;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by magneto on 17-3-13.
 */
public class JobController {
    public static Map<JobInfo.JobType, Class <? extends org.quartz.Job> > jobHandlers;
    public static DefaultHandler DEFAULT_HANDLER = new DefaultHandler();

    static {
        jobHandlers = new HashMap<>();
        jobHandlers.put(JobInfo.JobType.CLEAN, CleaningJob.class);
    }

    public static Class<? extends org.quartz.Job> getHandler(JobInfo.JobType jobType) {
        return jobHandlers.containsKey(jobType)? jobHandlers.get(jobType): DEFAULT_HANDLER.getClass();
    }

    static class DefaultHandler implements org.quartz.Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {

        }
    }
}
