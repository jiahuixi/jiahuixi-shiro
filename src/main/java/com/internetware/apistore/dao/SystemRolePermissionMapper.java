package com.internetware.apistore.dao;

import com.internetware.apistore.model.SystemRolePermission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemRolePermissionMapper {
    int insert(SystemRolePermission record);

    int insertSelective(SystemRolePermission record);
}