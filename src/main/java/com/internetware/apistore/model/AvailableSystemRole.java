package com.internetware.apistore.model;

/**
 * @ProjectName: data-retention
 * @Package: cn.internetware.yancloud.dataretention.login.entity
 * @ClassName: AvailableSystemRole
 * @Description: java类作用描述
 * @Author: Jack Sparrow
 * @CreateDate: 2018/11/21 10:11
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/11/21 10:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AvailableSystemRole {
    private Integer id;//编号
    private String role;//角色
    private Boolean available;//是否可用，默认不可用，不可用不会添加给用户
    private String description;//角色描述

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "AvailableSystemRole{" +
            "id=" + id +
            ", role='" + role + '\'' +
            ", available=" + available +
            ", description='" + description + '\'' +
            '}';
    }
}
