package org.quartz.datamaster.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.quartz.datamaster.client.CleaningJobService;

import static org.junit.Assert.*;

/**
 * Created by magneto on 17-3-14.
 */
public class MessageTest {
    Message message;
    String ip = "172.24.62.204";
    int port = 8188;
    Class<? extends Service> tClass = CleaningJobService.class;
    String msgStr = "测试";
    String exceptTagMsg = "172.24.62.204&8188&org.quartz.datamaster.client.CleaningJobService&测试";

    @Before public void setUp() throws Exception {
        message = new Message(ip, port, tClass, msgStr);
    }

    @After public void tearDown() throws Exception {

    }

    @Test
    public void testEquals(){
        //组装之后相等
        String tagMsg = message.tagMsg();
        assertEquals(tagMsg.trim(), exceptTagMsg);

        //拆分相等
        Message tmp = new Message(exceptTagMsg);
        assertEquals(tmp.getIp(), ip);
        assertEquals(tmp.getPort(), port);
        assertEquals(tmp.gettClass(), tClass);
        assertEquals(tmp.getMsgStr(), msgStr);
    }

    @Test
    public void testExecutor() {
        Message tmp = new Message(exceptTagMsg);
        Class<? extends Service> tClass = tmp.gettClass();
        try {
            Service service = tClass.newInstance();
            service.execute();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
