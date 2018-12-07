package com.bankcomm.shirodemo.config;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tianqi.Zhang
 * @date 2018/12/3
 * @time 10:56
 * @package com.bankcomm.shirodemo.config
 * @project 1126webdemo
 * @description Druid连接池配置文件
 */
@Configuration
public class DruidConfig {

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        Map<String, String> initParameters = new HashMap<>();
//        //禁用HTML页面上的“Rest All”功能
//        initParameters.put("resetEnable", "false");
//        //ip白名单（没有配置或者为空，则允许所有访问）
//        initParameters.put("allow", "10.8.9.115");
        //++监控页面登录用户名
        initParameters.put("loginUsername", "admin");
        //++监控页面登录用户密码
        initParameters.put("loginPassword", "000000");
//        //ip黑名单
//        initParameters.put("deny", "");
//        //如果某个ip同时存在，deny优先于allow
//        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }


}
