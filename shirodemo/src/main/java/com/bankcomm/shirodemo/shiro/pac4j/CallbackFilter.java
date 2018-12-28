package com.bankcomm.shirodemo.shiro.pac4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author Tianqi.Zhang
 * @date 2018/12/28
 * @time 16:45
 * @package com.bankcomm.shirodemo.shiro.pac4j
 * @project 1126webdemo
 * @description 是单点登录后回调使用的过滤器。
 */
public class CallbackFilter extends io.buji.pac4j.filter.CallbackFilter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        super.doFilter(servletRequest, servletResponse, filterChain);
    }
}
