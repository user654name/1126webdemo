package com.bankcomm.shirodemo.shiro.realm;


import com.bankcomm.shirodemo.bean.User;
import com.bankcomm.shirodemo.config.ShiroConfig;
import com.bankcomm.shirodemo.service.UserService;
import com.bankcomm.shirodemo.service.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author Tianqi.Zhang
 * @date 2018/11/26
 * @package com.bankcomm.shirodemo.config
 * @project 1126webdemo
 *
 * CustomRealm - 自定义Realm实现类 - 重写了认证和授权方法
 *
 * Realm必须被实现(AuthorizingRealm是抽象类)
 * 必须配置组件注解(@Component作为组件提供给Spring-IoC管理)
 * 注入SecurityManager时，依赖这个Realm实现类
 */
@Component
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    private final Logger log = LoggerFactory.getLogger(CustomRealm.class);

    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的
     * 若使用密码非明文存储 需要调用SimpleAuthenticationInfo的4参数的方法
     *
     * @see ShiroConfig 加密策略配置
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————进入身份认证（登录）方法————");
        // 1.把AuthenticationInfo转换为PasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;

        //2. 从 UsernamePasswordToken 中来获取 username
        String username = upToken.getUsername();

        User userFromDb = null;

        if (userService == null) {
            System.out.println("userService注入失败");
            System.out.println(" debug ");
        } else {
            //3. 调用数据库的方法, 从数据库中查询 username 对应的用户记录
            userFromDb = userService.findUserByUsername(username);
        }


        //3-1). principal: 认证的实体信息. 这里用username
        Object principal = userFromDb.getUsername();
        //3-2). credentials: 密码.
        Object credentials = userFromDb.getPassword();
        //3-3). realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
        String realmName = getName();
        //3-4). 盐值
        String salt = "可以自定义盐值如UUID";

         /*
            ByteSource - 接口
            Util - 内部类
            bytes() - 返回SimpleByteSource
            SimpleByteSource - 实现ByteSource接口
          */
        ByteSource credentialsSalt = ByteSource.Util.bytes(salt);

        /*

                //4. 若用户不存在, 则可以抛出 UnknownAccountException 异常
                // "unknown".equals(username)这只是举例子 假设unknown用户
                if ("Unknown".equals(username)) {
                    throw new UnknownAccountException("用户不存在!");
                }

                //5. 根据用户信息的情况, 决定是否需要抛出其他的 AuthenticationException 异常.
                // 这也是举例子 这里假设monster用户被锁定
                if ("Locked".equals(username)) {
                    throw new LockedAccountException("用户被锁定");
                }
        */

        //6. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 通常使用的实现类为: SimpleAuthenticationInfo
        SimpleAuthenticationInfo info = null;

//        info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);//加盐
        info= new SimpleAuthenticationInfo(principal,credentials,realmName);
        System.out.println("当前数据库查询的SimpleAuthenticationInfo对象信息：info = " + info);
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
     * 1 PrincipalCollection获取登录用户信息
     * 2 用户信息中获取当前用户角色或权限
     * 3 创建SimpleAuthorizationInfo设置其Roles属性
     * 4 返回SimpleAuthorizationInfo对象
     *
     * 【注意】权限的注解不能加在开启事务注解的Service中
     *      (导致成为代理对象的代理，加载异常)
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("———— 进入 权限认证 ————");

        Object primaryPrincipal = principalCollection.getPrimaryPrincipal();
        System.out.println("primaryPrincipal = " + primaryPrincipal);

        // 准备： 读取用户名
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        System.out.println("————权限认证————"+username);

        // 1 PrincipalCollection获取登录用户信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 2 获得该用户角色(fromDB)
//        String role = userMapper.getRole(username);
        Set<String> set = new HashSet<>();
        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
//        set.add(role);
        //设置该用户拥有的角色
        info.setRoles(set);
        return info;
    }
}