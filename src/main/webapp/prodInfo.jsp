<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
    <h1>${prod.name}</h1>
    <table>
        <tr>
            <td>
                <img src="/Img?imgurl=${prod.imgurl}" />
            </td>
            <td>
                商品名称 ${prod.name} <br>
                商品种类 ${prod.category} <br>
                商品库存 ${prod.pnum} <br>
                商品价格 ${prod.price} <br>
                商品描述 ${prod.description} <br>
                <a href="${pageContext.request.contextPath}/AddCart?id=${prod.id}"><img src="/img/buy.bmp" /></a>
            </td>
        </tr>
    </table>
</body>
</html>
