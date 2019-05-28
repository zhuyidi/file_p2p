package server.message;

/**
 *  by yidi 5/8/19
 */

public enum ServerMessageEnum {
    FILE_TASK(0, "服务端下达发送任务"),
    NOTICE_SEND_COUNT(1, "服务端向请求端通知有几个发送端"),
    NOTICE_SEND_COUNT_AND_TASK(2, "服务端向请求端通知由几个发送端，并传达任务");

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
