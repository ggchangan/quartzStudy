package org.quartz.datamaster.scheduler;

import org.quartz.*;
import org.quartz.datamaster.common.Service;
import org.quartz.impl.StdSchedulerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.quartz.impl.matchers.GroupMatcher.groupEquals;

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

    public static List<Task> allTasks() throws SchedulerException {
        List<Task> allTasks = new ArrayList<>();
        for (String group: scheduler.getJobGroupNames()) {
            for (JobKey jobKey: scheduler.getJobKeys(groupEquals(group))) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger: triggers) {
                    Task task = new Task() {
                        @Override public String getTaskId() {
                            return jobKey.getName();
                        }

                        @Override public String getTaskGroup() {
                            return jobKey.getGroup();
                        }

                        @Override public String getTriggerId() {
                            return trigger.getKey().getName();
                        }

                        @Override public String getTriggerGroup() {
                            return trigger.getKey().getGroup();
                        }

                        @Override public Class<? extends Service> getTaskHander() {
                            return null;
                        }
                    };

                    allTasks.add(task);
                }
            }
        }

        return allTasks;
    }

    public static List<Task> runningTasks() throws SchedulerException {
        List<JobExecutionContext> currentJobCtx = scheduler.getCurrentlyExecutingJobs();
        List<Task> tasks = new ArrayList<>();

        for (JobExecutionContext jobCtx: currentJobCtx) {
            Task task = new Task() {
                @Override public String getTaskId() {
                    return jobCtx.getJobDetail().getKey().getName();
                }

                @Override public String getTaskGroup() {
                    return jobCtx.getJobDetail().getKey().getGroup();
                }

                @Override public String getTriggerId() {
                    return jobCtx.getTrigger().getKey().getName();
                }

                @Override public String getTriggerGroup() {
                    return jobCtx.getTrigger().getKey().getGroup();
                }

                @Override public Class<? extends Service> getTaskHander() {
                    return null;
                }
            };

            tasks.add(task);
        }

        return tasks;
    }

    public static boolean deleteJob(JobKey jobKey) throws SchedulerException {
        return scheduler.deleteJob(jobKey);
    }
}
