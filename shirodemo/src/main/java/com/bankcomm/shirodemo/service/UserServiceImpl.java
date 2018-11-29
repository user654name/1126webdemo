package com.bankcomm.shirodemo.service;

import com.bankcomm.shirodemo.bean.User;
import com.bankcomm.shirodemo.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Tianqi.Zhang
 * @date 2018/11/28
 * @time 16:34
 * @package com.bankcomm.shirodemo.service
 * @project 1126webdemo
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByUsername(String username) {
        System.out.println("UserServiceImpl.findUserByUsername");
        System.out.println("username = " + username);
        User user = userMapper.selectByUsername(username);
        System.out.println("注册/登录findUserByUsername查询到用户结果为user = " + user);
        return user;

    }

    @Override
    public boolean insert(User userInsert) {
        Integer insert = userMapper.insert(userInsert);
        // 插入结果为一条则为成功
        boolean ok = insert == 1 ? true : false;
        System.out.println("注册用户--->成功插入:insert = " + insert+"条数据(应为1条)");
        return ok;
    }

    @Override
    public String getRole(String username) {
        User selectByUsername = userMapper.selectByUsername(username);
        String role = selectByUsername.getRole();
        System.out.println(username + "拥有权限：role = " + role);
        return role;
    }
}
