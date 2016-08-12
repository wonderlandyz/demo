package cc.doublez.platform.listener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 统计在线人数
 * Created by yz on 2016/7/27
 */
@WebListener
public class OnlineListener implements HttpSessionListener{
    /*当前在线人数*/
    private static Integer count;
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        ServletContext context=event.getSession().getServletContext();
        count=(Integer)(context.getAttribute("count"));
        if(count==null){
            count=0;
        }else{
            count++;
        }
        context.setAttribute("count", count);//保存人数
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        ServletContext context=event.getSession().getServletContext();
        count=(Integer)context.getAttribute("count");
        if(count>0){
            count--;
        }
        context.setAttribute("count", count);
    }
}
