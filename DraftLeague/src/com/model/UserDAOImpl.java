package com.model;

import com.common.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;
import java.net.URL;

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
        Connection con = null;
        try {
        	System.out.println("entered for registration");
        	 encrpPassword = cryptWithMD5(user.getPassword());
            con = DBConnection.getDBConnection();
            if (usernameExisted(user.getUsername())) {
                return -1;
            } else {
            	System.out.println("entered for inserting into database");

                PreparedStatement pstmt = con.prepareStatement("insert into users (username, password, name, phoneno, email) values(?, ?, ?, ?, ?) ");
                int i = 1;
                pstmt.setString(i++, user.getUsername());
                pstmt.setString(i++, encrpPassword);
                pstmt.setString(i++, user.getName());
                pstmt.setString(i++, user.getPhone());
                pstmt.setString(i++, user.getEmail());
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
    
    public boolean updateProfile(User newUser,HttpServletRequest request) {
    	 HttpSession session = request.getSession();
         
        Connection con = null;
        try {
        	String newPassword = (String)request.getParameter("password");
        	String phoneNo= (String)request.getParameter("phone");
        	String uname = (String)session.getAttribute("username");
        	String name = (String)session.getAttribute("name");
        	String photourl = (String)request.getParameter("photo");
        	System.out.println("photo "+photourl);
        	
        	int userId= (int)session.getAttribute("Id");
        	System.out.println("New pass "+newPassword+" new phone "+phoneNo+"user id "+ userId+" tryname"+uname+" name "+name);
        	String  encrpPassword = cryptWithMD5(newPassword);
        	// from here@@@@@@@@@@@@@@@@@
        	String destinationFile = "/images/sunil.jpeg";
        	try
        	{
        	URL url = new URL(photourl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(new File(destinationFile));
            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        	}
        	catch(Exception  ex)
        	{
        		System.out.println("ex"+ex);
        	}
        	

        	 
           // String ImgUrl = (String)request.getParameter("photo");
             // System.out.println("Image"+ImgUrl);
        	// till here @@@@@@@@@@@@@@@@@@@@@@@@
            con = DBConnection.getDBConnection();
            PreparedStatement pstmt = con.prepareStatement("update users set password=?, phoneno=? where userid=?");
            int i = 1;
            pstmt.setString(i++,encrpPassword);
            pstmt.setString(i++, phoneNo);
            pstmt.setInt(i++, userId);
            System.out.println(pstmt);
            pstmt.executeUpdate();
            request.getSession().setAttribute("phone", phoneNo);
            request.getSession().setAttribute("Id", userId);
            request.getSession().setAttribute("username",uname);
            request.getSession().setAttribute("name", name);
            
            
           
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
    public boolean updatePassword(int id, String newpass) {
        Connection con = null;
        try {
            con = DBConnection.getDBConnection();
            PreparedStatement pstmt = con.prepareStatement("update users set password=? where id=?");
            int i = 1;
            pstmt.setString(i++, newpass);
            pstmt.setInt(i++, id);
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
        	System.out.println("entered for email checking");
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
