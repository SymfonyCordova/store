<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
</head>
<body>
    <h1>store_商品</h1>
    <table width="100%">
        <c:forEach items="${requestScope.list}" var="prod">
            <tr>
                <td width="40%">
                    <a href="${pageContext.request.contextPath}/ProdInfo?id=${prod.id}">
                        <img src="/Img?imgurl=${prod.imgurl}" style="cursor: pointer" />
                    </a>
                </td>
                <td width="40%">
                    ${prod.name}<br/>
                    ${prod.price}<br/>
                    ${prod.category}<br/>
                    ${prod.pnum}<br/>
                </td>
                <td width="20%">
                    <c:if test="${prod.pnum > 0}">
                        <font color="blue">有货</font>
                    </c:if>
                    <c:if test="${prod.pnum <= 0}">
                        <font color="red">缺货</font>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td colspan="3"><hr></td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>
