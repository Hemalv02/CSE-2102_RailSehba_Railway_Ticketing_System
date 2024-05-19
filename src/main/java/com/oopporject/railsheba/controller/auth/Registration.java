package com.oopporject.railsheba.controller.auth;

import com.oopporject.railsheba.email.OTPMail;
import com.oopporject.railsheba.model.User;
import com.oopporject.railsheba.utils.AuthUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

public class Registration implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnRegister;

    @FXML
    private PasswordField confirmPassFld;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField emailFld;

    @FXML
    private TextField nameFld;

    @FXML
    private TextField nidFld;

    @FXML
    private PasswordField passFld;

    @FXML
    private CheckBox termsCheck;

    @FXML
    private TextField numberFld;

    @FXML
    public void onRegisterButton(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/auth/otp-verify.fxml"));
//        Parent root = loader.load();
//
//        String name = nameFld.getText();
//        String number = nameFld.getText();
//        String nid = nidFld.getText();
//        LocalDate birthdate = datePicker.getValue();
//        String email = emailFld.getText();
//        String password = passFld.getText();
//        String confirmPassword = confirmPassFld.getText();
//        Boolean termsChecked = termsCheck.isSelected();
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
        btnRegister.setOnAction(event -> {
            String name = nameFld.getText();
            String number = numberFld.getText();
            String nid = nidFld.getText();
            LocalDate birthdate = datePicker.getValue();
            String email = emailFld.getText();
            String password = passFld.getText();
            String confirmPassword = confirmPassFld.getText();
            boolean termsChecked = termsCheck.isSelected();

            User user = new User(name, email, number, nid, birthdate);

            Boolean validationStatus = AuthUtil.validateInfo(name, email, number, nid, birthdate);
            boolean passwordStatus = validationStatus && AuthUtil.validatePassword(password, confirmPassword);

            if (passwordStatus && !termsChecked) {
                AuthUtil.showAlert("Terms And Conditions", "You need to select the checkbox to complete the registration.");
            }

            if (passwordStatus && termsChecked) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/auth/otp-verify.fxml"));
                Parent root;

                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                OTPReg otpReg = loader.getController();
                otpReg.setUser(user);
                otpReg.setPassword(password);

                Random random = new Random();
                int randomNumber = random.nextInt(900000) + 100000;
                otpReg.setOTP(randomNumber);
                OTPMail.sendOTPReg(email, randomNumber);
                //System.out.println(randomNumber);

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root,800,500);

                stage.setScene(scene);
                stage.show();
            }
        });
    }

}
