package com.bankcomm.shirodemo.controller;

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
        return new ModelAndView("/guest/login");
    }

    @RequestMapping("/guest/register")
    public ModelAndView toPage3() {
        return new ModelAndView("/guest/register");
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
