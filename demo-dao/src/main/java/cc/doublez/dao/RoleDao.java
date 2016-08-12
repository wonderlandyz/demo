package cc.doublez.dao;

import cc.doublez.domain.pojo.user.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yz on 2016/8/2
 */
public interface RoleDao {
    Set<String> getRoleByUserId(@Param("id") int userId);
    List<RolePermission> getAll();
}
