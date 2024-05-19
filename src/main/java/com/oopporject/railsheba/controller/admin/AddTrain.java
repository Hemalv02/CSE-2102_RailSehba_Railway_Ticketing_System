package com.oopporject.railsheba.controller.admin;

import com.oopporject.railsheba.sql.TrainTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTrain implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private TextField from;

    @FXML
    private TextField name;

    @FXML
    private TextField seatAC;

    @FXML
    private TextField seatNonAC;

    @FXML
    private TextField to;

    @FXML
    private TextField trainNumber;

    @FXML
    private TextField  ticketPriceNonAc;

    @FXML
    private TextField ticketPriceAc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAdd.getStyleClass().add("button");
        from.getStyleClass().add("input");
        name.getStyleClass().add("input");
        seatAC.getStyleClass().add("input");
        seatNonAC.getStyleClass().add("input");
        to.getStyleClass().add("input");
        trainNumber.getStyleClass().add("input");
        ticketPriceNonAc.getStyleClass().add("input");
        ticketPriceAc.getStyleClass().add("input");

        btnAdd.setOnAction(event -> {
            String trainName = name.getText();
            String fromStn = from.getText();
            String toStn = to.getText();
            String acSeat = seatAC.getText();
            String nonAcSeat = seatNonAC.getText();
            String trainNum = trainNumber.getText();
            String ticketAc = ticketPriceAc.getText();
            String ticketNonAc = ticketPriceNonAc.getText();

            if (validateFields(trainName, fromStn, toStn, acSeat, nonAcSeat, trainNum, ticketNonAc, ticketAc)) {
                showAlert("Validation Error", "All fields must be filled out.", Alert.AlertType.ERROR);
            } else {
                TrainTask trainTask = new TrainTask();
                boolean trainStatus = trainTask.insertTrain(trainName, fromStn, toStn, acSeat, nonAcSeat, trainNum, ticketAc, ticketNonAc);
                if (trainStatus) {
                    showAlert("Add Train", "Train Added Successfully.", Alert.AlertType.INFORMATION);
                    name.clear();
                    from.clear();
                    to.clear();
                    seatAC.clear();
                    seatNonAC.clear();
                    trainNumber.clear();
                    ticketPriceAc.clear();
                    ticketPriceNonAc.clear();
                }
            }
        });
    }

    private boolean validateFields(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                return true; // Validation failed
            }
        }
        return false; // Validation passed
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}
