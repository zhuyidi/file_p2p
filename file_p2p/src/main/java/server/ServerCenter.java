package server;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import util.CloseUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * by yidi on 3/6/19
 */

public class ServerCenter {
    private static final Logger LOGGER = Logger.getLogger(ServerCenter.class);
    private static ServerSocket serverSocket;
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(1, 5, 30, TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(20), Thread::new, new ThreadPoolExecutor.AbortPolicy());

    static {
        try {
            serverSocket = new ServerSocket(33000);
        } catch (IOException e) {
            LOGGER.error("server启动失败！");
            close();
        }
        LOGGER.info("server启动成功");
    }

    public static void main(String[] args) {
        ServerCenter serverCenter = new ServerCenter();

        while (true) {
            // 等待客户端的连接
            String hostAddress = null;
            try {
                Socket socket = serverSocket.accept();
                hostAddress = socket.getLocalAddress().getHostAddress();
                LOGGER.info("客户端：" + hostAddress + "连接成功");
                THREAD_POOL_EXECUTOR.execute(new ServerThread(socket));
            } catch (IOException e) {
                LOGGER.error("客户端连接异常！客户端IP：" + hostAddress);
            }
        }
    }

    private static void close() {
        CloseUtil.closeServerSocket(serverSocket);
        CloseUtil.closeThreadPool(THREAD_POOL_EXECUTOR);
    }
}
