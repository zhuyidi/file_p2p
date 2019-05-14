package resourcetable;

import model.ConfigInfo;
import server.ClientInfoDTO;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import retry.Retryable;
import util.FileUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * by yidi on 3/19/19
 */

public class ResourceTable {
    private static final Logger LOGGER = Logger.getLogger(ResourceTable.class);
    private static Jedis jedis;

    // 初始化ResourceTable：连接redis
    static {
        jedis = new Jedis("localhost",6379);
    }

    // 客户端在redis进行IP注册
    // action1: 在clientInfo key中，注册这个客户端的信息，格式：<host|post, clientID>
    // action2: 创建这个客户端的资源列表，格式: <clientID, fileName1 filename2...>
    @Retryable
    public static boolean registerClientInRedis(ClientInfoDTO clientInfoDTO) {
        // 注册IP信息
        String key = clientInfoDTO.getHost() + "|" + clientInfoDTO.getPort();
        Map<String, String> clientInfo = new HashMap<>();
        clientInfo.put(key, String.valueOf(clientInfoDTO.getClientId()));
        try {
            jedis.hmset("clientInfo", clientInfo);
        } catch (Exception e) {
            LOGGER.error("客户端：" + clientInfoDTO.getHost() + "上线信息注册失败");
            return false;
        }
        return true;
    }

    // 客户端上线更新fileName key的value
    public static boolean updateResourceTableForOnline(String clientHost, ConfigInfo configInfo) {
        Set<String> fileNames;
        try {
            fileNames = FileUtil.getClientResource(configInfo);
        } catch (Exception e) {
            LOGGER.error("客户端：" + clientHost + "获取本地资源失败");
            return false;
        }
        String clientID = jedis.hmget("clientInfo", clientHost).get(0);
        try {
            fileNames.stream().forEach(e -> {
                jedis.sadd(clientID, e);
                addClientIdInFileKey(clientID, e);
            });
        } catch (Exception e) {
            LOGGER.error("客户端：" + clientHost + "更新fileName key的value失败 " + e.getMessage());
            return false;
        }
        return true;
    }

    // 客户端下线更新资源信息
    public static boolean updateResourceTableForOffline(String clientHost) {
        try {
            String clientID = jedis.hmget("clientInfo", clientHost).get(0);
            jedis.hdel("clientInfo", clientHost);
            Set<String> fileNames = jedis.smembers(clientID);
            fileNames.stream().forEach(e -> delClientIdInFileKey(clientID, e));
            jedis.del(clientID);
        } catch (Exception e) {
            LOGGER.error("客户端：" + clientHost + "下线更新资源信息失败 " + e.getMessage());
        }
        return true;
    }

    // 客户端根据keyword模糊查询资源表
    public static Set<String> queryFileNameByKeyword(String keyWord) {
        String[] tempStr = keyWord.split("");
        String kw = "*" + Arrays.stream(tempStr).map(e -> e+"*").reduce("", String::concat);
        Set<String> result = jedis.keys(kw);
        return result;
    }

    // 根据文件名获取持有本文件的端
    public static Set<String> getClientsByFileName(String fileName) {
        Set<String> result = jedis.smembers(fileName);
        return result;
    }

    // 获取badClient
    public static Set<String> getBadClients() {
        Set<String> result = jedis.smembers("badClient");
        return result;
    }

    // 根据文件名获取文件的信息：文件名&&文件大小
    public static String getAllFileInfo(String fileName) {
        return jedis.keys(fileName + "*").iterator().next();
    }

    public static void setBadClient(String clientId) {
        jedis.sadd("badClient", clientId);

    }

    private static void addClientIdInFileKey(String clientID, String fileName) {
        jedis.sadd(fileName, clientID);
    }

    private static void delClientIdInFileKey(String clientID, String fileName) {
        jedis.srem(fileName, clientID);
    }

    // and so on
}
