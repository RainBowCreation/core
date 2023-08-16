package net.rainbowcreation.core.datamanager;

import net.rainbowcreation.core.core;

public class Manager {
    public static String get(String table, String key, String wkey, String wvalue) {
        String value;
        Boolean redis;
        Boolean mysql;
        String keys = encriptKey(table, key, wkey, wvalue);
        if (redis = Service.get("redis")) {
            if ((value = Service.getRedis().get(keys)) != null) {
                return value;
            }
        }
        if (mysql = Service.get("mysql")) {
            if ((value = Service.getMySql().get(table, key, wkey, wvalue)) != null) {
                if (redis)
                    Service.getRedis().set(keys, value);
                return value;
            }
        }
        value = core.getInstance().getConfig().getString(keys);
        if (redis)
            Service.getRedis().set(keys, value);
        return value;
    }

    public static String encriptKey(String table, String key, String wkey, String wvalue) {
        return  table+"."+key+"."+wkey+"."+wvalue;
    }
}
