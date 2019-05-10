package util;

/**
 * by yidi on 3/22/19
 */

public enum NodeTypeEnum {
    SERVER(1, "SERVER"),
    CLIENT(2, "CLIENT");

    private int code;
    private String desc;

    private NodeTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
