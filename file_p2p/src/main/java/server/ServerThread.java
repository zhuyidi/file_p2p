package server;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedOutputStream;
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
    private BufferedOutputStream outputStream;

    public ServerThread(Socket socket) {
        this.socket = socket;
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            LOGGER.error("客户端：" + socket.getLocalAddress().getHostAddress() + "的IO流异常！");
        }
    }

    @Override
    public void run() {
        // 服务端处理线程
        try {
            String fileName = inputStream.readUTF();
            if (StringUtils.isNotEmpty(fileName)) {

            }
        } catch (IOException e) {

        }

    }
}
