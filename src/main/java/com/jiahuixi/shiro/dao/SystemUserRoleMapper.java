package com.jiahuixi.shiro.dao;

import com.jiahuixi.shiro.model.SystemUserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemUserRoleMapper {
    int insert(SystemUserRole record);

    int insertSelective(SystemUserRole record);
}