package client.receive;

import model.ConfigInfo;
import observer.IJoinFileListner;
import observer.IJoinFileSpeaker;
import observer.IReceiveSectionListener;
import org.apache.log4j.Logger;
import util.CloseUtil;
import view.JoinCenter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  by yidi on 5/4/19
 */

public class ReceiveCenter implements IReceiveSectionListener, IJoinFileSpeaker {
    private static final Logger LOGGER = Logger.getLogger(ReceiveCenter.class);
    private ServerSocket serverSocket;
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 30,TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(20), Thread::new, new ThreadPoolExecutor.AbortPolicy());
    private ConfigInfo configInfo;
    private Socket socket;
    public static AtomicInteger PORT = new AtomicInteger(44000);
    private int sendCount;
    private int recvSctionCount = 0;
    private List<IJoinFileListner> joinFileListners = new ArrayList<>();

    public ReceiveCenter(ConfigInfo configInfo, int sendCount, Socket socket) {
        this.configInfo = configInfo;
        this.sendCount = sendCount;
        this.socket = socket;
        try {
            serverSocket = new ServerSocket(ReceiveCenter.PORT.getAndIncrement());
        } catch (IOException e) {
            LOGGER.error("客户端接收服务器建立失败，客户端信息：" + serverSocket.getLocalSocketAddress());
            close();
        }
        LOGGER.info("客户端接收服务器建立成功，客户端信息：" + serverSocket.getLocalSocketAddress());
    }

    public void start() {
        int tempCount = sendCount;
        while (tempCount-- > 0) {
            // 等待客户端的连接
            String hostAddress = null;
            try{
                Socket socket = serverSocket.accept();
                hostAddress = socket.getLocalAddress().getHostAddress();
                LOGGER.info("客户端：" + hostAddress + "连接成功");
                ReceiveThread receiveThread = new ReceiveThread(socket, configInfo);
                receiveThread.addReceiveSectionListener(this);
                threadPoolExecutor.execute(receiveThread);
                JoinCenter joinCenter = new JoinCenter(this.socket, configInfo);
                joinFileListners.add(joinCenter);
            } catch (IOException e) {
                LOGGER.error("客户端连接异常！客户端IP：" + hostAddress);
            }
        }
        close();
    }

    private void close() {
        CloseUtil.closeServerSocket(this.serverSocket);
        CloseUtil.closeThreadPool(threadPoolExecutor);
    }

    @Override
    public void getReceiveOneSection(String fileName) {
        recvSctionCount++;
        if (recvSctionCount == sendCount) {
            sendStartJoin(fileName, sendCount);
        }
    }

    @Override
    public void addJoinFileListener(IJoinFileListner iJoinFileListner) {
        joinFileListners.add(iJoinFileListner);
    }

    @Override
    public void sendStartJoin(String fileName, int sendCount) {
        for (IJoinFileListner iJoinFileListner : joinFileListners) {
            iJoinFileListner.getStartJoinFile(fileName, sendCount);
        }
    }
}
