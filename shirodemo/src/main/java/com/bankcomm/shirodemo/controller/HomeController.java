package com.bankcomm.shirodemo.controller;

import org.springframework.stereotype.Controller;
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
}
