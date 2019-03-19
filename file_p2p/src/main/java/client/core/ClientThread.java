package client.core;

import client.receive.ReceiveThread;
import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import util.CloseUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * by yidi on 3/8/19
 */

// ClientThread建立临时服务端，等待其他客户端的连接，同时ClientThread要将自己的IP和端口发送给服务端，服务端进行注册。
public class ClientThread implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(ClientThread.class);
    private Socket socket;
    private String hostAddress;
    private ServerSocket clientServerSocket;
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(1, 5, 30, TimeUnit.MINUTES,
            new ArrayBlockingQueue<Runnable>(20), new ThreadFactory() {
        @Override
        public Thread newThread(@NotNull Runnable r) {
            return new Thread(r);
        }
    });

    public ClientThread(Socket socket) {
        this.socket = socket;
        hostAddress = socket.getLocalAddress().getHostAddress();
        try {
            clientServerSocket = new ServerSocket(33001);
        } catch (IOException e) {
            LOGGER.error("客户端：" + hostAddress + "建立serverSocket失败");
            close(this);
        }
    }

    @Override
    public void run() {
        // todo 这里后面要进行优化，serverSocket不能一直开启，要适时关闭
        while (true) {
            // 等待其他客户端的连接
            String sendHostAddress = null;
            try {
                Socket socket = clientServerSocket.accept();
                hostAddress = socket.getLocalAddress().getHostAddress();
                LOGGER.info("发送端：" + sendHostAddress + "连接成功");
                THREAD_POOL_EXECUTOR.execute(new ReceiveThread(socket));
            } catch (IOException e) {
                LOGGER.error("发送端连接异常！本客户端IP：" + hostAddress + "，发送端IP：" + sendHostAddress);
            }
        }
    }

    private static void close(ClientThread clientThread) {
        CloseUtil.closeThreadPool(THREAD_POOL_EXECUTOR);
        CloseUtil.closeServerSocket(clientThread.clientServerSocket);
    }
}
