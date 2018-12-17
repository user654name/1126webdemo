package com.bankcomm.shirodemo.shiro;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.ldap.DefaultLdapRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

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

    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的
     * 若使用密码非明文存储 需要调用SimpleAuthenticationInfo的4参数的方法
     *
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     * @see ShiroConfig 加密策略配置
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("第二个Realm——SecondRealm.doGetAuthenticationInfo");
        System.out.println("————第二个Realm——身份认证方法————");

        return null;
 }

    /**
     * 获取授权信息(获取用户对应的权限)
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("————权限认证————");
     return null;
    }
}