package com.bankcomm.shirodemo.controller;

import com.bankcomm.shirodemo.bean.User;
import com.bankcomm.shirodemo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 处理登录注册请求
 *
 * @author Tianqi.Zhang
 * @date 2018/11/26
 * @time 22:00
 * @package com.bankcomm.shirodemo.controller
 * @project 1126webdemo
 * @description
 */

@Api(description = "用户接口")
@Controller
@RequestMapping("/shiro")
public class UserController {

    @Autowired
    private UserService userService;

    private final Logger log = LoggerFactory.getLogger(UserController.class);


    /**
     * 执行注册
     *
     * @param username
     * @param password
     * @return
     */
    @ApiOperation(value = "新增用户" ,  notes="新增注册")
    @RequestMapping("/register")
    public ModelAndView register(@RequestParam("username") String username,
                                 @RequestParam("password") String password) {
        String toPage;
        System.out.println("username = " + username);


        //1 校验参数
        if ("".equals(username)){
            toPage = "/guest/error";
            System.out.println("数据校验出错（用户名为空）即将跳转toPage = " + toPage);
            return new ModelAndView(toPage);
        }
        //2 用户名对比
        User userFromDb = userService.findUserByUsername(username);

        if (userFromDb == null) {
            //3 用户名不存在 - 可注册
            //4 盐值生成
            //5 密码散列(注意密码散列要和【登录密码对比】时的策略相同)
            //6 将【用户名 非明文密码 盐值 注册时间】等等参数插入数据库
            User userInsert = new User();
            userInsert.setUsername(username);
            userInsert.setPassword(password);
            //默认角色为 undefined
            userInsert.setRole("undefined");

            boolean insert = userService.insert(userInsert);

            if (insert) {
                // 注册成功
                toPage = "/guest/login";
                System.out.println("注册成功即将跳转toPage = " + toPage);
                return new ModelAndView(toPage);

            } else {
                // 注册失败
                toPage = "/guest/error";
                System.out.println("注册成功即将跳转toPage = " + toPage);
                return new ModelAndView(toPage);

            }

        } else {
            // 用户名已存在 返回错误
            String errMsg = "用户名已存在";
            System.out.println("errMsg = " + errMsg);
            toPage = "/guest/error";
            System.out.println("注册成功即将跳转toPage = " + toPage);
            return new ModelAndView(toPage);
        }

    }

    /**
     * 执行登录
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        // 跳转的地址
        String toPage = "/guest/login-fail";
        //报错后的试图
        ModelAndView errorView = new ModelAndView(toPage);


        System.out.println("用户执行登录:username = " + username + "|| password = " + password);

        // 获取Subject 当前用户对象
        Subject currentUser = SecurityUtils.getSubject();



        // 如果当前用户已认证(登录)则跳过
        if (!currentUser.isAuthenticated()) {
            //先校验传入用户名密码合法性


            // 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            // rememberme
            token.setRememberMe(false);

            try {

                // 首先调用 Subject.login(token) 进行登录，
                // 其会自动委托给 Security Manager，
                // 调用之前必须通过 SecurityUtils.setSecurityManager() 设置；
                 currentUser.login(token);
                System.out.println("当前的用户凭证:token = " + token);
            }
            // 若没有指定的账户, 则 shiro 将会抛出 UnknownAccountException 异常.
            catch (UnknownAccountException uae) {
                log.info("----> There is no user with username of " + token.getPrincipal());
                return errorView;
            }
            // 若账户存在, 但密码不匹配, 则 shiro 会抛出 IncorrectCredentialsException 异常。
            catch (IncorrectCredentialsException ice) {
                log.info("----> Password for account " + token.getPrincipal() + " was incorrect!");
                return errorView;
            }
            // 用户被锁定的异常 LockedAccountException
            catch (LockedAccountException lae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
                return errorView;
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            // 所有认证时异常的父类.
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
                System.out.println("登录异常了" );
                System.out.println("即将跳转到 /guest/login-fail" );
                return errorView;
            }
        }
        //登录成功的视图
        toPage = "authc/home";
        System.out.println("即将跳转到"+toPage);
        return new ModelAndView(toPage);
    }

    @RequestMapping("/logout")
    public String logout(){
        return "/guest/logout";
    }


}

