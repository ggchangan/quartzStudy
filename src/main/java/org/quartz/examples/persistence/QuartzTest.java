package org.quartz.examples.persistence;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;


public class QuartzTest {

	private static SchedulerFactory sf = new StdSchedulerFactory();
	private static String JOB_GROUP_NAME = "ddlib";
	private static String TRIGGER_GROUP_NAME = "ddlibTrigger";

	public static void main(String[] args) throws SchedulerException,
			ParseException {
		//startSchedule();
		resumeJob();
	}
	/**
	 * ��ʼһ��simpleSchedule()����
	 */
	public static void startSchedule() {
		try {
			JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
					.withIdentity("job1_1", "jGroup1")
					.build();
			SimpleScheduleBuilder builder = SimpleScheduleBuilder
					.simpleSchedule()
        	.repeatSecondlyForTotalCount(10);
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity("trigger1_1", "tGroup1").startNow()
					.withSchedule(builder).build();
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			scheduler.scheduleJob(jobDetail, trigger);
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			scheduler.shutdown();

		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �����ݿ����ҵ��Ѿ����ڵ�job�������¿�������
	 */
	public static void resumeJob() {
		try {

			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Scheduler scheduler = schedulerFactory.getScheduler();
			List<String> triggerGroups = scheduler.getTriggerGroupNames();
			for (int i = 0; i < triggerGroups.size(); i++) {
				List<String> triggers = scheduler.getTriggerGroupNames();
				for (int j = 0; j < triggers.size(); j++) {
					Trigger tg = scheduler.getTrigger(new TriggerKey(triggers
							.get(j), triggerGroups.get(i)));
					if (tg instanceof SimpleTrigger
							&& tg.getDescription().equals("tgroup1.trigger1_1")) {
						scheduler.resumeJob(new JobKey(triggers.get(j),
								triggerGroups.get(i)));
					}
				}

			}
			scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
