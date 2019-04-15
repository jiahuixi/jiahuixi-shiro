package com.jiahuixi.shiro.service;


import com.jiahuixi.shiro.model.Result;
import com.jiahuixi.shiro.model.SystemPermission;

public interface SystemPermissionService {
    /**
     * 新增或更新系统权限
     *
     * @param systemPermission
     * @return
     */

    Result saveSystemPermission(SystemPermission systemPermission);

    Result findPermissionList(String name) throws Exception;

    Result deleteSystemPermission(Integer id);

    boolean systemPermissionExist(Integer id);
}
