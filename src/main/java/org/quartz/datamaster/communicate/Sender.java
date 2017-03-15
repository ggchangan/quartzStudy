package org.quartz.datamaster.communicate;

import org.apache.log4j.Logger;
import org.quartz.datamaster.common.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by magneto on 17-3-13.
 */
public class Sender {
    private static final Logger logger = Logger.getLogger(Sender.class);

    private String tagMsg;
    private Message message;

    //类名
    public Sender(String tagMsg) {
        this.tagMsg = tagMsg;
        this.message = new Message(tagMsg);
    }

    public boolean start() {
        try(Socket s = new Socket()) {
            s.connect(new InetSocketAddress(message.getIp(), message.getPort()), 10000);
            OutputStream outStream = s.getOutputStream();
            outStream.write(tagMsg.getBytes());

            //TODO 确认客户端成功执行
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
