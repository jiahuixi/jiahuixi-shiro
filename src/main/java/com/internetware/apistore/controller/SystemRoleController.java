package com.internetware.apistore.controller;

import com.internetware.apistore.model.Result;
import com.internetware.apistore.model.SystemRole;
import com.internetware.apistore.service.SystemRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ProjectName: data-retention
 * @Package: cn.internetware.yancloud.dataretention.login.controller
 * @ClassName: SystemRoleController
 * @Description: java类作用描述
 * @Author: Jack Sparrow
 * @CreateDate: 2018/11/20 12:00
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/11/20 12:00
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@RestController
@RequestMapping("/sysRole")
public class SystemRoleController {

    @Autowired
    SystemRoleService systemRoleService;

    /**
     * 角色查询
     *
     * @return
     */
    @GetMapping("/roleList")
    @ResponseBody
    @RequiresPermissions("userInfo:view")//权限管理;
    public Result getSystemRoleList(@RequestParam(value = "role", defaultValue = "%") String role) {
        return systemRoleService.findSystemRole(role);
    }

    /**
     * 角色添加
     *
     * @return
     */
    @PostMapping("/roleSave")
    @ResponseBody
    @RequiresPermissions("userInfo:save")//权限管理;
    public Result saveSystemRole(@RequestBody SystemRole systemRole) {
        return systemRoleService.saveSystemRole(systemRole);
    }

    /**
     * 角色删除
     *
     * @return
     */
    @RequestMapping("/roleDel")
    @ResponseBody
    @RequiresPermissions("userInfo:del")//权限管理;
    public Result deleteSystemRole(@RequestParam(value = "id") Integer id) {
        return systemRoleService.deleteSystemRole(id);
    }
}
