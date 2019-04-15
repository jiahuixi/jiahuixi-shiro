package com.jiahuixi.shiro.controller;

import com.jiahuixi.shiro.model.Result;
import com.jiahuixi.shiro.model.SystemPermission;
import com.jiahuixi.shiro.service.SystemPermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ProjectName: data-retention
 * @Package: cn.internetware.yancloud.dataretention.login.controller
 * @ClassName: SystemPermissionController
 * @Description: java类作用描述
 * @Author: Jack Sparrow
 * @CreateDate: 2018/11/20 12:00
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/11/20 12:00
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@RestController
@RequestMapping("/sysPermission")
public class SystemPermissionController {

    @Autowired
    SystemPermissionService systemPermissionService;

    /**
     * 系统权限查询
     *
     * @return
     */
    @GetMapping("/permissionList")
    @ResponseBody
    @RequiresPermissions("userInfo:view")//权限管理;
    public Result getSystemPermissionList(@RequestParam(value = "name", defaultValue = "%") String name) throws Exception {
        return systemPermissionService.findPermissionList(name);
    }

    /**
     * 系统权限添加或更新
     *
     * @return
     */
    @PostMapping("/permissionSave")
    @ResponseBody
    @RequiresPermissions("userInfo:save")//权限管理;
    public Result saveSystemPermission(@RequestBody SystemPermission systemPermission) {
        return systemPermissionService.saveSystemPermission(systemPermission);
    }

    /**
     * 系统权限删除
     *
     * @return
     */
    @RequestMapping("/permissionDel")
    @ResponseBody
    @RequiresPermissions("userInfo:del")//权限管理;
    public Result deleteSystemPermission(@RequestParam(value = "id") Integer id) {
        return systemPermissionService.deleteSystemPermission(id);
    }
}
