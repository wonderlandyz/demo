package cc.doublez.platform.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 流程：
 1、首先判断用户有没有任意角色，如果没有返回false，将到onAccessDenied进行处理；
 2、如果用户没有角色，接着判断用户有没有登录，如果没有登录先重定向到登录；
 3、如果用户没有角色且设置了未授权页面（unauthorizedUrl），那么重定向到未授权页面；否则直接返回401未授权错误码。
 * Created by yz on 2016/8/3
 */
public class RoleFilter extends AccessControlFilter{
    static final String LOGIN_URL = "/x3i9ze7w/login";
    static final String UNAUTHORIZED_URL = "/x3i9ze7w/unauthorized";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //System.out.println(mappedValue+"role filter");
        String[] roles = (String[])mappedValue;
        Subject currentUser = getSubject(request, response);
        if(null!=roles){
            for (String role : roles) {
                if(currentUser.hasRole(role)){
                    return true;
                }
            }
        }
        return false;//跳到onAccessDenied处理
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (subject.getPrincipal() == null) {//表示没有登录，重定向到登录页面
            saveRequest(request);
            WebUtils.issueRedirect(request, response, LOGIN_URL);
        } else {
            System.out.println("没有授权");
            if (StringUtils.hasText(UNAUTHORIZED_URL)) {//如果有未授权页面跳转过去
                WebUtils.issueRedirect(request, response, UNAUTHORIZED_URL);
            } else {//否则返回401未授权状态码
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return false;
    }
}
