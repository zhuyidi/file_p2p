package client.send;

import model.ConfigInfo;
import model.FileTaskInfo;
import org.apache.log4j.Logger;
import util.CloseUtil;
import util.PackageUtil;

import java.io.*;
import java.net.Socket;
import java.util.ResourceBundle;

/**
 * by yidi on 3/18/19
 */

public class SendFileThread implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(SendFileThread.class);
    private Socket socket;
    private BufferedInputStream inputStream;
    private BufferedOutputStream outputStream;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String hostAdress;
    private byte[] buffer;
    private ConfigInfo configInfo;
    private FileTaskInfo fileTaskInfo;
    private final int BUFFER_SIZE = Integer.parseInt(ResourceBundle.getBundle("file-config").getString("bufferSize"));

    public SendFileThread(String host, int port, ConfigInfo configInfo, FileTaskInfo fileTaskInfo) {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            LOGGER.error("发送端连接接收端失败");
        }
        this.configInfo = configInfo;
        this.fileTaskInfo = fileTaskInfo;
        buffer = new byte[BUFFER_SIZE];
        init();
    }

    private void init() {
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
        try {
            dataOutputStream.writeUTF(PackageUtil.packageFileTask(fileTaskInfo));
        } catch (IOException e) {
            LOGGER.error("发送端发送任务头失败");
        }
        String path = configInfo.getSendPath() + fileTaskInfo.getTargetFilename();
        File testFile = new File(path);
        if (!testFile.exists()) {
            path = configInfo.getTargetPath() + fileTaskInfo.getTargetFilename();
        }

        RandomAccessFile file;
        long fileLen = fileTaskInfo.getSectionLen();
        try {
            file = new RandomAccessFile(path, "rw");
            file.seek(fileTaskInfo.getOffSet());
        } catch (Exception e) {
            LOGGER.error("发送端打开文件流失败, 客户端信息：" + hostAdress);
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
        close(this);
    }

    public static void close(SendFileThread sendFileThread) {
        CloseUtil.closeInputStream(sendFileThread.inputStream);
        CloseUtil.closeOutputStream(sendFileThread.outputStream);
        CloseUtil.closeInputStream(sendFileThread.dataInputStream);
        CloseUtil.closeOutputStream(sendFileThread.dataOutputStream);
        CloseUtil.closeSocket(sendFileThread.socket);
    }
}
