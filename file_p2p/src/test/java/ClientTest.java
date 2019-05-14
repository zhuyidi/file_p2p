import client.core.ClientCenter;
import model.ConfigInfo;
import redis.clients.jedis.Jedis;

import java.io.IOException;

/**
 * by yidi on 3/7/19
 */

public class ClientTest {
    public static void main(String[] args) throws IOException {
//        Jedis jedis = new Jedis("localhost",6379);
//        jedis.flushAll();
        ClientCenter clientCenter1 = new ClientCenter(new ConfigInfo(
                "/Users/zhuyidi/sendFileData/client1/", "/Users/zhuyidi/recvFileData/client1/"));
        ClientCenter clientCenter2 = new ClientCenter(new ConfigInfo(
                "/Users/zhuyidi/sendFileData/client2/", "/Users/zhuyidi/recvFileData/client2/"));

    }
}
