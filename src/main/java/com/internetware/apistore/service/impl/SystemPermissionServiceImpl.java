package com.internetware.apistore.service.impl;

import com.internetware.apistore.dao.SystemPermissionMapper;
import com.internetware.apistore.model.Result;
import com.internetware.apistore.model.SystemPermission;
import com.internetware.apistore.service.SystemPermissionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SystemPermissionServiceImpl implements SystemPermissionService {

    private static final Logger logger = LoggerFactory.getLogger(SystemPermissionServiceImpl.class);

    @Resource
    private SystemPermissionMapper systemPermissionMapper;

    /**
     * 新增或更新系统权限
     *
     * @param systemPermission
     * @return Result
     */
    @Override
    public Result saveSystemPermission(SystemPermission systemPermission) {
        try {
            // 判断是否是新增：id不存在为新增
            if (systemPermission.getId() != null) {
                //判断DB中是否存在该权限
                if (systemPermissionMapper.exists(systemPermission.getId())) {
                    systemPermissionMapper.updateByPrimaryKeySelective(updateOriginalSystemPermission(systemPermissionMapper.selectByPrimaryKey(
                        (systemPermission.getId())), systemPermission));
                    return Result.success("系统权限更新成功!");
                }
                return Result.fail("此系统权限不存在，更新失败!");
            } else {
                systemPermission.setAvailable(true);
                systemPermissionMapper.insertSelective(systemPermission);
                return Result.success("新增系统权限成功!");
            }
        } catch (Exception e) {
            return Result.fail("database operating failed!");
        }
    }

    /**
     * 获取可用的系统权限
     *
     * @return Result
     */
    @Override
    public Result findPermissionList(String name) {
        name = name.trim();
        if (StringUtils.isBlank(name)) {
            name = "%";
        } else if (!"%".equals(name)) {
            name = "%" + name + "%";
        }
        try {
            if ("%".equals(name)) {
                return Result.success(systemPermissionMapper.findAll());
            } else {
                return Result.success(systemPermissionMapper.findByNameLike(name));
            }
//        Iterator<SystemPermission> systemPermissionIterator = systemPermissionList.iterator();
//        while (systemPermissionIterator.hasNext()) {
//            if (!systemPermissionIterator.next().getAvailable()) {
//                systemPermissionIterator.remove();
//            }
//        }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("database operating failed!");
        }
    }

    /**
     * 删除系统权限
     *
     * @param id
     * @return Result
     */
    @Override
    public Result deleteSystemPermission(Integer id) {
        try {
            if (systemPermissionExist(id)) {
                if (systemPermissionMapper.selectByPrimaryKey(id).getSystemRoles().size() != 0) {
                    return Result.fail("此权限存在关联角色，请先删除相关角色!");
                }
                systemPermissionMapper.deleteByPrimaryKey(id);
                return Result.success("系统权限删除成功!");
            }
            return Result.fail("该系统权限不存在!");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("database operating failed!");
        }
    }

    /**
     * 判断系统权限是否存在
     *
     * @param id
     * @return boolean
     */
    @Override
    public boolean systemPermissionExist(Integer id) {
        return systemPermissionMapper.exists(id);
    }

    /**
     * 更新系统权限更改的属性
     *
     * @param originalPermission
     * @param systemPermission
     * @return SystemPermission
     */
    private SystemPermission updateOriginalSystemPermission(SystemPermission originalPermission,
                                                            SystemPermission systemPermission) {
        if (StringUtils.isBlank(systemPermission.getName())) {
            systemPermission.setName(originalPermission.getName());
        }
        if (StringUtils.isBlank(systemPermission.getPermission())) {
            systemPermission.setPermission(originalPermission.getPermission());
        }
        if (StringUtils.isBlank(systemPermission.getParentIds())) {
            systemPermission.setParentIds(originalPermission.getParentIds());
        }
        if (StringUtils.isBlank(systemPermission.getResourceType())) {
            systemPermission.setResourceType(originalPermission.getResourceType());
        }
        if (StringUtils.isBlank(systemPermission.getUrl())) {
            systemPermission.setUrl(systemPermission.getUrl());
        }
        if (systemPermission.getAvailable() == null) {
            systemPermission.setAvailable(originalPermission.getAvailable());
        }
        if (systemPermission.getParentId() == null) {
            systemPermission.setParentId(originalPermission.getParentId());
        }
        return systemPermission;
    }
}
