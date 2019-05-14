package util;

import model.FileTaskInfo;
import model.MessageInfo;

import java.util.ResourceBundle;

/**
 *  by yidi on 5/8/19
 */

public class PackageUtil {
    public static String packageMessage(MessageInfo messageInfo) {
        StringBuffer result = new StringBuffer();
        result.append(messageInfo.getFrom());
        result.append("::");
        result.append(messageInfo.getFromInfo());
        result.append("::");
        result.append(messageInfo.getTo());
        result.append("::");
        result.append(messageInfo.getToInfo());
        result.append("::");
        result.append(messageInfo.getAction());
        result.append("::");
        result.append(messageInfo.getMessageContent());
        return result.toString();
    }

    public static String packageFileTask(FileTaskInfo fileTaskInfo) {
        StringBuffer result = new StringBuffer();
        result.append(fileTaskInfo.getClientId());
        result.append("&&");
        result.append(fileTaskInfo.getTargetFilename());
        result.append("&&");
        result.append(fileTaskInfo.getOffSet());
        result.append("&&");
        result.append(fileTaskInfo.getSectionLen());
        result.append("&&");
        result.append(fileTaskInfo.getSectionNum());
        return result.toString();
    }

    public static byte[] addHeader(String info) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("file-config");
        int size = Integer.parseInt(resourceBundle.getString("headerSize"));
        long headerLen = info.getBytes().length;

        long temp = 10;
        for (int i = 1; i < size; i++) {
            temp *= 10;
        }
        String header = String.valueOf(headerLen + temp).substring(1);
        byte[] result = (header + info).getBytes();
        return result;
    }
}
