import client.core.ClientCenter;
import model.ConfigInfo;

public class ClientTest3 {
    public static void main(String[] args) {
        ClientCenter clientCenter = new ClientCenter(new ConfigInfo(
                "/Users/zhuyidi/sendFileData/client7/", "/Users/zhuyidi/recvFileData/client7/"));

    }
}
