package com.model;

import javax.servlet.http.HttpServletRequest;

import com.common.User;

public interface UserDAO {
    
    public boolean validateUser(User user);
    public boolean validateEmail(User user);
    public int registerUser(User user);
    public boolean usernameExisted(String username);
    public boolean updateProfile(User newUser);
    public boolean updatePassword(int id, String newpass);
    public boolean checkSession(HttpServletRequest request);
    public boolean checkSport(String sportName);
  
}