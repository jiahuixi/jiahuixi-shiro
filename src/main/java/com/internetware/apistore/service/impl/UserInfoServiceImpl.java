package com.internetware.apistore.service.impl;

import com.internetware.apistore.dao.SystemPermissionMapper;
import com.internetware.apistore.dao.SystemRoleMapper;
import com.internetware.apistore.dao.UserInfoMapper;
import com.internetware.apistore.model.*;
import com.internetware.apistore.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    //指定散列算法为md5
    private String algorithmName = "MD5";
    //散列迭代次数
    private final int hashIterations = 2;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private SystemRoleMapper systemRoleMapper;
    @Resource
    private SystemPermissionMapper systemPermissionMapper;

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return UserInfo
     */
    @Override
    public UserInfo findByUsername(String username) {
        logger.debug("UserInfoServiceImpl.findByUsername() is start");
        try {

            UserInfo userInfo = userInfoMapper.findByUsername(username);
            List<SystemRole>  systemRolelist = systemRoleMapper.selectByUserInfo(userInfo.getUid());
            for(SystemRole systemRole : systemRolelist){
                List<SystemPermission> systemPermissionList = systemPermissionMapper.selectBySystemRoleId(systemRole.getId());
                systemRole.setPermissions(systemPermissionList);
            }
            userInfo.setSystemRoles(systemRolelist);
            return userInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 新增/更新用户信息
     *
     * @param userInfo
     * @return Result
     */
    @Override
    public Result saveUserInfo(UserInfo userInfo) {
        try {
            // 判断更新/新增用户信息 uid存在为更新
            if (userInfo.getUid() != null) {
                // 判断用户信息是否存在
                if (userInfoMapper.exists(userInfo.getUid())) {
                    userInfoMapper.updateByPrimaryKeySelective(updateOriginalUserInfo(userInfoMapper.selectByPrimaryKey(userInfo.getUid()), userInfo));
                    return Result.success("用户信息更新成功!");
                }
                return Result.fail("该用户不存在,更新失败！");
            } else {
                if (StringUtils.isNotBlank(userInfo.getName()) && StringUtils.isNotBlank(userInfo.getUsername()) &&
                    StringUtils.isNotBlank(userInfo.getPassword())) {
                    if (userInfoMapper.findByUsername(userInfo.getUsername()) == null) {
                        userInfo = userPwdDesign(userInfo);
                        //默认标记用户可用
                        userInfo.setState(0);
                        userInfoMapper.insertSelective(userInfo);
                        return Result.success("用户信息保存成功!");
                    }
                    return Result.fail("此账户已存在!");
                }
                return Result.fail("请求参数不完整，请检查后重试!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("database operating failed!");
        }
    }

    /**
     * 获取所有用户信息
     *
     * @return List<UserInfo>
     */
    @Override
    public Result findUser(String name) {
        try {
            name = name.trim();
            if (StringUtils.isBlank(name)) {
                name = "%";
            } else if (!"%".equals(name)) {
                name = "%" + name + "%";
            }

            if ("%".equals(name)) {
                List<UserInfo> userInfoList = userInfoMapper.findAll();
                for(UserInfo userInfo : userInfoList){
                    List<SystemRole>  systemRolelist = systemRoleMapper.selectByUserInfo(userInfo.getUid());
                    for(SystemRole systemRole : systemRolelist){
                        List<SystemPermission> systemPermissionList = systemPermissionMapper.selectBySystemRoleId(systemRole.getId());
                        systemRole.setPermissions(systemPermissionList);
                    }
                    userInfo.setSystemRoles(systemRolelist);
                }
                return Result.success(getImportUserInfos(userInfoList));
            } else {
                List<UserInfo> userInfoList = userInfoMapper.findByUsernameLikeOrNameLike(name, name);
                for(UserInfo userInfo : userInfoList){
                    List<SystemRole>  systemRolelist = systemRoleMapper.selectByUserInfo(userInfo.getUid());
                    for(SystemRole systemRole : systemRolelist){
                        List<SystemPermission> systemPermissionList = systemPermissionMapper.selectBySystemRoleId(systemRole.getId());
                        systemRole.setPermissions(systemPermissionList);
                    }
                    userInfo.setSystemRoles(systemRolelist);
                }
                return Result.success(getImportUserInfos(userInfoList));
            }

//            Iterator<UserInfo> userInfoIterator = userInfoList.iterator();
//            while (userInfoIterator.hasNext()) {
//                //去除所有状态为不可用的用户信息
//                if (userInfoIterator.next().getState() != 0) {
//                    userInfoIterator.remove();
//                }
//            }
//            return Result.success(getImportUserInfos(userInfoList));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("database operating failed!");
        }
    }

    /**
     * 判断用户信息是否存在
     *
     * @param id
     * @return boolean
     */
    @Override
    public boolean userInfoExist(Integer id) {
        return userInfoMapper.exists(id);
    }

    /**
     * 删除用户信息
     *
     * @param id
     * @return boolean
     */
    @Override
    public Result deleteUserInfo(Integer id) {
        try {
            if (1 == id) {
                return Result.fail("管理员账户不可删除!");
            }
            //判断此用户信息是否存在
            if (userInfoExist(id)) {
                userInfoMapper.deleteByPrimaryKey(id);
                return Result.success("用户信息删除成功!");
            }
            return Result.fail("此用户信息不存在!");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("database operating failed!");
        }
    }

    /**
     * 筛选用户中重要信息
     *
     * @param userInfos
     * @return List
     */
    private List<AvailableUserInfo> getImportUserInfos(List<UserInfo> userInfos) {
        List<AvailableUserInfo> userInfoList = new ArrayList<>();
        for (UserInfo userInfo : userInfos) {
            //筛选的用户信息
            AvailableUserInfo availableUserInfo = new AvailableUserInfo();

            List<AvailableSystemRole> availableSystemRoles = new ArrayList<>();

            List<SystemRole> systemRoles = userInfo.getSystemRoles();
            for (SystemRole systemRole : systemRoles) {
                //筛选的系统角色
                AvailableSystemRole availableSystemRole = new AvailableSystemRole();
                availableSystemRole.setId(systemRole.getId());
                availableSystemRole.setRole(systemRole.getRole());
                availableSystemRole.setAvailable(systemRole.getAvailable());
                availableSystemRole.setDescription(systemRole.getDescription());
                availableSystemRoles.add(availableSystemRole);
            }

            availableUserInfo.setUid(userInfo.getUid());
            availableUserInfo.setName(userInfo.getName());
            availableUserInfo.setUsername(userInfo.getUsername());
            availableUserInfo.setState(userInfo.getState());
            availableUserInfo.setAvailableSystemRoles(availableSystemRoles);

            userInfoList.add(availableUserInfo);
        }
        return userInfoList;
    }

    /**
     * 填充用户信息未修改字段
     *
     * @param originalUserInfo 原始用户信息
     * @param userInfo         更新用户信息
     * @return UserInfo
     */
    private UserInfo updateOriginalUserInfo(UserInfo originalUserInfo, UserInfo userInfo) {
        if (StringUtils.isBlank(userInfo.getName())) {
            userInfo.setName(originalUserInfo.getName());
        }
        if (StringUtils.isBlank(userInfo.getUsername())) {
            userInfo.setUsername(originalUserInfo.getUsername());
        }
        if (userInfo.getState() == null) {
            userInfo.setState(originalUserInfo.getState());
        }
        if (StringUtils.isBlank(userInfo.getPassword())) {
            userInfo.setPassword(originalUserInfo.getPassword());
            userInfo.setSalt(originalUserInfo.getSalt());
        } else {
            userInfo = userPwdDesign(userInfo);
        }
        if (userInfo.getSystemRoles() == null) {
            userInfo.setSystemRoles(originalUserInfo.getSystemRoles());
        }
        return userInfo;
    }

    /**
     * 生成盐并加密密码，采用2次MD5加密
     *
     * @param userInfo
     * @return UserInfo
     */
    private UserInfo userPwdDesign(UserInfo userInfo) {
        userInfo.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword = new SimpleHash(algorithmName, userInfo.getPassword(), ByteSource.Util.bytes(userInfo
            .getCredentialsSalt()), hashIterations).toHex();
        userInfo.setPassword(newPassword);
        return userInfo;
    }
}
