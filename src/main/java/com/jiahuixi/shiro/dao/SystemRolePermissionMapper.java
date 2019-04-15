package com.jiahuixi.shiro.dao;

import com.jiahuixi.shiro.model.SystemRolePermission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemRolePermissionMapper {
    int insert(SystemRolePermission record);

    int insertSelective(SystemRolePermission record);
}