package com.oopporject.railsheba.controller.auth;

import com.oopporject.railsheba.model.User;
import com.oopporject.railsheba.sql.AuthTask;
import com.oopporject.railsheba.utils.AuthUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OTPForgot implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnOTPVerify;

    @FXML
    private TextField otpFld;

    private String email;
    private int otp;

    @FXML
    public void onOTPVerify(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/auth/NewPassword.fxml"));
//        Parent root = loader.load();
//
//        //Registration registrationController = loader.getController();
//
//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root,800,500);
//
//        stage.setScene(scene);
//        stage.show();
    }

    @FXML
    public void onBack(ActionEvent event) throws IOException {
        btnBack.setDefaultButton(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/auth/forgot-password.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,800,500);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnOTPVerify.setOnAction(event -> {
            String otpIn = otpFld.getText();
            if (otpIn == null || otpIn.trim().isEmpty()) {
                AuthUtil.showAlert("OTP Verification Failed", "Please Enter Your OTP Code.");
            } else if (Integer.parseInt(otpIn) == otp) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/auth/NewPassword.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                ChangePassword changePassword = loader.getController();
                changePassword.setEmail(email);

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root,800,500);

                stage.setScene(scene);
                stage.show();
            } else {
                AuthUtil.showAlert("OTP Verification Failed", "You have entered wrong OTP.");
            }
        });
    }

    public void setOTP(int otp) {
        this.otp = otp;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
