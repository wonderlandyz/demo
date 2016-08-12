package cc.doublez.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * Created by yz on 2016/8/2
 */
public interface PermissionDao {
    Set<String> getPermissionByUserId(@Param("id") int userId);
}
