package client;

import lombok.AllArgsConstructor;
import lombok.Data;
import util.NodeTypeEnum;

/**
 * by yidi on 3/20/19
 */

@Data
@AllArgsConstructor
public class ClientInfoDTO {
    private int type = NodeTypeEnum.CLIENT.getCode();
    private String host;
    private String port;
}
