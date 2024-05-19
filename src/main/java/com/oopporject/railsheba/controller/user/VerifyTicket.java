package com.oopporject.railsheba.controller.user;

import com.oopporject.railsheba.sql.TicketTask;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class VerifyTicket implements Initializable {

    @FXML
    private Button btnVerify;

    @FXML
    private StackPane stackPaneArea;

    @FXML
    private TextField ticketFld;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnVerify.getStyleClass().add("button");
        ticketFld.getStyleClass().add("input");

        btnVerify.setOnAction(event -> {
            String ticketNum = ticketFld.getText();
            if (TicketTask.verifyTicket(ticketNum)) {
                showAlert("Ticket Verification", "This is a valid ticket purchase", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Ticket Verification Error", "Invalid Ticket Purchase", Alert.AlertType.ERROR);
            }
        });
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
