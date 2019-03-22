import client.ClientInfoDTO;
import util.FileUtil;
import util.NodeTypeEnum;

import java.util.Set;

/**
 * by yidi on 3/22/19
 */

public class FileUtilTest {
    public static void main(String[] args) {
        ClientInfoDTO clientInfoDTO = new ClientInfoDTO(NodeTypeEnum.CLIENT.getCode(), "127.0.0.1", "33001");
        Set<String> result = FileUtil.getClientResource(clientInfoDTO);
        result.forEach(System.out::println);
    }
}
