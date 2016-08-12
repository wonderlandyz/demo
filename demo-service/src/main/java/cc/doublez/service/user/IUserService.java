package cc.doublez.service.user;

import cc.doublez.domain.pojo.user.User;

import java.util.List;

/**
 * Created by yz on 2015/12/23
 */
public interface IUserService {
    public int insert(User user);
    List<User> list();
    String testAspect(String param);
    User getByPhone(String phone);
    User login(String username, char[] password);
}
