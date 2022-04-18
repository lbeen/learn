package lbeen.common.redis;

import org.crazycake.shiro.RedisManager;

/**
 * Desc: Jedis 操作客户端
 * Created by hafiz.zhang on 2017/7/21.
 */
public class RedisClient extends RedisManager {
    private static RedisClient REDIS_CLIENT;

    public static RedisClient getClient() {
        return REDIS_CLIENT;
    }

    public RedisClient() {
        setHost("192.168.1.111");
        setPort(6379);
        setPassword("123");
        setCount(10);
        REDIS_CLIENT = this;
    }
}