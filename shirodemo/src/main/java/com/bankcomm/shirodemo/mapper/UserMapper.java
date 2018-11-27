package com.bankcomm.shirodemo.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author Tianqi.Zhang
 * @date 2018/11/26
 * @package com.bankcomm.shirodemo.mapper
 * @project 1126webdemo
 */
@Mapper
public interface UserMapper {
    String getPassword(String username);
    String getRole(String username);
}
