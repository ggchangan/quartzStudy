package org.quartz.datamaster;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.quartz.datamaster.scheduler.NetworkService;
import org.quartz.datamaster.scheduler.job.SchedulerService;

/**
 * Created by magneto on 17-3-13.
 */
public class DispatchService {
    private static final Logger logger = Logger.getLogger(DispatchService.class);
    public static void main(String[] args) {
        try {
            /*
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    SimpleScheduleBuilder
                        simpleScheduleBuilder1 = SimpleScheduleBuilder.simpleSchedule().repeatSecondlyForTotalCount(10);
                    TaskBuilder taskBuilder = new TaskBuilder();
                    taskBuilder.builder(simpleScheduleBuilder1);

                    try {
                        schedulerService.accept(taskBuilder);
                    } catch (SchedulerException e) {
                        e.printStackTrace();
                    }
                }
            });

            thread1.start();
            */

            SchedulerService.start();
            NetworkService<String> networkService = new NetworkService();
            new Thread(networkService).start();
            //网络服务接收者


            //TODO 收到调度请求
            //TODO 调度
            //TODO 发送调度信息
            //TODO 任务状态展示模块
            //TODO 任务启停管理模块

            /*
            try {
                ServerSocket dispatchSocket = new ServerSocket(8189);
                Socket incoming = dispatchSocket.accept();
                InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream();

                try (Scanner in = new Scanner(inStream)) {
                    PrintWriter out = new PrintWriter(outStream, true);

                    out.println("Hello! Enter BYE to exit.");

                    boolean done = false;

                    while (!done && in.hasNextLine()) {
                        String line = in.nextLine();
                        System.out.println(line);
                        out.println(String.format("Echo: %s", line));
                        if (line.trim().equals("BYE")) done = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            */

            /*
            try {
                int i = 1;
                ServerSocket dispatchSocket = new ServerSocket(8189);

                while (true) {
                    Socket incoming = dispatchSocket.accept();
                    System.out.println("Spawning " + i);
                    Runnable r = new ThreadedEchoHandler(incoming);
                    Thread t = new Thread(r);
                    t.start();
                    i++;

                }
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
            */


            /*
            TaskBuilder taskBuilder = new TaskBuilder();
            SimpleScheduleBuilder
                simpleScheduleBuilder1 = SimpleScheduleBuilder.simpleSchedule().repeatSecondlyForTotalCount(10);
            taskBuilder.builder(simpleScheduleBuilder1);
            try {
                schedulerService.accept(taskBuilder);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
            */

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
