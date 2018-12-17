package com.bankcomm.shirodemo.mapper;


import com.bankcomm.shirodemo.ShirodemoApplication;
import com.bankcomm.shirodemo.bean.User;
import com.bankcomm.shirodemo.dao.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Tianqi.Zhang
 * @date 2018/11/28
 * @time 21:39
 * @package com.bankcomm.shirodemo.mapper
 * @project 1126webdemo
 * @description
 */


@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = ShirodemoApplication.class)
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;


    @Test
    public void selectByUsernameTest() {

        User user = userMapper.selectByUsername("root");
        System.out.println("user = " + user);
        System.out.println("UserMapperTest.selectByUsernameTest");
        System.out.println("user = " + user);
        System.out.println(1);

//        User




    }
}
