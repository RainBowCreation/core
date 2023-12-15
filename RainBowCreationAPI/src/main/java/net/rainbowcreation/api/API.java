package net.rainbowcreation.api;

import com.earth2me.essentials.Essentials;
import net.rainbowcreation.api.utils.Logger;
import net.rainbowcreation.api.utils.RString;

import java.util.HashMap;
import java.util.Map;

public class API {
    private static API instance;
    public static Logger logger;
    public Map<String, Object> centralMap;
    public static RString rString;
    public static Class<Essentials> ess = Essentials.class;

    public static API getInstance() {
        if (instance == null) {
            instance = new API();
        }
        return instance;
    }
    public Object getValue(String key) {
        return centralMap.get(key);
    }

    public void setValue(String key, Object value) {
        centralMap.put(key, value);
    }

    private API() {
        centralMap = new HashMap<>();
    }
}
