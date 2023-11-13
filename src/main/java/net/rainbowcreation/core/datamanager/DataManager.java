package net.rainbowcreation.core.datamanager;

import net.rainbowcreation.core.Core;
import net.rainbowcreation.core.datamanager.service.Service;

public class DataManager {
    private static final Core plugin = Core.getInstance();

    public static String get(String table, String key, String wkey, String wvalue) {
        String value;
        boolean redis;
        boolean mysql;
        String keys = encriptKey(table, key, wkey, wvalue);
        if (redis = Service.get("redis"))
            if ((value = Service.getRedis().get(keys)) != null)
                return value;
        if (mysql = Service.get("mysql")) {
            if ((value = Service.getMySql().get(table, key, wkey, wvalue)) != null) {
                if (redis)
                    Service.getRedis().set(keys, value);
                return value;
            }
        }
        value = plugin.getConfig().getString(keys);
        if (redis)
            Service.getRedis().set(keys, value);
        return value;
    }

    public static String get(String keys) {
        String value;
        boolean redis;
        boolean mysql;
        if (redis = Service.get("redis"))
            if ((value = Service.getRedis().get(keys)) != null)
                return value;
        if (mysql = Service.get("mysql")) {
            String[] keylst = decriptKey(keys);
            if ((value = Service.getMySql().get(keylst[0], keylst[1], keylst[2], keylst[3])) != null) {
                if (redis)
                    Service.getRedis().set(keys, value);
                return value;
            }
        }
        value = plugin.getConfig().getString(keys);
        if (redis)
            Service.getRedis().set(keys, value);
        return value;
    }

    public static String encriptKey(String table, String key, String wkey, String wvalue) {
        return  table+"."+key+"."+wkey+"."+wvalue;
    }

    public static String[] decriptKey(String keys) {
        String[] keylst = new String[4];
        String tmp;
        for (int i = 0; i < 4; i++) {
            int j = keys.indexOf(".");
            if (j != -1)
                keylst[i] = keys.substring(0,  j);
            keys = keys.substring(j);
        }
        return keylst;
    }
}
