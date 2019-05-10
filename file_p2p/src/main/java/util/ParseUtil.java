package util;

import model.MessageInfo;

public class ParseUtil {
    public static MessageInfo parseMessage(String strMessage){
        String[] toMessage = strMessage.split("::");
        MessageInfo result = new MessageInfo(Integer.valueOf(toMessage[0]), toMessage[1],
                Integer.valueOf(toMessage[2]), toMessage[3], Integer.valueOf(toMessage[4]), toMessage[5]);
        return result;
    }
}
