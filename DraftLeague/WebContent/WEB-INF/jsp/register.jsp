<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% int year = Calendar.getInstance().get(Calendar.YEAR); request.setAttribute("year", year); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
       
    </head>
    <body>
        <form action="./register.html" method="post" onsubmit="return validateRegister()">
            <table>
                <tr>
                    <td colspan="2" align="center">Register</td>
                </tr>
                <tr>
                    <td>
                        Username
                    </td>
                    <td>
                        <input type="text" name="username" id="uname" value='<%=request.getParameter("username") == null ? "" : request.getParameter("username")%>'/>
                    </td>
                    <td id="usermessage"></td>
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
                    <td>
                        Name
                    </td>
                    <td>
                        <input type="text" name="name" id="name" value='<%=request.getParameter("name") == null ? "" : request.getParameter("name")%>'/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Email
                    </td>
                    <td>
                        <input type="text" name="email" id="email" value='<%=request.getParameter("email") == null ? "" : request.getParameter("email")%>'/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Phone
                    </td>
                    <td>
                        <input type="text" name="phone" id="phone" value='<%=request.getParameter("phone") == null ? "" : request.getParameter("phone")%>'/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="color: red;">
                        <%=request.getAttribute("error") != null ? request.getAttribute("error").toString() : ""%>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        Existing User? <a href="./login.html">Login</a>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="right">
                        <input type="submit" value="Register"/>
                        <input type="reset" value="Clear"/>
                        <!--<input type="button" value="Login" onclick="validateLogin()"/>-->
                    </td>
                </tr>
            </table>
        </form>
<script type="text/javascript" src="./js/jquery-3.1.0.min.js"></script>
                    <script type="text/javascript" src="./js/app.js"></script>
    </body>
</html>