<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script type="text/javascript">
        window.onload = function () {
            var str = decodeURI('${cookie.rename.value}');
            document.getElementsByName("username")[0].value = str;
        };

    </script>
</head>
<body>
<div align="center">
<h1>store登陆</h1>
<font color="red">${msg}</font>
<form action="/Login" method="post">
    <table>
        <tr>
            <td>用户名:</td>
            <td><input type="text" name="username" value="${cookie.rename.value}" /></td>
        </tr>
        <tr>
            <td>密码:</td>
            <td><input type="password" name="password" /></td>
        </tr>
        <tr>
            <td><input type="checkbox" name="rename" value="true"
                    <c:if test="${cookie.rename != null }">
                        checked="checked"
                    </c:if>
            />记住我</td>

            <td><input type="checkbox" name="autologin" value="true"
                    <c:if test="${cookie.autologin != null }">
                        checked="checked"
                    </c:if>
            />30天内自动登陆</td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="登陆" /></td>
        </tr>
    </table>
</form>
</div>
</body>
</html>

