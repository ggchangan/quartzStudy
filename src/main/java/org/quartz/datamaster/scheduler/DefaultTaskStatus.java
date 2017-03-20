package org.quartz.datamaster.scheduler;

import org.quartz.SchedulerException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by magneto on 17-3-20.
 */
public class DefaultTaskStatus implements TaskStatus {
    @Override public List<Task> allTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            tasks = SchedulerService.allTasks();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    @Override public List<Task> runningTasks() {
        List<Task> runningTasks = new ArrayList<>();
        try {
            runningTasks = SchedulerService.runningTasks();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return runningTasks;
    }
}
