package org.quartz.datamaster.scheduler;

/**
 * Created by magneto on 17-3-20.
 */
public interface TaskManager {
    boolean start(Task task);
    boolean stop(Task task);
    boolean delete(Task task);
}
