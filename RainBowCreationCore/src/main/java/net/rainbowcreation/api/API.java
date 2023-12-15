package net.rainbowcreation.api;

import java.util.HashMap;
import java.util.Map;

/**
 * InterFace To use RBClib or API
 */
public final class API {
    public Map<String, Object> centralMap;

    public API() {
        centralMap = new HashMap<>();
    }

    /**
     *
     * @param key
     * @return Object value
     */
    public Object getValue(String key) {
        return centralMap.get(key);
    }

    /**
     *
     * @param key
     * @param value
     */
    public void setValue(String key, Object value) {
        centralMap.put(key, value);
    }
}
