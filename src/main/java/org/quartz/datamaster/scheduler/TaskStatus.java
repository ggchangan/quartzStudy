package org.quartz.datamaster.scheduler;

import java.util.List;

/**
 * Created by magneto on 17-3-20.
 */
public interface TaskStatus {
    List<Task> allTasks();
    List<Task> runningTasks();
}
