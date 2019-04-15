package com.internetware.apistore.service;


import com.internetware.apistore.model.Result;
import com.internetware.apistore.model.UserInfo;

public interface UserInfoService {

    UserInfo findByUsername(String username);

    Result saveUserInfo(UserInfo userInfo);

    Result findUser(String name);

    boolean userInfoExist(Integer id);

    Result deleteUserInfo(Integer id);
}
