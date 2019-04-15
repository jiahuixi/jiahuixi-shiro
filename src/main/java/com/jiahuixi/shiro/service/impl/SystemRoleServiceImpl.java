package com.jiahuixi.shiro.service.impl;

import com.jiahuixi.shiro.dao.SystemRoleMapper;
import com.jiahuixi.shiro.model.AvailableSystemRole;
import com.jiahuixi.shiro.model.Result;
import com.jiahuixi.shiro.model.SystemRole;
import com.jiahuixi.shiro.service.SystemRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SystemRoleServiceImpl implements SystemRoleService {

    @Resource
    private SystemRoleMapper systemRoleMapper;

    /**
     * 获取角色列表
     *
     * @return Result
     */
    @Override
    public Result findSystemRole(String role) {
        try {
            role = role.trim();
            if (StringUtils.isBlank(role)) {
                role = "%";
            } else if (!"%".equals(role)) {
                role = "%" + role + "%";
            }

            if ("%".equals(role)) {
                return Result.success(getImportSystemRoles(systemRoleMapper.findAll()));
            } else {
                return Result.success(getImportSystemRoles(systemRoleMapper.findByRoleLike(role)));
            }
//            Iterator<SystemRole> systemRoleIterator = systemRoleList.iterator();
//            while (systemRoleIterator.hasNext()) {
//                if (!systemRoleIterator.next().getAvailable()) {
//                    systemRoleIterator.remove();
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("database operating failed!");
        }
    }

    /**
     * 新增或更新系统角色
     *
     * @param systemRole
     * @return Result
     */
    @Override
    public Result saveSystemRole(SystemRole systemRole) {
        try {
            if (null != systemRole.getId()) {
                if (systemRoleIsExist(systemRole.getId())) {
                    systemRoleMapper.updateByPrimaryKeySelective(updateOriginalSystemRole(systemRoleMapper.selectByPrimaryKey(systemRole.getId()), systemRole));
                    return Result.success("更新系统角色成功!");
                }
                return Result.fail("该系统角色不存在!");
            } else {
                systemRole.setAvailable(true);
                if (StringUtils.isNotBlank(systemRole.getRole()) &&
                    StringUtils.isNotBlank(systemRole.getDescription())) {
                    systemRoleMapper.insertSelective(systemRole);
                    return Result.success("新增系统角色成功!");
                }
                return Result.fail("请求参数不完整!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("database operating failed!");
        }
    }

    /**
     * 判断系统角色是否存在
     *
     * @param id
     * @return boolean
     */
    @Override
    public boolean systemRoleIsExist(Integer id) {
        return systemRoleMapper.exists(id);
    }

    /**
     * 刪除系统角色
     *
     * @param id
     * @return Result
     */
    @Override
    public Result deleteSystemRole(Integer id) {
        try {
            //判断此系统角色是否存在
            if (systemRoleIsExist(id)) {
                if (systemRoleMapper.selectByPrimaryKey(id).getUserInfos().size() == 0) {
                    systemRoleMapper.deleteByPrimaryKey(id);
                    return Result.success("系统角色删除成功!");
                } else {
                    return Result.fail("该角色下存在关联用户，请删除相关用户!");
                }
            }
            return Result.fail("系统角色不存在!");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("database operating failed!");
        }
    }

    /**
     * 筛选角色中的重要信息
     *
     * @param systemRoles
     * @return List<AvailableSystemRole>
     */
    private List<AvailableSystemRole> getImportSystemRoles(List<SystemRole> systemRoles) {

        List<AvailableSystemRole> availableSystemRoles = new ArrayList<>();

        for (SystemRole systemRole : systemRoles) {
            AvailableSystemRole availableSystemRole = new AvailableSystemRole();
            availableSystemRole.setId(systemRole.getId());
            availableSystemRole.setRole(systemRole.getRole());
            availableSystemRole.setDescription(systemRole.getDescription());
            availableSystemRole.setAvailable(systemRole.getAvailable());
            availableSystemRoles.add(availableSystemRole);
        }
        return availableSystemRoles;
    }

    /**
     * 更新系统角色未修改的属性
     *
     * @param originalSystemRole
     * @param systemRole
     * @return SystemRole
     */
    private SystemRole updateOriginalSystemRole(SystemRole originalSystemRole, SystemRole systemRole) {
        if (StringUtils.isBlank(systemRole.getRole())) {
            systemRole.setRole(originalSystemRole.getRole());
        }
        if (StringUtils.isBlank(systemRole.getDescription())) {
            systemRole.setDescription(originalSystemRole.getDescription());
        }
        if (systemRole.getAvailable() == null) {
            systemRole.setAvailable(originalSystemRole.getAvailable());
        }
        if (systemRole.getPermissions() == null) {
            systemRole.setPermissions(originalSystemRole.getPermissions());
        }
        return systemRole;
    }
}
