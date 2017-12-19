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
    	<form action="./AccountSettings.html" method="post" onsubmit="return ValidateUpdateUser()" id="AcctSettingsfrm" enctype="multipart/form-data">
    	
    	<%= session.getAttribute("username") %> Please update the details and then click update
         <img alt="profile" src='<%=session.getAttribute("pic")%>' height="100" width="100" style="height:200px; background-color:red;"/>
              <table>
                <tr>
                    <td align="center">Update Details
                    </td>
                </tr>
                <tr>
                   <td>
                        Phone No
                   </td>
                    <td>
                        <input type="text" name="phone" id="phone" value='<%=session.getAttribute("phone")%>' />
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
                    <td>   Please select a photo to upload : </td>
                    <td>  <input type="file" name="file" /></td>
					</tr>
               
                <tr>
                    <td colspan="2" style="color: red;">
                        <%=request.getAttribute("error") != null ? request.getAttribute("error").toString() : ""%>
                    </td>
                </tr>
                
                 <tr>
                    <td colspan="2" align="right">
                        <input type="submit" value="Update"/>
                        <input type="reset" value="Clear"/>
                       
                    </td>
                </tr>
            
            </table>
        </form>
        
                    <script type="text/javascript" src="./js/jquery-3.1.0.min.js"></script>
                    <script type="text/javascript" src="./js/app.js"></script>
    </body>
</html>