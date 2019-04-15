package com.internetware.apistore.dao;

import com.internetware.apistore.model.SystemPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemPermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SystemPermission record);

    int insertSelective(SystemPermission record);

    SystemPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemPermission record);

    int updateByPrimaryKey(SystemPermission record);

    boolean exists(Integer id);

    List<SystemPermission> findAll();

    List<SystemPermission> findByNameLike(String name);

    List<SystemPermission> selectBySystemRoleId(Integer systemRoleId);
}