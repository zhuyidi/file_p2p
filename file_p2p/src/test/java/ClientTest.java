import client.core.ClientCenter;
import model.ConfigInfo;
import redis.clients.jedis.Jedis;

import java.io.IOException;

/**
 * by yidi on 3/7/19
 */

public class ClientTest {
    public static void main(String[] args) throws IOException {
        Jedis jedis = new Jedis("localhost",6379);
        jedis.flushAll();
        ClientCenter clientCenter1 = new ClientCenter(new ConfigInfo(
                "/Users/zhuyidi/sendFileData/client1/", "/Users/zhuyidi/recvFileData/client1/"));
        ClientCenter clientCenter2 = new ClientCenter(new ConfigInfo(
                "/Users/zhuyidi/sendFileData/client2/", "/Users/zhuyidi/recvFileData/client2/"));
        ClientCenter clientCenter3 = new ClientCenter(new ConfigInfo(
                "/Users/zhuyidi/sendFileData/client3/", "/Users/zhuyidi/recvFileData/client3/"));
        ClientCenter clientCenter4 = new ClientCenter(new ConfigInfo(
                "/Users/zhuyidi/sendFileData/client4/", "/Users/zhuyidi/recvFileData/client4/"));
//        ClientCenter clientCenter5 = new ClientCenter(new ConfigInfo(
//                "/Users/zhuyidi/sendFileData/client5/", "/Users/zhuyidi/recvFileData/client5/"));
//        ClientCenter clientCenter6 = new ClientCenter(new ConfigInfo(
//                "/Users/zhuyidi/sendFileData/client6/", "/Users/zhuyidi/recvFileData/client6/"));
//        ClientCenter clientCenter7 = new ClientCenter(new ConfigInfo(
//                "/Users/zhuyidi/sendFileData/client7/", "/Users/zhuyidi/recvFileData/client7/"));
    }
}
