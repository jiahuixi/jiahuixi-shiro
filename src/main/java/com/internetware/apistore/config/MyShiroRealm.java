package com.internetware.apistore.config;

import com.internetware.apistore.model.SystemPermission;
import com.internetware.apistore.model.SystemRole;
import com.internetware.apistore.model.UserInfo;
import com.internetware.apistore.service.UserInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MyShiroRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    private UserInfoService userInfoService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo = (UserInfo) principalCollection.getPrimaryPrincipal();
        for (SystemRole systemRole : userInfo.getSystemRoles()) {
            if (systemRole.getAvailable()) {
                authorizationInfo.addRole(systemRole.getRole());
                for (SystemPermission permission : systemRole.getPermissions()) {
                    if (permission.getAvailable()) {
                        authorizationInfo.addStringPermission(permission.getPermission());
                    }
                }
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws
            AuthenticationException {
        logger.info("MyShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号
        String username = (String) authenticationToken.getPrincipal();
        logger.debug("authenticationToken is :{}", authenticationToken.getCredentials());

        //DB查找UserInfo
        //todo Shiro时间机制，2分钟内不会重复执行该方法，缓存
        UserInfo userInfo = userInfoService.findByUsername(username);
        logger.debug("userInfo`s name is:{}", userInfo.getUsername());
        if (userInfo == null) {
            return null;
        }
        if (userInfo.getState() == 1) {
            throw new LockedAccountException();
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
            userInfo, //用户名
            userInfo.getPassword(), //密码
            ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt = username + salt
            getName()  //realm name
        );
        return authenticationInfo;
    }
}
