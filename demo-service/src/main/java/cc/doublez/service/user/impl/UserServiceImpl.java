package cc.doublez.service.user.impl;

import cc.doublez.dao.UserDao;
import cc.doublez.domain.pojo.Row;
import cc.doublez.domain.pojo.user.User;
import cc.doublez.platform.annotation.ServiceLog;
import cc.doublez.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sun.tools.javac.jvm.ByteCodes.ret;

/**
 * Created by yz on 2015/12/23
 */
@Service(value = "userService")
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserDao userDao;

    @Override
    public int insert(User user) {
        return userDao.insert(user);
    }

    @ServiceLog
    @Override
    public String testAspect(String param) {
        return null;
    }

    @Override
    public List<User> list() {
        return userDao.list();
    }

    @Override
    public User getByPhone(String phone) {
        return userDao.getByPhone(phone);
    }

    @Override
    public User login(String username, char[] password) {
        User user = getByPhone(username);
        if (null!=user){
            user.getPassword().equals(String.valueOf(password));
            return user;
        }
        return null;
    }
}
