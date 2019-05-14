import model.ConfigInfo;
import redis.clients.jedis.Jedis;
import resourcetable.ResourceTable;
import server.ClientInfoDTO;
import util.NodeTypeEnum;

/**
 *  by yidi on 5/10/19
 */

public class ResourceTableTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost",6379);
        jedis.flushAll();
        ClientInfoDTO clientInfoDTO = new ClientInfoDTO();
        clientInfoDTO.setClientId(1L);
        clientInfoDTO.setHost("127.0.0.1");
        clientInfoDTO.setPort("7788");
        clientInfoDTO.setType(NodeTypeEnum.CLIENT.getCode());

        ConfigInfo configInfo = new ConfigInfo();
        ResourceTable.registerClientInRedis(clientInfoDTO);
        ResourceTable.updateResourceTableForOnline(clientInfoDTO.getHost() + "|" + clientInfoDTO.getPort(), configInfo);
        ResourceTable.updateResourceTableForOffline(clientInfoDTO.getHost() + "|" + clientInfoDTO.getPort());
        ResourceTable.getClientsByFileName("Jony J - My City 南京.mp3").stream().forEach(System.out::println);
        System.out.println(ResourceTable.getAllFileInfo("Jony J"));
    }
}
