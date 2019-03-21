package client;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * by yidi on 3/20/19
 */

@Data
@AllArgsConstructor
public class ClientInfoDTO {
    private String host;
    private String port;
}
