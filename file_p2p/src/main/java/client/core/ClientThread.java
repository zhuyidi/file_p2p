package client.core;

import client.message.DealMessageForClient;
import model.ConfigInfo;
import model.MessageInfo;
import org.apache.log4j.Logger;
import util.CloseUtil;
import util.ParseUtil;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * by yidi on 3/8/19
 */

public class ClientThread implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(ClientThread.class);
    private Socket socket;
    private DataInputStream inputStream;
    private ConfigInfo configInfo;

    public ClientThread(Socket socket, ConfigInfo configInfo) {
        this.configInfo = configInfo;
        init(socket);
    }

    private void init(Socket socket) {
        this.socket = socket;
        try {
            inputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            LOGGER.error("客户端：" + socket.getLocalAddress().getHostAddress() + "|" + socket.getPort() + "获取输入流失败");
            close();
        }
    }

    @Override
    public void run() {
        // todo 循环监听消息
        while (true) {
            String strMessage;
            try {
                strMessage = inputStream.readUTF();
                MessageInfo message = ParseUtil.parseMessage(strMessage);
                DealMessageForClient.deal(message, configInfo);
            } catch (IOException e) {
                LOGGER.error("客户端：" + socket.getLocalAddress().getHostAddress() + "|" + socket.getPort() + "接收服务端消息失败");
            }
        }
    }

    private void close() {
        CloseUtil.closeInputStream(inputStream);
        CloseUtil.closeSocket(socket);
    }
}
