package server.message;

import model.MessageInfo;
import org.apache.log4j.Logger;
import server.ServerCenter;
import util.PackageUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *  by yidi on 5/13/19
 */

public class SendMessageForServer {
    private static final Logger LOGGER = Logger.getLogger(SendMessageForServer.class);

    public static void sendMessage(Socket socket, String clientId, MessageInfo messageInfo) {
        Socket targetSocket = socket;
        if (targetSocket == null) {
            socket = ServerCenter.clientInfoMap.get(clientId);
        }
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(PackageUtil.packageMessage(messageInfo));
        } catch (IOException e) {
            LOGGER.error("向客户端发送消息失败， 客户端信息：" + socket.getLocalAddress() + "|" + socket.getPort());
        }
    }
}
