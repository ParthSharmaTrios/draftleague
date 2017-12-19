package com.controller;

import com.common.User;
import com.common.getResultSet;
import com.model.DBConnection;
import com.model.UserDAO;
import com.model.Validations;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.model.MailService;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
//@RequestMapping("/user")
public class UserController {
    
	  public static final String DEFAULT_ENCODING = "UTF-8"; 
	    static BASE64Encoder enc = new BASE64Encoder();
	    static BASE64Decoder dec = new BASE64Decoder();

    @Autowired
    private UserDAO userDAO;
    
   
   
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
    	if (userDAO.checkSession(request)) {
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

    @RequestMapping(value = "/AccountSettings.html", method = RequestMethod.GET)
    public String AccountSettings(HttpServletRequest request) {  // http://ip:port/appname/AccountSettings.html
        //boolean SessionExt = userDAO.checkSession(request);
    	System.out.println("AccountSettings:" + userDAO.checkSession(request));
    	if ( userDAO.checkSession(request)) {
            return "AccountSettings";
        }
       // map.addAttribute("countryList", locationController.countryList(false));
        return "dashboard";
    }

    
    @RequestMapping(value = "/login.html", method = RequestMethod.POST)
    public String login(HttpServletRequest request, User user, ModelMap map) {  // http://ip:port/appname/login.html
    	System.out.println("entered login post");
    	if (userDAO.validateUser(user)) {
        	System.out.println("entered for validationg user");

            request.getSession().setAttribute("user", user);
           // request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("name", user.getName());
            request.getSession().setAttribute("username",user.getUsername());
            request.getSession().setAttribute("phone", user.getPhone());
            request.getSession().setAttribute("Id", user.getId());
            request.getSession().setAttribute("pic", user.getPic());
            request.getSession().setAttribute("password", user.getPassword());
           
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
            request.getSession().setAttribute("name", user.getName());
            request.getSession().setAttribute("username",user.getUsername());
            request.getSession().setAttribute("phone", user.getPhone());
            request.getSession().setAttribute("Id", user.getId());
            request.getSession().setAttribute("pic", user.getPic());
            request.getSession().setAttribute("password", user.getPassword());
            
            
            
            
            
            return "dashboard";
        } else if(flag == -1)
            map.addAttribute("error", "username is not available");
        else
            map.addAttribute("error", "Operation failed...");
       // map.addAttribute("countryList", locationController.countryList(false));
        return "register";
    }
    
    @RequestMapping(value = "/AccountSettings.html", method = RequestMethod.POST)
     public String AccountSettings(@RequestParam("file") MultipartFile file,ModelMap map,HttpServletRequest request,User user ) throws IOException
    {  
    	 HttpSession session = request.getSession();
         
    	String uname = (String)session.getAttribute("name");
    	System.out.println("username"+uname);
    	String picLink = "";
    	Random rand = new Random();
    	int  n = rand.nextInt(500) + 1;
    	uname = uname+n;
    	if (!file.isEmpty()) {
    		 BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
    		 picLink = "C:\\Users\\Owner\\eclipse-workspace\\DraftLeague_new\\WebContent\\images\\"+uname+".jpg";
    	    	
    		 File destination = new File(picLink); // something like C:/Users/tom/Documents/nameBasedOnSomeId.png
    		 ImageIO.write(src, "jpg", destination);
    		 //Save the id you have used to create the file name in the DB. You can retrieve the image in future with the ID.
    		// picLink = "C:\\Users\\Owner\\eclipse-workspace\\DraftLeague_new\\WebContent\\images\\sunil.jpg";
    	}
    	boolean flag  = userDAO.updateProfile(user,request,picLink);
        if (flag) {
        	
                        
            return "dashboard";
        } else
            map.addAttribute("error", "Operation failed...");
       // map.addAttribute("countryList", locationController.countryList(false));
        return "AccountSettings";
    }
   
    
    @RequestMapping(value = "/logout.html", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {  // http://ip:port/appname/logout.html
        
    	request.getSession().invalidate();
    	System.out.println("Detroyed session");
        return "index";
    }

    
    @RequestMapping(value = "/League.html", method = RequestMethod.POST)
    public String CreateLeague(HttpServletRequest request, User user, ModelMap map) {  // http://ip:port/appname/register.html
    	int leagueId  = userDAO.createMyLeague(user,request);
        if (leagueId != 0) {
            
            String leagueName =getResultSet.getLeagueName(leagueId);
            System.out.println("LeagueName"+leagueName+" LeagueId"+leagueId);
        	map.addAttribute("LeagueName",leagueName);
        	map.addAttribute("LeagueId",leagueId);
            
            return "InviteUsers";
        } 
        else
            map.addAttribute("error", "Operation failed...");
       // map.addAttribute("countryList", locationController.countryList(false));
        return "CreateNewLeague";
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
	
	
	@RequestMapping(value="/sendInvite", method = RequestMethod.GET)
	public String InviteUsers()
	{
		return "InviteUsers"; 
	}
	
	
	@RequestMapping(value="/sendInvite", method = RequestMethod.POST)
	public String InviteUsers(HttpServletRequest request, User user, ModelMap map)
	{
		 HttpSession session = request.getSession();
	        
		int LeagueId = Integer.parseInt(request.getParameter("LeagueId"));
		String leagueName = getResultSet.getLeagueName(LeagueId);
		String emailAddresses = (String)request.getParameter("EmailAdd");
		String[] emailArray = emailAddresses.split("\\s*,\\s*");
		String invitee = (String)session.getAttribute("name");
		String tokens=leagueName+"_"+LeagueId;
		 String MessageContent =" Lucky you."+invitee+" has invited you to join their custom league  "+leagueName+"  http://localhost:8080/DraftLeague_new/myPage/"+tokens;
		 String fromAddress = "Draftleaguefantasy@gmail.com";
         String sendPassword="DraftLeague123456@#";
         String subject = "Come join us";
         for(String name : emailArray){
 			System.out.println(name);
 			 String toAddress=name;
 			 doSendMail(fromAddress, sendPassword, toAddress, subject, MessageContent);

 	        
 			}
 		
         
         
         
		return "dashboard"; 
	}
	
	
	
	@RequestMapping(value = "/ForgotPassword.html", method = RequestMethod.POST)
    public String checkUserAlreadyExist(HttpServletRequest request, User user, ModelMap map) {  // http://ip:port/appname/ForgotPassword.html
    	System.out.println("entered login post");
    	if (userDAO.validateEmail(user)) {
        	System.out.println("entered for validating user"+user.getEmail());

          //  request.getSession().setAttribute("user", user);
        	System.out.println("Useremail exists");
        	/*
        	 * Draftleaguefantasy@gmail.com
        	 * enter code for sending mail here.........
        	 * http://localhost:8080/DraftLeague_new/newPassword.html?token=
        	 * */
        	 //From here for encryption of email
            String txt = user.getEmail();
            String key = "DraftLeague";
            System.out.println(txt + " XOR-ed to: " + (txt = xorMessage(txt, key)));

            String encoded = base64encode(txt);       
            System.out.println(" is encoded to: " + encoded + " and that is decoding to: " + (txt = base64decode(encoded)));
            System.out.print("XOR-ing back to original: " + xorMessage(txt, key));
            String MessageContent ="Please copy and paste the link  http://localhost:8080/DraftLeague_new/newPassword?token="+encoded;
            System.out.println("The mail content is "+MessageContent);
            
            String fromAddress = "Draftleaguefantasy@gmail.com";
            String sendPassword="DraftLeague123456@#";
            String toAddress=user.getEmail();
            String subject = "Reset Password";
            doSendMail(fromAddress, sendPassword, toAddress, subject, MessageContent);

        	//till here
            return "checkMail";
        }
        map.addAttribute("error", "Email not registered with us");
        System.out.println("just before return checkmails");

        return "checkMail";
    }

	
	
	@RequestMapping(value="/Sport/{name}", method= RequestMethod.GET)
    public String SportPage(@PathVariable(value="name") String name, ModelMap map) {

		Validations vs= new Validations();
		
		if(vs.checkSport(name.toString())) {
			map.addAttribute("SportName", name);
			return "SportPage";
		}
		else {
			return "404";
		}
		       
           
    }
	
	
	@RequestMapping(value="/createLeague/{name}", method= RequestMethod.GET)
    public String CreateNewLeague(@PathVariable(value="name") String name, ModelMap map) {

		Validations vs= new Validations();
		
		if(vs.checkSport(name.toString())) {
			map.addAttribute("SportName", name);
			map.addAttribute("SportId",vs.getSportId(name));
			return "CreateNewLeague";
		}
		else {
			return "404";
		}
		       
           
    }
	
	 @RequestMapping(value = "/newPassword", method = RequestMethod.GET)
	    public String changePassword(HttpServletRequest request, ModelMap map) {  // http://ip:port/appname/logout.html
	        
	    	System.out.println("Inside change password");
	    	 String token = (String)request.getParameter("token");
	    	 map.addAttribute("token", token);
	 		
		       
	        return "newPassword";
	    }

	 @RequestMapping(value = "/newPassword", method = RequestMethod.POST)
	    public String changePassword(ModelMap map,HttpServletRequest request,User user) {  // http://ip:port/appname/logout.html
		 System.out.println("entered post");
	        String token = (String)request.getParameter("token");
	        System.out.println("token" + token);
	        String txt  = base64decode(token);
	        String key = "DraftLeague";
	        String updateEmail = xorMessage(txt, key);
	        System.out.println("updateEmail"+updateEmail);
		 boolean flag  = userDAO.updatePassword(user,request,updateEmail);
	        if (flag) {
	        	
	                        
	            return "login";
	        } else
	            map.addAttribute("error", "Operation failed...");
	       // map.addAttribute("countryList", locationController.countryList(false));
	        return "logout";
	    }
	    

	    public static String base64encode(String text) {
	        try {
	            return enc.encode(text.getBytes(DEFAULT_ENCODING));
	        } catch (UnsupportedEncodingException e) {
	            return null;
	        }
	    }//base64encode

	    public static String base64decode(String text) {
	        try {
	            return new String(dec.decodeBuffer(text), DEFAULT_ENCODING);
	        } catch (IOException e) {
	            return null;
	        }
	    }//base64decode
	    
	    public static String xorMessage(String message, String key) {
	        try {
	            if (message == null || key == null) return null;

	            char[] keys = key.toCharArray();
	            char[] mesg = message.toCharArray();

	            int ml = mesg.length;
	            int kl = keys.length;
	            char[] newmsg = new char[ml];

	            for (int i = 0; i < ml; i++) {
	                newmsg[i] = (char)(mesg[i] ^ keys[i % kl]);
	            }//for i

	            return new String(newmsg);
	        } catch (Exception e) {
	            return null;
	        }
	    }//xorMessage

	    public void doSendMail(final String username, final String password, String to, String subject, String email_body) {

	        Properties props = new Properties();
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.socketFactory.port", "587");
	        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.port", "587");

	        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	        });
	        try {
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(username));
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	            message.setSubject(subject);
	            message.setText(email_body);
	            Transport.send(message);
	            System.out.println("message sent");
	            JOptionPane.showMessageDialog(null, "Message Sent!", "Sent", JOptionPane.INFORMATION_MESSAGE);
	        } catch (Exception e) {
	            System.out.println(e);
	            JOptionPane.showMessageDialog(null, e.toString());
	        }
	    }

}
