package com.bankcomm.shirodemo.controller;

import com.bankcomm.shirodemo.service.TestService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @author Tianqi.Zhang
 * @date 2018/11/29
 * @time 21:26
 * @package com.bankcomm.shirodemo.controller
 * @project 1126webdemo
 * @description
 */
@Controller
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping("/test")
    public ModelAndView doTest(HttpSession session, Model model) {
        System.out.println("TestController.doTest");
        session.setAttribute("key", "httpSsessionKey");

        //调试session内容 并销毁session
        testService.doSessionMethod();


        System.out.println(" = ");

        System.out.println("session = " + session);
        System.out.println(" = ");

        Object key = null;
        try {
            // 若session已经销毁 执行方法会抛出IllegalStateException
            key = session.getAttribute("key");
        } catch (Exception e) {
            System.out.println("session已经销毁 无法getAttribute");
            e.printStackTrace();

        }
        System.out.println("操作shiro-session后 查看 key = " + key);


        model.addAttribute("key",session.getAttribute("key"));
        System.out.println("测试结束 ");
        String toPage = "authc/home";
        System.out.println("即将跳转toPage = " + toPage);
        return new ModelAndView(toPage);
    }


    @RequestMapping("/getkey")
    public ModelAndView getSessionByKey(HttpSession session, Model model) {
        Object key = session.getAttribute("key");
        System.out.println("当前session域的key为 = " + key);
        model.addAttribute("key", key);
        System.out.println("key加入Model");

        String toPage = "authc/home";
        System.out.println("即将跳转toPage = " + toPage);
        return new ModelAndView(toPage);
    }


    @RequestMapping("/setkey")
    public ModelAndView setKey(HttpSession session, Model model) {
        System.out.println("TestController.set");
        session.setAttribute("key","newKey");
        model.addAttribute("key",session.getAttribute("key"));
        System.out.println("设置 key = newKey");
        String toPage = "authc/home";
        System.out.println("即将跳转toPage = " + toPage);
        return new ModelAndView(toPage);
    }

    @RequestMapping("/removekey")
    public ModelAndView removeKey(HttpSession session, Model model) {
        System.out.println("TestController.removeKey");
        session.removeAttribute("key");
        System.out.println("key的内容已删除");
        String toPage = "authc/home";
        System.out.println("即将跳转toPage = " + toPage);
        return new ModelAndView(toPage);
    }
}
