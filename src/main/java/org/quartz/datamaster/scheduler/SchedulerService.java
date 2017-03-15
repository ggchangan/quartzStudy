package org.quartz.datamaster.scheduler;

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

    public static void accept(Task task) throws SchedulerException {
        TaskBuilder taskBuilder = new TaskBuilder(task);
        taskBuilder.builer();
        scheduler.scheduleJob(taskBuilder.getJobDetail(), taskBuilder.getTrigger());
    }

    public static void accept(Task task, Class<? extends Job> tClass) throws SchedulerException {
        TaskBuilder taskBuilder = new TaskBuilder(task, tClass);
        taskBuilder.builer();
        scheduler.scheduleJob(taskBuilder.getJobDetail(), taskBuilder.getTrigger());
    }

    public static void shutdown() throws SchedulerException {
        scheduler.shutdown();
    }
}
