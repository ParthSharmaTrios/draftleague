package com.model;

import javax.servlet.http.HttpServletRequest;

import com.common.User;

public interface UserDAO {
    
    public boolean validateUser(User user);
    public boolean validateEmail(User user);
    public int registerUser(User user);
    public int createMyLeague(User user,HttpServletRequest request);
    public boolean usernameExisted(String username);
    public boolean updateProfile(User newUsers,HttpServletRequest request,String picLink);
    public boolean updatePassword(User user,HttpServletRequest request, String updateEmail);
    public boolean checkSession(HttpServletRequest request);
  
}