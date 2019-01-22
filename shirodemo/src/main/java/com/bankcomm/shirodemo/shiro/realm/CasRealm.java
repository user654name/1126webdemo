package com.bankcomm.shirodemo.shiro.realm;

import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jPrincipal;
import io.buji.pac4j.token.Pac4jToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * 用于Cas认证的Realm
 *
 * @author Tianqi.Zhang
 * @date 2018/12/28
 * @time 13:55
 * @package com.bankcomm.shirodemo.shiro.realm
 * @project 1126webdemo
 * @description
 */
@Component
public class CasRealm extends Pac4jRealm {

    /**
     * 客户端名称
     */
    @Value("${cas.client-name}")
    private String clientName;

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        System.out.println("=========进入认证域========");
        Pac4jToken pac4jToken = (Pac4jToken) authenticationToken;
        final List<CommonProfile> commonProfileList = pac4jToken.getProfiles();
        final CommonProfile commonProfile = commonProfileList.get(0);
        System.out.println("单点登录返回的信息" + commonProfile.toString());
        System.out.println("commonProfile.isExpired()" + commonProfile.isExpired());
        //todo
        final Pac4jPrincipal principal = new Pac4jPrincipal(commonProfileList, getPrincipalNameAttribute());
        final PrincipalCollection principalCollection = new SimplePrincipalCollection(principal, getName());
        return new SimpleAuthenticationInfo(principalCollection, commonProfileList.hashCode());

    }


//        System.out.print("进入");
//        System.out.println("authenticationToken = " + authenticationToken);
//        Pac4jToken pac4jToken = (Pac4jToken) authenticationToken;
//        final List<CommonProfile> commonProfileList = pac4jToken.getProfiles();
//        final CommonProfile commonProfile = commonProfileList.get(0);
//        System.out.println("单点登录返回的信息" + commonProfile.toString());
//        //todo
//        final Pac4jPrincipal principal = new Pac4jPrincipal(commonProfileList, getPrincipalNameAttribute());
//        final PrincipalCollection principalCollection = new SimplePrincipalCollection(principal, getName());
//        return new SimpleAuthenticationInfo(principalCollection, commonProfileList.hashCode());
//    }

    /**
     * 授权/验权（todo 后续有权限在此增加）
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
        authInfo.addStringPermission("user");
        return authInfo;
    }
}


