package com.bankcomm.shirodemo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Tianqi.Zhang
 * @date 2018/11/26
 * @time 22:00
 * @package com.bankcomm.shirodemo.controller
 * @project 1126webdemo
 * @description
 */

@Controller
@RequestMapping("/shiro")
public class ShiroHandler {

    /**
     * 登录入口
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {

        System.out.println("username = " + username);

        Subject currentUser = SecurityUtils.getSubject();

        // 如果当前用户已认证(登录)则跳过
        if (!currentUser.isAuthenticated()) {
            // 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            // rememberme
            token.setRememberMe(false);
            try {
                // 执行登录
                currentUser.login(token);
            }
            // 所有认证时异常的父类.
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
                System.out.println("登录失败: " + ae.getMessage());
            }
        }
//        return "";
        return "login-fail";

    }
}

