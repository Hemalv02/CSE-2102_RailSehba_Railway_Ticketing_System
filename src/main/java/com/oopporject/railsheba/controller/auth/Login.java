package com.oopporject.railsheba.controller.auth;

import com.oopporject.railsheba.controller.user.DashBoard;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private Button btnCreateAccount;

    @FXML
    private Button btnSignIn;

    @FXML
    private TextField emailFld;

    @FXML
    private Label labelForgot;

    @FXML
    private PasswordField passFld;

    @FXML
    private ImageView imageView;

    @FXML
    void onCreateAccount(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/auth/registration.fxml"));
        Parent root = loader.load();

        //Registration registrationController = loader.getController();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,800,500);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onForgotPassword(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/auth/forgot-password.fxml"));
        Parent root = loader.load();

        //Registration registrationController = loader.getController();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,800,500);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onSignIn(ActionEvent event) {

    }
    String email;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageView.setImage(new Image(String.valueOf(getClass().getResource("/Images/railway.png"))));
        btnSignIn.setOnAction(event -> {
            email = emailFld.getText();
            String password = passFld.getText();
            boolean validateEmail = AuthUtil.isValidEmail(email);
            if (!validateEmail) {
                AuthUtil.showAlert("Invalid Email Format", "Please Enter Valid Email Format.");
            }
            boolean validatePass = AuthUtil.validatePassword(password, password);
            boolean loggedIn = false;
            if (validatePass && validateEmail) {
                loggedIn = AuthTask.verifyLogin(email, password);
            }

            String isAdmin;
            try {
                isAdmin = AuthTask.getUserRole(email, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (loggedIn) {
                if (isAdmin.equalsIgnoreCase("admin")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/admin/DashBoardAdmin.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root,900,500);
                    scene.getStylesheets().add(getClass().getResource("/Styles/component.css").toExternalForm());
                    stage.setScene(scene);
                    stage.show();
                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/users/DashBoard.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    DashBoard dashBoard = loader.getController();
                    dashBoard.setEmail(email);

                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root,800,500);
                    scene.getStylesheets().add(getClass().getResource("/Styles/component.css").toExternalForm());
                    stage.setScene(scene);
                    stage.show();
                }

            }
        });
    }
}
