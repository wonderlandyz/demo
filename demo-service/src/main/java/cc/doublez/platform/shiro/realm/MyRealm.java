package cc.doublez.platform.shiro.realm;

import cc.doublez.domain.pojo.user.User;
import cc.doublez.service.login.ILoginService;
import cc.doublez.service.user.IPermissionService;
import cc.doublez.service.user.IRoleService;
import cc.doublez.service.user.IUserService;
import org.apache.activemq.selector.Token;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

import static java.awt.SystemColor.info;
import static org.apache.shiro.web.filter.mgt.DefaultFilter.user;

/**
 * Created by yz on 2016/8/1
 */
public class MyRealm extends AuthorizingRealm {
    @Resource
    private IUserService userService;
    @Resource
    private IRoleService roleService;
    @Resource
    private IPermissionService permissionService;
    /**
     * 授权信息
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        System.out.println("授权信息");
        String phone =(String)principal.getPrimaryPrincipal();
        User user = this.userService.getByPhone(phone);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //查询用户角色
        Set<String> roles = roleService.getRoleByUserId(user.getId());
       /* for (String s:roles){
            System.out.println(s);
        }*/
        authorizationInfo.setRoles(roles);
        //查询用户权限
        Set<String> permissions = permissionService.getPermissionByUserId(user.getId());
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 认证信息
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证信息");
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        User user = this.userService.login(token.getUsername(),token.getPassword());
        if(user == null) {
            throw new UnknownAccountException("没有该用户");//没找到帐号
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getPhone(),user.getPassword(),this.getName());
        this.setSession("user",user);//从数据库查询出的user对象
        return authenticationInfo;
    }

    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     *    比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    private void setSession(Object key, Object value){
        Subject currentUser = SecurityUtils.getSubject();
        if(null != currentUser){
            Session session = currentUser.getSession();
            //log.info("key=[{}],value=[{}]插入session[{}],host=[{}]" ,key,value,session.getId(),session.getHost());
            if(null != session){
                session.setAttribute(key, value);
            }
        }
    }
}
