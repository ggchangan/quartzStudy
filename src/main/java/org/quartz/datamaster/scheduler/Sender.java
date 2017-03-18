package org.quartz.datamaster.scheduler;

import org.apache.log4j.Logger;

/**
 * Created by magneto on 17-3-15.
 */
public interface Sender {
    Logger logger = Logger.getLogger(LocalSchedulerJob.class);
    void send();
}
