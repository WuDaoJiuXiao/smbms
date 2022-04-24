package com.jiuxiao.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.jiuxiao.pojo.Role;
import com.jiuxiao.pojo.User;
import com.jiuxiao.service.role.RoleService;
import com.jiuxiao.service.role.RoleServiceImpl;
import com.jiuxiao.service.user.UserService;
import com.jiuxiao.service.user.UserServiceImpl;
import com.jiuxiao.utils.Constants;
import com.jiuxiao.utils.PageSupport;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户 Servlet
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/22 15:28
 * @since 1.0.0
 */
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");

        if (method != null) {
            if (method.equals("savepwd")) {
                this.upDatePwd(req, resp);
            } else if (method.equals("pwdmodify")) {
                this.pwdModify(req, resp);
            } else if (method.equals("query")) {
                this.query(req, resp);
            } else if (method.equals("add")) {
                this.add(req, resp);
            } else if (method.equals("getrolelist")) {
                this.getRoleList(req, resp);
            } else if (method.equals("ucexist")) {
                this.userCodeExist(req, resp);
            } else if (method.equals("deluser")) {
                this.delUser(req, resp);
            } else if (method.equals("view")) {
                this.getUserById(req, resp, "userview.jsp");
            } else if (method.equals("modify")) {
                this.getUserById(req, resp, "usermodify.jsp");
            } else if (method.equals("modifyexe")) {
                this.modify(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 修改用户密码
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void upDatePwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        String newpassword = req.getParameter("newpassword");
        boolean flag = false;

        if (o != null && newpassword != null && newpassword.length() != 0) {
            UserService userService = new UserServiceImpl();
            flag = userService.updatePwd(((User) o).getId(), newpassword);
            if (flag) {
                req.setAttribute(Constants.SHOW_MESSAGE, "密码修改成功，请重新登录...");
                req.getSession().setAttribute(Constants.USER_SESSION, null);
            } else {
                req.setAttribute(Constants.SHOW_MESSAGE, "密码修改失败!");
            }
        } else {
            req.setAttribute(Constants.SHOW_MESSAGE, "新密码有问题!");
        }
        req.getRequestDispatcher("pwdmodify.jsp").forward(req, resp);
    }

    /**
     * 验证旧密码， Session 中有用户的密码
     *
     * @param request
     * @param response
     */
    public void pwdModify(HttpServletRequest request, HttpServletResponse response) {
        Object o = request.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = request.getParameter("oldpassword");

        Map<String, String> resultMap = new HashMap<String, String>();
        if (o == null) { //session 过期了
            resultMap.put("result", "sessionerror");
        } else if (StringUtils.isNullOrEmpty(oldpassword)) { //输入的密码为空
            resultMap.put("result", "error");
        } else {
            String userPassword = ((User) o).getUserPassword();
            if (oldpassword.equals(userPassword)) {
                resultMap.put("result", "true");
            } else {
                resultMap.put("result", "false");
            }
        }

        try {
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询用户列表（重难点）
     *
     * @param req
     * @param resp
     */
    public void query(HttpServletRequest req, HttpServletResponse resp) {
        //从前端获取数据
        String queryUserName = req.getParameter("queryname");
        String temp = req.getParameter("queryUserRole");
        String pageIndex = req.getParameter("pageIndex");
        int queryUserRole = 0;

        UserServiceImpl userService = new UserServiceImpl();
        List<User> userList = null;

        //第一次显示该页面，一定是第一页，且页面大小固定
        int pageSize = Constants.PAGE_SIZE;
        int currentPageNo = Constants.CURRENT_PAGE_NO;

        if (queryUserName == null) {
            queryUserName = "";
        }
        if (temp != null && !temp.equals("")) {
            queryUserRole = Integer.parseInt(temp);
        }
        if (pageIndex != null) {
            currentPageNo = Integer.parseInt(pageIndex);
        }

        //设置页面支持
        int totalCount = userService.getUserCount(queryUserName, queryUserRole);
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);

        //控制首页和尾页
        int totalPageCount = pageSupport.getTotalPageCount();
        if (currentPageNo < 1) {
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount) {
            currentPageNo = totalPageCount;
        }

        //获取用户列表展示
        userList = userService.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
        req.setAttribute("userList", userList);

        //获取角色列表展示
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        req.setAttribute("roleList", roleList);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPageNo", currentPageNo);
        req.setAttribute("totalPageCount", totalPageCount);
        req.setAttribute("queryUserName", queryUserName);
        req.setAttribute("queryUserRole", queryUserRole);

        //返回前端
        try {
            req.getRequestDispatcher("userlist.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加新用户
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userCode = request.getParameter("userCode");
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");
        String gender = request.getParameter("gender");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String userRole = request.getParameter("userRole");

        User user = new User();
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setAddress(address);
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setGender(Integer.parseInt(gender));
        user.setPhone(phone);
        user.setUserRole(Integer.valueOf(userRole));
        user.setCreationDate(new Date());
        user.setCreatedBy(((User) request.getSession().getAttribute(Constants.USER_SESSION)).getId());

        UserService userService = new UserServiceImpl();
        if (userService.add(user)) {
            response.sendRedirect(request.getContextPath() + "/jsp/user.do?method=query");
        } else {
            request.getRequestDispatcher("useradd.jsp").forward(request, response);
        }
    }

    /**
     * 删除用户
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void delUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("uid");
        int delId = 0;

        try {
            delId = Integer.parseInt(id);
        } catch (Exception e) {
            delId = 0;
            e.printStackTrace();
        }

        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (delId <= 0) {
            resultMap.put("delResult", "notexist");
        } else {
            UserService userService = new UserServiceImpl();
            if (userService.deleteUserById(delId)) {
                resultMap.put("delResult", "true");
            } else {
                resultMap.put("delResult", "false");
            }
        }

        response.setContentType("application/json");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    /**
     * 修改用户信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void modify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("uid");
        String userName = request.getParameter("userName");
        String gender = request.getParameter("gender");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String userRole = request.getParameter("userRole");

        User user = new User();
        user.setId(Integer.valueOf(id));
        user.setUserName(userName);
        user.setGender(Integer.parseInt(gender));
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(Integer.valueOf(userRole));
        user.setModifyBy(((User) request.getSession().getAttribute(Constants.USER_SESSION)).getId());
        user.setModifyDate(new Date());

        UserService userService = new UserServiceImpl();
        if (userService.modify(user)) {
            response.sendRedirect(request.getContextPath() + "/jsp/user.do?method=query");
        } else {
            request.getRequestDispatcher("usermodify.jsp").forward(request, response);
        }

    }

    /**
     * 通过 ID 获取用户类型
     *
     * @param request
     * @param response
     * @param url
     * @throws ServletException
     * @throws IOException
     */
    private void getUserById(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException {
        String id = request.getParameter("uid");
        if (!StringUtils.isNullOrEmpty(id)) {
            UserService userService = new UserServiceImpl();
            User user = userService.getUserById(id);
            request.setAttribute("user", user);
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    /**
     * 用户是否存在
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void userCodeExist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userCode = request.getParameter("userCode");
        HashMap<String, String> resultMap = new HashMap<String, String>();

        if (StringUtils.isNullOrEmpty(userCode)) {
            resultMap.put("userCode", "exist");
        } else {
            UserService userService = new UserServiceImpl();
            User user = userService.selectUserCodeExist(userCode);
            if (null != user) {
                resultMap.put("userCode", "exist");
            } else {
                resultMap.put("userCode", "notexist");
            }
        }

        response.setContentType("application/json");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    /**
     * 获取角色列表
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void getRoleList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Role> roleList = null;
        RoleService roleService = new RoleServiceImpl();
        roleList = roleService.getRoleList();

        response.setContentType("application/json");
        PrintWriter outPrintWriter = response.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(roleList));
        outPrintWriter.flush();
        outPrintWriter.close();
    }
}
