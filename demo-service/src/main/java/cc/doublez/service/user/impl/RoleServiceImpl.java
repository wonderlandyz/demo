package cc.doublez.service.user.impl;

import cc.doublez.dao.RoleDao;
import cc.doublez.domain.pojo.user.RolePermission;
import cc.doublez.service.user.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by yz on 2016/8/2
 */
@Service
public class RoleServiceImpl implements IRoleService{
    @Autowired
    private RoleDao roleDao;
    @Override
    public Set<String> getRoleByUserId(int userId) {
        return roleDao.getRoleByUserId(userId);
    }

    @Override
    public List<RolePermission> getAll() {
        return roleDao. getAll();
    }
}
