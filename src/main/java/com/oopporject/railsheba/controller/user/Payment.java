package com.oopporject.railsheba.controller.user;

import com.oopporject.railsheba.sql.TicketTask;
import com.oopporject.railsheba.sql.TrainTask;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Payment implements Initializable {


    @FXML
    private Label amountLbl;

    @FXML
    private CheckBox bkash;

    @FXML
    private Button btnPurchase;

    @FXML
    private Button btnBack;

    @FXML
    private Label fromLbl;

    @FXML
    private CheckBox nagad;

    @FXML
    private Label nameLbl;

    @FXML
    private Label ticketLbl;

    @FXML
    private Label toLbl;

    private String trainName;
    private String ticketCount;
    private String amount;
    private String from;
    private String to;
    private String email;
    private String date;
    private int ticket;
    private String trainNum;
    private String seatType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnPurchase.getStyleClass().add("button");
        btnBack.getStyleClass().add("button");


        btnBack.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/users/DashBoard.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root,800,500);
            scene.getStylesheets().add(getClass().getResource("/Styles/component.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        });

        btnPurchase.setOnAction(event -> {
            if (!(bkash.isSelected() || nagad.isSelected())) {
                showAlert("Payment Method Error", "Please Select a payment method", Alert.AlertType.ERROR);
            } else {
                String paymentMethod = "";
                if (nagad.isSelected()) {
                    paymentMethod = "nagad";
                }

                if (bkash.isSelected()){
                    paymentMethod = "bkash";
                }
                TicketTask ticketTask = new TicketTask();
                String ticket_number = TicketTask.generateTicketNumber();
                boolean validation = TicketTask.verifyTicket(ticket_number);

                while (validation) {
                    ticket_number = TicketTask.generateTicketNumber();
                    validation = TicketTask.verifyTicket(ticket_number);
                }

                if (!validation) {
                    System.out.println(email);
                    ticketTask.insertTicket(trainName, from, to, date, Integer.parseInt(ticketCount), paymentMethod, Double.parseDouble(amount), ticket_number, email);
                    showAlert("Ticket Purchase", "You have successfully purchased your ticket(s).\nYour ticket number: " + ticket_number + "\nPlease check your purchase history.", Alert.AlertType.CONFIRMATION);
                    TrainTask trainTask = new TrainTask();
                    //trainTask.updateInfo(trainNum, String.valueOf(ticket), seatType);
                }
            }
        });
    }

    public void setDetails(String trainName, String ticketCount, String amount, String from, String to, String date, String email, int remaining, String trainNum, String seatType) {
        this.trainName = trainName;
        this.ticketCount = ticketCount;
        this.amount = amount;
        this.from = from;
        this.to = to;
        this.email = email;
        this.date = date;
        this.ticket = remaining;
        this.trainNum = trainNum;
        this.seatType = this.seatType;

        nameLbl.setText(trainName);
        fromLbl.setText(from);
        toLbl.setText(to);
        ticketLbl.setText(ticketCount);
        amountLbl.setText(amount);
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
