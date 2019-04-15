package com.internetware.apistore.service;


import com.internetware.apistore.model.Result;
import com.internetware.apistore.model.SystemPermission;

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
