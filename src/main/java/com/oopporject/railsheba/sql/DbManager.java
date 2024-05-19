package com.oopporject.railsheba.sql;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbManager {
    public static Connection MakeDbConnection() {
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "Hemal4362";

        try {
            DBGenerator.genDB();
            return DriverManager.getConnection(url,username,password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
