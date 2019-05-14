package client.send;

import model.MessageInfo;
import org.apache.log4j.Logger;
import util.PackageUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *  by yidi on 5/9/19
 */

public class SendMessageForClient {
    private static final Logger LOGGER = Logger.getLogger(SendMessageForClient.class);

    public static void sendMessage(MessageInfo messageInfo, Socket socket) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String strMessage = PackageUtil.packageMessage(messageInfo);
//            System.out.println("client message: " + strMessage);
            dataOutputStream.writeUTF(strMessage);
        } catch (IOException e) {
            LOGGER.error("客户端输出流获取失败，无法发送消息。客户端信息：" + socket.getLocalAddress().getHostAddress() + "|" + socket.getLocalPort());
        }
    }

}
