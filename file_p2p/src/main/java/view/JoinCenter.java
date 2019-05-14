package view;

import model.ConfigInfo;
import observer.IJoinFileListner;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ResourceBundle;

/**
 *  by yidi on 5/13/19
 */

public class JoinCenter implements IJoinFileListner {
    private ConfigInfo configInfo;
    private byte[] buffer;
    private final int BUFFER_SIZE = Integer.parseInt(ResourceBundle.getBundle("file-config").getString("bufferSize"));


    public JoinCenter(ConfigInfo configInfo) {
        this.configInfo = configInfo;
        buffer = new byte[BUFFER_SIZE];
    }

    @Override
    public void getStartJoinFile(String fileName, int sendCount) {
        System.out.println("开始合并");
        String filePath = configInfo.getTargetPath() + fileName;
        try {
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            int tempCount = 1;
            while (tempCount++ <= sendCount) {
                String tempFileName = filePath + ".tmp." + (tempCount-1);
                RandomAccessFile tempFile = new RandomAccessFile(tempFileName, "rw");
                long tempFileLen = tempFile.length();
                while (tempFileLen > 0) {
                    try {
                        int temp = tempFile.read(buffer, 0, tempFileLen > BUFFER_SIZE
                                ? BUFFER_SIZE : (int) tempFileLen);
                        file.write(buffer, 0, temp);
                        System.out.println("当前文件指针：" + file.getFilePointer());
                        tempFileLen -= temp;
                    } catch (IOException e) {
                        System.out.println("合并出错，" + e.getMessage());
                    }
                }
                tempFile.close();
                File delFile = new File(tempFileName);
                delFile.delete();
                System.out.println("临时文件已删除");
            }
            file.close();
        } catch (Exception e) {
            System.out.println("合并出错2：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
