package com.jiahuixi.shiro.model;

import java.util.List;

/**
 * @ProjectName: data-retention
 * @Package: cn.internetware.yancloud.dataretention.login.entity
 * @ClassName: AvailableUser
 * @Description: java类作用描述
 * @Author: Jack Sparrow
 * @CreateDate: 2018/11/21 10:09
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/11/21 10:09
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AvailableUserInfo {

    private Integer uid;
    private String username;//账号
    private Integer state;//用户状态 0:可用  1:账号状态异常(锁定，删除等)
    private String name;//名称(昵称或者其他)
    private List<AvailableSystemRole> availableSystemRoles;//活跃的系统角色

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AvailableSystemRole> getAvailableSystemRoles() {
        return availableSystemRoles;
    }

    public void setAvailableSystemRoles(List<AvailableSystemRole> availableSystemRoles) {
        this.availableSystemRoles = availableSystemRoles;
    }

    @Override
    public String toString() {
        return "AvailableUserInfo{" +
            "uid=" + uid +
            ", username='" + username + '\'' +
            ", state=" + state +
            ", name='" + name + '\'' +
            ", availableSystemRoles=" + availableSystemRoles +
            '}';
    }
}
