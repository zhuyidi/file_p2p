package server;

import org.apache.log4j.Logger;
import resourcetable.ResourceTable;
import util.CloseUtil;
import util.NodeTypeEnum;
import view.ServerMainJFrame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * by yidi on 3/6/19
 */

public class ServerCenter {
    private static final Logger LOGGER = Logger.getLogger(ServerCenter.class);
    private static ServerSocket serverSocket;
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(5, 10, 30, TimeUnit.MINUTES,
            new ArrayBlockingQueue<>(20), Thread::new, new ThreadPoolExecutor.AbortPolicy());
    private static AtomicLong ClientId = new AtomicLong(1L);
    public static ConcurrentHashMap<String, Socket> clientInfoMap = new ConcurrentHashMap<>();
    static {
        try {
            serverSocket = new ServerSocket(33000);
        } catch (IOException e) {
            LOGGER.error("server启动失败！");
            close();
        }
        LOGGER.info("server启动成功");
    }

    public void start() {
        new ServerMainJFrame();
        while (true) {
            // 等待客户端的连接
            String hostAddress = null;
            Socket socket = null;
            String port;
            try{
                socket = serverSocket.accept();
                hostAddress = socket.getLocalAddress().getHostAddress();
                port = String.valueOf(socket.getPort());
                long id = ClientId.getAndIncrement();
                LOGGER.info("客户端：" + hostAddress + "连接成功");
                clientInfoMap.put(String.valueOf(id), socket);
                ClientInfoDTO clientInfoDTO = new ClientInfoDTO(NodeTypeEnum.CLIENT.getCode(), socket, id, hostAddress, port);
                init(clientInfoDTO);
                THREAD_POOL_EXECUTOR.execute(new ServerThread(clientInfoDTO));
            } catch (IOException e) {
                LOGGER.error("客户端连接异常！客户端IP：" + hostAddress);
                closeClient(socket.getLocalAddress().getHostAddress() + "|" + socket.getPort());
            }
        }
    }

    private void init(ClientInfoDTO clientInfoDTO) {
        ResourceTable.registerClientInRedis(clientInfoDTO);
    }

    private static void closeClient(String clientHost) {
        ResourceTable.updateResourceTableForOffline(clientHost);
    }

    private static void close() {
        CloseUtil.closeServerSocket(serverSocket);
        CloseUtil.closeThreadPool(THREAD_POOL_EXECUTOR);
    }
}
