package org.quartz.datamaster.scheduler;

import org.quartz.datamaster.client.CleaningJobService;
import org.quartz.datamaster.common.Service;

/**
 * Created by magneto on 17-3-15.
 */
public class LocalSender implements Sender{
    private String taskId;

    public LocalSender(String taskId) {
        this.taskId = taskId;
    }

    @Override public void send() {
        logger.info(String.format("开始局部发送任务:%s", taskId));
        Service service = new CleaningJobService();
        service.execute();
    }
}
