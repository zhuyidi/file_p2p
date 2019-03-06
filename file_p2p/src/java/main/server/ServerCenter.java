package server;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * by yidi on 3/6/19
 */

public class ServerCenter {
    private static final Logger LOGGER = Logger.getLogger(ServerCenter.class);
    private static ServerSocket serverSocket;
    private static final String SEND_PATH = ResourceBundle.getBundle("file-config").getString("sendPath");
    private static final String TARGET_PATH = ResourceBundle.getBundle("file-config").getString("targetPath");
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(1, 5, 30, TimeUnit.MINUTES,
            new ArrayBlockingQueue<Runnable>(20), new ThreadFactory() {
        @Override
        public Thread newThread(@NotNull Runnable r) {
            return new Thread(r);
        }
    });

    static {
        try {
            serverSocket = new ServerSocket(33000);
        } catch (IOException e) {
            LOGGER.error("server启动失败！");
        }
    }

    public static void main(String[] args) {
        ServerCenter serverCenter = new ServerCenter();

        while (true) {
            // 等待客户端的连接
        }
    }
}
