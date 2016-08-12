package cc.doublez.domain.pojo.user;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/22.
 */
public class User implements Serializable {

    private int id;
    @NotNull
    //@Size(min = 2, max = 14,message = "测试字符串大小")
    private String phone;
    @NotBlank(message = "{username.not.empty}")
    private String userName;
    @NotEmpty(message="密码不能为空")
    @Size(min=1,max=20,message="密码长度应在1-20之间")
    private String password;
    //@Min(value = 1,message = "最小年龄为1")
    @Max(value = 100,message = "最大年龄为100")
    private int age;
    private String createdTime;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
