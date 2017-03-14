package org.quartz.datamaster.common;

/**
 * Created by magneto on 17-3-13.
 */
public class JobInfo {
    //基本信息
    String name;
    JobType type;

    //调度信息
    JobScheduler scheduler;


    public static class JobScheduler {
        String jobId;
        String jobGroup;
        String triggerId;
        String triggerGroup;
        SchedulerType schedulerType;

        @Override
        public String toString() {
            return "JobScheduler{" + "jobId='" + jobId + '\'' + ", jobGroup='" + jobGroup + '\''
                + ", triggerId='" + triggerId + '\'' + ", triggerGroup='" + triggerGroup + '\''
                + ", schedulerType=" + schedulerType + '}';
        }
    }

    public enum SchedulerType {
        SIMPLE,
        CRON
    }

    public enum JobType {
        CLEAN
    }

    public String toJson(){
        return "";
    }

    @Override public String toString() {
        return "Job{" + "name='" + name + '\'' + ", type=" + type + ", scheduler=" + scheduler
            + '}';
    }
}
