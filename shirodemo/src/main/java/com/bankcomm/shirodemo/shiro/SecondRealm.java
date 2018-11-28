package com.bankcomm.shirodemo.shiro;


import com.bankcomm.shirodemo.config.ShiroConfig;
import com.bankcomm.shirodemo.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 这个Realm复制自 CustomRealm
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
 */
@Component
public class SecondRealm extends AuthorizingRealm {

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


        SimpleAuthenticationInfo info = null;
        Object principal = "root";
        Object credentials = "666888";
//        ByteSource credentialsSalt= (ByteSource) "";
        String realmName = "";
        info = new SimpleAuthenticationInfo(principal, credentials, realmName);
        return info;
        //        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
//        // 从数据库获取对应用户名密码的用户
//        String password = userMapper.getPassword(token.getUsername());
//        if (null == password) {
//            throw new AccountException("用户名不正确");
//        } else if (!password.equals(new String((char[]) token.getCredentials()))) {
//            throw new AccountException("密码不正确");
//        }
//        return new SimpleAuthenticationInfo(token.getPrincipal(), password, getName());
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
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
//        String role = userMapper.getRole(username);
        Set<String> set = new HashSet<>();
        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
//        set.add(role);
        //设置该用户拥有的角色
        info.setRoles(set);
        return info;
    }
}