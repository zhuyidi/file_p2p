package client.message;

import model.ConfigInfo;
import model.MessageInfo;
import resourcetable.ResourceTable;

/**
 *  by yidi on 5/8/19
 */

public class DealMessageForClient {
    public static void deal(MessageInfo messageInfo, ConfigInfo configInfo) {
        if (messageInfo.getAction() == ClientMessageEnum.UPDATE.getCode()) {
            update(messageInfo, configInfo);
        } else if (messageInfo.getAction() == ClientMessageEnum.OFF_LINE.getCode()) {

        }
    }

    private static void update(MessageInfo messageInfo, ConfigInfo configInfo) {
        ResourceTable.updateResourceTableForOnline(messageInfo.getFromInfo(), configInfo);
    }

    private static void offline(MessageInfo messageInfo) {
        ResourceTable.updateResourceTableForOffline(messageInfo.getFromInfo());
    }
}
