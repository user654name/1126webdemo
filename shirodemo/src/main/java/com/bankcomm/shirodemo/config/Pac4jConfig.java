package com.bankcomm.shirodemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
/**
 * @author Tianqi.Zhang
 * @date 2018/12/28
 * @time 13:58
 * @package com.bankcomm.shirodemo.config
 * @project 1126webdemo
 * @description
 */
@Configuration
public class Pac4jConfig {
    //地址为：cas地址
    @Value("${shiro.cas}")
    String casServerUrlPrefix;

    //地址为：验证返回后的项目地址：http://localhost:8080
    @Value("${shiro.server}")
    String shiroServerUrlPrefix;

    //相当于一个标志，可以随意，shiroConfig中也会用到
    @Value("${pac4j.clientName}")
    String clientName;

}
