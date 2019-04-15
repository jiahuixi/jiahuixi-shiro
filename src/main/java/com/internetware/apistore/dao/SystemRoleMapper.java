package com.internetware.apistore.dao;

import com.internetware.apistore.model.SystemRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SystemRole record);

    int insertSelective(SystemRole record);

    SystemRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemRole record);

    int updateByPrimaryKey(SystemRole record);

    List<SystemRole> findAll();

    List<SystemRole> findByRoleLike(String role);

    boolean exists(Integer id);

    List<SystemRole> selectByUserInfo(Integer uid);
}