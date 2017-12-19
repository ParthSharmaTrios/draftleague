
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <title>${SportName}</title>
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
                <a class="dropdown-item" href="/register.html">Sign Up</a>
                <a class="dropdown-item" href="/login.html">Login</a>
              </div>
        </li>
      <% } else { %>
        <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><%= session.getAttribute("username") %></a>
              <div class="dropdown-menu" aria-labelledby="dropdown01">
                <a class="dropdown-item" href="/profile">Profile</a>
                <a class="dropdown-item" href="/DraftLeague/AccountSettings.html">Account Settings</a>
                <a class="dropdown-item" href="/DraftLeague/logout.html">Logout</a>
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

	<h1> Create a new league for ${SportName}</h1>
	
	<br />
	
	<form method="post" action="../League.html">
	
	<input type="hidden" name="UserId" value="<%= session.getAttribute("Id") %>">
	<input type="hidden" name="SportId" value="${SportId}">
	
	<label for="leagueName">League Name: </label>
		<input required type="text" name="leagueName" id="leagueName"><br><br>
	
	<label for="contestType">Contest Type: </label>
		<select id = "contestType" name="contestType">
               <option value = "1">Head-to-Head</option>
               <option value = "2">Points Only</option>
               <option value = "3">Rotisserie</option>
        </select><br><br>
	
	<label for="draftType">Draft Type: </label>
		<select id = "draftType" name="draftType">
               <option value = "1">Live Standard Draft</option>
               <option value = "2">Autopick Draft</option>
        </select><br><br>
	
	<label for="draftDate">Draft Date: </label>
		<input required type="date" name="draftDate" id="draftDate"><br><br>
	
	<label for="draftTime">Draft Time: </label>
		<input required type="time" name="draftTime" id="draftTime"><br><br>
	
	<input type="submit" value = "Create">
	
	</form>
	
	
	
</div>
<script type="text/javascript" src="./js/jquery-3.1.0.min.js"></script>
                    <script type="text/javascript" src="./js/app.js"></script>

</body>
</html>