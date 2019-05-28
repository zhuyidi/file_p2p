package client.message;

import client.receive.ReceiveCenter;
import client.send.SendFileThread;
import model.ConfigInfo;
import model.MessageInfo;
import org.apache.log4j.Logger;
import server.message.ServerMessageEnum;
import util.ParseUtil;

import java.net.Socket;


/**
 *  by yidi on 5/8/19
 */

public class DealMessageForClient {
    private static final Logger LOGGER = Logger.getLogger(DealMessageForClient.class);

    public static void deal(MessageInfo messageInfo, ConfigInfo configInfo, Socket socket) {
        // 来自服务端的任务分配消息
        if (messageInfo.getAction() == ServerMessageEnum.FILE_TASK.getCode()) {
            dealFileTask(messageInfo, configInfo);
        } else if (messageInfo.getAction() == ServerMessageEnum.NOTICE_SEND_COUNT.getCode()) {
            dealNoticeSendCount(messageInfo, configInfo, socket);
        }
    }

    private static void dealFileTask(MessageInfo messageInfo, ConfigInfo configInfo) {
        // 首先取出接收端信息，连接接收端
        String targetHost = messageInfo.getToInfo().split("\\|")[0];
        String targetPort = messageInfo.getToInfo().split("\\|")[1];
        new Thread(new SendFileThread(targetHost, Integer.parseInt(targetPort), configInfo,
                ParseUtil.parseFileTask(messageInfo.getMessageContent()))).start();

    }

    private static void dealNoticeSendCount(MessageInfo messageInfo, ConfigInfo configInfo, Socket socket) {
        int sendCount = Integer.parseInt(messageInfo.getMessageContent());
        new ReceiveCenter(configInfo, sendCount, socket).start();
    }
}
