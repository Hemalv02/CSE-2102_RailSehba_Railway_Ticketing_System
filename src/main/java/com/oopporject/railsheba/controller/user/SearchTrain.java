package com.oopporject.railsheba.controller.user;

import com.oopporject.railsheba.controller.admin.DashBoardAdmin;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SearchTrain implements Initializable {

    @FXML
    private Button btnSearchTrain;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField fromFld;

    @FXML
    private ChoiceBox<String> seatCheck;

    @FXML
    private TextField toFld;

    private String seatType = "NON-AC";
    private String email;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSearchTrain.getStyleClass().add("button");
        datePicker.getStyleClass().add("input");
        fromFld.getStyleClass().add("input");
        seatCheck.getStyleClass().add("input");
        toFld.getStyleClass().add("input");

        seatCheck.setItems(FXCollections.observableArrayList("NON-AC","AC"));
        seatCheck.setValue("NON-AC");

        seatCheck.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            seatType = seatCheck.getItems().get(newValue.intValue());
        });

        btnSearchTrain.setOnAction(event -> {
            if (validateFields(fromFld.getText(), toFld.getText(), seatType)) {
                showAlert("Validation Error", "All fields must be filled out.", Alert.AlertType.ERROR);
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/users/AvailableTrain.fxml"));

                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                String from = fromFld.getText();
                String to = toFld.getText();
                LocalDate date = datePicker.getValue();

                AvailableTrain availableTrain = loader.getController();
                availableTrain.getData(from, to, seatType, email, date.toString());
                System.out.println(email);

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root,800,500);
                scene.getStylesheets().add(getClass().getResource("/Styles/component.css").toExternalForm());
                stage.setScene(scene);
                stage.show();
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

    public void setEmail(String email) {
        this.email = email;
    }
}
