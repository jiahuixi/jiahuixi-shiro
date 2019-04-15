package com.jiahuixi.shiro.dao;

import com.jiahuixi.shiro.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    UserInfo findByUsername(String username);

    boolean exists(Integer uid);

    List<UserInfo> findAll();

    List<UserInfo> findByUsernameLikeOrNameLike(String name, String name1);
}