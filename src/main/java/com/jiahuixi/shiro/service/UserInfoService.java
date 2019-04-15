package com.jiahuixi.shiro.service;


import com.jiahuixi.shiro.model.Result;
import com.jiahuixi.shiro.model.UserInfo;

public interface UserInfoService {

    UserInfo findByUsername(String username);

    Result saveUserInfo(UserInfo userInfo);

    Result findUser(String name);

    boolean userInfoExist(Integer id);

    Result deleteUserInfo(Integer id);
}
