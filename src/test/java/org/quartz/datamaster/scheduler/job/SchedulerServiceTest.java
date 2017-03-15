package org.quartz.datamaster.scheduler.job;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.quartz.datamaster.common.Service;
import org.quartz.datamaster.scheduler.LocalAccepter;
import org.quartz.datamaster.scheduler.SchedulerService;
import org.quartz.datamaster.scheduler.Task;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by magneto on 17-3-14.
 */
public class SchedulerServiceTest {
    private static final Logger logger = Logger.getLogger(SchedulerServiceTest.class);

    @org.junit.Before public void setUp() throws Exception {
        SchedulerService.start();
    }

    @org.junit.After public void tearDown() throws Exception {
        SchedulerService.shutdown();
    }

    @Test
    public void schedulerTest() throws Exception {
        //测试调度顺序
        List<Task> tasks = new ArrayList<>();
        Task task1 = new TaskMock();
        Task task2 = new TaskMock();
        Task task3 = new TaskMock();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        LocalAccepter accepter = new LocalAccepter();
        accepter.setExecutor(SchedulerJobMock.class);
        for (Task task: tasks) {
            accepter.accept(task);
        }

        //保证被调度
        Thread.sleep(10000);

        Set<String> jobIds = new HashSet<>(SchedulerJobMock.getJobs());
        assertEquals(3, jobIds.size());
        jobIds.contains(task1.getTaskId());
        jobIds.contains(task2.getTaskId());
        jobIds.contains(task3.getTaskId());

        //并不能保证顺序调度
    }

    @Test
    public void localSenderTest() throws Exception {
        Task task = new TaskMock();
        LocalAccepter accepter = new LocalAccepter();
        accepter.accept(task);
        //保证被调度
        Thread.sleep(10000);
    }

    static class TaskMock implements Task {
        @Override public String getTaskId() {
            return UUID.randomUUID().toString();
        }

        @Override public String getTaskGroup() {
            return "tGroup";
        }

        @Override public String getTriggerId() {
            return UUID.randomUUID().toString();
        }

        @Override public String getTriggerGroup() {
            return "tGroup";
        }

        @Override public Class<? extends Service> getTaskHander() {
            return Service.class;
        }
    }

    /*
    //这种内部类方式，使得类地初始化失败，尚不知道具体原因
    static class SchedulerJobMock implements Job {
        SchedulerJobMock(){}

        @Override public void execute(JobExecutionContext context) throws JobExecutionException {
            //任务被执行，需要转发相应任务信息
            String executor = context.getJobDetail().getJobDataMap().getString(TaskBuilder.EXECUTOR_KEY);
            logger.info(executor);
            System.out.println(String.format("%s............"));
        }
    }
    */

}
