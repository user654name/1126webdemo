package com.bankcomm.shirodemo.config;

import org.apache.shiro.realm.ldap.DefaultLdapRealm;
import org.apache.shiro.realm.ldap.JndiLdapContextFactory;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.naming.NamingException;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;

/**
 * @author Tianqi.Zhang
 * @date 2018/12/20
 * @time 10:30
 * @package com.bankcomm.shirodemo.config
 * @project 1126webdemo
 * @description
 */
@Configuration
public class LdapRealmConfig {


}
