package org.quartz.datamaster.client;

import org.apache.log4j.Logger;
import org.quartz.datamaster.common.Config;
import org.quartz.datamaster.common.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by magneto on 17-3-13.
 */
public class Sender {
    private static final Logger logger = Logger.getLogger(Sender.class);

    private String msg;

    //类名
    public Sender(String msg) {
        this.msg = msg;
    }

    public boolean start() {
        try(Socket s = new Socket()) {
            //TODO 改成服务器地址
            //Config.serverIp;
            s.connect(new InetSocketAddress(Config.serverIp, Config.serverPort), 10000);
            OutputStream outStream = s.getOutputStream();
            //TODO 使用什么方法，不应该写死此类
            Message message = new Message(Config.ip, Config.port, CleaningJobService.class, msg);
            outStream.write(message.tagMsg().getBytes());

            InputStream inStream = s.getInputStream();
            try (Scanner in = new Scanner(inStream)) {

                String msg = null;
                if (in.hasNextLine()) {
                    msg = in.nextLine();
                }

                //TODO 接收服务端的一个响应，代表服务端收到了此消息
                if (!msg.equalsIgnoreCase("succeed")) return false;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            logger.warn(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn(e.getMessage());
        }


        return true;
    }

}
