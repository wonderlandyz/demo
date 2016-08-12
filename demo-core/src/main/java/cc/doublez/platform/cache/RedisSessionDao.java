package cc.doublez.platform.cache;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * 如DefaultSessionManager在创建完session后会调用该方法
 * Created by yz on 2016/8/9
 */
@Service
public class RedisSessionDao extends EnterpriseCacheSessionDAO {
    private static final Logger LOG = LoggerFactory.getLogger(RedisSessionDao.class);

    @Autowired
    @Qualifier("cacheService")
    private ICacheService cacheService;
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        LOG.info("创建seesion,id=[{}]",session.getId().toString());
        try {
            cacheService.put(sessionId.toString(),serialize(session),null);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        LOG.info("获取seesion,id=[{}]",serializable.toString());
        Session session = null;
        try {
            session = (Session) deserialize(cacheService.get(serializable.toString()));
        } catch (Exception e) {
            LOG.error(e.getMessage()+"没有获取到");
        }
        //TODO 获取失败（redis连接超时等）处理
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        LOG.info("更新seesion,id=[{}]",session.getId().toString());
        try {
            cacheService.put(session.getId().toString(),serialize(session),null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Session session) {
        LOG.info("删除seesion,id=[{}]",session.getId().toString());
        try {
            cacheService.del(session.getId().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        LOG.info("获取存活的session");
        Set<String> result = cacheService.search("*");
        Collection<Session> sessions = new ArrayList<Session>();
        if(CollectionUtils.isNotEmpty(result)){
            for(String s:result){
                sessions.add((Session)deserialize(cacheService.get(s)));
            }
        }
        return sessions;
    }

    private static Object deserialize(String str) {
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(Base64.decode(str));
            ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("deserialize session error", e);
        } finally {
            try {
                ois.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static String serialize(Object obj) {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            return Base64.encodeToString(bos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("serialize session error", e);
        } finally {
            try {
                oos.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
