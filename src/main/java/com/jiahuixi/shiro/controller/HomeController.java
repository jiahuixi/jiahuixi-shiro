package com.jiahuixi.shiro.controller;

import com.alibaba.fastjson.JSONObject;
import com.jiahuixi.shiro.model.JsonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: data-retention
 * @Package: cn.internetware.yancloud.dataretention.login.controller
 * @ClassName: HomeController
 * @Description: java类作用描述
 * @Author: Jack Sparrow
 * @CreateDate: 2018/11/20 12:00
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/11/20 12:00
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping({"/", "/index"})
    @ResponseBody
    public String index() {
        return "登陆成功";
    }

    /**
     * 登录
     *
     * @param
     * @return
     */
    @GetMapping(value = "/login")
//    @ResponseBody
    public String login(@RequestParam(value = "username") String username,
                        @RequestParam(value = "password") String password) {
        logger.info("HomeController.login()");
        // 登录失败从request中获取shiro处理的异常信息。
        JSONObject jsonObject = new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getUsername(), userInfo.getPassword());
        try {
            subject.login(token);
            jsonObject.put("token", subject.getSession().getId());
            jsonObject.put("msg", "登录成功");
        } catch (IncorrectCredentialsException e) {
            jsonObject.put("msg", "密码错误");
        } catch (LockedAccountException e) {
            jsonObject.put("msg", "登录失败，该用户已被冻结");
        } catch (AuthenticationException e) {
            jsonObject.put("msg", "该用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    /**
     * 未登录，shiro应重定向到登录界面
     * 此处返回未登录状态信息由前端控制跳转页面
     *
     * @return
     */
    @RequestMapping(value = "/unauth")
    @ResponseBody
    public String unauth() {
        Map<String, Object> map = new HashMap();
        map.put("code", "999999");
        map.put("msg", "未登录");
        return JsonResult.success(map);
    }

    /**
     * 未授权
     *
     * @return
     */
    @RequestMapping(value = "/403")
    @ResponseBody
    public String unauthorized() {
        Map<String, Object> map = new HashMap();
        map.put("code", "999998");
        map.put("msg", "未授权");
        return JsonResult.success(map);
    }
}
