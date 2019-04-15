package com.jiahuixi.shiro.model;

import java.util.List;

public class SystemRole {
    private Integer id;

    private Boolean available;

    private String description;

    private String role;
    private List<SystemPermission> permissions;
    private List<UserInfo> userInfos;

    public List<SystemPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SystemPermission> permissions) {
        this.permissions = permissions;
    }

    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        this.description = description == null ? null : description.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }
}