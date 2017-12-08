<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<script>
$(function () {

    if (localStorage.chkbox && localStorage.chkbox != '') {
        $('#rememberChkBox').attr('checked', 'checked');
        $('#username').val(localStorage.username);
        $('#password').val(localStorage.pass);
    } else {
        $('#rememberChkBox').removeAttr('checked');
        $('#username').val('');
        $('#password').val('');
    }

    $('#rememberChkBox').click(function () {

        if ($('#rememberChkBox').is(':checked')) {
            // save username and password
            localStorage.username = $('#username').val();
            localStorage.pass = $('#password').val();
            localStorage.chkbox = $('#rememberChkBox').val();
        } else {
            localStorage.username = '';
            localStorage.pass = '';
            localStorage.chkbox = '';
        }
    });
});
</script>
    </head>
    <body>
        <form action="./login.html" method="post" id="loginForm">
            <table>
                <tr>
                    <td colspan="2" align="center">Login</td>
                </tr>
                <tr>
                    <td>
                        Username or emailid
                    </td>
                    <td>
                        <input type="text" name="username" id="username" value='<%=request.getParameter("username") == null ? "" : request.getParameter("username")%>'/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Password
                    </td>
                    <td>
                        <input type="password" name="password" id="password"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="color: red;">
                        ${error}
                 </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        New User? <a href="./register.html">Register</a>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="right">
                        <!--<input type="submit" value="Login"/>-->
                        Remember Me<input  name="rememberChkBox" id="rememberChkBox" type="checkbox">
                        <input type="reset" value="Clear"/>
                        <input type="button" value="Login" onclick="validateLogin()"/>
                    </td>
                </tr>
                <tr>
				<td colspan="2"><a href="./ForgotPassword.html">Forgot Password</a></td>
				</tr>
                
            </table>
        </form>
        <script type="text/javascript" src="./js/jquery-3.1.0.min.js"></script>
        <script type="text/javascript" src="./js/app.js"></script>
    </body>
</html>