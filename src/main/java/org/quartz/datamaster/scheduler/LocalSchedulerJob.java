package org.quartz.datamaster.scheduler;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.datamaster.common.Service;

/**
 * Created by magneto on 17-3-13.
 */
public class LocalSchedulerJob implements Job {
    private static final Logger logger = Logger.getLogger(LocalSchedulerJob.class);

    public LocalSchedulerJob(){}

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //任务被执行，需要转发相应任务信息
        String taskStr = context.getJobDetail().getJobDataMap().getString(TaskBuilder.EXECUTOR_KEY);
        try {
            Class<? extends Task> taskClass = (Class<? extends Task>) Class.forName(taskStr);
            Task task = taskClass.newInstance();
            String taskId = task.getTaskId();
            logger.info(String.format("调度任务:%s", taskId));

            Class<? extends Service> serviceClass = task.getTaskHander();
            Service service = serviceClass.newInstance();
            service.execute();

            /*
            Sender sender = new LocalSender(taskId);
            sender.send();
            */
            logger.info(String.format("任务：%s，运行完毕！", taskId));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
