<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
    <h1>订单生成</h1>
    <form action="/AddOrder" method="post">
        收货地址:<textarea rows="5" cols="45"></textarea> <br>
        支付方式: <input type="radio" />在线支付 <br>
        <input type="submit" value="生成订单" />
    </form>
    购物清单: <br>

</body>
</html>
