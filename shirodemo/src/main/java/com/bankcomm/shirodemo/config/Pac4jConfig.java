package com.bankcomm.shirodemo.config;

import com.bankcomm.shirodemo.shiro.pac4j.MyCasClient;
import io.buji.pac4j.context.ShiroSessionStore;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.cas.logout.CasLogoutHandler;
import org.pac4j.cas.logout.DefaultCasLogoutHandler;
import org.pac4j.core.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tianqi.Zhang
 * @date 2018/12/28
 * @time 16:40
 * @package com.bankcomm.shirodemo.config
 * @project 1126webdemo
 * @description
 */
@Configuration
public class Pac4jConfig {

    /** 地址为：cas地址 */
    @Value("${cas.server.url}")
    private String casServerUrl;

    /** 地址为：验证返回后的项目地址：http://localhost:8081 */
    @Value("${cas.project.url}")
    private String projectUrl;

    /** 相当于一个标志，可以随意 */
    @Value("${cas.client-name}")
    private String clientName;

    @Autowired
    private com.bankcomm.shirodemo.shiro.pac4j.MyCasClient myCasClient;




    /**
     * 自定义存储
     * @return
     */
    @Bean
    public ShiroSessionStore shiroSessionStore(){
        return new ShiroSessionStore();
    }



    /**
     * pac4j配置
     *
     * @param myCasClient
     * @param shiroSessionStore
     * @return
     */
    @Bean("authcConfig")
    public Config config(ShiroSessionStore shiroSessionStore) {
        {
            // 可以删除
            System.out.println("myCasClient = " + myCasClient);
            boolean flag1 = myCasClient == null;
            System.out.println("myCasClient注入失败？false代表注入成功 = " + flag1);
        }
        Config config = new Config(myCasClient);
        {
            // 可以删除
            System.out.println("config = " + config);
            boolean flag = config == null;
            System.out.println("config注入失败？flag = " + flag);
        }
        config.setSessionStore(shiroSessionStore);
        return config;
    }



    /**
     * cas 客户端配置
     * 可能这里还要设置更多信息
     * @param casConfig
     * @return
     */
    @Bean
    public MyCasClient casClient(CasConfiguration casConfig) {
        MyCasClient casClient = new MyCasClient(casConfig);
        String backUrl = projectUrl + "/callback?client_name=" + clientName;
        System.out.println("【回调地址】backUrl = " + backUrl);
        //客户端回调地址
        casClient.setCallbackUrl(backUrl);
        casClient.setName(clientName);
        return casClient;
    }

//
//    /**
//     * 临时添加（会出错）
//     * @return
//     */
//    @Bean
//    public CasLogoutHandler casLogoutHandler(){
//
//        CasLogoutHandler casLogoutHandler=new DefaultCasLogoutHandler();
//        casLogoutHandler.destroySessionBack(null,null);
//        casLogoutHandler.destroySessionFront(null,null);
//        casLogoutHandler.recordSession(null,null);
//        casLogoutHandler.renewSession(null,null);
//        return casLogoutHandler;
//    }

    /**
     * 请求cas服务端配置
     * @param casLogoutHandler
     */
    @Bean
    public CasConfiguration casConfig(){
        final CasConfiguration configuration = new CasConfiguration();
        //CAS server登录地址
        configuration.setLoginUrl(casServerUrl + "/login");
        //CAS 版本，默认为 CAS30，我们使用的是 CAS20
        configuration.setProtocol(CasProtocol.CAS30);
        configuration.setAcceptAnyProxy(true);
        configuration.setPrefixUrl(casServerUrl + "/");
        return configuration;
    }
}
