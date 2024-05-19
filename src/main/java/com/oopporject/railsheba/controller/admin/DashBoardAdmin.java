package com.oopporject.railsheba.controller.admin;

import com.jfoenix.controls.JFXButton;
import com.oopporject.railsheba.controller.user.DashBoard;
import com.oopporject.railsheba.controller.user.UpdatePassword;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashBoardAdmin implements Initializable {

    @FXML
    private JFXButton btnHistory;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnTrains;

    @FXML
    private JFXButton btnTrainsList;

    @FXML
    private JFXButton btnUsers;

    @FXML
    private StackPane stackPaneArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnHistory.getStyleClass().add("jfx-button");
        btnTrains.getStyleClass().add("jfx-button");
        btnUsers.getStyleClass().add("jfx-button");

        btnUsers.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/Fxml/admin/UserList.fxml")));
            Parent fxml = null;
            try {
                fxml = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            stackPaneArea.getChildren().removeAll();
            stackPaneArea.getChildren().setAll(fxml);
        });

        btnTrainsList.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/Fxml/admin/TrainList.fxml")));
            Parent fxml = null;
            try {
                fxml = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            stackPaneArea.getChildren().removeAll();
            stackPaneArea.getChildren().setAll(fxml);
        });

        btnTrains.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/Fxml/admin/AddTrain.fxml")));
            Parent fxml = null;
            try {
                fxml = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            stackPaneArea.getChildren().removeAll();
            stackPaneArea.getChildren().setAll(fxml);
        });

        btnLogout.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/auth/login.fxml"));
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

        btnHistory.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/Fxml/admin/PurchaseHistory.fxml")));
            Parent fxml = null;
            try {
                fxml = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            stackPaneArea.getChildren().removeAll();
            stackPaneArea.getChildren().setAll(fxml);
        });

        FXMLLoader fxmlLoaderr = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/Fxml/admin/AddTrain.fxml")));
        Parent fxmll = null;
        try {
            fxmll = fxmlLoaderr.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stackPaneArea.getChildren().removeAll();
        stackPaneArea.getChildren().setAll(fxmll);
    }


}
