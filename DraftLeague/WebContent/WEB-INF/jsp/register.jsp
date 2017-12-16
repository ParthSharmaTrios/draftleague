<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>DraftLeague Registration</title>


    <!-- Bootstrap -->
    <script src="js/login/pace.js"></script>
    <link href="css/login/bootstrap.css" rel="stylesheet">
    <link href="css/login/theme.css" rel="stylesheet">
    <link href="css/login/font-awesome.css" rel="stylesheet">
    <link href="css/login/animate.css" rel="stylesheet">
    <link href='https://fonts.googleapis.com/css?family=Roboto+Slab:700,400|Open+Sans+Condensed:300' rel='stylesheet' type='text/css'>
    <link href="css/login/theme-loading-bar.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
	
	<style>
		input[type="email"], input[type="password"] {

			background-color : #b7b7b7;
			border:1px solid #ffff00;

		}
		label{
			color:#d7d7d7;
		}
		.lg-btn, .lg-btn:hover{
			background-color:#ffff00;
			color:black;
			 font-weight: 900;
			border:#ffff00;
			border-radius:0px;
			width:100px;
			height:40px;
		}
		#logo{
			z-index:999;
			position:relative;
		}
		
		
	</style>
	
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
<div class="container" id="container" style="display:none;">
    <header style="z-index:-10;position:relative;">
        <!-- Main comapny header -->
        <nav class="navbar navbar-default navbar-fixed-top" role="navigation" style="background-color:#707070; border-bottom:2px solid #ffff00; z-index:0;">
            <div class="container">
                <div class="navbar-header">
                 
                </div>
            </div>
			
        </nav>
		  <section id="logo" class="col-md-12 col-md-offset-5">
		<img src="images/logo.png" height="200" >
	</section>
    </header>
	
    <section id="form" class="animated fadeInDown">
        <div class="container">
            <div id="loginbox" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
                <div class="panel white-alpha-90" >
                    
                    <div class="panel-body" >
                        <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>
                        <form class="form-horizontal" role="form" method="POST" action="./register.html" id="loginForm">
                           



                            <div class="form-group">
                                <label class="col-md-4 control-label">Username</label>

                                <div class="col-md-6">
                                    <input type="text" class="form-control" name="username" id="uname" value='<%=request.getParameter("username") == null ? "" : request.getParameter("username")%>'>

                                   
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 control-label">Password</label>

                                <div class="col-md-6">
                                    <input type="password" class="form-control" name="password" id="password">

                                  
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 control-label">Name</label>

                                <div class="col-md-6">
                                    <input class="form-control" type="text" name="name" id="name" value='<%=request.getParameter("name") == null ? "" : request.getParameter("name")%>'/>

                                  
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 control-label">Email</label>

                                <div class="col-md-6">
                                    <input type="email" class="form-control" name="email" id="email" value='<%=request.getParameter("email") == null ? "" : request.getParameter("email")%>'/>


                                  
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 control-label">Phone</label>

                                <div class="col-md-6">
                        			<input type="text" class="form-control" name="phone" id="phone" value='<%=request.getParameter("phone") == null ? "" : request.getParameter("phone")%>'/>


                                  
                                </div>
                            </div>
                            
                            <div class="form-group">
                                    <%=request.getAttribute("error") != null ? request.getAttribute("error").toString() : ""%>


                            </div>

                           

                            
                    </div>
					
					
                </div>
				<div class="row">
						<div class="col-md-12">
							 <div class="form-group">
                                <div class="col-md-6 col-md-offset-4">
									<br><br>
                                    <button type="submit" class="btn btn-primary lg-btn"" >
                                       Register
                                    </button>

                                   
                                </div>
                            </div>
                        </form>
						</div>
						
					</div>
					<br><br>
            </div>
        </div>
    </section>
    <footer>
        <nav class="navbar navbar-default navbar-fixed-bottom" role="navigation">
            <div class="container text-center">
                <div class="footer-content" style="color:#d7d7d7;">
                    Haven't registered yet? <a href="./login.html" class="btn btn-primary lg-btn" style="width:130px;"> Login </a>
                </div>
            </div><!-- /.container-fluid -->
        </nav>
    </footer>
</div>

<script src="js/login/jquery.min.js"></script>
<script src="js/login/bootstrap.min.js"></script>
<script src="js/login/jquery.backstretch.min.js"></script>
<script type="text/javascript" src="js/app.js"></script>

<script>
    Pace.on('hide', function(){
        $("#container").fadeIn('1000');
        $.backstretch([
            "images/bg2.jpg",
            
        ], {duration: 5000, fade: 1000});
    });

</script>

</body>
</html>







