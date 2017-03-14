package org.quartz.examples.persistence;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob implements Job{
	private static final Logger logger = Logger.getLogger(MyJob.class);  
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
      System.out.println("Hello quzrtz  "+
          new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date()));

      logger.info("test......................................");
		
	}

}
