package cc.doublez.platform.cache.impl;

import cc.doublez.platform.cache.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by yz on 2016/4/28
 */
@Service(value = "redisService")
public class RedisServiceImpl implements IRedisService{
    @Autowired
    @Qualifier("jedisPool")
    private JedisPool pool;

    @Override
    public Jedis getConnection() throws Exception {
        try{
           return pool.getResource();
        }catch (Exception e){
            throw new Exception("faild to connect redis");
        }
    }

    @Override
    public void cleanup(Jedis connection) {
        this.pool.returnResourceObject(connection);
    }
}
