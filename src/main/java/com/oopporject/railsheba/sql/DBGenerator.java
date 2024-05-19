package com.oopporject.railsheba.sql;

import java.sql.*;

public class DBGenerator {
    public static void genDB() {
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "rail_db";
        String driver = "com.mysql.cj.jdbc.Driver";
        String userName = "root";
        String password = "Hemal4362";

            try {
                Class.forName(driver);
                Connection conn = DriverManager.getConnection(url, userName, password);

                Statement stmt = conn.createStatement();

                // Check if database exists
                ResultSet resultSet = conn.getMetaData().getCatalogs();
                boolean databaseExists = false;
                while (resultSet.next()) {
                    String databaseName = resultSet.getString(1);
                    if (databaseName.equals(dbName)) {
                        databaseExists = true;
                        break;
                    }
                }
                resultSet.close();


                // Create database if it doesn't exist
                if (!databaseExists) {
                    stmt.executeUpdate("create database " + dbName);
                    conn = DriverManager.getConnection(url+dbName, userName, password);
                }
                else {
                    return;
                }

                // Use database
                stmt.executeUpdate("use "+ dbName);

                // user profile
                String createProfileTableQuery = "CREATE TABLE IF NOT EXISTS user_profile ("
                        + "uid INT AUTO_INCREMENT PRIMARY KEY,"
                        + "name VARCHAR(255) NOT NULL,"
                        + "email VARCHAR(255) UNIQUE NOT NULL,"
                        + "phone_number VARCHAR(15),"
                        + "nid_number VARCHAR(20),"
                        + "date_of_birth DATE NOT NULL"
                        + ")";
                stmt.executeUpdate(createProfileTableQuery);

                // user login
                String createUserTableQuery = "CREATE TABLE IF NOT EXISTS user ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY,"
                        + "email VARCHAR(255) UNIQUE NOT NULL,"
                        + "password_hash VARCHAR(255) NOT NULL,"
                        + "role ENUM('user', 'admin') DEFAULT 'user' NOT NULL"
                        + ")";
                stmt.executeUpdate(createUserTableQuery);

                String createTrainTable = "CREATE TABLE IF NOT EXISTS trains ("
                        + "id INTEGER PRIMARY KEY AUTO_INCREMENT, "
                        + "trainName TEXT NOT NULL, "
                        + "fromStn TEXT NOT NULL, "
                        + "toStn TEXT NOT NULL, "
                        + "acSeat TEXT NOT NULL, "
                        + "nonAcSeat TEXT NOT NULL, "
                        + "trainNum TEXT NOT NULL, "
                        + "acPrice INT NOT NULL, "
                        + "nonAcPrice INT NOT NULL"
                        + ");";


                stmt.executeUpdate(createTrainTable);


                String purchase_history = "CREATE TABLE IF NOT EXISTS purchase_history (" +
                        "id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                        "train_name TEXT NOT NULL," +
                        "from_station TEXT NOT NULL," +
                        "to_station TEXT NOT NULL," +
                        "travel_date TEXT NOT NULL," +
                        "ticket_count INTEGER NOT NULL," +
                        "payment_method TEXT NOT NULL," +
                        "amount REAL NOT NULL," +
                        "ticket_number TEXT NOT NULL" +
                        "email TEXT NOT NULL" +
                        ");";

                stmt.executeUpdate(purchase_history);

                conn.close();
            } catch (ClassNotFoundException e) {
                System.err.println("Could not load JDBC driver: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("SQL Exception: " + e.getMessage());
            }
    }

    public static void main(String[] args) {
        genDB();
    }
}