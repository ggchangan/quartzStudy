package org.quartz.datamaster.client;

import org.apache.log4j.Logger;
import org.quartz.datamaster.common.Config;
import org.quartz.datamaster.common.Message;
import org.quartz.datamaster.common.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by magneto on 17-3-13.
 * 测试：telnet 172.24.62.204 8188
 * 172.24.62.204&8188&org.quartz.datamaster.client.CleaningJobService&测试
 * 每一行一个请求，请求一次即关闭，接收服务作为后端进程不结束
 */
public class Receiver {
    private static final Logger logger = Logger.getLogger(Receiver.class);

    public void start() {
        String tagMsg = "";

        ServerSocket dispatchSocket = null;
        try {
            dispatchSocket = new ServerSocket(Config.port);
            logger.info("接收任务服务启动成功!");

            while (true) {
                Socket incoming = dispatchSocket.accept();
                InputStream inStream = incoming.getInputStream();
                BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(inStream));
                tagMsg = bufferedReader.readLine();
                Message message = new Message(tagMsg);
                //TODO 执行相应服务的execute()方法，异步执行地方法
                Class<? extends Service> tClass = message.gettClass();
                try {
                    Service service = tClass.newInstance();
                    service.execute();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                incoming.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn(e.getMessage());
        } finally {
            try {
                dispatchSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
