package com.oopporject.railsheba.controller.auth;

import com.oopporject.railsheba.email.OTPMail;
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
import java.util.Random;
import java.util.ResourceBundle;

import static com.oopporject.railsheba.utils.AuthUtil.*;

public class ForgotPassword implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnEmailVerify;

    @FXML
    private TextField emailFld;

    @FXML
    private Label name11;

    @FXML
    public void onEmailVerify(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/auth/otp-forgot.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/auth/login.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,800,500);

        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnEmailVerify.setOnAction(event -> {
            String email = emailFld.getText();
            boolean validationPassed = true;
            if (email == null || email.trim().isEmpty()) {
                showAlert("Email Error", "Email field can't be empty.");
                validationPassed = false;
            } else {
                if (!isValidEmail(email)) {
                    showAlert("Invalid Email Format", "Please Enter a valid email.\n" +
                            "Example: JhoneDoe@gmail.com");
                    validationPassed = false;
                } else if (!isEmailUnique(email)) {
                    showAlert("Email Error", "No user found associated with this email.");
                    validationPassed = false;
                }
            }

            if (validationPassed) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/auth/otp-forgot.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Random random = new Random();
                int randomNumber = random.nextInt(900000) + 100000;
                OTPMail.sendOTPForgot(email, randomNumber);
                System.out.println(randomNumber);

                OTPForgot otpForgot = loader.getController();
                otpForgot.setOTP(randomNumber);
                otpForgot.setEmail(email);

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root,800,500);

                stage.setScene(scene);
                stage.show();
            }
        });
    }
}
