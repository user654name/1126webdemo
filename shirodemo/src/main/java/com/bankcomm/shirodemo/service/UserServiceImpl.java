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
        return userMapper.selectByUsername(username);

    }
}
