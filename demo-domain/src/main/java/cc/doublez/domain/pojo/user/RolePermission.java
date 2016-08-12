package cc.doublez.domain.pojo.user;

import java.io.Serializable;

/**
 * 角色和对应的权限
 * Created by yz on 2016/8/4
 */
public class RolePermission implements Serializable{
    private String role;
    private String permission;
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
