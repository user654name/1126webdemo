package com.bankcomm.shirodemo.service;

import com.bankcomm.shirodemo.bean.User;

/**
 * @author Tianqi.Zhang
 * @date 2018/11/28
 * @time 16:33
 * @package com.bankcomm.shirodemo.service
 * @project 1126webdemo
 * @description
 */
public interface UserService {

    /**
     * 用户注册的用户名 与 数据库内所有用户名对比
     *
     * @param username
     * @return true 代表用户名已经存在
     */
    User findUserByUsername(String username);
}
