package org.quartz.datamaster.scheduler.job;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by magneto on 17-3-13.
 */
public class SchedulerService {
    private static Scheduler scheduler;

    private SchedulerService(){}

    public static Scheduler start() throws SchedulerException {
        scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }

    public static void accept(String taskInfo) throws SchedulerException {
        TaskBuilder taskBuilder = new TaskBuilder(taskInfo);
        taskBuilder.builer();
        scheduler.scheduleJob(taskBuilder.getJobDetail(), taskBuilder.getTrigger());
    }

    public static void accept(String taskInfo, Class<? extends Job> tClass) throws SchedulerException {
        TaskBuilder taskBuilder = new TaskBuilder(taskInfo, tClass);
        taskBuilder.builer();
        scheduler.scheduleJob(taskBuilder.getJobDetail(), taskBuilder.getTrigger());
    }

    public static void shutdown() throws SchedulerException {
        scheduler.shutdown();
    }
}
