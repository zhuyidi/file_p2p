package server.message;

/**
 *  by yidi 5/8/19
 */

public enum ServerMessageEnum {
    FILE_TASK(0, "服务端下达发送任务"),
    NOTICE_SEND_COUNT(1, "服务端向请求端通知有几个发送端");

    private int code;
    private String desc;

    private ServerMessageEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }
}
