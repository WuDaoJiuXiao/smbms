<h3 align="center">超市订单管理系统开发笔记</h3>

#### 1 项目概述

##### 1.1 项目基本功能

超市订单管理系统只有以下几个简单的功能：登录功能、注销功能、修改密码、用户管理、订单管理、供应商管理等

![image-20220420153742585](https://my-pic-1309513254.cos.ap-shanghai.myqcloud.com//image-20220420153742585.png)

##### 1.2 数据库结构

数据库分为以下几个对象：订单表、用户表、供应商表、地址表、角色表五中关系模型

![image-20220420154347197](https://my-pic-1309513254.cos.ap-shanghai.myqcloud.com//image-20220420154347197.png)

#### 2 项目搭建准备工作

##### 2.1 管理工具选择

为了方便与项目 jar 包的管理，这里选择使用 Maven 进行管理

##### 2.2 完善目录结构

新建 Maven 项目，使用模板为 `webapp` ，项目创建好之后，完善项目目录结构

##### 2.3 配置 Tomcat

配置好 Tomcat 服务器，此处使用的端口为 `8088`，项目的默认 `index.jsp` 路径为 `localhost:8088/smbms`，首先启动 Tomcat，查看是否能启动服务

![image-20220420160840353](https://my-pic-1309513254.cos.ap-shanghai.myqcloud.com//image-20220420160840353.png)

##### 2.4 导入依赖

导入项目相关依赖：先导入最基本的 `Servlet、mysql、jsp、junit、jstl、standard` 依赖，其他的依赖后续导入

```xml
<dependencies>
    <!--Servlet依赖-->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>servlet-api</artifactId>
        <version>2.5</version>
    </dependency>
    <!--JSP依赖-->
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>jsp-api</artifactId>
        <version>2.2</version>
    </dependency>
    <!--mysql依赖-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.28</version>
    </dependency>
    <!--junit依赖-->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>
    </dependency>
    <!--JSTL依赖-->
    <dependency>
        <groupId>javax.servlet.jsp.jstl</groupId>
        <artifactId>jstl-api</artifactId>
        <version>1.2</version>
    </dependency>
    <!--standard依赖-->
    <dependency>
        <groupId>taglibs</groupId>
        <artifactId>standard</artifactId>
        <version>1.1.2</version>
    </dependency>
</dependencies>
```

##### 2.5 实体类

编写 ORM 映射：对应数据库的字段信息，分别编写实体类 `Address、Bill、User、Provider、Role`，然后补全所有实体类的 `Get、Set、ToString` 方法

```java
package com.jiuxiao.pojo;

import java.util.Date;

/**
 * 用户信息表
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/18 13:37
 * @since 1.0.0
 */
public class User {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 性别(1:女、 2:男)
     */
    private int gender;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 手机
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 用户角色(取自角色表-角色id)
     */
    private Integer userRole;

    /**
     * 创建者(userId)
     */
    private Integer createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 更新者(userId)
     */
    private Integer modifyBy;

    /**
     * 更新时间
     */
    private Date modifyDate;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 用户角色名称
     */
    private String userRoleName;
}
```

```java
package com.jiuxiao.pojo;

import java.util.Date;

/**
 * 角色信息表
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/18 13:36
 * @since 1.0.0
 */
public class Role {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 创建者
     */
    private Integer createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 修改者
     */
    private Integer modifyBy;

    /**
     * 修改时间
     */
    private Date modifyDate;
}
```

```java
package com.jiuxiao.pojo;

import java.util.Date;

/**
 * 地址信息表
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/18 13:34
 * @since 1.0.0
 */
public class Address {

    /**
     * 主键 ID
     */
    private Integer id;

    /**
     * 联系人姓名
     */
    private String contact;

    /**
     * 收货地址明细
     */
    private String addressDesc;

    /**
     * 邮编
     */
    private String postCode;

    /**
     * 联系人电话
     */
    private String tel;

    /**
     * 创建者
     */
    private Integer createBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 修改者
     */
    private Integer modifyBy;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 用户 ID
     */
    private Integer userId;
}
```

```java
package com.jiuxiao.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单信息表
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/18 13:35
 * @since 1.0.0
 */
public class Bill {

    /**
     * 主键 ID
     */
    private Integer id;

    /**
     * 账单编码
     */
    private String billCode;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品描述
     */
    private String productDesc;

    /**
     * 商品单位
     */
    private String productUnit;

    /**
     * 商品数量
     */
    private BigDecimal productCount;

    /**
     * 商品总额
     */
    private BigDecimal totalPrice;

    /**
     * 是否支付(1:未支付 2:已支付)
     */
    private int isPayment;

    /**
     * 创建者(userId)
     */
    private Integer createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 更新者(userId)
     */
    private Integer modifyBy;

    /**
     * 更新时间
     */
    private Date modifyDate;

    /**
     * 供应商ID
     */
    private Integer providerId;

    /**
     * 供应商名称
     */
    private String provideName;
}
```

```java
package com.jiuxiao.pojo;

import java.util.Date;

/**
 * 供应商信息表
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/18 13:36
 * @since 1.0.0
 */
public class Provider {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 供应商编码
     */
    private String proCode;

    /**
     * 供应商名称
     */
    private String proName;

    /**
     * 供应商详细描述
     */
    private String proDesc;

    /**
     * 供应商联系人
     */
    private String proContact;

    /**
     * 联系电话
     */
    private String proPhone;

    /**
     * 地址
     */
    private String proAddress;

    /**
     * 传真
     */
    private String proFax;

    /**
     * 创建者(userId)
     */
    private Integer createdBy;

    /**
     * 创建时间
     */
    private Date creationDate;

    /**
     * 更新时间
     */
    private Date modifyDate;

    /**
     * 更新者(userId)
     */
    private Integer modifyBy;
}
```

##### 2.6 项目配置文件

```properties
# 数据库配置文件
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306?useUnicode=true&characterEncoding=utf-8
username=root
password=0531

```

##### 2.7 数据库公共类

数据库的查询基本分为以下几个步骤：创建连接、查询数据、增加数据、更新数据、删除数据、关闭连接，而且这些步骤的写法基本都是固定不变的，所以，我们将其抽象出来成一个公共类中的方法，方便调用

```java
package com.jiuxiao.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 数据库基础公共类
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/20 17:01
 * @since 1.0.0
 */
public class BaseDao {

    private static final String driver;
    private static final String url;
    private static final String username;
    private static final String password;

    //类加载的时候就初始化
    static {
        Properties properties = new Properties();
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    //获取数据库连接
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    //查询公共方法
    public static ResultSet execute(Connection connection, String sql, Object[] params, ResultSet resultSet, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        //setObject 占位符从 1 开始，但是数组下标从 0 开始
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    //增删改公共方法
    public static int execute(Connection connection, String sql, Object[] params, PreparedStatement preparedStatement) throws SQLException {
        //预编译的 sql，在后面不用加，直接执行就行
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeUpdate();
    }

    //关闭连接，释放资源
    public static boolean closeResource(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        boolean flag = true;

        if (resultSet != null) {
            try {
                resultSet.close();
                resultSet = null;   //GC回收
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                preparedStatement = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }
}
```

##### 2.8 字符过滤器

项目中所有的请求和响应一般都要设置字符集编码格式，否则会乱码，因此直接使用过滤器进行设置

```java
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
```

在 `web.xml` 中将过滤器进行注册

```xml
<!--字符编码过滤器-->
<filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>com.jiuxiao.filter.characterEncodingFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>characterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

##### 2.9 静态资源

将本项目所要用到静态资源：图片、JS、CSS等导入项目，[资源文件获取点我](https://github.com/WuDaoJiuXiao/smbms)

#### 3 登录功能

用户登录的大致流程如下

![image-20220421144532417](https://my-pic-1309513254.cos.ap-shanghai.myqcloud.com//image-20220421144532417.png)

##### 3.1 登录页前端页面

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>系统登录-超市订单管理系统</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/style.css">
    <script></script>
</head>
<body class="login_bg">
<section class="loginBox">
    <header class="loginHeader">
        <h1>超市订单管理系统</h1>
    </header>
    <section class="loginCont">
        <form class="loginForm" action="${pageContext.request.contextPath}/login.do" name="actionForm" id="actionForm" method="post">
            <div class="info">${error}</div>
            <div class="inputbox">
                <label>用&nbsp;户&nbsp;名&nbsp;:&nbsp;</label>
                <input type="text" class="input-text" id="userCode" name="userCode" placeholder="请输入用户名" required/>
            </div>
            <div class="inputbox">
                <label>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码&nbsp;:&nbsp;</label>
                <input type="password" id="userPassword" name="userPassword" placeholder="请输入密码" required/>
            </div>
            <div class="subBtn">
                <input type="submit" value="登录"/>
                <input type="reset" value="重置"/>
            </div>
        </form>
    </section>
</section>
</body>
</html>
```

在 `web.xml` 中修改项目启动时的默认页面为 `login.jsp` 

```xml
<!--设置登录首页为 login.jsp-->
<welcome-file-list>
    <welcome-file>login.jsp</welcome-file>
</welcome-file-list>
```

启动项目，发现默认登录页面为 `login.jsp` ，修改成功

![image-20220421161058729](https://my-pic-1309513254.cos.ap-shanghai.myqcloud.com//image-20220421161058729.png)

##### 3.2 用户登录功能

用户登录时需要根据数据库的信息去查询该用户是否为管理员，因此需要去读取数据库信息

###### 3.2.1 Dao 层

 Dao 层用户登录接口

```java
package com.jiuxiao.dao.user;

import com.jiuxiao.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 根据数据库信息判断登录的用户身份
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/21 16:12
 * @since 1.0.0
 */
public interface UserDao {

    //得到登录用户的信息
    public User getLoginUser(Connection connection, String userCode) throws SQLException;
}
```

Dao 层用户登录接口实现类

```java
package com.jiuxiao.dao.user;

import com.jiuxiao.dao.BaseDao;
import com.jiuxiao.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 根据数据库信息判断登录的用户身份
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/21 20:26
 * @since 1.0.0
 */
public class UserDaoImpl implements UserDao {
    public User getLoginUser(Connection connection, String userCode) throws SQLException {

        User user = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        if (connection != null) {
            Object[] params = {userCode};
            String sql = "select * from smbms_user where userCode = ?";

            resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql, params);
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setCreatedBy(resultSet.getInt("createBy"));
                user.setCreationDate(resultSet.getTimestamp("creationDate"));
                user.setModifyBy(resultSet.getInt("modifyBy"));
                user.setModifyDate(resultSet.getTimestamp("modifyDate"));
            }
            BaseDao.closeResource(resultSet, preparedStatement, null);
        }
        return user;
    }
}
```

###### 3.2.2 Service 层

Service 层用户登录接口

```java
package com.jiuxiao.service.user;

import com.jiuxiao.pojo.User;

/**
 * 用户登录信息业务层接口
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/21 20:54
 * @since 1.0.0
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param userCode
     * @param password
     * @return
     */
    public User login(String userCode, String password);
}
```

Service 层用户登录接口实现类

```java
package com.jiuxiao.service.user;

import com.jiuxiao.dao.BaseDao;
import com.jiuxiao.dao.user.UserDao;
import com.jiuxiao.dao.user.UserDaoImpl;
import com.jiuxiao.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 用户登录信息业务层接口实现类
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/21 20:55
 * @since 1.0.0
 */
public class UserServiceImpl implements UserService {

    //业务层都会使用 Dao 层，所以要引入 Dao 层
    private final UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    /**
     * 用户登录
     *
     * @param userCode
     * @param password
     * @return
     */
    public User login(String userCode, String password) {
        Connection connection = null;
        User user = null;

        try {
            connection = BaseDao.getConnection();
            user = userDao.getLoginUser(connection, userCode);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(null, null, connection);
        }
        return user;
    }
}
```

###### 3.2.3 Servlet 层

```java
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
        //只有用户不为空，并且用户名和密码都正确的情况下才可以登录成功
        if (user != null && user.getUserCode().equals(userCode) && user.getUserPassword().equals(userPassword))) {
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
```

在 `web.xml` 中注册该 Servlet 路径为 `login.do`

```xml
<!--LoginServlet注册-->
<servlet>
    <servlet-name>LoginServlet</servlet-name>
    <servlet-class>com.jiuxiao.servlet.user.LoginServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>LoginServlet</servlet-name>
    <url-pattern>/login.do</url-pattern>
</servlet-mapping>
```

启动项目，检查用户登录模块是否正确，首先输入错误的用户名或密码，提示登录信息错误的信息成功显示到前端

![image-20220421215735041](https://my-pic-1309513254.cos.ap-shanghai.myqcloud.com//image-20220421215735041.png)

然后输入正确的管理员账户和密码，管理员成功登录

![image-20220421215847309](https://my-pic-1309513254.cos.ap-shanghai.myqcloud.com//image-20220421215847309.png)

###### 3.3.4 小结

此模块中，Servlet 层负责接收前端传过来的请求和响应，调用 Service 层的 login 方法来进行登录业务，同时调用 Dao 层查询数据库中的信息，根据信息正确与否来控制页面进行跳转，而前端的 jsp 只负责展示数据，实现了 MVC 三层架构的清晰分离

#### 4 登录功能优化

##### 4.1 用户注销功能

思路：移除 Session，返回登录页

```java
package com.jiuxiao.servlet.user;

import com.jiuxiao.utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户注销 Servelt
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/22 9:36
 * @since 1.0.0
 */
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute(Constants.USER_SESSION);
        resp.sendRedirect("/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
```

```xml
<!--LogoutServlet注册-->
<servlet>
    <servlet-name>LogoutServlet</servlet-name>
    <servlet-class>com.jiuxiao.servlet.user.LogoutServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>LogoutServlet</servlet-name>
    <url-pattern>/logout.do</url-pattern>
</servlet-mapping>
```

##### 4.2 登录拦截器

当成功登录并且注销之后，我们访问 `frame.jsp`页面，发现任然可以跳过登录直接进入，所以，我们需要一个登录拦截器对未登录用户进行拦截

```java
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
```

```xml
<!--登录过滤器-->
<filter>
    <filter-name>SysFilter</filter-name>
    <filter-class>com.jiuxiao.filter.SysFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>SysFilter</filter-name>
    <url-pattern>/jsp/*</url-pattern>
</filter-mapping>
```

重启项目，再次登录之后点击注销，然后直接访问 `frame.jsp` 页面，成功重定向到了 `error.jsp` 页面，登录拦截器设置成功

![image-20220422095950498](https://my-pic-1309513254.cos.ap-shanghai.myqcloud.com//image-20220422095950498.png)

#### 5 密码修改功能

目前先实现修改新密码功能，验证旧密码功能后续使用 Ajax 实现

##### 5.1 流程分析

写功能一般从底层开始写，针对该项目，分析如下：

+ Dao 层：根据登录用户的信息，从数据库中查询到该用户的旧密码、修改新密码

Dao 层接口

```java
/**
 * 修改用户密码
 *
 * @param connection
 * @param id
 * @param password
 * @return
 */
public int updatePwd(Connection connection, Integer id, String password) throws SQLException;
```

Dao 层实现类

```java
/**
 * 修改用户密码
 *
 * @param connection
 * @param id
 * @param password
 * @return
 */
public int updatePwd(Connection connection, Integer id, String password) throws SQLException {
    int execute = 0;
    PreparedStatement preparedStatement = null;

    if (connection != null) {
        Object[] params = {password, id};	//注意两个参数要按照下方的 ? 顺序写，否则数据库语句错误！！！
        String sql = "update smbms_user set userPassword = ? where id = ?";
        execute = BaseDao.execute(connection, preparedStatement, sql, params);
    }
    return execute;
}
```

+ Service 层：将前端传过来的旧密码与数据库的密码进行对比

Service 层接口

```java
/**
 * 根据用户 id 修改密码
 *
 * @param password
 * @param id
 * @return
 */
public boolean updatePwd(int id, String password);
```

Service 层实现类

```java
/**
 * 根据用户 Id 修改密码
 *
 * @param password
 * @param id
 * @return
 */
public boolean updatePwd(int id, String password) {
    Connection connection = null;
    boolean flag = false;
    try {
        connection = BaseDao.getConnection();
        if (userDao.updatePwd(connection, id, password) > 0) {
            flag = true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        BaseDao.closeResource(null, null, connection);
    }
    return flag;
}
```

+ Servlet 层：获取前端传过来的请求，交给业务层进行处理、传达修改密码指令给 Service 层、控制 JSP 页面跳转

```java
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
 * 实现 Servlet 复用
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/22 15:28
 * @since 1.0.0
 */
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object objcet = req.getSession().getAttribute(Constants.USER_SESSION);
        String newpassword = req.getParameter("newpassword");
        boolean flag = false;

        if (objcet != null && newpassword != null && newpassword.length() != 0) {
            UserService userService = new UserServiceImpl();
            flag = userService.updatePwd(((User) objcet).getId(), newpassword);
            if (flag) {
                req.setAttribute(Constants.SHOW_MESSAGE, "密码修改成功，请退出重新登录!");
                req.getSession().removeAttribute(Constants.USER_SESSION);
            } else {
                req.setAttribute(Constants.SHOW_MESSAGE, "密码修改失败!");
            }
        } else {
            req.setAttribute(Constants.SHOW_MESSAGE, "新密码有问题!");
        }
        req.getRequestDispatcher("pwdmodify.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
```

注册 Servlet 层

```xml
<!--UserServlet注册-->
<servlet>
    <servlet-name>UserServlet</servlet-name>
    <servlet-class>com.jiuxiao.servlet.user.UserServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>UserServlet</servlet-name>
    <url-pattern>/jsp/user.do</url-pattern>
</servlet-mapping>
```

重启项目，登陆之后修改密码为 `11111111` ，显示修改成功后，查看数据库，发现数据库中已经改变

![image-20220422215340610](https://my-pic-1309513254.cos.ap-shanghai.myqcloud.com//image-20220422215340610.png)

##### 5.2 Servlet 复用

在修改密码的功能中，修改密码需要一个 Servlet，那么后续肯定还有修改用户、修改订单、修改供应商、用户信息修改、订单信息修改数不胜数，难道都要写一个 Servlet 去实现吗？显然不现实

那么，就需要实现 Servlet 的复用，怎么操作呢？只有一个核心，**提取方法！** 

在修改密码中，我们需要接受前端 `pwdmodify.jsp` 传来的参数，我们可以在后端 Servlet 中，根据前端页面中的 `value` 和 `name` 来判断到底是哪个页面传来的请求

![image-20220423101441450](https://my-pic-1309513254.cos.ap-shanghai.myqcloud.com//image-20220423101441450.png)

因此，只需要使用 `value` 进行判断，即可实现 Servlet 的复用

```java
//将修改密码的部分提取为一个单独的方法
public void upDatePwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object objcet = req.getSession().getAttribute(Constants.USER_SESSION);
        String newpassword = req.getParameter("newpassword");
        boolean flag = false;

        if (objcet != null && newpassword != null && newpassword.length() != 0) {
            UserService userService = new UserServiceImpl();
            flag = userService.updatePwd(((User) objcet).getId(), newpassword);
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
}

//在 doGet 中使用 method 的值进行判断执行哪种方法
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String method = req.getParameter("method");

    if (method != null) {
        if (method.equals("savepwd")) {  //pwdmodify.jsp
            this.upDatePwd(req, resp);
        }
    }
}
```

#### 6 Ajax 验证旧密码

现在实现 5 中提到的旧密码验证功能，因为前段传输的数据格式一般为 `json` ，所以我们后端的数据需要用到阿里巴巴的一个工具类来实现

引入 `fastjson` 依赖

```xml
<!--阿里巴巴json依赖-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>2.0.1</version>
</dependency>
```

在 UserServlet 中继续添加，验证旧密码的 Servlet 方法，记得最后要在 doGet 中调用

```java
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
```

```java
if(method.equals("pwdmodify")){
	this.pwdModify(req, resp);
}
```

 启动项目，我们输入原始密码，当鼠标失去焦点的时候， Ajax 就会向服务器发起一条请求，后端判断结束后，返回给前端一条 json 数据

![image-20220423163248295](https://my-pic-1309513254.cos.ap-shanghai.myqcloud.com//image-20220423163248295.png)

#### 7 用户管理

##### 7.1 思路分析

用户管理页面应该包括以下几个功能：所用用户的名单、每个用户的信息、每页显示的信息条数、用户搜索、总记录条数、用户身份分类等等

![image-20220423172103003](https://my-pic-1309513254.cos.ap-shanghai.myqcloud.com//image-20220423172103003.png)

##### 7.2 分页工具类

很明显，不可能将所有用户都放在一个页面进行展示，所以我们要设置一个用户列表分页功能，首先需要自己写一个分页支持的工具类

```java
package com.jiuxiao.utils;

/**
 * 分页支持工具类
 *
 * @author WuDaoJiuXiao
 * @Date 2022/4/23 17:24
 * @since 1.0.0
 */
public class PageSupport {

    /**
     * 当前页码
     */
    private int currentPageNo;

    /**
     * 表总数量
     */
    private int totalCount;

    /**
     * 页面容量
     */
    private int pageSize;

    /**
     * 总页数 totalCount/pageSize
     */
    private int totalPageCount;

    public PageSupport() {
        currentPageNo = 1;
        totalCount = 0;
        pageSize = 1;
        totalPageCount = 1;
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        if (currentPageNo > 0) {
            this.currentPageNo = currentPageNo;
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        if (totalCount > 0) {
            this.totalCount = totalCount;
            this.setTotalPageCountByRs();   //设置总页数
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize > 0) {
            this.pageSize = pageSize;
        }
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    private void setTotalPageCountByRs() {
        int over = this.totalCount % this.pageSize;
        if (over == 0) {
            this.totalPageCount = this.totalCount / this.pageSize;
        } else if (over > 0) {
            this.totalPageCount = this.totalCount / this.pageSize + 1;
        } else {
            this.totalPageCount = 0;
        }
    }
}
```

##### 7.3 获取用户数量

当前条件下的用户总数量，显示在用户列表的左下角，用来指示翻页

Dao层

```java
/**
 * 根据用户名或者角色，获取用户总数(难点)
 *
 * @param connection
 * @param username
 * @param userRole
 * @return
 * @throws SQLException
 */
public int getUserCount(Connection connection, String username, int userRole) throws SQLException {
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    int count = 0;
    ArrayList<Object> list = new ArrayList<Object>();
    if (connection != null) {
        StringBuffer sql = new StringBuffer();
        sql.append("select count(1) as count from smbms_user u, smbms_role r where u.userRole = r.id");
        //按照用户名查询
        if (!StringUtils.isNullOrEmpty(username)) {
            sql.append(" and u.userName like ?");
            list.add("%" + username + "%");
        }
        //按照角色查询
        if (userRole > 0) {
            sql.append(" and u.userRole like ?");
            list.add(userRole);
        }
        Object[] param = list.toArray();
        System.out.println("UserDao->getUserCount : " + sql.toString());
        resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql.toString(), param);
        if (resultSet.next()) {
            count = resultSet.getInt("count");
        }
        BaseDao.closeResource(resultSet, preparedStatement, null);
    }
    return count;
}
```

Service 层

```java
/**
 * 查询用户数量
 *
 * @param username
 * @param userRole
 * @return
 */
public int getUserCount(String username, int userRole) {
    int count = 0;
    Connection connection = null;
    try {
        connection = BaseDao.getConnection();
        count = userDao.getUserCount(connection, username, userRole);
    }catch (SQLException e){
        e.printStackTrace();
    }finally {
        BaseDao.closeResource(null, null, connection);
    }
    return count;
}
```

##### 7.4 获取用户列表

当选定了查询条件之后，就是查询结果的展示了，而查询出来的用户，应该保存在一个列表中返回

Dao 层（要使用 `limit` 进行分页）

```java
/**
 * 获取用户列表
 *
 * @param connection
 * @param userName
 * @param userRole
 * @param currentPageNo
 * @param pageSize
 * @return
 * @throws SQLException
 */
public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws SQLException {
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<User> userList = new ArrayList<User>();
    if (connection != null) {
        StringBuffer sql = new StringBuffer();
        sql.append("select u.*, r.roleName as userRoleName from smbms_user u, ambms_role r where u.userRole = r.id");
        ArrayList<Object> list = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(userName)) {
            sql.append(" and u.userName like ?");
            list.add("%" + userName + "%");
        }
        if (userRole > 0) {
            sql.append(" and u.userRole = ?");
            list.add(userRole);
        }
        //使用 limit 分页
        sql.append(" order by creationDate DESC limit ?,?");
        currentPageNo = (currentPageNo - 1) * pageSize;
        list.add(currentPageNo);
        list.add(pageSize);
        Object[] param = list.toArray();
        System.out.println("UserDaoImpl -> getUserList : " + sql.toString());
        resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql.toString(), param);
        while (resultSet.next()) {
            User _user = new User();
            _user.setId(resultSet.getInt("id"));
            _user.setUserCode(resultSet.getString("userCode"));
            _user.setUserName(resultSet.getString("userName"));
            _user.setGender(resultSet.getInt("gender"));
            _user.setBirthday(resultSet.getDate("birthday"));
            _user.setPhone(resultSet.getString("phone"));
            _user.setUserRole(resultSet.getInt("userRole"));
            _user.setUserRoleName(resultSet.getString("userRoleName"));
            userList.add(_user);
        }
        BaseDao.closeResource(resultSet, preparedStatement, null);
    }
    return userList;
}
```

Service 层

```java
/**
 * 根据条件获取用户列表
 *
 * @param queryUserName
 * @param queryUserRole
 * @param currentPageNo
 * @param pageSize
 * @return
 */
public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
    Connection connection = null;
    List<User> userList = null;
    
    try {
        connection = BaseDao.getConnection();
        userList = userDao.getUserList(connection, queryUserName, queryUserRole, currentPageNo, pageSize);
    }catch (SQLException e){
        e.printStackTrace();
    }finally {
        BaseDao.closeResource(null, null, connection);
    }
    return userList;
 }
```

##### 7.5 获取角色列表

根据角色进行查询时，就需要返回角色列表，**为了与 pojo 中实体类一一对应，角色相关操作应该新建一个包与用户类区分开来**

Dao层

```java
/**
 * 获取角色列表
 *
 * @param connection
 * @return
 * @throws SQLException
 */
public List<Role> getRoleList(Connection connection) throws SQLException {
    List<Role> roleList = new ArrayList<Role>();
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    if (connection != null) {
        String sql = "select * from smbms_role";
        Object[] param = {};
        resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql, param);
        while (resultSet.next()) {
            Role _role = new Role();
            _role.setId(resultSet.getInt("id"));
            _role.setRoleCode(resultSet.getString("roleCode"));
            _role.setRoleName(resultSet.getString("roleName"));
            roleList.add(_role);
        }
        BaseDao.closeResource(resultSet, preparedStatement, null);
    }
    return roleList;
}
```

Service 层

```java
/**
 * 获取角色列表
 *
 * @return
 */
public List<Role> getRoleList() {
    Connection connection = null;
    List<Role> roleList = null;
    try {
        connection = BaseDao.getConnection();
        roleList = roleDao.getRoleList(connection);
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        BaseDao.closeResource(null, null, connection);
    }
    return roleList;
}
```

##### 7.6 Servlet 层

+ 获取用户前端的数据

+ 判断请求是否需要执行，对参数的值进行判断

+ 为了实现分页，需要计算出当前页面、总页面、页面大小

+ 用户列表展示

+ 返回前端

```java
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
    if (queryUserName == null){
        queryUserName = "";
    }
    if (temp != null && !temp.equals("")){
        queryUserRole = Integer.parseInt(temp);
    }
    if (pageIndex != null){
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
    if (currentPageNo < 1){
        currentPageNo = 1;
    }else if (currentPageNo > totalPageCount){
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
```

![image-20220424162926259](https://my-pic-1309513254.cos.ap-shanghai.myqcloud.com//image-20220424162926259.png)

其他供应商、订单与用户管理类似

