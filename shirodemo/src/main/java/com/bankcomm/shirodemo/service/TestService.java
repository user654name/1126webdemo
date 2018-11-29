package com.bankcomm.shirodemo.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 测试用的业务类
 *
 * @author Tianqi.Zhang
 * @date 2018/11/29
 * @time 21:28
 * @package com.bankcomm.shirodemo.service
 * @project 1126webdemo
 * @description
 */
@Service
public class TestService {

    /**
     * subject获取的Session拥有HttpSession的数据
     */
    public void doMethod() {

        // 获取Subject 获取Session
        Session session = SecurityUtils.getSubject().getSession();
        // Session中获取主机信息
        String host = session.getHost();
        System.out.println("主机信息host = " + host);
        // 获取HttpSession信息
        Object value = session.getAttribute("key");
        System.out.println("key对应value = " + value);
        Date startTimestamp = session.getStartTimestamp();// 会话启动时间
        System.out.println("会话启动时间startTimestamp = " + startTimestamp);
        Date lastAccessTime = session.getLastAccessTime();// 最后访问时间
        System.out.println("最后访问时间lastAccessTime = " + lastAccessTime);
        long timeout = session.getTimeout();// Session过期时间默认30分钟
        System.out.println("当前Session过期时间为timeout = " + timeout);
        session.setTimeout(1000 * 60 * 40);// Session过期时间40分钟
        System.out.println("设置过期时间为2,400,000毫秒");
        timeout = session.getTimeout();
        System.out.println("当前Session过期时间为timeout = " + timeout);
        session.touch();// 更新最后访问时间
//        session.stop();// 销毁会话(可实现注销)

    }
}
