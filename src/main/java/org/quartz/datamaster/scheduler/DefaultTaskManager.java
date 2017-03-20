package org.quartz.datamaster.scheduler;

import org.quartz.JobKey;
import org.quartz.SchedulerException;

/**
 * Created by magneto on 17-3-20.
 */
public class DefaultTaskManager implements TaskManager {
    @Override public boolean start(Task task) {
        return false;
    }

    @Override public boolean stop(Task task) {
        return false;
    }

    @Override public boolean delete(Task task) {
        try {
            return SchedulerService.deleteJob(JobKey.jobKey(task.getTaskId(), task.getTaskGroup()));
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
