 function validateLogin() {
    var username = $("#username").val().trim();
    var password = document.getElementById("password").value.trim();

    if (username === "") {
        alert("Please enter username");
    } else if (password === "") {
        alert("Please enter password");
    } else {
        document.getElementById("loginForm").submit();
    }
}

function checkUserExists() {
    var userEmail = document.getElementById("email").value.trim();

    if (userEmail === "") {
        alert("Please enter registered emailId");
    }  else {
    	 document.getElementById("resetPasswordForm").submit();
    	    }
}

function validateRegister() {
    var username = document.getElementById("uname").value;
    var password = document.getElementById("password").value;
    var name = document.getElementById("name").value;
    var email = document.getElementById("email").value;
    var phone = document.getElementById("phone").value;
    var country = parseInt($("#country").val());
   
    if (username === "") {
        alert("Please enter username");
        return false;
    } else if (password === "") {
        alert("Please enter password");
        return false;
    } else if (name === "") {
        alert("Please enter name");
        return false;
    } else if (email === "") {
        alert("Please enter email");
        return false;
    } else if (phone === "") {
        alert("Please enter phone");
        return false;
    } else if(country === 0) { 
        alert("Please select country");
        return false;
    } else {
        return true;
    }
}
function ValidateUpdateUser()
{
		var password = document.getElementById("password").value;
	    var phone = document.getElementById("phone").value;
	    if (phone === "") {
	        alert("Please enter phone");
	        return false;
	    }else if (password === "") {
	        alert("Please enter password");
	        return false;
	    }else {
	        return true;
	    }
	    
	}

function validateProfile() {
    var name = document.getElementById("name").value;
    var email = document.getElementById("email").value;
    var phone = document.getElementById("phone").value;

    if (name === "") {
        alert("Please enter name");
        return false;
    } else if (email === "") {
        alert("Please enter email");
        return false;
    } else if (phone === "") {
        alert("Please enter phone");
        return false;
    } else {
        return true;
    }
}

function showEditProfile() {
    document.getElementById("info").style.display = "none";
    document.getElementById("editForm").style.display = "block";
}

function cancelEditProfile() {
    document.getElementById("info").style.display = "block";
    document.getElementById("editForm").style.display = "none";
}

function validatePassword() {
	console.log("enetered validate pass");
    var password1 = document.getElementById("txtPass1").value;
    var password2 = document.getElementById("txtPass2").value;
    
   if(password1 !== password2) {
        alert("The passwords do not match");
        return false;
    } else {
    	return true;   
    }
}

$(function() { 
    $("#uname").keyup(function() {
        validateUsername($(this).val().trim());
    });
    $("#uname").focusout(function() {
        validateUsername($(this).val().trim());
    });
    
    $("#country").change(function() {
        var c = parseInt($(this).val());
        if(c > 0) {
            $.post("./state-list.json", {countryId: c}, function(data) {
                if(data) {
                    $("#state").empty();
                    $("#state").append("<option value='0'>Select State</option>");
                    for(var e in data) {
                        $("#state").append("<option value='"+data[e].id+"'>"+data[e].name+"</option>");
                    }
                    //$("#state").html(data);
                }
            });
        }
    });
});

function validateUsername(username) {
        if(username !== "") {
            $.post("./username.html", {username: username}, function(data) {
                $("#usermessage").html(data);
            });
        }
}