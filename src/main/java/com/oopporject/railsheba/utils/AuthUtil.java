package com.oopporject.railsheba.utils;

import com.oopporject.railsheba.sql.DbManager;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class AuthUtil {
    public static Boolean validateInfo(String name, String email, String number, String nid, LocalDate date) {
        boolean validationPassed = true; // Flag to track validation status

        if (name == null || name.trim().isEmpty()) {
            showAlert("Name Error", "Name field can't be empty.");
            validationPassed = false;
        }

        if (validationPassed && (number == null || number.trim().isEmpty() || number.isBlank())) {
            // phone number can be null
        } else if (validationPassed) {
            if (!isValidNumber(number)) {
                showAlert("Invalid Phone Number", "Phone number must be of 11 digits.\n" +
                        "Example: 12345678901");
                validationPassed = false;
            } else if (!isPhoneNumberUnique(number)) {
                showAlert("Phone Number Error", "Phone Number Already in Use.");
                validationPassed = false;
            }
        }

        if (validationPassed && (nid == null || nid.trim().isEmpty() || nid.isBlank())) {
            // nid can be null
        } else if (validationPassed) {
            if (!isNidNumberUnique(nid)) {
                showAlert("NID Error", "NID number Already in Use.");
                validationPassed = false;
            }
        }

        if (validationPassed && (date == null)) {
            showAlert("Birth Date Error", "Please Select Your BirthDate.");
            validationPassed = false;
        }

        if (validationPassed && (email == null || email.trim().isEmpty())) {
            showAlert("Email Error", "Email field can't be empty.");
            validationPassed = false;
        } else if (validationPassed) {
            if (!isValidEmail(email)) {
                showAlert("Invalid Email Format", "Please Enter a valid email.\n" +
                        "Example: JhoneDoe@gmail.com");
                validationPassed = false;
            } else if (isEmailUnique(email)) {
                showAlert("Email Error", "Email Already in Use.");
                validationPassed = false;
            }
        }

        return validationPassed;
    }


    public static boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    public static boolean isEmailUnique(String email) {
        String query = "SELECT COUNT(*) FROM rail_db.user_profile WHERE email = ?";
        try {
            Connection conn = DbManager.MakeDbConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isNidNumberUnique(String nid) {
        String query = "SELECT COUNT(*) FROM rail_db.user_profile WHERE nid_number = ?";
        try {
            Connection conn = DbManager.MakeDbConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, nid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isPhoneNumberUnique(String number) {
        String query = "SELECT COUNT(*) FROM rail_db.user_profile WHERE phone_number = ?";
        try {
            Connection conn = DbManager.MakeDbConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, number);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean validatePassword(String pass, String confirm_pass) {
        boolean validation = true;
        if (pass == null || pass.trim().isEmpty() || confirm_pass == null || confirm_pass.trim().isEmpty()) {
            showAlert("Password Error", "Please Enter your password.");
            validation = false;
        }
        if (validation && !Objects.equals(pass, confirm_pass)) {
            showAlert("Passowrd Mismatch", "Passwords don't match with each other..");
            validation = false;
        }
        return validation && isValidPassword(pass) && isValidPassword(confirm_pass);
    }

    public static boolean isValidPassword(String str)
    {
        String Regex_combination_of_letters_and_numbers = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$";
        String Regex_just_letters = "^(?=.*[a-zA-Z])[a-zA-Z]+$";
        String Regex_just_numbers = "^(?=.*[0-9])[0-9]+$";
        String Regex_just_specialcharachters = "^(?=.*[@#$%^&+=])[@#$%^&+=]+$";
        String Regex_combination_of_letters_and_specialcharachters = "^(?=.*[a-zA-Z])(?=.*[@#$%^&+=])[a-zA-Z@#$%^&+=]+$";
        String Regex_combination_of_numbers_and_specialcharachters = "^(?=.*[0-9])(?=.*[@#$%^&+=])[0-9@#$%^&+=]+$";
        String Regex_combination_of_letters_and_numbers_and_specialcharachters = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=])[a-zA-Z0-9@#$%^&+=]+$";

        if(str.length()<8) {
            showAlert("Password is Not strong Enough, Try Again","1. Password Length must be greater than or equal 8\n" +
                    "2. Password Should include atleast one letter or special character(&,#,! etc)");return false;}
        if(str.matches(Regex_combination_of_letters_and_numbers))
            return true;
        if(str.matches(Regex_combination_of_letters_and_specialcharachters))
            return true;
        if(str.matches(Regex_combination_of_numbers_and_specialcharachters))
            return true;
        if(str.matches(Regex_combination_of_letters_and_numbers_and_specialcharachters))
            return true;
        showAlert("Password is Not strong Enough, Try Again","1. Password Length must be greater than or equal 8\n" +
                "2. Password Should include atleast one letter or special character(&,#,! etc)");
        return false;
    }

    private static boolean isValidNumber(String number) {
        return number.matches("\\d{11}");
    }

    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
