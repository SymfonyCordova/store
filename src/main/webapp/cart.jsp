<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <script type="text/javascript">
        function changeNum(id, obj, oldnum) {
            if(!/^[1-9]\d*$/.test(obj.value)){
                alert("购买数量必须为数字,并且大于0！");
               obj.value = oldnum;
                return ;
            }
            window.location.href = "/ChangeCart?id="+id+"&buynum="+obj.value;
        }
    </script>
</head>
<body>
    <h1>我的购物车</h1>
    <a href="/ProdList">继续购物</a>
    <a href="/ClearCart">清空购物车</a>
    <a href="#"><img src="/img/gotoorder.bmp" /></a>
    <c:if test="${empty sessionScope.cartmap}">
        <h2><a href="${pageContext.request.contextPath}">购物车空空如也,请先去挑点东西吧</a></h2>
    </c:if>
    <c:if test="${not empty sessionScope.cartmap}">
        <table>
            <tr>
                <td>缩略图</td>
                <td>商品名称</td>
                <td>商品种类</td>
                <td>商品单价</td>
                <td>购买数量</td>
                <td>库存状态</td>
                <td>总价</td>
                <td>删除</td>
            </tr>
            <c:set var="money" value="0"></c:set>
            <c:forEach items="${sessionScope.cartmap}" var="entry">
                <tr>
                    <td>${entry.key.imgurl}</td>
                    <td>${entry.key.name}</td>
                    <td>${entry.key.category}</td>
                    <td>${entry.key.price}元</td>
                    <td>
                        <input type="text" onchange="changeNum('${entry.key.id}', this, ${entry.value})" value="${entry.value}" style="width: 30px"/> 件
                    </td>
                    <td>
                        <c:if test="${entry.value<=entry.key.pnum}">
                            <font color="blue">有货</font>
                        </c:if>
                        <c:if test="${entry.value>entry.key.pnum}">
                            <font color="red">缺货</font>
                        </c:if>
                    </td>
                    <td>
                        ${entry.key.price * entry.value} 元
                        <c:set var="money" value="${money+entry.key.price * entry.value}"></c:set>
                    </td>
                    <td>
                        <a href="/DelCart?id=${entry.key.id}">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div>
          总价:${money}
        </div>
    </c:if>
</body>
</html>
