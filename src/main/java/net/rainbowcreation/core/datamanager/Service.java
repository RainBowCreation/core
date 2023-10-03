package net.rainbowcreation.core.datamanager;

import net.rainbowcreation.core.chat.Console;
import net.rainbowcreation.core.Core;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public class Service {
    private static final Core plugin = Core.getInstance();
    private static boolean mysql;
    private static boolean cur_mysql;
    private static Redis redisClass;
    private static boolean redis;
    private static MySql mySqlClass;
    private static boolean cur_redis;
    public static void initialize() {
        FileConfiguration config = plugin.getConfig();
        if (redis = config.getBoolean("redis.enable"))
            redisClass = new Redis();
        if (mysql = config.getBoolean("mySQL.enable")) {
            mySqlClass = new MySql();
            if (config.getBoolean("mySQL.heartbeat_fix")) {
                mySqlClass.setup();
                Console.info("update config");
                config.set("mySQL.heartbeat_fix", false);
                plugin.saveConfig();
            }
        }
        Console.info("mySQL :"+mysql);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (mysql) {
                    cur_mysql = mySqlClass.ping();
                }
                if (redis) {
                    cur_redis = redisClass.ping();
                }
            }
        }.runTaskTimer(plugin,1,6000);
    }

    public static boolean get(String service) {
        switch (service.toLowerCase()) {
            case ("mysql") -> {
                return cur_mysql;
            }
            case ("redis") -> {
                return cur_redis;
            }
        }
        return false;
    }

    public static Redis getRedis() {
        return redisClass;
    }
    public static MySql getMySql() {
        return mySqlClass;
    }
    public void close() {
        if (cur_mysql)
            mySqlClass.close();
    }
}
