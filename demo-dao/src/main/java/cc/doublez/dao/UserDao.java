package cc.doublez.dao;

import cc.doublez.domain.pojo.Row;
import cc.doublez.domain.pojo.user.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yz on 2015/12/23
 */
public interface UserDao {
    int insert(User user);
    List<User> list();
    User getByPhone(@Param("phone") String phone);
    User login(Row row);
}
