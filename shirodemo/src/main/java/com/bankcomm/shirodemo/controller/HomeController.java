package com.bankcomm.shirodemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Tianqi.Zhang
 * @date 2018/11/26
 * @package com.bankcomm.shirodemo.controller
 * @project 1126webdemo
 */
@Controller
public class HomeController {

    @RequestMapping("/home")
    public String toHome() {
        return "home";
    }

    @RequestMapping("/login")
    public String toPage1() {
        return "login";
    }

    @RequestMapping("/notLogin")
    public String toPage2() {
        return "notLogin";
    }


//    /**
//     * 仅仅为了方便开发，配置直接访问资源
//     *
//     * @param path
//     * @return
//     */
//    @RequestMapping("{path}")
//    public String toAnyWhere(@PathVariable String path) {
//        return path;
//    }
}
