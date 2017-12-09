package com.controller;

import com.common.User;
import com.model.DBConnection;
import com.model.UserDAO;
import com.model.MailService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
	private MailService mailService; 
	
    @Autowired
    private LocationController locationController;

    @RequestMapping(value = "/index.html")
    public String index(HttpServletRequest request) {  // http://ip:port/appname/index.html
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            return "forward:/login.html";
        }
        return "index";
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {  // http://ip:port/appname/login.html
        
    	DBConnection.fillDBInfo("db");
    	//boolean SessionExt = userDAO.checkSession(request);
    	if (userDAO.checkSession(request)) {
            return "redirect:/";
        }
        System.out.println("just before login redirect");

        return "login";
    }
    @RequestMapping(value = "/register.html", method = RequestMethod.GET)
    public String register(HttpServletRequest request, ModelMap map) {  // http://ip:port/appname/register.html
        DBConnection.fillDBInfo("db");
        //boolean SessionExt = userDAO.checkSession(request);
    	if ( userDAO.checkSession(request)) {
            return "redirect:/";
        }
       // map.addAttribute("countryList", locationController.countryList(false));
        return "register";
    }

    @RequestMapping(value = "/dashboard.html", method = RequestMethod.GET)
    public String dashboard(HttpServletRequest request, User user, ModelMap map) {  // http://ip:port/appname/dashboard.html
        //boolean SessionExt = userDAO.checkSession(request);
    	System.out.println("DashBoard:" + userDAO.checkSession(request));
    	if ( userDAO.checkSession(request)) {
            return "dashboard";
        }
       // map.addAttribute("countryList", locationController.countryList(false));
        return "login";
    }

    
    
    @RequestMapping(value = "/login.html", method = RequestMethod.POST)
    public String login(HttpServletRequest request, User user, ModelMap map) {  // http://ip:port/appname/login.html
    	System.out.println("entered login post");
    	if (userDAO.validateUser(user)) {
        	System.out.println("entered for validationg user");

            request.getSession().setAttribute("user", user);
           // request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("username", user.getName());
            request.getSession().setAttribute("name",user.getUsername());
            
            return "dashboard";
        }
        map.addAttribute("error", "invalid username and/or password");
        System.out.println("just before return login");

        return "login";
    }

    @RequestMapping(value = "/register.html", method = RequestMethod.POST)
    public String register(HttpServletRequest request, User user, ModelMap map) {  // http://ip:port/appname/register.html
        int flag  = userDAO.registerUser(user);
        if (flag == 1) {
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("username", user.getName());
            request.getSession().setAttribute("name",user.getUsername());
            
            return "redirect:/";
        } else if(flag == -1)
            map.addAttribute("error", "username is not available");
        else
            map.addAttribute("error", "Operation failed...");
       // map.addAttribute("countryList", locationController.countryList(false));
        return "register";
    }
    @RequestMapping(value = "/logout.html", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {  // http://ip:port/appname/logout.html
        
    	request.getSession().invalidate();
    	System.out.println("Detroyed session");
        return "index";
    }

    
    @RequestMapping(value="/username.html", method = RequestMethod.POST)
    @ResponseBody
    public String checkUseranme(String username) {
        boolean flag = userDAO.usernameExisted(username);
        if(flag)
            return "username is not available";
        return "username is available";
    }
    
    //from here
    
    
	@RequestMapping(value="/ForgotPassword.html", method = RequestMethod.GET)
	public String forgotPassword()
	{
		return "ForgotPassword"; 
	}
	
	
	
	@RequestMapping(value = "/ForgotPassword.html", method = RequestMethod.POST)
    public String checkUserAlreadyExist(HttpServletRequest request, User user, ModelMap map) {  // http://ip:port/appname/login.html
    	System.out.println("entered login post");
    	if (userDAO.validateEmail(user)) {
        	System.out.println("entered for validationg user");

          //  request.getSession().setAttribute("user", user);
        	System.out.println("Useremail exists");
        	/*
        	 * 
        	 * enter code for sending mail here.........
        	 * */
        	 
            return "checkMail";
        }
        map.addAttribute("error", "Email not registered with us");
        System.out.println("just before return checkmail");

        return "checkMail";
    }

	
/*	
	@RequestMapping(value="/resetPassword.html" , method=RequestMethod.POST)
	public String resetRequest(@RequestParam(value="email") String email)
	{
		//check if the email id is valid and registered with us.
		mailService.sendMail(email);
		return "checkMail";
	}
	
	@RequestMapping(value="/newPassword/{email}" )
	public String resetPassword(@PathVariable String email,Map<String,String> model)
	{
		//check if the email id is valid and registered with us.
		model.put("emailid", email);
		return "newPassword";
	}
*/

}
