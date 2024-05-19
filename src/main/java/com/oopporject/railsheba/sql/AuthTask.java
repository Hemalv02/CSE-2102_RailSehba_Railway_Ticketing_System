package com.oopporject.railsheba.sql;

import com.oopporject.railsheba.utils.AuthUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthTask {
    private static PreparedStatement pstmt = null;
    private static PreparedStatement getPreparedStatement(String query, String email, String password) throws SQLException {
        Connection conn = DbManager.MakeDbConnection();
        if (conn == null) {
            System.out.println("Null Connection");
        }
        if (conn != null) {
            pstmt = conn.prepareStatement(query);
        }
        pstmt.setString(1, email);
        pstmt.setString(2, password);
        return pstmt;
    }

    public static void InsertIntoDatabase(String email, String password) {
        String query = "INSERT INTO rail_db.user (email, password_hash) VALUES (?, SHA2(?, 256))";
        try {
            pstmt = getPreparedStatement(query, email, password);
            pstmt.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static boolean UpdateIntoDatabase(String email, String password) {
        String query = "UPDATE rail_db.user SET password_hash = SHA2(?, 256) WHERE email = ?";
        try {
            Connection conn = DbManager.MakeDbConnection();
            if (conn == null) {
                System.out.println("Null Connection");
            }
            if (conn != null) {
                pstmt = conn.prepareStatement(query);
            }
            pstmt.setString(1, password);
            pstmt.setString(2, email);
            int row = pstmt.executeUpdate();
            return row > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean verifyLogin(String email, String password) {

        String query = "SELECT * FROM rail_db.user WHERE email = ? AND password_hash = SHA2(?, 256)";

        try {
            Connection conn = DbManager.MakeDbConnection();
            if (conn == null) {
                System.out.println("Null Connection");
            }
            if (conn != null) {
                pstmt = conn.prepareStatement(query);
            }

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            // If the result set is not empty, it means the user exists with the provided email and password
            if (rs.next()) {
                return true;
            } else {
                AuthUtil.showAlert("Login Error","Invalid email or password.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getUserRole(String email, String password) throws SQLException {
        String role = null;
        String query = "SELECT role FROM rail_db.user WHERE email = ? AND password_hash = SHA2(?, 256)";

        try {
            Connection conn = DbManager.MakeDbConnection();
            if (conn == null) {
                System.out.println("Null Connection");
            }

            PreparedStatement preparedStatement = null;
            if (conn != null) {
                preparedStatement = conn.prepareStatement(query);
            }

            if (preparedStatement != null) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
            }


            if (preparedStatement != null) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        role = resultSet.getString("role");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return role;
    }

    public static void main(String[] args) {
        UpdateIntoDatabase("djfdj@gmail.com", "12345678");
    }


}
