package cc.doublez.platform.shiro.manager;

import cc.doublez.platform.shiro.realm.MyRealm;
import cc.doublez.util.SpringContextUtil;

/**
 * Shiro管理下的Token工具类
 * Created by yz on 2016/8/2
 */
public class TokenManager {
    //用户登录管理
    public static final MyRealm realm = SpringContextUtil.getBean("myRealm",MyRealm.class);
}
