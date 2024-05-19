package com.oopporject.railsheba.sql;

import com.oopporject.railsheba.model.Entity;
import com.oopporject.railsheba.utils.AuthUtil;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class TicketTask {
    public void insertTicket(String trainName, String fromStation, String toStation, String travelDate, int ticketCount, String paymentMethod, double amount, String ticket_number, String email) {
        String addTrain = "INSERT INTO rail_db.purchase_history (train_name, from_station, to_station, travel_date, ticket_count, payment_method, amount, ticket_number, email) VALUES(?,?,?,?,?,?,?,?,?)";

        try {
            Connection conn = DbManager.MakeDbConnection();
            if (conn == null) {
                System.out.println("Null Connection");
            }

            PreparedStatement pstmt = null;
            if (conn != null) {
                pstmt = conn.prepareStatement(addTrain);
            }

            Objects.requireNonNull(pstmt).setString(1, trainName);
            pstmt.setString(2, fromStation);
            pstmt.setString(3, toStation);
            pstmt.setString(4, travelDate);
            pstmt.setInt(5, ticketCount);
            pstmt.setString(6, paymentMethod);
            pstmt.setDouble(7, amount);
            pstmt.setString(8, ticket_number);
            pstmt.setString(9, email);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int TICKET_NUMBER_LENGTH = 10;
    private static SecureRandom random = new SecureRandom();

    public static String generateTicketNumber() {
        StringBuilder ticketNumber = new StringBuilder(TICKET_NUMBER_LENGTH);

        for (int i = 0; i < TICKET_NUMBER_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            ticketNumber.append(CHARACTERS.charAt(index));
        }

        return ticketNumber.toString();
    }

    public static boolean verifyTicket(String ticketNumber) {

        String query = "SELECT * FROM rail_db.purchase_history WHERE ticket_number = ?";

        try {
            Connection conn = DbManager.MakeDbConnection();
            if (conn == null) {
                System.out.println("Null Connection");
            }

            PreparedStatement pstmt = null;
            if (conn != null) {
                pstmt = conn.prepareStatement(query);
            }

            pstmt.setString(1, ticketNumber);

            ResultSet rs = pstmt.executeQuery();

            // If the result set is not empty, it means the user exists with the provided email and password
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
