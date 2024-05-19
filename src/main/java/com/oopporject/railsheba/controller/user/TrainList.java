package com.oopporject.railsheba.controller.user;

import com.oopporject.railsheba.sql.DbManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class TrainList implements Initializable {
    @FXML
    TableView tblTrainList;

    private ObservableList<ObservableList> data;
    final String SQL = "SELECT * from rail_db.trains";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetColumnList();
        fetRowList();
    }

    private void fetColumnList() {

        try {
            ResultSet rs = Objects.requireNonNull(DbManager.MakeDbConnection()).createStatement().executeQuery(SQL);

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tblTrainList.getColumns().removeAll(col);
                tblTrainList.getColumns().addAll(col);

                //System.out.println("Column [" + i + "] ");

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void fetRowList() {
        data = FXCollections.observableArrayList();
        ResultSet rs;
        try {
            rs = Objects.requireNonNull(DbManager.MakeDbConnection()).createStatement().executeQuery(SQL);

            while (rs.next()) {
                //Iterate Row
                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                //System.out.println("Row [1] added " + row);
                data.add(row);

            }

            tblTrainList.setItems(data);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}