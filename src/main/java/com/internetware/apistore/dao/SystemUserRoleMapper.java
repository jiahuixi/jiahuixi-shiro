package com.internetware.apistore.dao;

import com.internetware.apistore.model.SystemUserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemUserRoleMapper {
    int insert(SystemUserRole record);

    int insertSelective(SystemUserRole record);
}