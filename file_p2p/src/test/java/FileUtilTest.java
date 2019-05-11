import model.ConfigInfo;
import server.ClientInfoDTO;
import util.FileUtil;

import java.io.IOException;
import java.util.Set;

/**
 * by yidi on 3/22/19
 */

public class FileUtilTest {
    public static void main(String[] args) {
        ClientInfoDTO clientInfoDTO = new ClientInfoDTO("127.0.0.1", "33000");
        System.out.println(clientInfoDTO.getType() + " " + clientInfoDTO.getPort() + clientInfoDTO.getHost());
        Set<String> result = null;
        try {
            result = FileUtil.getClientResource(new ConfigInfo());
        } catch (IOException e) {}
        result.forEach(System.out::println);
    }
}
