<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
</head>
<body>
    <h1>订单列表</h1>
    <c:forEach items="${requestScope.list}" var="olf">
        订单号: ${olf.id} <br>
        用户名称: ${olf.username} <br>
        订单金额: ${olf.money} <br>
        支付状态: 
            <c:if test="${olf.paystate==0}">
                <font color="red">未支付</font>
            </c:if>
            <c:if test="${olf.paystate!=0}">
                <font color="blue">已支付</font>
            </c:if>
         <br>
        收货地址: ${olf.receiverinfo} <br>
        下单时间: ${olf.ordertime} <br>
        <table width="100%" border="1">
            <tr>
                <th>商品名称</th>
                <th>商品种类</th>
                <th>购买数量</th>
                <th>商品单价</th>
                <th>总金额</th>
            </tr>
            <c:forEach items="${olf.prodMap}" var="entry">
                <tr>
                    <td>${entry.key.name}</td>
                    <td>${entry.key.category}</td>
                    <td>${entry.value}</td>
                    <td>${entry.key.price}</td>
                    <td>${entry.key.price * entry.value}</td>
                </tr>
            </c:forEach>
        </table>
        <hr>
    </c:forEach>
</body>
</html>
