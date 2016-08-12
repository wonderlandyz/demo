package cc.doublez.service.user;

import java.util.Set;

/**
 * Created by yz on 2016/8/2
 */
public interface IPermissionService {
    Set<String> getPermissionByUserId(int userId);
}
