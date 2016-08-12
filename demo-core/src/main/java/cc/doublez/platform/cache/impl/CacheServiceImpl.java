package cc.doublez.platform.cache.impl;

import cc.doublez.constant.RedisConstant;
import cc.doublez.platform.cache.ICacheService;
import cc.doublez.platform.cache.IRedisService;
import cc.doublez.util.SerializeUtil;
import cc.doublez.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yz on 2016/4/28
 */
@Service(value = "cacheService")
public class CacheServiceImpl implements ICacheService{
    private static final Logger LOG = LoggerFactory.getLogger(CacheServiceImpl.class);
    @Autowired
    private IRedisService redisService;

    @Override
    public void put(String key, Object value, Integer expire) {
        Jedis connection = null;
        try {
            connection = redisService.getConnection();
        } catch (Exception e) {
            LOG.error("connect redis has error,please check redis connection is valid!");
            return;
        }

        String str = SerializeUtil.writeAsString(value);
        String finalKey = buildKey(key);
        try {
            connection.set(finalKey, str);
            if(expire != null && expire > 0){
                connection.expire(finalKey, (int)expire);
            }
        }catch (Exception e){
            LOG.error("redis set key[" + buildKey(key) + "] has error.",e);
        }finally {
            redisService.cleanup(connection);
        }
    }
    private String buildKey(String key) {
        if(StringUtil.isNotEmpty(key) && StringUtil.startWith(key, RedisConstant.NS_SYSTEM)){
            return key;
        }
        return RedisConstant.NS_SYSTEM + key;
    }
    @Override
    public String get(String key) {
        Jedis connection = null;
        try {
            connection = redisService.getConnection();
        } catch (Exception e) {
            LOG.error("connect redis has error,please check redis connection is valid!");
            return "";
        }
        try {
            return connection.get(buildKey(key));
        } catch (Exception e) {
            LOG.error("redis get key[" + buildKey(key) + "] has error.",e);
            return null;
        } finally {
            redisService.cleanup(connection);
        }
    }

    @Override
    public Long del(String key) {
        Jedis connection = null;
        try {
            connection = redisService.getConnection();
        } catch (Exception e) {
            LOG.error("connect redis has error,please check redis connection is valid!");
            return 0l;
        }
        try {
            return connection.del(buildKey(key));
        } catch (Exception e) {
            LOG.error("redis del key[" + buildKey(key) + "] has error.",e);
            return 0l;
        } finally {
            redisService.cleanup(connection);
        }
    }

    @Override
    public Set<String> search(String pattern) {
        Jedis connection = null;
        try {
            connection = redisService.getConnection();
        } catch (Exception e) {
            LOG.error("connect redis has error,please check redis connection is valid!");
            return Collections.emptySet();
        }
        try {
            return connection.keys(RedisConstant.NS_SYSTEM+"*"+pattern+"*");
        } catch (Exception e) {
            LOG.error("redis search key has error.",e);
            return Collections.emptySet();
        } finally {
            redisService.cleanup(connection);
        }
    }

    @Override
    public boolean exist(String key) {
        Jedis connection = null;
        try {
            connection = redisService.getConnection();
        } catch (Exception e) {
            LOG.error("connect redis has error,please check redis connection is valid!");
        }
        try {
            return connection.exists(buildKey(key));
        } catch (Exception e) {
            LOG.error("redis get key has error.",e);
        } finally {
            redisService.cleanup(connection);
        }
        return  false;
    }

    @Override
    public void hset(String key, String field, Object value) {
        Jedis connection = null;
        try {
            connection = redisService.getConnection();
        } catch (Exception e) {
            LOG.error("connect redis has error,please check redis connection is valid!");
        }

        try {
            String finalKey = buildKey(key);
            connection.hset(finalKey, field, SerializeUtil.writeAsString(value));
        } catch (Exception e) {
            LOG.error("redis hset key"+buildKey(key)+" has error.",e);
        } finally {
            redisService.cleanup(connection);
        }
    }

    @Override
    public String hget(String key, String field) {
        Jedis connection = null;
        try {
            connection = redisService.getConnection();
        } catch (Exception e) {
            LOG.error("connect redis has error,please check redis connection is valid!");
            return "";
        }
        try {
            return connection.hget(buildKey(key), field);
        } catch (Exception e) {
            LOG.error("redis get key[" + buildKey(key) + "] field[" + field + "] has error.",e);
            return null;
        } finally {
            redisService.cleanup(connection);
        }
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        Jedis connection = null;
        try {
            connection = redisService.getConnection();
        } catch (Exception e) {
            LOG.error("connect redis has error,please check redis connection is valid!");
            return Collections.emptyMap();
        }
        try {
            return connection.hgetAll(buildKey(key));
        } catch (Exception e) {
            LOG.error("redis get key[" + buildKey(key) + "] has error.",e);
            return Collections.emptyMap();
        } finally {
            redisService.cleanup(connection);
        }
    }

    @Override
    public Long lpush(String key, Object value) {
        Jedis connection = null;
        try {
            connection = redisService.getConnection();
        } catch (Exception e) {
            LOG.error("connect redis has error,please check redis connection is valid!");
            return 0l;
        }
        try {
            return connection.lpush(buildKey(key),SerializeUtil.writeAsString(value));
        } catch (Exception e) {
            LOG.error("redis lpush key[" + buildKey(key) + "] has error.",e);
            return 0l;
        } finally {
            redisService.cleanup(connection);
        }
    }
    @Override
    public Long rpush(String key, Object value) {
        Jedis connection = null;
        try {
            connection = redisService.getConnection();
        } catch (Exception e) {
            LOG.error("connect redis has error,please check redis connection is valid!");
            return 0l;
        }
        try {
            return connection.rpush(buildKey(key),SerializeUtil.writeAsString(value));
        } catch (Exception e) {
            LOG.error("redis lpush key[" + buildKey(key) + "] has error.",e);
            return 0l;
        } finally {
            redisService.cleanup(connection);
        }
    }

    @Override
    public List<String> lrange(String key) {
        Jedis connection = null;
        try {
            connection = redisService.getConnection();
        } catch (Exception e) {
            LOG.error("connect redis has error,please check redis connection is valid!");
            return Collections.emptyList();
        }
        try {
            return connection.lrange(buildKey(key),0,connection.llen(buildKey(key)));
        } catch (Exception e) {
            LOG.error("redis lrange key[" + buildKey(key) + "] has error.",e);
            return Collections.emptyList();
        } finally {
            redisService.cleanup(connection);
        }
    }

    @Override
    public Long sadd(String key, Object value) {
        Jedis connection = null;
        try {
            connection = redisService.getConnection();
        } catch (Exception e) {
            LOG.error("connect redis has error,please check redis connection is valid!");
            return 0l;
        }
        try {
            return connection.sadd(buildKey(key),SerializeUtil.writeAsString(value));
        } catch (Exception e) {
            LOG.error("redis sadd key[" + buildKey(key) + "] has error.",e);
            return 0l;
        } finally {
            redisService.cleanup(connection);
        }
    }

    @Override
    public Set<String> smembers(String key) {
        Jedis connection = null;
        try {
            connection = redisService.getConnection();
        } catch (Exception e) {
            LOG.error("connect redis has error,please check redis connection is valid!");
            return Collections.emptySet();
        }
        try {
            return connection.smembers(buildKey(key));
        } catch (Exception e) {
            LOG.error("redis smembers key[" + buildKey(key) + "] has error.",e);
            return Collections.emptySet();
        } finally {
            redisService.cleanup(connection);
        }
    }

    @Override
    public Long incr(String key) {
        Jedis connection = null;
        try {
            connection = redisService.getConnection();
        } catch (Exception e) {
            LOG.error("connect redis has error,please check redis connection is valid!");
            return 0l;
        }
        try {
            return connection.incr(buildKey(key));
        } catch (Exception e) {
            LOG.error("redis incr key[" + buildKey(key) + "] has error.",e);
            return 0l;
        } finally {
            redisService.cleanup(connection);
        }
    }
}
