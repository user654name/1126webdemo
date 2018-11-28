package com.bankcomm.shirodemo.config;



import com.bankcomm.shirodemo.shiro.CustomRealm;
import com.bankcomm.shirodemo.shiro.SecondRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Tianqi.Zhang
 * @date 2018/11/26
 * @package com.bankcomm.shirodemo.config
 * @project 1126webdemo
 * @description 这是Shiro的核心配置文件
 */
@Configuration
public class ShiroConfig {

    private  final transient Logger log = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * 过滤器默认权限表 {anon=anon 匿名访问, authc=authc, authcBasic=authcBasic, 登出logout=logout,
     * noSessionCreation=noSessionCreation, perms=perms, port=port,
     * rest=rest, roles=roles, ssl=ssl, user=user}
     * <p>
     * anon, authc, authcBasic, user 是第一组认证过滤器
     * perms, port, rest, roles, ssl 是第二组授权过滤器
     * <p>
     * user 和 authc 的不同：当应用开启了rememberMe时, 用户下次访问时可以是一个user, 但绝不会是authc,
     * 因为authc是需要重新认证的, user表示用户不一定已通过认证, 只要曾被Shiro记住过登录状态的用户就可以正常发起请求,比如rememberMe
     * 以前的一个用户登录时开启了rememberMe, 然后他关闭浏览器, 下次再访问时他就是一个user, 而不会authc
     *
     * @param securityManager 初始化 ShiroFilterFactoryBean 的时候需要注入 SecurityManager
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        shiroFilterFactoryBean.setLoginUrl("/guest/login");
        // 设置无权限时跳转的 url;
        shiroFilterFactoryBean.setUnauthorizedUrl("/guest/notRole");
        shiroFilterFactoryBean.setSuccessUrl("/login-success");

        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //游客，开发权限
//        filterChainDefinitionMap.put("/guest/**", "anon");
        //用户，需要角色权限 “user”
//        filterChainDefinitionMap.put("/user/**", "roles[user]");
        //管理员，需要角色权限 “admin”
//        filterChainDefinitionMap.put("/admin/**", "roles[admin]");
        //开放登陆接口
        filterChainDefinitionMap.put("/guest/*", "anon");
        filterChainDefinitionMap.put("/shiro/**", "anon");
        filterChainDefinitionMap.put("/shiro/logout", "logout");

        //home放行
//        filterChainDefinitionMap.put("/home", "anon");
//        filterChainDefinitionMap.put("/shiro/login", "anon");
        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        log.info("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }


    //将自己的验证方式加入容器
    @Bean
    public Collection<Realm> myShiroRealms() {
        CustomRealm customRealm = new CustomRealm();
        SecondRealm secondRealm = new SecondRealm();
        Collection<Realm> myShiroRealms = new HashSet<>();
        myShiroRealms.add(customRealm);
        myShiroRealms.add(secondRealm);
        return myShiroRealms;
    }


    /**
     * 注入 securityManager
     *
     * @param customRealm 【重要】securityManager依赖于这个参数，若不实现，项目无法启动
     * @return
     */
    @Bean
    public SecurityManager securityManager(CustomRealm customRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置自定义的realm实现
        // securityManager.setRealm(customRealm);
        // 尝试配置多Realm
        securityManager.setRealms(myShiroRealms());
        return securityManager;
    }

    /**
     *
     * 使用密码非明文存储
     *
     * @return
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // 指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        // 加密次数 64
        credentialsMatcher.setHashIterations(64);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }


}