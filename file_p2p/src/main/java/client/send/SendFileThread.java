package client.send;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import util.CloseUtil;

import java.io.*;
import java.net.Socket;
import java.util.ResourceBundle;

/**
 * by yidi on 3/18/19
 */

public class SendFileThread implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(SendFileThread.class);
    private static final int BUFFER_SIZE = 32768; // todo 后面要将buffer的大小配置进properties
    private Socket socket;
    private BufferedInputStream inputStream;
    private BufferedOutputStream outputStream;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String hostAdress;
    private byte[] buffer = new byte[BUFFER_SIZE];

    public SendFileThread(Socket socket) {
        init(socket);
    }

    private void init(Socket socket) {
        this.socket = socket;
        hostAdress = socket.getLocalAddress().getHostAddress();
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new BufferedInputStream(socket.getInputStream());
            outputStream = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            LOGGER.error("客户端：" + socket.getLocalAddress().getHostAddress() + "的IO流异常！");
        }
    }

    @Override
    public void run() {
        String fileName;
        Long fileLen;
        // 服务端处理线程
        try {
            fileName = dataInputStream.readUTF();
            System.out.println("文件名：" + fileName);
            if (StringUtils.isEmpty(fileName)) {
                close(this);
            }
        } catch (IOException e) {
            LOGGER.error("接收客户端：" + hostAdress + "文件名失败");
            return;
        }
        String filePath = ResourceBundle.getBundle("file-config").getString("sendPath") + fileName;
        RandomAccessFile file;
        try {
            file = new RandomAccessFile(filePath, "rw");
            fileLen = file.length();
            LOGGER.info("发送端传送文件：" + fileName + ", 客户端信息：" + hostAdress);
        } catch (Exception e) {
            LOGGER.error("发送端打开文件流失败, 客户端信息：" + hostAdress);
            return;
        }
        try {
            dataOutputStream.writeLong(fileLen);
        } catch (IOException e) {
            LOGGER.error("发送文件大小失败，客户端信息：" + hostAdress);
            return;
        }
        while (fileLen > 0) {
            try {
                fileLen -= file.read(buffer, 0, BUFFER_SIZE);
            } catch (IOException e) {
                LOGGER.error("发送端读取文件内容失败，客户端信息：" + hostAdress);
                return;
            }
            try {
                outputStream.write(buffer, 0, BUFFER_SIZE);
                outputStream.flush();
            } catch (IOException e) {
                LOGGER.error("发送端发送文件内容失败，客户端信息：" + hostAdress);
                return;
            }
        }
    }

    public static void close(SendFileThread sendFileThread) {
        CloseUtil.closeInputStream(sendFileThread.inputStream);
        CloseUtil.closeOutputStream(sendFileThread.outputStream);
        CloseUtil.closeInputStream(sendFileThread.dataInputStream);
        CloseUtil.closeOutputStream(sendFileThread.dataOutputStream);
        CloseUtil.closeSocket(sendFileThread.socket);
    }
}