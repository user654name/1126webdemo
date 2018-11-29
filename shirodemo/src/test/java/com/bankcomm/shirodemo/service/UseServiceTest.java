package com.bankcomm.shirodemo.service;

import com.bankcomm.shirodemo.ShirodemoApplication;
import com.bankcomm.shirodemo.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Tianqi.Zhang
 * @date 2018/11/29
 * @time 15:37
 * @package com.bankcomm.shirodemo.service
 * @project 1126webdemo
 * @description
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = ShirodemoApplication.class)
public class UseServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void findUserByUsernameTest(){

        User root = userService.findUserByUsername("root");
        System.out.println("root = " + root);

    }
}
