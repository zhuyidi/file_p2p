package server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import util.NodeTypeEnum;

import java.net.Socket;

/**
 * by yidi on 3/20/19
 */

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ClientInfoDTO {
    private int type = NodeTypeEnum.CLIENT.getCode();
    private Socket socket;
    private long clientId;
    @NonNull
    private String host;
    @NonNull
    private String port;
}
