package util;

import java.util.HashMap;

public class LogInfo {
    private HashMap<String, String> info;

    public LogInfo() {
        info = new HashMap<>();
    }

    public LogInfo(HashMap info) {
        this.info = info;
    }

    public void putInfo(String key, String value) {
        info.put(key, value);
    }

    @Override
    public String toString() {
        return info.toString();
    }
}
