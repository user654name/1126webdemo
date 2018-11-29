package com.bankcomm.shirodemo.controller;

import com.bankcomm.shirodemo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public ModelAndView doTest(HttpSession session) {
        System.out.println("TestController.doTest");
        session.setAttribute("key", "value——4000元");

        //调试session内容 并销毁session
        testService.doMethod();


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
        System.out.println("销毁shiro-session后 查看 key = " + key);


        System.out.println("测试结束 ");
        String toPage = "authc/home";
        return new ModelAndView(toPage);
    }
}
