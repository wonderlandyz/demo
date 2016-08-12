package cc.doublez.service.user;

import cc.doublez.domain.pojo.user.RolePermission;

import java.util.List;
import java.util.Set;

/**
 * Created by yz on 2016/8/2
 */
public interface IRoleService {
    Set<String> getRoleByUserId(int userId);

    /**
     * 获取role对应的url地址
     * @return
     */
    List<RolePermission> getAll();
}
