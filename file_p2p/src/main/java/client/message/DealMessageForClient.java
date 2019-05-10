package client.message;

import model.MessageInfo;
import resourcetable.ResourceTable;

/**
 *  by yidi on 5/8/19
 */

public class DealMessageForClient {
    public static void deal(MessageInfo messageInfo) {
        if (messageInfo.getAction() == ClientMessageEnum.UPDATE.getCode()) {
            update(messageInfo);
        } else if (messageInfo.getAction() == ClientMessageEnum.OFF_LINE.getCode()) {

        }
    }

    private static void update(MessageInfo messageInfo) {
        ResourceTable.updateResourceTableForOnline(messageInfo.getFromInfo());
    }

    private static void offline(MessageInfo messageInfo) {
        ResourceTable.updateResourceTableForOffline(messageInfo.getFromInfo());
    }
}