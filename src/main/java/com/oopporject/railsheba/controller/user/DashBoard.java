package com.oopporject.railsheba.controller.user;

import com.jfoenix.controls.JFXButton;
import com.oopporject.railsheba.model.User;
import com.oopporject.railsheba.sql.UserTask;
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
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashBoard implements Initializable {

    @FXML
    private JFXButton btnHistory;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnProfile;

    @FXML
    private JFXButton btnPurchaseTicket;

    @FXML
    private JFXButton btnTrainInfo;

    @FXML
    private JFXButton btnUpdatePassWord;

    @FXML
    private JFXButton btnVerifyTicket;

    @FXML
    private StackPane stackPaneArea;

    private String email;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnHistory.getStyleClass().add("jfx-button");
        btnLogout.getStyleClass().add("jfx-button");
        btnProfile.getStyleClass().add("jfx-button");
        btnPurchaseTicket.getStyleClass().add("jfx-button");
        btnTrainInfo.getStyleClass().add("jfx-button");
        btnUpdatePassWord.getStyleClass().add("jfx-button");
        btnVerifyTicket.getStyleClass().add("jfx-button");




        FXMLLoader fxmlLoaderr = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/Fxml/users/SearchTrain.fxml")));
        Parent fxmll = null;
        try {
            fxmll = fxmlLoaderr.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SearchTrain searchTrain = fxmlLoaderr.getController();
        searchTrain.setEmail(email);

        stackPaneArea.getChildren().removeAll();
        stackPaneArea.getChildren().setAll(fxmll);


        btnUpdatePassWord.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/Fxml/users/UpdatePassword.fxml")));
                Parent fxml = fxmlLoader.load();

                UpdatePassword updatePassword = fxmlLoader.getController();
                updatePassword.setEmail(email);
                stackPaneArea.getChildren().removeAll();
                stackPaneArea.getChildren().setAll(fxml);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btnVerifyTicket.setOnAction(event -> {
            try {
                Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/users/VerifyTicket.fxml")));
                stackPaneArea.getChildren().removeAll();
                stackPaneArea.getChildren().setAll(fxml);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btnProfile.setOnAction(event -> {
            UserTask userTask = new UserTask();
            User user = userTask.getUserFromDatabase(email);
            if (user == null) {
                System.out.println("Null User Found................");
            } else {
                System.out.println(user.toString());
            }
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/Fxml/users/Profile.fxml")));
                Parent fxml = fxmlLoader.load();

//                UserTask userTask = new UserTask();
//                User user = userTask.getUserFromDatabase(email);

                Profile profile = fxmlLoader.getController();
                profile.setEmail(email);
                profile.setUser(user);

                stackPaneArea.getChildren().removeAll();
                stackPaneArea.getChildren().setAll(fxml);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        btnPurchaseTicket.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/Fxml/users/SearchTrain.fxml")));
            Parent fxml = null;
            try {
                fxml = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            SearchTrain searchTrains = fxmlLoader.getController();
            searchTrains.setEmail(email);
            System.out.println(email);

            stackPaneArea.getChildren().removeAll();
            stackPaneArea.getChildren().setAll(fxml);
        });

        btnTrainInfo.setOnAction(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/Fxml/users/TrainList.fxml")));
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
            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/Fxml/users/PurchaseHistory.fxml")));
            Parent fxml = null;
            try {
                fxml = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            PurchaseHistory purchaseHistory = fxmlLoader.getController();
            purchaseHistory.setEmail(email);

            stackPaneArea.getChildren().removeAll();
            stackPaneArea.getChildren().setAll(fxml);
        });
    }



    public void setEmail(String email) {
        this.email = email;
    }
}
