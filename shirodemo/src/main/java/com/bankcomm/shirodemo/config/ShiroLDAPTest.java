package com.bankcomm.shirodemo.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tianqi.Zhang
 * @date 2018/12/20
 * @time 10:55
 * @package com.bankcomm.shirodemo.config
 * @project 1126webdemo
 * @description
 */
// snipped...
public class ShiroLDAPTest {

    private static Logger logger = LoggerFactory.getLogger(ShiroLDAPTest.class);

    /**
     * @param args
     */
    public static void main(String[] args) {

        // Using the IniSecurityManagerFactory, which will use the an INI file
        // as the security file.
        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:actived.ini");

        // Setting up the SecurityManager...
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        Subject user = SecurityUtils.getSubject();

        logger.info("User is authenticated:  " + user.isAuthenticated());

        UsernamePasswordToken token =
                new UsernamePasswordToken(
                        "cn=Cornelius Buckley,ou=people,o=sevenSeas", "argh");

        user.login(token);

        logger.info("User is authenticated:  " + user.isAuthenticated());
    }
}