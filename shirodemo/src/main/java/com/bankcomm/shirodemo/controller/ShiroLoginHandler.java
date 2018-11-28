package com.bankcomm.shirodemo.controller;

import com.bankcomm.shirodemo.config.ShiroConfig;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ShiroLoginHandler {

    private final Logger log = LoggerFactory.getLogger(ShiroLoginHandler.class);

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
                //首先调用 Subject.login(token) 进行登录，
                // 其会自动委托给 Security Manager，
                // 调用之前必须通过 SecurityUtils.setSecurityManager() 设置；
                currentUser.login(token);
                System.out.println("token = " + token);
            }
            // 若没有指定的账户, 则 shiro 将会抛出 UnknownAccountException 异常.
            catch (UnknownAccountException uae) {
                log.info("----> There is no user with username of " + token.getPrincipal());
                return "/guest/login-fail";
            }
            // 若账户存在, 但密码不匹配, 则 shiro 会抛出 IncorrectCredentialsException 异常。
            catch (IncorrectCredentialsException ice) {
                log.info("----> Password for account " + token.getPrincipal() + " was incorrect!");
                return "/guest/login-fail";
            }
            // 用户被锁定的异常 LockedAccountException
            catch (LockedAccountException lae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
                return "/guest/login-fail";
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            // 所有认证时异常的父类.
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
                return "/guest/login-fail";
            }
        }
        return "login-success";
    }

    @RequestMapping("/logout")
    public String logout(){
        return "/guest/logout";
    }
}

