package com.bankcomm.shirodemo.shiro;


import com.bankcomm.shirodemo.controller.TestController;
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
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.naming.ldap.LdapContext;

/**
 * 用于使用Ldap的目录数据库的Realm
 */
@Deprecated
@Component
public class MyLdapRealm extends DefaultLdapRealm {


    private final Logger log = LoggerFactory.getLogger(TestController.class);

    @Override
    protected String getUserDn(String principal) throws IllegalArgumentException, IllegalStateException {

        if (!StringUtils.hasText(principal)) {
            throw new IllegalArgumentException("User principal cannot be null or empty for User DN construction.");
        } else {
            String prefix = "uid=";
            String suffix;
            if (principal.indexOf("sdc") > 0) {
                // 包含sdc后缀的用户名名拼凑以下内容
                suffix = ",ou=partner,ou=People,o=bankcomm.com,o=bankcomm";
            } else {
                // 不包含sdc后缀的用户名拼凑相应内容
                suffix = "ou=People,o=bankcomm.com,o=bankcomm";
            }
            if (prefix == null && suffix == null) {
                log.debug("userDnTemplate property has not been configured, indicating the submitted AuthenticationToken's principal is the same as the User DN.  Returning the method argument as is.");
                return principal;
            } else {
                int prefixLength = prefix != null ? prefix.length() : 0;
                int suffixLength = suffix != null ? suffix.length() : 0;
                StringBuilder sb = new StringBuilder(prefixLength + principal.length() + suffixLength);
                if (prefixLength > 0) {
                    sb.append(prefix);
                }

                sb.append(principal);
                if (suffixLength > 0) {
                    sb.append(suffix);
                }
                String userDn = sb.toString();
                log.info("userDn=" + userDn);
                return sb.toString();
            }
        }
    }
}