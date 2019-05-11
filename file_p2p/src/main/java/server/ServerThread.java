package server;

import org.apache.log4j.Logger;

import java.net.Socket;

/**
 * by yidi on 3/7/19
 */

public class ServerThread implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(ServerThread.class);
    private Socket socket;

    public ServerThread(ClientInfoDTO clientInfoDTO) {
        init(clientInfoDTO);
    }

    private void init(ClientInfoDTO clientInfoDTO) {
        this.socket = clientInfoDTO.getSocket();
    }

    // run里循环监听来自客户端的消息
    @Override
    public void run() {

    }
}
