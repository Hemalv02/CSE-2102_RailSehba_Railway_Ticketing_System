package com.oopporject.railsheba.sql;

import com.oopporject.railsheba.model.Entity;
import com.oopporject.railsheba.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class UserTask implements SqlTask {

    private PreparedStatement pstmt = null;
    private PreparedStatement getPreparedStatement(String query, User user) throws SQLException {
        Connection conn = DbManager.MakeDbConnection();
        if (conn == null) {
            System.out.println("Null Connection");
        }
        if (conn != null) {
            pstmt = conn.prepareStatement(query);
        }
        // pstmt = Objects.requireNonNull(DbManager.MakeDbConnection()).prepareStatement(query);
        pstmt.setString(1, user.getName());
        pstmt.setString(2, user.getEmail());
        pstmt.setString(3, user.getNumber());
        pstmt.setString(4, user.getNid());
        pstmt.setDate(5, Date.valueOf(user.getBirthDate()));
        return pstmt;
    }

    @Override
    public void InsertIntoDatabase(Entity entity) {
        String insertQuery = "INSERT INTO rail_db.user_profile (name, email, phone_number, nid_number, date_of_birth) VALUES (?, ?, ?, ?, ?)";
        try {
            //pstmt.executeUpdate("use rail_db");
            pstmt = getPreparedStatement(insertQuery, (User) entity);
            pstmt.executeUpdate();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void UpdateIntoDatabase(Entity entity) {
        String updateQuery = "UPDATE rail_db.user_profile SET name = ?, email = ?, phone_number = ?, nid_number = ?, date_of_birth = ? WHERE uid = ?";
    }

    @Override
    public void InsertFromDatabase(Entity entity) {
        try {
            String query = "SELECT name, email, phone_number, nid_number, date_of_birth FROM rail_db.user_profile WHERE email = ?";
            Connection conn = DbManager.MakeDbConnection();
            PreparedStatement pstmt = null;
            if (conn != null) {
                pstmt = conn.prepareStatement(query);
            }

            ResultSet rs = null;

            if (pstmt != null) {
                pstmt.setString(1, ((User) entity).getEmail());
                rs = pstmt.executeQuery();
            }

            if (rs != null && rs.next()) {
                String name = rs.getString("name");
                String phoneNumber = rs.getString("phone_number");
                String nidNumber = rs.getString("nid_number");
                LocalDate date_of_birth = rs.getDate("date_of_birth").toLocalDate();
                User newUser = new User(name, ((User) entity).getEmail(), phoneNumber, nidNumber, date_of_birth);
                entity = newUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUserFromDatabase(String email) {
        User user = null;
        String query = "SELECT name, email, phone_number, nid_number, date_of_birth FROM rail_db.user_profile WHERE email = ?";

        try {
            Connection conn = DbManager.MakeDbConnection();
            if (conn == null) {
                System.out.println("Null Connection");
            }
            if (conn != null) {
                pstmt = conn.prepareStatement(query);
            }

            if (pstmt == null) {
                System.out.println("Prepare statement error.");
            }

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            // If the result set is not empty, it means the user exists with the provided email and password
            if (rs.next()) {
                String name = rs.getString("name");
                String phoneNumber = rs.getString("phone_number");
                String nidNumber = rs.getString("nid_number");
                LocalDate date_of_birth = rs.getDate("date_of_birth").toLocalDate();
                user = new User(name, email, phoneNumber, nidNumber, date_of_birth);
                System.out.println(name + email + date_of_birth.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;


//        User user = null;
//        try {
//            String query = "SELECT name, email, phone_number, nid_number, date_of_birth FROM rail_db.user_profile WHERE email = ?";
//            Connection conn = DbManager.MakeDbConnection();
//            PreparedStatement pstmt = null;
//            if (conn != null) {
//                pstmt = conn.prepareStatement(query);
//            }
//
//            ResultSet rs = null;
//
//            if (pstmt != null) {
//                pstmt.setString(1, email);
//                rs = pstmt.executeQuery();
//            }
//
//            if (rs != null && rs.next()) {
//                String name = rs.getString("name");
//                String phoneNumber = rs.getString("phone_number");
//                String nidNumber = rs.getString("nid_number");
//                LocalDate date_of_birth = rs.getDate("date_of_birth").toLocalDate();
//                user = new User(name, email, phoneNumber, nidNumber, date_of_birth);
//                System.out.println(name + email + date_of_birth.toString());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return user;
    }
}
