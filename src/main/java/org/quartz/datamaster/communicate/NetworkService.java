package org.quartz.datamaster.communicate;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.quartz.datamaster.common.Config;
import org.quartz.datamaster.scheduler.SchedulerService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by magneto on 17-3-14.
 */
public class NetworkService<T> implements Runnable {
    private static final Logger logger = Logger.getLogger(NetworkService.class);

    @Override public void run() {
        try {
            ServerSocket dispatchSocket = new ServerSocket(Config.serverPort);
            Socket incoming = dispatchSocket.accept();
            InputStream inStream = incoming.getInputStream();
            OutputStream outStream = incoming.getOutputStream();

            try (Scanner in = new Scanner(inStream)) {

                while (in.hasNextLine()) {
                    String msg = in.nextLine();
                    String infoStr = String.format("收到的消息是：%s", msg);
                    logger.info(infoStr);
                    System.out.println(infoStr);

                    //TODO 可无限接收消息，接收服务不终止
                    try {
                        SchedulerService.accept(infoStr);
                    } catch (SchedulerException e) {
                        e.printStackTrace();
                        logger.warn(e.getMessage());
                    }

                    PrintWriter out = new PrintWriter(outStream, true);
                    String echoMsg = String.format("%s", "succeed");
                    out.println(echoMsg);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
