package resourcetable.redis;


import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * by yidi on 3/18/19
 */

public class RedisTest {
    private static final Logger LOGGER = Logger.getLogger(RedisTest.class);

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost",6379);
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());

        // 有关ClientInfo的test
        Map<String, String> clientInfo = new HashMap<>();
        clientInfo.put("1", "1.1.1|33000");
        clientInfo.put("2", "2.2.2|33001");
        jedis.hmset("clientInfo", clientInfo);
        Map<String, String> result = jedis.hgetAll("clientInfo");
        System.out.println(result);
        List<String> clientHost = jedis.hmget("clientInfo", "1");
        System.out.println(clientHost);
        Set<String> hostKeys = jedis.hkeys("clientInfo");
        System.out.println(hostKeys);
        jedis.hdel("clientInfo", "1");
        result = jedis.hgetAll("clientInfo");
        System.out.println(result);

        // 有关ResourceTable的test
        jedis.sadd("a", "1", "2", "4");
        jedis.sadd("b", "1", "2", "3", "5");
        System.out.println(jedis.smembers("a"));
        jedis.srem("a", "1");
        System.out.println(jedis.smembers("a"));
        System.out.println(jedis.sunion("a", "b"));

        // 再给一个badClient的key，用来存放那些经过降权处理的端
        jedis.sadd("badClient", "1", "5");
    }
}
