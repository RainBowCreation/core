package net.rainbowcreation.core.datamanager;

import net.rainbowcreation.core.core;
import redis.clients.jedis.Jedis;

public class Redis {
    private final Jedis redis;

    public Redis() {
        redis = new Jedis(core.getInstance().getConfig().getString("redis.host"), core.getInstance().getConfig().getInt("redis.port"));
        redis.auth(core.getInstance().getConfig().getString("redis.requirepass"));
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
