
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<div align="center">


    <h1>store_添加商品</h1>
    <form action="${pageContext.request.contextPath}/Addprod" method="post" enctype="multipart/form-data">
        <table>
            <tr>
                <td>商品名称</td>
                <td><input type="text" name="name"></td>
            </tr>
            <tr>
                <td>单价</td>
                <td><input type="text" name="price"></td>
            </tr>
            <tr>
                <td>商品种类</td>
                <td>
                    <select name="category">
                        <option value="电子数码">电子数码</option>
                        <option value="图书杂志">图书杂志</option>
                        <option value="床上用品">床上用品</option>
                        <option value="日用百货">日用百货</option>
                        <option value="大型家电">大型家电</option>
                        <option value="家用武器">家用武器</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>库存数量</td>
                <td><input type="text" name="pnum"></td>
            </tr>
            <tr>
                <td>商品图片</td>
                <td><input type="file" name="file1"></td>
            </tr>
            <tr>
                <td>描述信息</td>
                <td><textarea name="description" cols="30" rows="10"></textarea></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="添加商品"></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
