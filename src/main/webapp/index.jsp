<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
  </head>
  <body>
    <h1>store</h1>
    <c:if test="${sessionScope.user == null}">
      欢迎光临,游客
      <a href="${pageContext.request.contextPath}/register.jsp">注册</a>
      <a href="${pageContext.request.contextPath}/login.jsp">登陆</a>
    </c:if>
    <c:if test="${sessionScope.user != null}">
      欢迎回来,${sessionScope.user.username}
      <a href="/addProd.jsp">添加商品</a>
      <a href="/ProdList">商品列表</a>
      <a href="/cart.jsp">购物车</a>
      <a href="/OrderList">订单查询</a>
      <a href="${pageContext.request.contextPath}/Logout">注销</a>
    </c:if>
  </body>
</html>