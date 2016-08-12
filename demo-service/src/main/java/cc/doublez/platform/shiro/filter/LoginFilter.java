package cc.doublez.platform.shiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
public class LoginFilter  extends AccessControlFilter {
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		System.out.println("shiro login filter");
		boolean isLogin = (null != SecurityUtils.getSubject().getPrincipal());
		if(isLogin || isLoginRequest(request, response)){
            return Boolean.TRUE;
        }
		return Boolean.FALSE ;
	}
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		System.out.println("没有登录");
		//保存Request和Response 到登录后的链接
		saveRequestAndRedirectToLogin(request, response);
		return Boolean.FALSE ;
	}
	

}
