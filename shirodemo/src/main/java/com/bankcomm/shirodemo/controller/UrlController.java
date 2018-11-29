package com.bankcomm.shirodemo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Tianqi.Zhang
 * @date 2018/11/26
 * @package com.bankcomm.shirodemo.controller
 * @project 1126webdemo
 */
@Controller
public class UrlController {
    @RequestMapping("/authc/home")
    public ModelAndView toHome() {
        return new ModelAndView("/authc/home");
    }

    @RequestMapping("/guest/login")
    public ModelAndView toPage1() {

        String toPage = "/guest/login";
        if (SecurityUtils.getSubject().isAuthenticated()) {
            // 如果已经登录,不跳转到登录页面,直接回首页
            toPage = "authc/home";
            System.out.println("即将跳转到" + toPage);
            return new ModelAndView(toPage);
        }
        System.out.println("即将跳转到" + toPage);
        return new ModelAndView(toPage);
    }

    @RequestMapping("/guest/register")
    public ModelAndView toPage2() {
        String toPage;
        if (SecurityUtils.getSubject().isAuthenticated()) {
            // 如果已经登录,不跳转到登录页面，直接回首页
            toPage = "authc/home";
            System.out.println("即将跳转到" + toPage);
            return new ModelAndView(toPage);
        }
        toPage="/guest/register";
        System.out.println("即将跳转到" + toPage);
        return new ModelAndView(toPage);
    }


    @RequiresRoles("admin")
    @RequestMapping("/admin/adminpage")
    public ModelAndView toPage3() {
        return new ModelAndView("/admin/adminpage");
    }

    @RequestMapping("/authc/notrole")
    public ModelAndView toPage4() {
        return new ModelAndView("/authc/notrole");
    }

    @RequestMapping("/authc/infopage")
    public ModelAndView toPage5() {
        return new ModelAndView("/authc/infopage");
    }











    /*  *//**
     * 用于跳转到guest目录下指定页面
     *
     * @param pagename
     * @return
     *//*
    @RequestMapping("/guest/{pagename}")
    public ModelAndView toGuestView(@PathVariable String pagename) {
        System.out.println("即将跳转到pagename = /guest/" + pagename);
        ModelAndView view = null;

        if (!"".equals(pagename)) {
            switch (pagename) {
                case "login":
                case "notLogin":
                case "register":
                    view = new ModelAndView("/guest/" + pagename);
                    break;
                default:
                    view = new ModelAndView("/guest/error");
                    break;
            }
        }
        System.out.println("view = " + view);
        return view;
    }*/


}
