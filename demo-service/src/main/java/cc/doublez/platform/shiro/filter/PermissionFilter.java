package cc.doublez.platform.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yz on 2016/8/3
 */
public class PermissionFilter extends AccessControlFilter{
    static final String LOGIN_URL = "/x3i9ze7w/login";
    static final String UNAUTHORIZED_URL = "/x3i9ze7w/unauthorized";
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //先判断带参数的权限判断
        Subject subject = getSubject(request, response);
        if(null != mappedValue){
            String[] arra = (String[])mappedValue;
            for (String permission : arra) {
                System.out.println(permission);
                if(subject.isPermitted(permission)){
                    return Boolean.TRUE;
                }
            }
        }
        //取到请求的uri ，进行权限判断
        String uri = ((HttpServletRequest)request).getRequestURI();
        System.out.println("请求的url:"+uri);
        if(subject.isPermitted(uri)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (null == subject.getPrincipal()) {//表示没有登录，重定向到登录页面
            saveRequest(request);
            WebUtils.issueRedirect(request, response, LOGIN_URL);
        } else {
            if (StringUtils.hasText(UNAUTHORIZED_URL)) {//如果有未授权页面跳转过去
                WebUtils.issueRedirect(request, response,UNAUTHORIZED_URL);
            } else {//否则返回401未授权状态码
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return Boolean.FALSE;
    }
}
