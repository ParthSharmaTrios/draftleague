package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBConnection {

    public static String driver, url, user, password;

    public DBConnection() {

    }

    public static void fillDBInfo(String fileName) {
        if (driver == null) {
            ResourceBundle bundle = ResourceBundle.getBundle(fileName);
            driver = bundle.getString("driver");
            url = bundle.getString("url");
            user = bundle.getString("user");
            password = bundle.getString("password");
            System.out.println("got DBInfo url "+ url+ " user "+ user+ " password "+password);

        }
    }

    public static Connection getDBConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        System.out.println("entered for connection checking");

        return DriverManager.getConnection(url, user, password);
    }

}
