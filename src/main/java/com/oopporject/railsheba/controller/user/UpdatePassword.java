package com.oopporject.railsheba.controller.user;

import com.oopporject.railsheba.sql.AuthTask;
import com.oopporject.railsheba.utils.AuthUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdatePassword implements Initializable {

    @FXML
    private Button btnUpdatePass;

    @FXML
    private TextField confirmPassFld;

    @FXML
    private TextField newPassFld;

    @FXML
    private TextField oldPassFld;

    private String email;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        confirmPassFld.getStyleClass().add("input");
        newPassFld.getStyleClass().add("input");
        oldPassFld.getStyleClass().add("input");
        btnUpdatePass.getStyleClass().add("button");

        btnUpdatePass.setOnAction(event -> {
            String oldPass = oldPassFld.getText();
            String newPass = newPassFld.getText();
            String confirmPass = confirmPassFld.getText();

            if (oldPass == null || oldPass.trim().isEmpty() || newPass == null || newPass.trim().isEmpty() || confirmPass == null || confirmPass.trim().isEmpty()) {
                AuthUtil.showAlert("Password Error", "Password Field Can't be empty.");
            }

            boolean loggedIn = AuthTask.verifyLogin(email, oldPass);
            if (loggedIn) {
                boolean validatePass = AuthUtil.validatePassword(newPass, confirmPass);
                if (validatePass) {
                    boolean success = AuthTask.UpdateIntoDatabase(email, newPass);
                    if (success) {
                        if (showSuccess()) {
                            oldPassFld.clear();
                            newPassFld.clear();
                            confirmPassFld.clear();
                        }
                    }
                }
            }

        });
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean showSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update Password");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully updated the password.");
        alert.showAndWait();
        return true;
    }

}
