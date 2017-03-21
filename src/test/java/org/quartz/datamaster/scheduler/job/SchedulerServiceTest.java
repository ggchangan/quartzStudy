package org.quartz.datamaster.scheduler.job;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.quartz.datamaster.client.AnalysisJobService;
import org.quartz.datamaster.client.CleaningJobService;
import org.quartz.datamaster.common.Service;
import org.quartz.datamaster.scheduler.*;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by magneto on 17-3-14.
 */
public class SchedulerServiceTest {
    private static final Logger logger = Logger.getLogger(SchedulerServiceTest.class);

    @org.junit.Before public void setUp() throws Exception {
    }

    @org.junit.After public void tearDown() throws Exception {
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        SchedulerService.start();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        SchedulerService.shutdown();
    }

    @Test
    public void localShedulerTest() throws Exception {
        //测试调度顺序
        List<Task> tasks = new ArrayList<>();
        Task task1 = new CleaningTaskMock();
        Task task2 = new AnalysisTaskMock();
        Task task3 = new CleaningTaskMock();
        Task task4 = new AnalysisTaskMock();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);

        LocalAccepter accepter = new LocalAccepter();
        for (Task task: tasks) {
            accepter.accept(task);
        }

        TaskStatus taskStatus = new DefaultTaskStatus();
        List<Task> allTasks = taskStatus.allTasks();
        System.out.println("输出所有任务：");
        for (Task task: allTasks) {
            System.out.println(task.getTaskId());
        }

        System.out.println("第一次获取正在运行的任务：");
        List<Task> runningTasks = taskStatus.runningTasks();
        for (Task task: runningTasks) {
            System.out.println(task.getTaskId());
        }

        Thread.sleep(6000);

        System.out.println("第二次获取正在运行的任务：");
        runningTasks = taskStatus.runningTasks();
        for (Task task: runningTasks) {
            System.out.println(task.getTaskId());
        }

        //取消任务，未运行的任务会被取消
        TaskManager taskManager = new DefaultTaskManager();
        for (Task task: tasks) {
            taskManager.delete(task);
        }

        Thread.sleep(1000);
        System.out.println("第三次获取正在运行的任务：");
        runningTasks = taskStatus.runningTasks();
        for (Task task: runningTasks) {
            System.out.println(task.getTaskId());
        }

        allTasks = taskStatus.allTasks();
        System.out.println("输出所有任务：");
        for (Task task: allTasks) {
            System.out.println(task.getTaskId());
        }

        //保证被调度
        Thread.sleep(1000000);

        //并不能保证顺序调度
        //TODO 接收任务之后进行缓存
    }

    @Test
    public void localSenderTest() throws Exception {
        Task task = new AnalysisTaskMock();
        LocalAccepter accepter = new LocalAccepter();
        accepter.accept(task);
        //保证被调度
        Thread.sleep(10000);
    }

    @Test
    public void taskStatusTest() throws Exception {
        TaskStatus taskStatus = new DefaultTaskStatus();
        List<Task> allTasks = taskStatus.allTasks();
        System.out.println("输出所有任务：");
        for (Task task: allTasks) {
            System.out.println(task.getTaskId());
        }

        System.out.println("第一次获取正在运行的任务：");
        List<Task> runningTasks = taskStatus.runningTasks();
        for (Task task: runningTasks) {
            System.out.println(task.getTaskId());
        }

        Thread.sleep(1000);

        System.out.println("第二次获取正在运行的任务：");
        runningTasks = taskStatus.runningTasks();
        for (Task task: runningTasks) {
            System.out.println(task.getTaskId());
        }

        Thread.sleep(1000);

        System.out.println("第三次获取正在运行的任务：");
        runningTasks = taskStatus.runningTasks();
        for (Task task: runningTasks) {
            System.out.println(task.getTaskId());
        }
    }

    public static class CleaningTaskMock implements Task {
        private String taskId = UUID.randomUUID().toString();
        private String triggerId = UUID.randomUUID().toString();

        @Override public String getTaskId() {
            return taskId;
        }

        @Override public String getTaskGroup() {
            return "tGroup";
        }

        @Override public String getTriggerId() {
            return triggerId;
        }

        @Override public String getTriggerGroup() {
            return "tGroup";
        }

        @Override public Class<? extends Service> getTaskHander() {
            return CleaningJobService.class;
        }
    }

    public static class AnalysisTaskMock implements Task {
        private String taskId = UUID.randomUUID().toString();
        private String triggerId = UUID.randomUUID().toString();

        @Override public String getTaskId() {
            return taskId;
        }

        @Override public String getTaskGroup() {
            return "tGroup";
        }

        @Override public String getTriggerId() {
            return triggerId;
        }

        @Override public String getTriggerGroup() {
            return "tGroup";
        }

        @Override public Class<? extends Service> getTaskHander() {
            return AnalysisJobService.class;
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
