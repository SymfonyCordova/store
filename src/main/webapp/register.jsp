<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript">
        function changeImg(img) {
            img.src = img.src + "?time=" + new Date().getTime();
        }
        function checkForm() {
            var canSub = true;
            //1.非空校验
            canSub = checkNull("username", "用户名不能为空") && canSub;
            console.log(canSub);
            canSub = checkNull("password", "密码不能为空") && canSub;
            canSub = checkNull("password2", "确认密码不能为空") && canSub;
            canSub = checkNull("nickname", "昵称不能为空") && canSub;
            canSub = checkNull("email", "邮箱不能为空") && canSub;
            canSub = checkNull("valistr", "验证码不能为空") && canSub;

            //2.两次密码是否一致
            var psw1 = document.getElementsByName("password")[0].value;
            var psw2 = document.getElementsByName("password2")[0].value;
            if(psw1 != psw2){
                document.getElementById("password2_msg").innerHTML = "<span color='red'>"+两次密码不一致+"</span>";
                canSub = false;
            }

            //3.邮箱格式校验 \w+@\w+(\.\w+)+
            var email = document.getElementsByName("email")[0].value;
            if(!/^\w+@\w+(\.\w+)+$/.test(email)){
                document.getElementById("email_msg").innerHTML = "<span color='red'>"+邮箱格式不正确+"</span>";
                canSub = false;
            }

            return canSub;
        }
        function checkNull(name, msg) {
            document.getElementById(name+"_msg").innerHTML = "";
            var objValue = document.getElementsByName(name)[0].value;
            if(objValue == null || objValue == ""){
                document.getElementById(name+"_msg").innerHTML = "<span color='red'>"+msg+"</span>";
                return false;
            }else{
                return true;
            }
        }
    </script>
</head>
<body>
<div align="center">
    <h1>store注册</h1>
    <form action="${pageContext.request.contextPath}/Register" method="post" onsubmit="return checkForm();">
        <table>
            <tr>
                <td>用户名:</td>
                <td><input type="text" name="username" value="${param.username}" /></td>
                <td id="username_msg"></td>
            </tr>
            <tr>
                <td>密码:</td>
                <td><input type="password" name="password" value="" /></td>
                <td id="password_msg"></td>
            </tr>
            <tr>
                <td>确认密码:</td>
                <td><input type="password" name="password2" value="" /></td>
                <td id="password2_msg"></td>
            </tr>
            <tr>
                <td>昵称:</td>
                <td><input type="text" name="nickname" value="${param.nickname}" /></td>
                <td id="nickname_msg"></td>
            </tr>
            <tr>
                <td>邮箱:</td>
                <td><input type="text" name="email" value="${param.email}" /></td>
                <td id="email_msg"></td>
            </tr>
            <tr>
                <td>验证码:</td>
                <td><input type="text" name="valistr" value="" /></td>
                <td id="valistr_msg">${msg}</td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="注册">
                </td>
                <td>
                    <img src="${pageContext.request.contextPath}/Valilmg" alt="验证码" style="cursor: pointer" onclick="changeImg(this)"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
