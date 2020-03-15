<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>请你输入正确的账号和密码 你需要输入正确的密码</h2>
<form>
    Enter Your Name: <input type="text" id="username" />
    Enter Your Password: <input type="text" id="password" />
</form>
<br>
<button id="login">login</button>

</body>


<script>
    $(document).ready(function() {
        $('#login').blur(function(event) {
            var username = $('#username').val();
            var password = $('#password').val();
            $.get('/users/login', {
                username : username,
                password : password
            }, function(responseText) {

            });
        });
    });
</script>
</html>
