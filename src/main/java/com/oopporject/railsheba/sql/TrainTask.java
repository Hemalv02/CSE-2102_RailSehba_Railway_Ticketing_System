package com.oopporject.railsheba.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainTask {
    public boolean insertTrain(String trainName, String fromStn, String toStn, String acSeat, String nonAcSeat, String trainNum, String acPrice, String nonAcPrice) {
        String addTrain = "INSERT INTO rail_db.trains (trainName, fromStn, toStn, acSeat, nonAcSeat, trainNum, acPrice, nonAcPrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DbManager.MakeDbConnection();
            if (conn == null) {
                System.out.println("Null Connection");
            }

            PreparedStatement pstmt = null;
            if (conn != null) {
                pstmt = conn.prepareStatement(addTrain);
            }

            if (pstmt != null) {
                pstmt.setString(1, trainName);
                pstmt.setString(2, fromStn);
                pstmt.setString(3, toStn);
                pstmt.setString(4, acSeat);
                pstmt.setString(5, nonAcSeat);
                pstmt.setString(6, trainNum);
                pstmt.setString(7, acPrice);
                pstmt.setString(8, nonAcPrice);
                int row = pstmt.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet searchTrains(String fromStn, String toStn) {
        String searchSQL = "SELECT * FROM rail_db.trains WHERE fromStn = ? AND toStn = ?";
        ResultSet resultSet = null;
        try {
            Connection conn = DbManager.MakeDbConnection();
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(searchSQL);
            pstmt.setString(1, fromStn);
            pstmt.setString(2, toStn);
            resultSet = pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultSet;
    }

    public ResultSet trainInfo(String trainNo) {
        String searchSQL = "SELECT * FROM rail_db.trains WHERE trainNum = ?";
        ResultSet resultSet = null;
        try {
            Connection conn = DbManager.MakeDbConnection();
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement(searchSQL);
            pstmt.setString(1, trainNo);
            resultSet = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void updateInfo(String trainNo, String amount, String seat) {
        String updateSQL;
        if (seat == "AC") {
            updateSQL = "UPDATE rail_db.trains SET acSeat = ? WHERE trainNum = ?";
        } else {
            updateSQL = "UPDATE rail_db.trains SET nonAcSeat = ? WHERE trainNum = ?";
        }

        try {
            Connection conn = DbManager.MakeDbConnection();
            if (conn == null) {
                System.out.println("Null Connection");
            }

            PreparedStatement pstmt = null;
            if (conn != null) {
                pstmt = conn.prepareStatement(updateSQL);
            }

            if (pstmt != null) {
                pstmt.setString(1, amount);
                pstmt.setString(2, trainNo);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
