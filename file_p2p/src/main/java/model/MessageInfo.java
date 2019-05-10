package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  by yidi on 4/24/19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageInfo {
    // 发送方类型
    private int from;
    // 发送方信息
    private String fromInfo;
    // 接收方类型
    private int to;
    // 接收方信息
    private String toInfo;
    // 消息类型
    private int Action;
    // 消息内容
    private String messageContent;
}
