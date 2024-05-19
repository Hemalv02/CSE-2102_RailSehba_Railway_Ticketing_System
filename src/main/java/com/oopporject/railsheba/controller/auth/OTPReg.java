package com.oopporject.railsheba.controller.auth;

import com.oopporject.railsheba.model.User;
import com.oopporject.railsheba.sql.AuthTask;
import com.oopporject.railsheba.sql.UserTask;
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

public class OTPReg implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnOTP;

    @FXML
    private TextField otpFld;

    private String password;
    private User user;
    private int otp;

    @FXML
    public void onOTPVerify(ActionEvent event) throws IOException  {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/auth/login.fxml"));
//        Parent root = loader.load();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/auth/registration.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,800,500);

        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnOTP.setOnAction(event -> {
            String otpIn = otpFld.getText();
            if (otpIn == null || otpIn.trim().isEmpty()) {
                AuthUtil.showAlert("OTP Verification Failed", "Please Enter Your OTP Code.");
            } else if (Integer.parseInt(otpIn) == otp) {

                UserTask userTask = new UserTask();
                userTask.InsertIntoDatabase(user);

                AuthTask authTask = new AuthTask();
                authTask.InsertIntoDatabase(user.getEmail(), password);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/auth/login.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root,800,500);

                stage.setScene(scene);
                stage.show();
            } else {
                AuthUtil.showAlert("OTP Verification Failed", "You have entered wrong OTP.");
            }
        });
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOTP(int otp) {
        this.otp = otp;
    }
}
