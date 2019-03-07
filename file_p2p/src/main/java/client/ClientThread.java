package client;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * by yidi on 3/7/19
 */

public class ClientThread implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(ClientCenter.class);
    private static final int BUFFER_SIZE = 32768; // todo 后面要将buffer的大小配置进properties
    private Socket socket;
    private String hostAdress;
    private DataInputStream inputStream;
    private BufferedOutputStream outputStream;
    private byte[] buffer = new byte[BUFFER_SIZE];

    public ClientThread(Socket socket) {
        this.socket = socket;
        hostAdress = socket.getLocalAddress().getHostAddress();
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            LOGGER.error("客户端：" + hostAdress + "初始化IO流失败！");
        }
    }

    @Override
    public void run() {
        String fileName; // todo Demo字段
        long fileLen; // todo Demo字段

        // 输入需要接受的文件名，传送给服务端
        Scanner scanner = new Scanner(System.in);
        fileName = scanner.nextLine();
        if (StringUtils.isEmpty(fileName)) {
            close(this);
            return;
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle("file-config");
        String targetPath = resourceBundle.getString("targetPath");

        // 接收服务端传送过来的文件大小
        long recvLen = 0;
        try {
            fileLen = inputStream.readLong();
        } catch (IOException e) {
            LOGGER.error("客户端：" + hostAdress + "读取文件大小失败！");
            close(this);
            return;
        }

        // 创建目标文件
        RandomAccessFile file = null;
        try {
            String filePath = targetPath + fileName;
            file = new RandomAccessFile(filePath, "rw");
        } catch (FileNotFoundException e) {
            LOGGER.error("客户端：" + hostAdress + "创建目标文件失败！");
            close(this);
            return;
        }
        System.out.println("开始接收文件！"); // todo 后面要将此条信息用下面的日志记录替换
        // LOGGER.info("客户端：" + hostAdress + "开始接受文件");

        // 开始接收文件
        try {
            while (recvLen >= fileLen) {
                int temp = inputStream.read(buffer, 0, BUFFER_SIZE);
                recvLen += temp;
            }
        } catch (IOException e) {
            LOGGER.error("客户端：" + hostAdress + "接收" + fileName + "文件异常！");
            close(this);
            return;
        }
    }

    private static void close(ClientThread clientThread) {
        try {
            if (clientThread.inputStream != null) {
                clientThread.inputStream.close();
                clientThread.inputStream = null;
            }
            if (clientThread.outputStream != null) {
                clientThread.outputStream.close();
                clientThread.outputStream = null;
            }
            if (clientThread.socket != null) {
                clientThread.socket.close();
                clientThread.socket = null;
            }
        } catch (Exception e) {
            LOGGER.error("客户端：" + clientThread.hostAdress + "资源关闭异常！");
        }
    }
}
