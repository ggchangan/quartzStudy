package org.quartz.datamaster;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.quartz.datamaster.scheduler.SchedulerService;

/**
 * Created by magneto on 17-3-13.
 */
//TODO 收到调度请求
//TODO 调度
//TODO 发送调度信息
//TODO 任务状态展示模块
//TODO 任务启停管理模块
public class DispatchService {
    private static final Logger logger = Logger.getLogger(DispatchService.class);
    public static void main(String[] args) {
        try {

            SchedulerService.start();
            //NetworkService<String> networkService = new NetworkService();
            //new Thread(networkService).start();
            //网络服务接收者

            try {
                Thread.sleep(6000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (SchedulerException e) {
            e.printStackTrace();
        } finally {
            try {
                SchedulerService.shutdown();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }
}
