package org.quartz.datamaster.scheduler.job;

import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.junit.Test;
import org.quartz.datamaster.scheduler.Sender;

import java.util.ArrayList;
import java.util.List;

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
        Thread.sleep(10000);
        SchedulerService.shutdown();
    }

    @Test
    public void schedulerTest() throws Exception {
        //测试调度顺序
        List<String> jobs = new ArrayList<String>();
        jobs.add("job1");
        jobs.add("job2");
        jobs.add("job3");

        Sender senderMock = EasyMock.createMock(Sender.class);
        EasyMock.expect(senderMock.start()).andStubReturn(true);

        for (String job: jobs) {
            SchedulerService.accept(job, SchedulerJobMock.class);
        }

        //保证被调度
        Thread.sleep(10000);

        assertTrue(SchedulerJobMock.getJobs().containsAll(jobs) && jobs.containsAll(SchedulerJobMock.getJobs()));


        //并不能保证顺序调度
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
