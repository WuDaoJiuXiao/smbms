package com.jiuxiao.servlet.user;

import com.jiuxiao.pojo.User;
import com.jiuxiao.service.user.UserService;
import com.jiuxiao.service.user.UserServiceImpl;
import com.jiuxiao.utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录 Servlet
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/21 21:30
 * @since 1.0.0
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
        UserService userService = new UserServiceImpl();
        User user = userService.login(userCode, userPassword);
        if (user != null && user.getUserCode().equals(userCode) && user.getUserPassword().equals(userPassword)) {
            //信息正确，将用户的信息放入 Session，并且跳转到主页
            req.getSession().setAttribute(Constants.USER_SESSION, user);
            resp.sendRedirect("jsp/frame.jsp");
        } else {
            //信息不正确，提示用户并且重新转发请求到登录页面
            req.setAttribute("error", "用户名或者密码不正确!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
