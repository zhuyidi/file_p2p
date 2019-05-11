import client.core.ClientCenter;
import model.ConfigInfo;
import java.io.IOException;

/**
 * by yidi on 3/7/19
 */

public class ClientTest {
    public static void main(String[] args) throws IOException {
        ClientCenter clientCenter = new ClientCenter(new ConfigInfo());
    }
}
