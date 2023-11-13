package net.rainbowcreation.core.datamanager.redis;

import net.rainbowcreation.core.Core;
import redis.clients.jedis.Jedis;

public class Redis {
    private static final Core plugin = Core.getInstance();
    private final Jedis redis;

    public Redis() {
        redis = new Jedis(plugin.getConfig().getString("redis.host"), plugin.getConfig().getInt("redis.port"));
        redis.auth(plugin.getConfig().getString("redis.requirepass"));
    }

    public Jedis getRedis() {
        return redis;
    }

    public String get(String key) {
        String value = null;
        if ((value = redis.get(key)) != null)
            redis.expire(key, 60 + redis.ttl(key));
        return value;
    }

    public void set(String key, String  value) {
        redis.setex(key, 60, value);
    }

    public Boolean ping() {
        return  redis.get("ping").equals("pong");
    }
}
