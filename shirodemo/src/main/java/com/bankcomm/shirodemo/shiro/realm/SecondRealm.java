package com.bankcomm.shirodemo.shiro;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.ldap.DefaultLdapContextFactory;
import org.apache.shiro.realm.ldap.DefaultLdapRealm;
import org.apache.shiro.realm.ldap.JndiLdapContextFactory;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.naming.ldap.LdapContext;

/**
 * 这个Realm复制自 CustomRealm
 *
 *
 *
 *
 * @author Tianqi.Zhang
 * @date 2018/11/28
 * @package com.bankcomm.shirodemo.config
 * @project 1126webdemo
 * <p>
 * SecondRealm - 自定义Realm实现类（多Realm实现）
 * Realm必须被实现(AuthorizingRealm是抽象类)
 * 必须配置组件注解(@Component作为组件提供给Spring-IoC管理)
 * 注入SecurityManager时，依赖这个Realm实现类
 *
 * 若改动 要通知修改ShiroConfig.myShiroRealms
 * @see ShiroConfig.myShiroRealms()
 */
@Component
public class SecondRealm extends DefaultLdapRealm {


    // 1
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        return super.doGetAuthenticationInfo(token);
    }

    // 2
    @Override
    public LdapContextFactory getContextFactory() {
        // 配置LdapContext尝试配置类属性


        return super.getContextFactory();
    }

    // 3
    @Override
    protected AuthenticationInfo queryForAuthenticationInfo(AuthenticationToken token, LdapContextFactory ldapContextFactory) throws NamingException {
        return super.queryForAuthenticationInfo(token, ldapContextFactory);
    }

    // 4
    @Override
    protected Object getLdapPrincipal(AuthenticationToken token) {
        return super.getLdapPrincipal(token);
    }

    // 5
    @Override
    protected String getUserDn(String principal) throws IllegalArgumentException, IllegalStateException {
        return super.getUserDn(principal);
    }

    // 6
    @Override
    protected String getUserDnPrefix() {
        return super.getUserDnPrefix();
    }

    // 7
    @Override
    protected String getUserDnSuffix() {
        return super.getUserDnSuffix();
    }

    @Override
    public void setUserDnTemplate(String template) throws IllegalArgumentException {
        super.setUserDnTemplate(template);
    }

    @Override
    public String getUserDnTemplate() {
        return super.getUserDnTemplate();

    }
    @Override
    public void setContextFactory(LdapContextFactory contextFactory) {
        super.setContextFactory(contextFactory);
    }


    @Override
    protected AuthenticationInfo createAuthenticationInfo(AuthenticationToken token, Object ldapPrincipal, Object ldapCredentials, LdapContext ldapContext) throws NamingException {
        return super.createAuthenticationInfo(token, ldapPrincipal, ldapCredentials, ldapContext);
    }


}