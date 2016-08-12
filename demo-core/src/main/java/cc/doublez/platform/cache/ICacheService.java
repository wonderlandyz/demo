package cc.doublez.platform.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yz on 2016/4/28
 */
public interface ICacheService {
    /**
     * 缓存<Key,Value>并指定失效时间
     * @param key
     * @param value
     * @param expire 默认无限长（单位秒）
     */
    public void put(String key, Object value, Integer expire);

    /**
     * 获取key对应的value
     * @param key
     * @return
     */
    public String get(String key);

    /**
     * 删除key
     * @param key
     * @return
     */
    public Long del(String key);

    /**
     * 查询key
     * @param pattern
     * @return
     */
    public Set<String> search(String pattern);

    /**
     * 是否存在
     * @param key
     * @return
     */
    public boolean exist(String key);

    /**
     * Hash
     * @param key
     * @param field
     * @param value
     */
    public void hset(String key, String field, Object value);

    public String hget(String key, String field);

    public Map<String,String> hgetAll(String key);

    /**
     * List
     * @param key
     * @param value
     * @return
     */
    public Long lpush(String key, Object value);

    public Long rpush(String key, Object value);

    public List<String> lrange(String key);

    /**
     * 无序Set
     * @param key
     * @param value
     * @return
     */
    public Long sadd(String key, Object value);

    public Set<String> smembers(String key);

    public Long incr(String key);
}
