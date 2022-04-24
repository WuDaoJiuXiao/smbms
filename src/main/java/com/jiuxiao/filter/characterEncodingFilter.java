package com.jiuxiao.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * 字符编码过滤器
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/21 9:52
 * @since 1.0.0
 */
public class characterEncodingFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
