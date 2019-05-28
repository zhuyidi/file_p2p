package server.message;

import client.message.ClientMessageEnum;
import model.FileTaskInfo;
import model.MessageInfo;
import org.apache.log4j.Logger;
import resourcetable.ResourceTable;
import resourcetable.allocationpolicy.AllocationPolicy;
import util.NodeTypeEnum;
import util.PackageUtil;

import java.net.Socket;
import java.util.List;
import java.util.Set;

/**
 *  by yidi on 5/11/19
 */

public class DealMessageForServer {
    private static final Logger LOGGER = Logger.getLogger(DealMessageForServer.class);

    public static void deal(MessageInfo messageInfo, Socket socket) {
        // 客户端请求资源
        if (messageInfo.getAction() == ClientMessageEnum.REQUEST.getCode()) {
            dealRequest(messageInfo, socket);
        }
    }

    private static void dealRequest(MessageInfo messageInfo, Socket socket) {
        // 获取请求的文件信息（即redis里的key值）：文件名和文件大小
        String fileName = ResourceTable.getAllFileInfo(messageInfo.getMessageContent());
        long fileLen = Long.parseLong(fileName.split("&&")[1]);

        // 获取可以发送该文件的端
        Set<String> clients = ResourceTable.getClientsByFileName(fileName);
        clients.removeAll(ResourceTable.getBadClients());

        // 决定分配策略
        List<FileTaskInfo> taskResult = AllocationPolicy.allocationPolicyForClient(
                messageInfo.getMessageContent(), fileLen, clients);

        // 向请求端通知有几个端要去执行发送任务
        MessageInfo toRequest = new MessageInfo(NodeTypeEnum.SERVER.getCode(), "", NodeTypeEnum.CLIENT.getCode(),
                "", ServerMessageEnum.NOTICE_SEND_COUNT.getCode(), String.valueOf(taskResult.size()));
        SendMessageForServer.sendMessage(socket, "", toRequest);

        // 向发送端下发任务
        taskResult.stream().forEach(e -> {
            MessageInfo message = new MessageInfo(NodeTypeEnum.SERVER.getCode(), "", NodeTypeEnum.CLIENT.getCode(),
                    messageInfo.getFromInfo(), ServerMessageEnum.FILE_TASK.getCode(), PackageUtil.packageFileTask(e));
            SendMessageForServer.sendMessage(null, e.getClientId(), message);
        });

    }
}
