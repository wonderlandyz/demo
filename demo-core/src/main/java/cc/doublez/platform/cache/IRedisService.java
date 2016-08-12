package cc.doublez.platform.cache;

import redis.clients.jedis.Jedis;

/**
 * Created by yz on 2016/4/28
 */
public interface IRedisService {
    /**
     * 获取Redis客户端连接，使用后需使用{@link #cleanup(Jedis)}清理
     * @return
     * @throws Exception
     * @since luoka @ 2014年8月7日 下午12:14:28
     */
    public Jedis getConnection() throws Exception;

    /**
     * 清理{@link #getConnection()}分配的Redis客户端连接
     * @param connection
     * @since luoka @ 2014年8月7日 下午12:14:41
     */
    public void cleanup(Jedis connection);
}