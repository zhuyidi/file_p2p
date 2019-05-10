package client.message;

/**
 *  by yidi on 5/8/19
 */

public enum ClientMessageEnum {
    ON_LINE(0, "客户端上线"),
    OFF_LINE(1, "客户端下线"),
    UPDATE(2, "客户端更新资源"),
    REQUEST(3, "客户端请求资源");

    private int code;
    private String desc;

    private ClientMessageEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode(){
        return this.code;
    }
}
