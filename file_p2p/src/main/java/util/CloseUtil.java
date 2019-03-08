package util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * by yidi on 3/7/19
 */

public class CloseUtil {
    private static final Logger LOGGER = Logger.getLogger(CloseUtil.class);

    public static void closeInputStream(InputStream inputStream) {
        if (inputStream == null) {
            return;
        }
        try {
            inputStream.close();
            inputStream = null;
        } catch (IOException e) {
            LOGGER.error("输入流关闭异常");
        }
    }

    public static void closeOutputStream(OutputStream outputStream) {
        if (outputStream == null) {
            return;
        }
        try {
            outputStream.close();
            outputStream = null;
        } catch (IOException e) {
            LOGGER.error("输出流关闭异常");
        }
    }

    public static void closeSocket(Socket socket) {
        if (socket == null) {
            return;
        }
        try {
            socket.close();
            socket = null;
        } catch (IOException e) {
            LOGGER.error("socket关闭异常");
        }
    }

    public static void closeThreadPool(ThreadPoolExecutor threadPoolExecutor) {
        if (threadPoolExecutor == null) {
            return;
        }
        threadPoolExecutor.shutdown();
    }

    public static void closeServerSocket(ServerSocket serverSocket) {
        if (serverSocket == null) {
            return;
        }
        try {
            serverSocket.close();
            serverSocket = null;
        } catch (IOException e) {
            LOGGER.error("serverSocket关闭异常");
        }
    }
}
