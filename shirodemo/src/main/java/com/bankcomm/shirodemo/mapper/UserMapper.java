package com.bankcomm.shirodemo.mapper;

/**
 * @author Tianqi.Zhang
 * @date 2018/11/26
 * @package com.bankcomm.shirodemo.mapper
 * @project 1126webdemo
 */
public interface UserMapper {
    String getPassword(String username);

    String getRole(String username);
}
