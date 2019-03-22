package util;

/**
 * by yidi on 3/22/19
 */

public enum NodeTypeEnum {
    SERVER(1),
    CLIENT(2);

    private int code;

    private NodeTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
