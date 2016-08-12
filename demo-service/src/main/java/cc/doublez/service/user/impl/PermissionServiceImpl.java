package cc.doublez.service.user.impl;

import cc.doublez.dao.PermissionDao;
import cc.doublez.service.user.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by yz on 2016/8/2
 */
@Service
public class PermissionServiceImpl implements IPermissionService{
    @Autowired
    private PermissionDao permissionDao;
    @Override
    public Set<String> getPermissionByUserId(int userId) {
        return permissionDao.getPermissionByUserId(userId);
    }
}
