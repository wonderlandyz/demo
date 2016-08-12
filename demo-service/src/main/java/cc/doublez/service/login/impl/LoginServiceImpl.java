package cc.doublez.service.login.impl;

import cc.doublez.service.login.ILoginService;
import cc.doublez.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yz on 2016/7/27
 */
@Service
public class LoginServiceImpl implements ILoginService{
    @Autowired
    private IUserService userService;
    @Override
    public void userLogin(String phone, String password) {
    }
}
