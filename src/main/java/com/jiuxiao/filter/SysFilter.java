package com.jiuxiao.filter;

import com.jiuxiao.pojo.User;
import com.jiuxiao.utils.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录拦截
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/22 9:47
 * @since 1.0.0
 */
public class SysFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        User user = (User) req.getSession().getAttribute(Constants.USER_SESSION);
        if (user == null) {
            resp.sendRedirect("/smbms/error.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {

    }
}
