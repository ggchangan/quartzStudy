package org.quartz.datamaster.client;


import org.apache.log4j.Logger;
import org.quartz.datamaster.common.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by magneto on 17-3-13.
 */
public class CleaningJobService implements Service {
    private static final Logger logger = Logger.getLogger(CleaningJobService.class);

    @Override
    public void execute(){
        String msg = String.format("cleaning tast at %s",
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        logger.info(msg);
    }
}
