package com.oopporject.railsheba.controller.user;

import com.oopporject.railsheba.model.User;
import com.oopporject.railsheba.sql.DbManager;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;


public class Profile implements Initializable {

    @FXML
    private Label dateLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nidLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Button btnEditProfile;

    private String email;
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnEditProfile.getStyleClass().add("button");

        nameLabel.setText(user.getName());
        emailLabel.setText(user.getEmail());
        phoneLabel.setText(user.getNumber());
        nidLabel.setText(user.getNid());
        dateLabel.setText(user.getBirthDate().toString());
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
