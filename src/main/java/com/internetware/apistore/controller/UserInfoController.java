package com.internetware.apistore.controller;

import com.internetware.apistore.model.Result;
import com.internetware.apistore.model.UserInfo;
import com.internetware.apistore.service.UserInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ProjectName: data-retention
 * @Package: cn.internetware.yancloud.dataretention.login.controller
 * @ClassName: UserInfoController
 * @Description: java类作用描述
 * @Author: Jack Sparrow
 * @CreateDate: 2018/11/20 12:00
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/11/20 12:00
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    /**
     * 用户查询
     *
     * @return
     */
    @GetMapping("/userList")
    @ResponseBody
    @RequiresPermissions("userInfo:view")//权限管理;
    public Result getUserInfoList(@RequestParam(value = "name", defaultValue = "%") String name) {
        return userInfoService.findUser(name);
    }

    /**
     * 用户添加或更新
     *
     * @return
     */
    @PostMapping("/userSave")
    @ResponseBody
    @RequiresPermissions("userInfo:save")//权限管理;
    public Result saveUserInfo(@RequestBody UserInfo userInfo) {
        return userInfoService.saveUserInfo(userInfo);
    }

    /**
     * 用户删除
     *
     * @return
     */
    @RequestMapping("/userDel")
    @ResponseBody
    @RequiresPermissions("userInfo:del")//权限管理;
    public Result deleteUserInfo(@RequestParam(value = "id") Integer id) {
        return userInfoService.deleteUserInfo(id);
    }
}
