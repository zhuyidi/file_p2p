package resourcetable.redis;


import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * by yidi on 3/18/19
 */

public class RedisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost",6379);
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());

        jedis.flushAll();
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
        Map<String, String> clientInfo2 = new HashMap<>();
        clientInfo2.put("3", "3.3.3|33002");
        jedis.hmset("clientInfo", clientInfo2);
        result = jedis.hgetAll("clientInfo");
        System.out.println(result);
        Set<String> testSet = new HashSet<>();
        testSet.add("aaa");
        testSet.add("bbb");
        testSet.add("ccc");
        testSet.stream().forEach(e -> jedis.sadd("testSet", e));
        System.out.println(jedis.smembers("testSet"));

        // 有关ResourceTable的test(key:fileName; value:clientId)
        jedis.sadd("a", "1");
        System.out.println("a:" + jedis.smembers("a"));
        jedis.sadd("a", "2");
        System.out.println("a:" + jedis.smembers("a"));
        jedis.sadd("a", "3");
        System.out.println("a:" + jedis.smembers("a"));
        jedis.sadd("b", "1", "2", "3", "5");
        jedis.srem("a", "1");
        jedis.srem("cccc", "13333");
        System.out.println(jedis.smembers("a"));
        System.out.println(jedis.sunion("a", "b"));

        // 再给一个badClient的key，用来存放那些经过降权处理的端
        jedis.sadd("badClient", "1", "5");

        // jedis模糊查询key
        jedis.set("h", "h");
        jedis.set("e", "e");
        jedis.set("l", "l");
        jedis.set("hello", "o");
        System.out.println(jedis.keys("l*"));

        jedis.sadd("Jony j - 喜新恋旧", "1", "2", "3");
        jedis.sadd("Jony j - 28", "1", "2", "3");
        jedis.sadd("Jony j - 泛滥", "1", "2", "3");
        jedis.sadd("Jony j - 迷宫", "1", "2", "3");
    }
}
