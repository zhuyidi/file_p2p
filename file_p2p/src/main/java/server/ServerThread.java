package server;

import model.MessageInfo;
import org.apache.log4j.Logger;
import server.message.DealMessageForServer;
import util.CloseUtil;
import util.ParseUtil;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * by yidi on 3/7/19
 */

public class ServerThread implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(ServerThread.class);
    private Socket socket;
    private DataInputStream inputStream;

    public ServerThread(ClientInfoDTO clientInfoDTO) {
        init(clientInfoDTO);
    }

    private void init(ClientInfoDTO clientInfoDTO) {
        this.socket = clientInfoDTO.getSocket();
        try {
            inputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            LOGGER.error("获取客户端的socket的输入流失败， 客户端信息：" + socket.getLocalAddress().getHostAddress() + "|" + socket.getPort());
        }
    }

    // run里循环监听来自客户端的消息
    @Override
    public void run() {
        // 循环监听消息
        while (true) {
            String strMessage;
            System.out.println("服务端启动监听线程");
            try {
                strMessage = inputStream.readUTF();
                System.out.println("message:" + strMessage);
                MessageInfo message = ParseUtil.parseMessage(strMessage);
                DealMessageForServer.deal(message, socket);
            } catch (IOException e) {
                LOGGER.error("服务端接收消息失败， 客户端信息：" + socket.getLocalAddress().getHostAddress() +
                        "|" + socket.getPort() + e.getMessage());
                close(this);
                return;
            }
        }
    }

    private static void close(ServerThread serverThread) {
        CloseUtil.closeInputStream(serverThread.inputStream);
        CloseUtil.closeSocket(serverThread.socket);
    }
}
