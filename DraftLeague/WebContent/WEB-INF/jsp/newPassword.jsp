<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Home Page</title>
</head>
<body>
	<form action="./newPassword" method="post" onsubmit=" return validatePassword()" id="updatePassfrm">
		<table>
			<tr>
				<td>Password</td>
				<td><input type="password" name = "txtPass1" id="txtPass1"> </td>
			</tr>
			<tr>
				<td>Retype-Password</td>
				<td><input type="password" id="txtPass2"> </td>
			</tr>
			<tr>
			
				<td colspan="2"><center><input type="submit" value="Save"> </center> </td>
			
			</tr>
			
		</table>
		<input type="hidden" name="token" value="${token}">
	
	</form>
	     <script type="text/javascript" src="./js/jquery-3.1.0.min.js"></script>
                    <script type="text/javascript" src="./js/app.js"></script>
	
</body>
</html>