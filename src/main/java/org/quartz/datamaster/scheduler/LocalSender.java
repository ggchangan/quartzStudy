package org.quartz.datamaster.scheduler;

/**
 * Created by magneto on 17-3-15.
 */
public class LocalSender implements Sender{
    @Override public void send() {
        logger.info("开始局部发送.....");
    }
}
