package com.bankcomm.shirodemo.shiro.config;


import com.bankcomm.shirodemo.shiro.realm.CustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.*;

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

    private final Logger log = LoggerFactory.getLogger(ShiroConfig.class);


    /**
     * 2018年11月29日 23:16:29
     * 为了启动模版对标签支持 引入配置
     *
     * @return
     *//*
    @Bean
    public ITemplateResolver fileTemplateResolver(ThymeleafProperties properties) {
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
        resolver.setCacheable(properties.isCache());
        resolver.setSuffix(properties.getSuffix());
        if (properties.getEncoding() != null) {
            resolver.setCharacterEncoding(properties.getEncoding().name());
        }
        resolver.setTemplateMode(properties.getMode());
        resolver.setPrefix("./templates/");
        return resolver;
    }

    *//**
     * 2018年11月29日 23:16:29
     * 为了启动模版对标签支持 引入配置
     *
     * @return
     *//*
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(fileTemplateResolver());

        Set<IDialect> additionalDialects = new HashSet<IDialect>();

        additionalDialects.add(new ShiroDialect());

        templateEngine.setAdditionalDialects(additionalDialects);

        return templateEngine;
    }
*/

    /**
     * 2018年11月28日 09:58:16
     * 配置生命周期管理器
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        log.info("生命周期管理配置OK-ShiroConfig.getLifecycleBeanPostProcessor");
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 2018年11月28日 09:58:16
     * 配置生命周期管理器 代理
     *
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxy = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxy.setProxyTargetClass(true);
        log.info("开启AOP自动代理，扫描切入点 ShiroConfig.getDefaultAdvisorAutoProxyCreator");
        return defaultAdvisorAutoProxy;
    }

/*    *//**
     * 引入shiro-ehcache缓存配置
     * 暂时没有配置成功
     * 2018年11月29日 22:49:02
     *
     * @return
     *//*
    @Bean
    public EhCacheManager ehCacheManager() {
        log.info("------------->ShiroConfiguration.getEhCacheManager()执行");
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }*/


    /**
     * 过滤器默认权限表
     * {
     *    anon=anon 匿名访问,
     *    authc=authc,
     *    authcBasic=authcBasic,
     *    logout=logout 登出,
     *    noSessionCreation=noSessionCreation,
     *    perms=perms,
     *    port=port,
     *    rest=rest,
     *    roles=roles,
     *    ssl=ssl,
     *    user=user }
     *
     * anon, authc, authcBasic, user 是第一组认证过滤器
     * perms, port, rest, roles, ssl 是第二组授权过滤器
     *
     * user 和 authc 的不同：
     * 当应用开启了rememberMe时, 用户下次访问时可以是一个user, 但绝不会是authc,
     * 因为authc是需要重新认证的, user表示用户不一定已通过认证,
     * 只要曾被Shiro记住过登录状态的用户就可以正常发起请求,比如rememberMe
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
        // 无权限时 跳转的
        shiroFilterFactoryBean.setUnauthorizedUrl("/authc/notrole");
        // 登录成功
        shiroFilterFactoryBean.setSuccessUrl("/login-success");

        // 设置拦截器 LinkedHashMap 注意顺序
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();


        // 设置过滤器内容
        buildFilterChainDefinitionMap(filterChainDefinitionMap);

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        log.info("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }

    /**
     * 设置过滤器 内容
     *
     * @param filterChainDefinitionMap
     */
    private void buildFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap) {
        //游客，开发权限
//        filterChainDefinitionMap.put("/guest/**", "anon");
        //用户，需要角色权限 “user”
//        filterChainDefinitionMap.put("/user/**", "roles[user]");
        //管理员，需要角色权限 “admin”
//        filterChainDefinitionMap.put("/admin/**", "roles[admin]");
        //开放登陆接口
        filterChainDefinitionMap.put("/guest/*", "anon");
        filterChainDefinitionMap.put("/shiro/register", "anon");
        filterChainDefinitionMap.put("/shiro/login", "anon");
        filterChainDefinitionMap.put("/shiro/logout", "logout");
        //加入权限页面2018年11月29日 06:12:49
        filterChainDefinitionMap.put("/admin/adminpage", "roles[admin]");
        filterChainDefinitionMap.put("/shiro/*", "authc");

        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterChainDefinitionMap.put("/**", "authc");
    }

//
//    /**
//     * 将自己的验证方式加入容器
//     * 2018年11月29日 16:55:26
//     */
//    @Bean
//    @Deprecated
//    public Collection<Realm> myShiroRealms() {
//        CustomRealm customRealm = new CustomRealm();
////        SecondRealm secondRealm = new SecondRealm();
//        Collection<Realm> myShiroRealms = new HashSet<>();
//        myShiroRealms.add(customRealm);
////        myShiroRealms.add(secondRealm);
//        log.info("当前Realms情况为myShiroRealms = " + myShiroRealms);
//        return myShiroRealms;
//    }


    /**
     * 身份认证Realm，此处的注入不可以缺少。否则会在UserRealm中注入对象会报空指针.
     *
     * @return
     */
    @Bean
    public CustomRealm myShiroRealm() {
        CustomRealm myShiroRealm = new CustomRealm();
//        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }



    /**
     * 注入 securityManager
     *
     * @param customRealm 【重要】securityManager依赖于这个参数，若不实现，项目无法启动
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置自定义的realm实现
        // securityManager.setRealm(customRealm);
        // 尝试配置多Realm
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    /**
     *
     * 使用密码非明文存储
     * 指定加密策略(不可逆加密策略——MD5 SHA1)
     * 可作为属性注入给各个Realm(就在这个类配置)
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