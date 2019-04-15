package com.jiahuixi.shiro.service;


import com.jiahuixi.shiro.model.Result;
import com.jiahuixi.shiro.model.SystemRole;

public interface SystemRoleService {

    Result findSystemRole(String role);

    Result saveSystemRole(SystemRole systemRole);

    boolean systemRoleIsExist(Integer id);

    Result deleteSystemRole(Integer id);
}
