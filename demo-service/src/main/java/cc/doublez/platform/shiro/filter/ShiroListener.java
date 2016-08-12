package cc.doublez.platform.shiro.filter;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;

/**
 * 统计在线人数
 * Created by yz on 2016/7/27
 */
@WebListener
public class ShiroListener implements SessionListener {
    private static Integer count;
    @Override
    public void onStart(Session session) {//会话创建时触发
        //System.out.println("会话创建：" + session.getId());
    }

    @Override
    public void onStop(Session session) {//退出/会话过期时触发
        //System.out.println("会话退出：" + session.getId());
    }

    @Override
    public void onExpiration(Session session) {//会话过期时触发
        //System.out.println("会话退出：" + session.getId());
    }
}
