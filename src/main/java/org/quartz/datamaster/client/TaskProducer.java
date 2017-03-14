package org.quartz.datamaster.client;

import org.apache.log4j.Logger;
import org.quartz.datamaster.common.JobInfo;

/**
 * Created by magneto on 17-3-13.
 */
public class TaskProducer {
    private static final Logger logger = Logger.getLogger(TaskProducer.class);

    public static void main(String[] args) {
        String msg = JobInfo.SchedulerType.SIMPLE.toString();
        System.out.println(msg);
        try {
            boolean toScheduler = new Sender(msg).start();
        } catch (Exception e) {
            String warnMsg = String.format("发送任务服务失败，请查看配置！");
            logger.warn(warnMsg);
            logger.warn(e.getMessage());
        }
        //发送一次,要求服务其有响应

        try {
            new Receiver().start();
        } catch (Exception e) {
            String warnMsg = String.format("接收任务服务失败，请查看配置！");
            logger.warn(warnMsg);
            logger.warn(e.getMessage());
        }
    }
}
