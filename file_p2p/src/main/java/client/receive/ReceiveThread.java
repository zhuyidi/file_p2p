package client.receive;

import model.ConfigInfo;
import observer.IReceiveSectionListener;
import observer.IReceiveSectionSpeaker;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import util.CloseUtil;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * by yidi on 3/7/19
 */

public class ReceiveThread implements Runnable, IReceiveSectionSpeaker {
    private static final Logger LOGGER = Logger.getLogger(ReceiveThread.class);
    private Socket socket;
    private String hostAdress;
    private BufferedInputStream inputStream;
    private BufferedOutputStream outputStream;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private byte[] buffer;
    private ConfigInfo configInfo;
    private List<IReceiveSectionListener> listeners = new ArrayList<>();

    public ReceiveThread(Socket socket, ConfigInfo configInfo) {
        this.configInfo = configInfo;
        buffer = new byte[configInfo.getBufferSize()];
        init(socket);
    }

    private void init(Socket socket) {
        this.socket = socket;
        hostAdress = socket.getLocalAddress().getHostAddress();
        try {
            inputStream = new BufferedInputStream(socket.getInputStream());
            outputStream = new BufferedOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            LOGGER.error("客户端：" + hostAdress + "初始化IO流失败！");
        }
    }

    // todo
    // 接下来在ReceiveThread里面要做的就是接收分片文件。
    // 每接收完一个分片文件，向ReceiveCenter通知一次，在RC里判断所有的分片是否接收完毕，接收完毕后关闭接收服务器

    @Override
    public void run() {
        String fileName; // todo Demo字段
        long fileLen; // todo Demo字段

        System.out.println("请输入需要接收的文件名：");
        // 输入需要接受的文件名，传送给服务端
        Scanner scanner = new Scanner(System.in);
        fileName = scanner.nextLine();
        if (StringUtils.isEmpty(fileName)) {
            close(this);
            return;
        }
        try {
            dataOutputStream.writeUTF(fileName);
        } catch (IOException e) {
            LOGGER.error("客户端：" + hostAdress + "发送文件名失败");
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle("file-config");
        String targetPath = resourceBundle.getString("targetPath");

        // 接收服务端传送过来的文件大小
        try {
            fileLen = dataInputStream.readLong();
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
         LOGGER.info("客户端：" + hostAdress + "开始接受文件");

        // 开始接收文件
        try {
            while (fileLen > 0) {
                int temp = inputStream.read(buffer, 0, fileLen > configInfo.getBufferSize()
                        ? configInfo.getBufferSize() : (int) fileLen);
                file.write(buffer, 0, temp);
                fileLen -= temp;
            }
        } catch (IOException e) {
            LOGGER.error("客户端：" + hostAdress + "接收" + fileName + "文件异常！");
            close(this);
            return;
        }
        LOGGER.info("客户端：" + hostAdress + "接收文件：" + fileName + "成功");
    }

    private static void close(ReceiveThread clientThread) {
        CloseUtil.closeInputStream(clientThread.dataInputStream);
        CloseUtil.closeOutputStream(clientThread.dataOutputStream);
        CloseUtil.closeInputStream(clientThread.inputStream);
        CloseUtil.closeOutputStream(clientThread.outputStream);
        CloseUtil.closeSocket(clientThread.socket);
    }

    @Override
    public void setListener(List<IReceiveSectionListener> listeners) {
        this.listeners = listeners;
    }

    @Override
    public void addListener(IReceiveSectionListener listener) {
        this.listeners.add(listener);
    }
}
