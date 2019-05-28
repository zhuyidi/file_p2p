package client.receive;

import model.ConfigInfo;
import model.FileTaskInfo;
import observer.IReceiveSectionListener;
import observer.IReceiveSectionSpeaker;
import org.apache.log4j.Logger;
import util.CloseUtil;
import util.ParseUtil;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
    private final int BUFFER_SIZE = Integer.parseInt(ResourceBundle.getBundle("file-config").getString("bufferSize"));
    private FileTaskInfo fileTaskInfo;

    public ReceiveThread(Socket socket, ConfigInfo configInfo) {
        this.configInfo = configInfo;
        buffer = new byte[BUFFER_SIZE];
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

    @Override
    public void run() {
        try {
            fileTaskInfo = ParseUtil.parseFileTask(dataInputStream.readUTF());
        } catch (IOException e) {
            LOGGER.error("接收端接收任务头失败");
        }

        String fileName = fileTaskInfo.getTargetFilename();
        long fileLen = fileTaskInfo.getSectionLen();
        RandomAccessFile file;
        try {
            String filePath = configInfo.getTargetPath() + fileName + ".tmp." + fileTaskInfo.getSectionNum();
            file = new RandomAccessFile(filePath, "rw");
        } catch (FileNotFoundException e) {
            LOGGER.error("客户端：" + hostAdress + "创建目标文件失败！");
            close(this);
            return;
        }
        LOGGER.info("客户端：" + hostAdress + "开始接受文件");
        // 开始接收分片文件
        try {
            while (fileLen > 0) {
                int temp = inputStream.read(buffer, 0, fileLen > BUFFER_SIZE
                        ? BUFFER_SIZE : (int) fileLen);
                file.write(buffer, 0, temp);
                fileLen -= temp;
            }
        } catch (IOException e) {
            LOGGER.error("客户端：" + hostAdress + "接收" + fileName + "文件异常！");
            close(this);
            return;
        }
        LOGGER.info("客户端：" + hostAdress + "接收文件：" + fileName + "成功");
        sendGetOneSection(fileName);
    }

    private static void close(ReceiveThread clientThread) {
        CloseUtil.closeInputStream(clientThread.dataInputStream);
        CloseUtil.closeOutputStream(clientThread.dataOutputStream);
        CloseUtil.closeInputStream(clientThread.inputStream);
        CloseUtil.closeOutputStream(clientThread.outputStream);
        CloseUtil.closeSocket(clientThread.socket);
    }

    @Override
    public void addReceiveSectionListener(IReceiveSectionListener listener) {
        listeners.add(listener);
    }

    @Override
    public void sendGetOneSection(String fileName) {
        System.out.println("sendGetOneSection");
        System.out.println(listeners);
        for (IReceiveSectionListener listener : listeners) {
            listener.getReceiveOneSection(fileName);
        }
    }
}
