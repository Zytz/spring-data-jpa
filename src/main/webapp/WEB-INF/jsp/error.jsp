<html>
<body>
<h2>请你输入正确的账号和密码 你需要输入正确的秘密</h2>
<input>

<form>
    Enter Your Name: <input type="text" id="username" />
    Enter Your Password: <input type="text" id="password" />
</form>
<br>

<strong>users Response</strong>
</body>


<script>
    $(document).ready(function() {
        $('#userName').blur(function(event) {
            var name = $('#userName').val();
            $.get('/users/login', {
                username : username,
                password : password
            }, function(responseText) {

            });
        });
    });
</script>
</html>
