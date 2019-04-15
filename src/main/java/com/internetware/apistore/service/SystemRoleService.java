package com.internetware.apistore.service;


import com.internetware.apistore.model.Result;
import com.internetware.apistore.model.SystemRole;

public interface SystemRoleService {

    Result findSystemRole(String role);

    Result saveSystemRole(SystemRole systemRole);

    boolean systemRoleIsExist(Integer id);

    Result deleteSystemRole(Integer id);
}
