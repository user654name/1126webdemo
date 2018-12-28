package com.bankcomm.shirodemo.config;


import com.bankcomm.shirodemo.shiro.MyLdapRealm;
import com.bankcomm.shirodemo.shiro.realm.CasRealm;
import com.bankcomm.shirodemo.shiro.realm.CustomRealm;
import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.filter.LogoutFilter;
import io.buji.pac4j.filter.SecurityFilter;
import io.buji.pac4j.subject.Pac4jSubjectFactory;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.ldap.JndiLdapContextFactory;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.pac4j.core.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.*;

/**
 *Ldap的Realm也配置在这里
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
     * 项目工程路径
     */
    @Value("${cas.project.url}")
    private String projectUrl;
    /**
     * 项目cas服务路径
     */
    @Value("${cas.server.url}")
    private String casServerUrl;
    /**
     * 客户端名称
     */
    @Value("${cas.client-name}")
    private String clientName;

    /**
     * Ldap链接等配置
     *
     * 配置好放入自定Realm
     */
    @Bean
    public LdapContextFactory ldapContextFactory() {
        JndiLdapContextFactory factory = new JndiLdapContextFactory();
        factory.setUrl("ldap:127.0.0.1:1389");
//        factory.setUrl("ldap:182.119.168.147:1389");
        factory.setPoolingEnabled(true);
        return factory;
    }

    /**
     * 用于Ldap的自定Realm
     *
     * 配置好放入securityManager
     */
    @Bean
    public MyLdapRealm myRealm2() {
        MyLdapRealm myLdapRealm = new MyLdapRealm();
        // 将Ldap配置放入
        myLdapRealm.setContextFactory(ldapContextFactory());
        return myLdapRealm;
    }


    /**
     * CAS Realm
     * 配置好放入securityManager
     */
    @Bean
    public CasRealm myRealm3() {
        return new CasRealm();
    }


    /**
     * 身份认证Realm，此处的注入不可以缺少。否则会在UserRealm中注入对象会报空指针.
     *
     * @return
     */
    @Bean
    public CustomRealm myRealm1() {
        return new CustomRealm();
    }


    /**
     * 使用 pac4j 的 subjectFactory
     *
     * @return
     */
    @Bean
    public Pac4jSubjectFactory subjectFactory() {
        return new Pac4jSubjectFactory();
    }


    /**
     * SessionDAO
     */
    @Bean
    public SessionDAO sessionDAO() {
        return new MemorySessionDAO();
    }

    /**
     * SimpleCookie
     * 自定义cookie名称
     */
    @Bean
    public SimpleCookie sessionIdCookie() {
        SimpleCookie cookie = new SimpleCookie("sid");
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        return cookie;
    }

    /**
     * SessionManager
     *
     * @param sessionIdCookie
     * @param sessionDAO
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setSessionIdCookieEnabled(true);
        //30分钟
        sessionManager.setGlobalSessionTimeout(180000);
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }

    /**
     * 2018年12月28日 17:05:31 将接口改为实现类DefaultWebSecurityManager
     *
     * 注入 securityManager
     *
     * @param customRealm 【重要】securityManager依赖于这个参数，若不实现，项目无法启动
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {

//        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
//        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) factory.getInstance();
//

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置自定义的realm实现
        // securityManager.setRealm(customRealm);
        // 尝试配置多Realm
//        securityManager.setRealm(myShiroRealm());
        List realms = new ArrayList();
        realms.add(myRealm1());
//        realms.add(myRealm2());
        realms.add(myRealm3());
        securityManager.setRealms(realms);
        securityManager.setSubjectFactory(subjectFactory());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * CAS 添加 securityManager
     * @param securityManager
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }


    /**
     * 过滤器注册
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD);
        return filterRegistration;
    }


    /**
     * 加载shiroFilter权限控制规则（从数据库读取然后配置）
     * @param shiroFilterFactoryBean
     */
    private void loadShiroCasFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean){
        /*下面这些规则配置最好配置到配置文件中 */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/", "securityFilter");
        filterChainDefinitionMap.put("/application/**", "securityFilter");
        filterChainDefinitionMap.put("/index", "securityFilter");
        filterChainDefinitionMap.put("/callback", "callbackFilter");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/**","anon");
        // filterChainDefinitionMap.put("/user/edit/**", "authc,perms[user:edit]");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

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
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager,Config config) {


        clientName="client0";
        projectUrl="http://www.ssoclient2.com:10033";
        casServerUrl="http://www.cas.com";
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        // 添加casFilter到shiroFilter中
        loadShiroCasFilterChain(shiroFilterFactoryBean);



        ///////////////////////////////
        Map<String, Filter> filters = new HashMap<>(3);
        //cas 资源认证拦截器
        SecurityFilter securityFilter = new SecurityFilter();
        securityFilter.setConfig(config);
        securityFilter.setClients(clientName);
        filters.put("securityFilter", securityFilter);
        //cas 认证后回调拦截器
        CallbackFilter callbackFilter = new CallbackFilter();
        callbackFilter.setConfig(config);
        callbackFilter.setDefaultUrl(projectUrl);
        filters.put("callbackFilter", callbackFilter);
        // 注销 拦截器
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setConfig(config);
        logoutFilter.setCentralLogout(true);
        logoutFilter.setLocalLogout(true);
        logoutFilter.setDefaultUrl(projectUrl + "/callback?client_name=" + clientName);
        System.out.println("【重要设置】projectUrl="+projectUrl + "/callback?client_name=" + clientName);
        System.out.println("【重要设置】clientName="+clientName);
        System.out.println("【重要设置】casServerUrl="+casServerUrl);
        filters.put("logout",logoutFilter);
        shiroFilterFactoryBean.setFilters(filters);
        ///////////////////////////////









//
//        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
//        shiroFilterFactoryBean.setLoginUrl("/guest/login");
//        // 无权限时 跳转的
//        shiroFilterFactoryBean.setUnauthorizedUrl("/authc/notrole");
//        // 登录成功
//        shiroFilterFactoryBean.setSuccessUrl("/login-success");
//
//        // 设置拦截器 LinkedHashMap 注意顺序
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//
//
//        // 设置过滤器内容
//        buildFilterChainDefinitionMap(filterChainDefinitionMap);
//
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//
        log.info("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }


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
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
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
/*
        filterChainDefinitionMap.put("/swagger-ui.html**","anon");
        filterChainDefinitionMap.put("/guest/*", "anon");
        filterChainDefinitionMap.put("/shiro/register", "anon");
        filterChainDefinitionMap.put("/shiro/login", "anon");
        filterChainDefinitionMap.put("/druid/** ", "anon");
        filterChainDefinitionMap.put("/shiro/logout", "logout");
        //加入权限页面2018年11月29日 06:12:49
        filterChainDefinitionMap.put("/admin/adminpage", "roles[admin]");
        filterChainDefinitionMap.put("/shiro/*", "authc");

        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterChainDefinitionMap.put("/**", "authc");
*/


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