package org.quartz.datamaster.scheduler;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.quartz.datamaster.scheduler.job.SchedulerService;
import org.quartz.datamaster.scheduler.job.TaskBuilder;

import java.util.LinkedList;

import static java.lang.Thread.sleep;

/**
 * Created by magneto on 17-3-14.
 */
public class MessageQueue<T> {
    private static final Logger logger = Logger.getLogger(MessageQueue.class);
    //TODO 具体的对象传递
    private LinkedList<T> messageQueue = new LinkedList<>();
    static Object lock = new Object();
    static boolean flag = true;

    class Consumer implements Runnable {
        @Override public void run() {
            synchronized (lock) {
                while (messageQueue.isEmpty()) {
                    //条件不满足，等待，同时释放锁
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //条件满足，消费消息，加入调度服务
                    while (!messageQueue.isEmpty()) {
                        T executor = messageQueue.poll();
                        try {
                            SchedulerService.accept(executor.toString());
                        } catch (SchedulerException e) {
                            e.printStackTrace();
                            logger.warn(e.getMessage());
                        }
                    }
                }
            }
        }
    }

    public void producer(T msg) {
        synchronized (lock) {
            messageQueue.push(msg);

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lock.notifyAll();
        }
    }

}
