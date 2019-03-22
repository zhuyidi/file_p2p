package resourcetable;

import client.ClientInfoDTO;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

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

    // 客户端上线更新资源信息
    public static boolean updateResourceTable(ClientInfoDTO clientInfoDTO) {
        String key = clientInfoDTO.getHost() + "|" + clientInfoDTO.getPort();
        
        return false;
    }

    // 客户端下线更新资源信息

    // and so on
}
