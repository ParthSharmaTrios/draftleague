<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.io.*"%>
<%@page import="java.sql.Connection" %>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.PreparedStatement" %>
<%@page import="java.sql.Statement" %>
<%@page import="org.springframework.stereotype.Service"%>
<%@page import="com.model.DBConnection"%>
<%@page import="com.common.getResultSet"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
 



<%
int userId= (int)session.getAttribute("Id");
System.out.println("UserId in Dashboard"+userId);
Connection con = null;
ResultSet rs = null;
try {
    con = DBConnection.getDBConnection();
    Statement stmt = con.createStatement();
     rs = stmt.executeQuery("select * from leagues where userid = " + userId);
   
} catch (SQLException | ClassNotFoundException ex) {
    ex.printStackTrace();
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Dashboard Page</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-md bg-dark navbar-dark">
	<a class="navbar-brand" href="#">
		<img src="DRAFT.png" alt="Logo" style="width:40px;">
		Navbar
	</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
    <span class="navbar-toggler-icon"></span>
  </button>
  
  <div class="collapse navbar-collapse justify-content-end" id="collapsibleNavbar">
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" href="#">Link</a>
      </li>
	
	  <% if (session.getAttribute("user") == null ) { %>
        <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Signup/Login</a>
              <div class="dropdown-menu" aria-labelledby="dropdown01">
                <a class="dropdown-item" href="register.html">Sign Up</a>
                <a class="dropdown-item" href="login.html">Login</a>
              </div>
        </li>
      <% } else { %>
        <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><%= session.getAttribute("username") %></a>
              <div class="dropdown-menu" aria-labelledby="dropdown01">
                <a class="dropdown-item" href="/profile">Profile</a>
                <a class="dropdown-item" href="AccountSettings.html">Account Settings</a>
                <a class="dropdown-item" href="logout.html">Logout</a>
              </div>
        </li>
        <% } %>
      <li class="nav-item">
        <a class="nav-link" href="#">Link</a>
      </li>    
    </ul>
  </div>  
</nav>
<br>



<div class="container">

	<h1>Welcome Name: <%= session.getAttribute("username") %></h1>
	<br />
	</div>
	<Table border ="1">
	<% if(rs.next()) 
	{
		 %>
<tr>
<Th>LeagueName</Th>
<th> Contest Type</th>
<th> Sport Name </th>
<th>Draft Date</th>
<th>Draft Time</th>
<th>Draft Type</th>
</tr>
	
	<%
	rs.beforeFirst();
	
 	 while(rs.next()) 
		{
		 System.out.println("entered resultset");
		
		
		%>
<tr>
<td><%=rs.getString(2) %></td>
<td><%=getResultSet.getResult(Integer.parseInt(rs.getString(3)), "ContestType")%></td>
<td><%=getResultSet.getResult(Integer.parseInt(rs.getString(5)), "Sports")%></td>
<td><%=rs.getString(6)%></td>
<td><%=rs.getString(7) %></td>
<td><%=rs.getString(8) %></td>

</tr>
		<%} %>
</Table>
<%} %>
</body>
</html>