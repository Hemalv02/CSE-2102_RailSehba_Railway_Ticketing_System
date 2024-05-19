package com.oopporject.railsheba.controller.auth;

import com.oopporject.railsheba.sql.AuthTask;
import com.oopporject.railsheba.utils.AuthUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangePassword implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnConfirmPassword;

    @FXML
    private TextField newPassConfirmFld;

    @FXML
    private TextField newPassFld;

    private String email;

    @FXML
    public void onConfirmPassword(ActionEvent event) {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/auth/login.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/auth/otp-forgot.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,800,500);

        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnConfirmPassword.setOnAction(event -> {
            String newPassword = newPassFld.getText();
            String confirmPassword = newPassConfirmFld.getText();

            boolean validation = AuthUtil.validatePassword(newPassword, confirmPassword);
            System.out.println(validation);
            boolean updated = false;
            if (validation) {
                boolean status = AuthTask.UpdateIntoDatabase(email, confirmPassword);
                if (status) {
                    showSuccess();
                    updated = true;
                }
            }

            if (validation && updated) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/auth/login.fxml"));
                Parent root;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //Registration registrationController = loader.getController();

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root,800,500);

                stage.setScene(scene);
                stage.show();
            }
        });
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean showSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Forgot Password");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully changed the password.");
        alert.showAndWait();
        return true;
    }
}
