package org.quartz.datamaster.scheduler.job;

import org.quartz.*;

import java.util.Date;
import java.util.UUID;

/**
 * Created by magneto on 17-3-13.
 */
public class TaskBuilder {
    public static final String EXECUTOR_KEY = "EXECUTOR";
    public static final int FUTURE_TIME = 5;

    //现在所有任务放在同一个个组，按照先后顺序被调度
    //TODO 为了负载均衡，不同类型的任务放在不同的队列里，或者对相同类型的任务分成不同的队列，总之，需要添加负载均衡地处理逻辑
    private JobDetail jobDetail;
    private Trigger trigger;

    private String executorStr;
    private Class <? extends Job> executerClass;

    public TaskBuilder(String executorStr) {
        this(executorStr, SchedulerJob.class);
    }

    public TaskBuilder(String executorStr, Class<? extends Job> executerClass) {
        this.executorStr = executorStr;
        this.executerClass = executerClass;
    }

    public void builder(SimpleScheduleBuilder simpleScheduleBuilder) {
        jobDetail = JobBuilder.newJob(executerClass)
            .withIdentity(getJobId(), getJobGroup())
            .usingJobData(EXECUTOR_KEY, executorStr)
            .build();
        // get a "nice round" time a few seconds in the future...
        Date startTime = DateBuilder.nextGivenSecondDate(null, FUTURE_TIME);

        TriggerBuilder<Trigger> triggerBuilder =
            TriggerBuilder.newTrigger();
        if (simpleScheduleBuilder != null) triggerBuilder.withSchedule(simpleScheduleBuilder);

        trigger = triggerBuilder.withIdentity(getTriggerId(), getTriggerGroup()).startAt(startTime).build();
    }

    public void builer() {
        builder(null);
    }

    public JobDetail getJobDetail() {
        return jobDetail;
    }

    public Trigger getTrigger() {
        return trigger;
    }


    public String getJobId() {
        return UUID.randomUUID().toString();
    }

    public String getJobGroup() {
        return "tGroup1";
    }

    public String getTriggerId() {
        return UUID.randomUUID().toString();
    }

    public String getTriggerGroup() {
        return "tGroup1";
    }

}
