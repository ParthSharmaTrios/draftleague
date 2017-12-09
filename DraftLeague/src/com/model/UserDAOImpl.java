package com.model;

import com.common.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    public boolean updateProfile(User newUser) {
        Connection con = null;
        try {
            con = DBConnection.getDBConnection();
            PreparedStatement pstmt = con.prepareStatement("update users set name=?, email=?, phoneno=? where id=?");
            int i = 1;
            pstmt.setString(i++, newUser.getName());
            pstmt.setString(i++, newUser.getEmail());
            pstmt.setString(i++, newUser.getPhone());
            pstmt.setInt(i++, newUser.getId());
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
