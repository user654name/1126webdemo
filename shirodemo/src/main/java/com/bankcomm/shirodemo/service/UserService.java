package com.bankcomm.shirodemo.service;

import com.bankcomm.shirodemo.bean.User;
import org.springframework.stereotype.Service;

/**
 * @author Tianqi.Zhang
 * @date 2018/11/28
 * @time 16:33
 * @package com.bankcomm.shirodemo.service
 * @project 1126webdemo
 * @description
 */
@Service
public interface UserService {

    /**
     * 根据用户名 查询 用户信息
     *
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /***
     * 注册新用户
     *
     * @param userInsert
     * @return
     */
    boolean insert(User userInsert);
}
