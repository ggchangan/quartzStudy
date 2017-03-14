package org.quartz.datamaster.common;

/**
 * Created by magneto on 17-3-13.
 */
public class Message {
    private String ip;
    private int port;
    private Class<? extends Service> tClass;
    private String msgStr;


    public Message(String ip, int port, Class<? extends Service> tClass, String msgStr) {
        this.ip = ip;
        this.port = port;
        this.tClass = tClass;
        this.msgStr = msgStr;
    }

    public String tagMsg() {
        return String.format("%s&%d&%s&%s\n", ip, port, tClass.getName(), msgStr);
    }

    public Message(String tagMsg) {
        String[] strs = tagMsg.split("&");
        ip = strs[0];
        port = Integer.valueOf(strs[1]);
        try {
            tClass = (Class<? extends Service>) Class.forName(strs[2]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.msgStr = strs[3];
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public Class gettClass() {
        return tClass;
    }

    public String getMsgStr() {
        return msgStr;
    }
}
