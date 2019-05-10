package util;

import model.MessageInfo;

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
}
