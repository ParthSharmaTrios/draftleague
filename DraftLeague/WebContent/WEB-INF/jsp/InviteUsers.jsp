<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="com.common.getResultSet"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Invite to play</title>
</head>
<body>

	<h1> Invite friends to play for ${LeagueName}</h1>
	
	<br />
	
<form action="./sendInvite" method="post" id="inviteToPlayfrm">
<label for="EmailAddress">Email Address: </label>
		<input required type="text" name="EmailAdd" id="EmailAdd" size = "100" title="Email addresses separated by commas"><br><br>
		<input type="submit" value="Invite">
	<input type="hidden" name="UserId" value="<%= session.getAttribute("Id") %>">
	<input type="hidden" name="LeagueId" value="${LeagueId}">
	
</form>
   <script type="text/javascript" src="./js/jquery-3.1.0.min.js"></script>
                    <script type="text/javascript" src="./js/app.js"></script>
	
</body>
</html>