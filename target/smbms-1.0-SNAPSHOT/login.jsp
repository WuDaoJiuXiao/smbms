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