package org.quartz.datamaster.scheduler;

import org.quartz.datamaster.common.Service;

/**
 * Created by magneto on 17-3-15.
 * 为了和Quartz中的job相区分，客户端提供的每个任务实例，被成为Task
 */
public interface Task{
    String getTaskId();
    String getTaskGroup();
    String getTriggerId();
    String getTriggerGroup();
    Class<? extends Service> getTaskHander();
}
