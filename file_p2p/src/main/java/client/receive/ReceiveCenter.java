package client.receive;

import observer.IReceiveSectionListener;
import org.apache.log4j.Logger;
import util.CloseUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *  by yidi on 5/4/19
 */

public class ReceiveCenter implements IReceiveSectionListener {
    private static final Logger LOGGER = Logger.getLogger(ReceiveCenter.class);
    private ServerSocket serverSocket;
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 5, 30,TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(20), Thread::new, new ThreadPoolExecutor.AbortPolicy());

    public ReceiveCenter() {
        try {
            serverSocket = new ServerSocket(44000);
        } catch (IOException e) {
            LOGGER.error("客户端接收服务器建立失败，客户端信息：" + serverSocket.getLocalSocketAddress());
            close();
        }
        LOGGER.info("客户端接收服务器建立成功，客户端信息：" + serverSocket.getLocalSocketAddress());
    }

    public void start() {
        while (true) {
            // 等待客户端的连接
            String hostAddress = null;
            try{
                Socket socket = serverSocket.accept();
                hostAddress = socket.getLocalAddress().getHostAddress();
                LOGGER.info("客户端：" + hostAddress + "连接成功");
                threadPoolExecutor.execute(new ReceiveThread(socket));
            } catch (IOException e) {
                LOGGER.error("客户端连接异常！客户端IP：" + hostAddress);
            }
        }
    }

    private void close() {
        CloseUtil.closeServerSocket(this.serverSocket);
        CloseUtil.closeThreadPool(threadPoolExecutor);
    }

    @Override
    public void onGetOneSection(long sectionLen) {
        // 判断文件是否接收完，接收完的话，就关闭上面的临时服务器
        // 如何判断一个文件接收完？暂时想法是通过接收到的长度和文件的总长度进行比较，判断文件是否接收完？可要是多个文件呢？
    }
}
