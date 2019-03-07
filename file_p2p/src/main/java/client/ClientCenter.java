package client;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

/**
 * by yidi on 3/6/19
 */

public class ClientCenter {
    private static final Logger LOGGER = Logger.getLogger(ClientCenter.class);
    private Socket socket;

    public ClientCenter() {
        String hostAddress = null;
        try {
            socket = new Socket("127.0.0.1", 33000);
            hostAddress = socket.getLocalAddress().getHostAddress();
            new Thread(new ClientThread(socket));
        } catch (IOException e) {
            LOGGER.error("客户端：" + hostAddress + "连接服务端失败！");
        }
        LOGGER.info("客户端：" + hostAddress + "已连接服务端！");
    }
}
