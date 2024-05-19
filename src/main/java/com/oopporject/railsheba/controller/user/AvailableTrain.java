package com.oopporject.railsheba.controller.user;

import com.jfoenix.controls.JFXButton;
import com.oopporject.railsheba.sql.DbManager;
import com.oopporject.railsheba.sql.TrainTask;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AvailableTrain implements Initializable {

    @FXML
    private JFXButton btnBack;

    @FXML
    private Button btnNext;

    @FXML
    private TextField ticketCount;

    @FXML
    private TextField trainNo;

    private String fromStn;
    private String toStn;
    private String seatType;
    private String email;
    private String date;

    @FXML
    private TableView<ObservableList<String>> trainTable;

    private ObservableList<ObservableList<String>> data;
    private final String searchSQL = "SELECT * FROM rail_db.trains WHERE fromStn = ? AND toStn = ?";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization logic here if needed
        btnBack.getStyleClass().add("button");
        btnNext.getStyleClass().add("button");
        ticketCount.getStyleClass().add("input");
        trainNo.getStyleClass().add("input");

        btnNext.setOnAction(event -> {
            boolean validation = true;
            String ticket = ticketCount.getText();
            String train = trainNo.getText();
            if (validateFields(ticket, train)) {
                showAlert("Validation Error", "All fields must be filled out.", Alert.AlertType.ERROR);
            } else {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/users/Payment.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                TrainTask trainTask = new TrainTask();
                ResultSet rs = trainTask.trainInfo(train);
                try {
                    if (rs.next()) {
                        String trainName = rs.getString(2);
                        String fromStn = rs.getString(3);
                        String toStn = rs.getString(4);
                        int seat = 0;
                        int price = 0;
                        if (seatType == "AC") {
                            seat = Integer.parseInt(rs.getString(5));
                            price = Integer.parseInt(rs.getString(8));
                        } else {
                            seat = Integer.parseInt(rs.getString(6));
                            price = Integer.parseInt(rs.getString(9));
                        }

                        int remainingTicket = 0;
                        if (Integer.parseInt(ticket) > seat) {
                            showAlert("Ticket Available Error", "Ticket amount exceeded available ticket amount.", Alert.AlertType.ERROR);
                            validation = false;
                        } else {
                            remainingTicket = seat - Integer.parseInt(ticket);
                        }

                        if (validation){
                            int amount = price * Integer.parseInt(ticket);
                            Payment payment = loader.getController();
                            System.out.println(email);
                            payment.setDetails(trainName, ticket, String.valueOf(amount), fromStn, toStn, date, email, remainingTicket, String.valueOf(trainNo), seatType);
                        }

                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                if (validation) {
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root,800,500);
                    scene.getStylesheets().add(getClass().getResource("/Styles/component.css").toExternalForm());
                    stage.setScene(scene);
                    stage.show();
                }
            }
        });

    }

    public void getData(String fromStn, String toStn, String seatType, String email, String date) {
        this.fromStn = fromStn;
        this.toStn = toStn;
        this.seatType = seatType;
        this.date = date;
        this.email = email;

        fetColumnList();
        fetRowList();
    }

    private void fetColumnList() {
        try (Connection conn = DbManager.MakeDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(searchSQL)) {

            pstmt.setString(1, fromStn);
            pstmt.setString(2, toStn);
            ResultSet rs = pstmt.executeQuery();

            trainTable.getColumns().clear();

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<String>, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList<String>, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j));
                    }
                });

                trainTable.getColumns().add(col);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetRowList() {
        data = FXCollections.observableArrayList();

        try (Connection conn = DbManager.MakeDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(searchSQL)) {

            pstmt.setString(1, fromStn);
            pstmt.setString(2, toStn);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

            trainTable.setItems(data);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
