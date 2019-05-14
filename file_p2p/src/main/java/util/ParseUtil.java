package util;

import model.FileTaskInfo;
import model.MessageInfo;

public class ParseUtil {
    public static MessageInfo parseMessage(String strMessage){
        String[] toMessage = strMessage.split("::");
        MessageInfo result = new MessageInfo(Integer.valueOf(toMessage[0]), toMessage[1],
                Integer.valueOf(toMessage[2]), toMessage[3], Integer.valueOf(toMessage[4]), toMessage[5]);
        return result;
    }

    public static FileTaskInfo parseFileTask(String strFileTask) {
        String[] toFileTask = strFileTask.split("&&");
        FileTaskInfo result = new FileTaskInfo(toFileTask[0], toFileTask[1], Long.valueOf(toFileTask[2]),
                Long.valueOf(toFileTask[3]), Integer.valueOf(toFileTask[4]));
        return result;
    }

    public static long getHeaderLen(byte[] len) {
        return Long.parseLong(new String(len));
    }
}
