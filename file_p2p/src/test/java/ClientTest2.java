import client.core.ClientCenter;
import model.ConfigInfo;

/**
 *  by yidi on 5/13/19
 */

public class ClientTest2 {
    public static void main(String[] args) {
        ClientCenter clientCenter = new ClientCenter(new ConfigInfo(
                "/Users/zhuyidi/sendFileData/client3/", "/Users/zhuyidi/recvFileData/client3/"));

    }
}
