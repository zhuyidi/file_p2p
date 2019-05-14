package model;

import lombok.*;

/**
 *  by yidi on 5/11/19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigInfo {
    private String sendPath = "/Users/zhuyidi/sendFileData/";
    private String targetPath = "/Users/zhuyidi/recvFileData/";
}
