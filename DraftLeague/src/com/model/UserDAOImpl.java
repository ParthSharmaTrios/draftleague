package com.model;

import com.common.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class UserDAOImpl implements UserDAO {

    @Override
    public boolean validateUser(User user) {
    	String encrptPassword="";
        Connection con = null;
        try {
        	System.out.println("entered for login checking");
        	con = DBConnection.getDBConnection();
            Statement stmt = con.createStatement();
            encrptPassword = cryptWithMD5(user.getPassword());
            System.out.println("select * from users where (email= '"+user.getUsername() +"' or username='" + user.getUsername() + "')" + (encrptPassword == null ? "" : " and password='" + encrptPassword + "'"));
            ResultSet rs = stmt.executeQuery("select * from users where (email= '"+user.getUsername() +"' or username='" + user.getUsername() + "')" + (encrptPassword == null ? "" : " and password='" + encrptPassword + "'"));
            if (rs.next()) {
            	System.out.println("entered resultset");
            	System.out.println("user id"+rs.getInt(1)+"name"+rs.getString(2)+"username"+rs.getString(4)+""+rs.getString(5));
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setUsername(rs.getString(4));
                user.setPhone(rs.getString(5));
                user.setEmail(rs.getString(6));
                user.setPic(rs.getString(7));
                return true;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                }
            }
        }
        return false;
    }

    @Override
    public int registerUser(User user) {
    		String encrpPassword = "";
    		String  picLink = "C:\\Users\\Owner\\eclipse-workspace\\DraftLeague_new\\WebContent\\images\\profilepic.jpeg";
     	    
        Connection con = null;
        try {
        	System.out.println("entered for registration");
        	 encrpPassword = cryptWithMD5(user.getPassword());
            con = DBConnection.getDBConnection();
            if (usernameExisted(user.getUsername())) {
                return -1;
            } else {
            	System.out.println("entered for inserting into database");

                PreparedStatement pstmt = con.prepareStatement("insert into users (username, password, name, phoneno, email,displaypic) values(?, ?, ?, ?, ?,?) ");
                int i = 1;
                pstmt.setString(i++, user.getUsername());
                pstmt.setString(i++, encrpPassword);
                pstmt.setString(i++, user.getName());
                pstmt.setString(i++, user.getPhone());
                pstmt.setString(i++, user.getEmail());
                pstmt.setString(i++, picLink);
                
                pstmt.executeUpdate();
                return 1;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                }
            }
        }
        return 0;
    }

    
    @Override
    public int createMyLeague(User user,HttpServletRequest request) {
    	 Connection con = null;
         
    	try {
        	System.out.println("entered for creating league");
        	  con = DBConnection.getDBConnection();
            	System.out.println("entered for inserting into database");
            	String leagueName = (String)request.getParameter("leagueName");
            	String contestType = (String)request.getParameter("contestType");
            	int userId = Integer.parseInt(request.getParameter("UserId"));
            	int sportId = Integer.parseInt(request.getParameter("SportId"));
            	String drDate = (String)request.getParameter("draftDate");
            	
            	SimpleDateFormat sdf1 = new SimpleDateFormat("dd-mm-yyyy");
            	java.sql.Date sqlStartDate = null;
            	try {
            	java.util.Date date = sdf1.parse(drDate);
            	 sqlStartDate = new java.sql.Date(date.getTime());  
            	
            	}
            	catch (Exception e)
            	{
            		System.out.println("Date exception");
            	
            	}
            	String drTime = (String)request.getParameter("draftTime");
            	DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            	 SimpleDateFormat format = new SimpleDateFormat("HH:mm"); // 12 hour format
            	 java.sql.Time ppstime = null;
            	 try {

            	    java.util.Date d1 =(java.util.Date)format.parse(drTime);

            	     ppstime = new java.sql.Time(d1.getTime());
            	 }
            	 catch(Exception e)
            	 {
            		 System.out.println("Time exception");
                 	
            	 }
            	    String draftType = (String)request.getParameter("draftType");
            	
            	
                PreparedStatement pstmt = con.prepareStatement("insert into leagues (leagueName, contestType, userId, sportId, draftDate,draftTime,draftType) values(?, ?, ?, ?, ?, ?, ?) ",Statement.RETURN_GENERATED_KEYS);
                int i = 1;
                pstmt.setString(i++, leagueName);
                pstmt.setString(i++, contestType);
                pstmt.setInt(i++, userId);
                pstmt.setInt(i++, sportId);
                pstmt.setDate(i++, sqlStartDate);
                pstmt.setTime(i++, ppstime);
                pstmt.setString(i++, draftType);
                pstmt.executeUpdate();
                ResultSet rs = pstmt.getGeneratedKeys();
                int generatedKey = 0;
                if (rs.next())
                {
                generatedKey = rs.getInt(1);
                }
                System.out.println("Inserted record's ID: " + generatedKey);
                return generatedKey ;
            
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                }
            }
        }
        return 0;
    }

    @Override
    public boolean usernameExisted(String username) {
        Connection con = null;
        try {
            con = DBConnection.getDBConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from users where username = '" + username + "'");
            if (rs.next()) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                }
            }
        }
        return false;
    }

    @Override
     public boolean updateProfile(User newUser,HttpServletRequest request, String picLink) {
    	 HttpSession session = request.getSession();
         
        Connection con = null;
        try {
        	String newPassword = (String)request.getParameter("password");
        	String phoneNo= (String)request.getParameter("phone");
        	String uname = (String)session.getAttribute("username");
        	String name = (String)session.getAttribute("name");
        	String oldPassword = cryptWithMD5((String)session.getAttribute("password"));
            System.out.println("old password"+oldPassword);
            String oldPic = (String)session.getAttribute("pic");
            System.out.println("old pic"+oldPic);
            String  encrpPassword ="";
        	
            if (newPassword == null)
            {
            	encrpPassword = oldPassword;
            }
            else
            	encrpPassword = cryptWithMD5(newPassword);
        	if (picLink == "")
        	{
        		picLink =oldPic;
        	}
        	int userId= (int)session.getAttribute("Id");
        	System.out.println("New pass "+newPassword+" new phone "+phoneNo+"user id "+ userId+" tryname"+uname+" name "+name);
        	
        	// from here@@@@@@@@@@@@@@@@@
        	String destinationFile = "/images/sunil.jpeg";
        	        	

        	 
           // String ImgUrl = (String)request.getParameter("photo");
             // System.out.println("Image"+ImgUrl);
        	// till here @@@@@@@@@@@@@@@@@@@@@@@@
            con = DBConnection.getDBConnection();
            PreparedStatement pstmt = con.prepareStatement("update users set password=?, phoneno=?, displaypic=? where userid=?");
            int i = 1;
            pstmt.setString(i++,encrpPassword);
            pstmt.setString(i++, phoneNo);
            pstmt.setString(i++, picLink);
            pstmt.setInt(i++, userId);
            System.out.println(pstmt);
            pstmt.executeUpdate();
            request.getSession().setAttribute("phone", phoneNo);
            request.getSession().setAttribute("Id", userId);
            request.getSession().setAttribute("username",uname);
            request.getSession().setAttribute("name", name);
            request.getSession().setAttribute("pic", picLink);
            
            
            
           
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException x) {
                }
            }
        }
        return false;
    }

    @Override
    public boolean updatePassword(User user,HttpServletRequest request, String updateEmail) {
        Connection con = null;
        String newPassword = (String)request.getParameter("txtPass1");
        System.out.println("Email id "+updateEmail + "password"+newPassword);
        
        String encrpPassword = cryptWithMD5(newPassword);
        try {
            con = DBConnection.getDBConnection();
            PreparedStatement pstmt = con.prepareStatement("update users set password=? where email=?");
            int i = 1;
            pstmt.setString(i++, encrpPassword);
            pstmt.setString(i++, updateEmail);
            pstmt.executeUpdate();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException x) {
                }
            }
        }
        return false;
    }
    @Override
    public boolean checkSession(HttpServletRequest request)
    {
    	HttpSession session = request.getSession();
        System.out.println("@@@@@@@@@@@@@@@@My session user " +session.getAttribute("user"));
        if (session.getAttribute("user") != null) {
            return true;
        }
        else
        {
        	System.out.println("just before login redirect");

        	return false;
        }

    }
    
    
    @Override
    public boolean validateEmail(User user) {
    	Connection con = null;
        try {
        	System.out.println("entered for email checking"+user.getEmail());
        	con = DBConnection.getDBConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from users where email='" + user.getEmail() + "'" );
            if (rs.next()) {
            	System.out.println("entered resultset");

                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setPhone(rs.getString(5));
                user.setEmail(rs.getString(6));
                return true;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                }
            }
        }
        return false;
    }

    
    //from here
    
    

    public static String cryptWithMD5(String pass){
     try {
    	 MessageDigest md = MessageDigest.getInstance("MD5");
         byte[] passBytes = pass.getBytes();
         md.reset();
         byte[] digested = md.digest(passBytes);
         StringBuffer sb = new StringBuffer();
         for(int i=0;i<digested.length;i++){
             sb.append(Integer.toHexString(0xff & digested[i]));
         }
         System.out.println("Encrypted string"+sb.toString());
         return sb.toString();
     		} catch (Exception ex) {
    	  ex.printStackTrace();
    	  }
         return null;


    }

    //till here
    
}
