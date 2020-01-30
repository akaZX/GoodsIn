package app.controller;

import app.model.Supplier;
import app.pojos.Suppliers;
import app.view.SupplierDetailsPane;
import app.view.table_columns.SuppliersTableColumns;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

class SuppliersView implements Initializable {

    @FXML
    private StackPane mainPane;
    @FXML
    private StackPane tablePane;
    @FXML
    GridPane gridPane;

    @FXML
    JFXButton refreshBtn;
    @FXML
    JFXButton addEmailBtn;
    @FXML
    JFXButton deleteEmailBtn;
    @FXML
    JFXButton addPhoneNumberBtn;
    @FXML
    JFXButton deletePhoneNumberBtn;
    @FXML
    JFXButton saveBtn;


    JFXTreeTableView<Suppliers> table = new JFXTreeTableView<>();

    SuppliersView() {

        addSuppliersToTable();
        construct();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tablePane.getChildren().add(table);

    }


    private void tableListeners(){
        table.setRowFactory(tr -> {
            JFXTreeTableRow<Suppliers> row = new JFXTreeTableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                  Suppliers supplier = table.getSelectionModel().getSelectedItem().getValue();
                    System.out.println(supplier.getRowID());
                }
            });
            return row;
        });

    }



    private void addSuppliersToTable() {
        table.getColumns().addAll(SuppliersTableColumns.getNameCol(table));

        SQLiteJDBC.getSuppliers().forEach((sup)->{
            System.out.println(sup.getSupplierName());
        });


        final TreeItem<Suppliers> root = new RecursiveTreeItem<>(SQLiteJDBC.getSuppliers(), RecursiveTreeObject::getChildren);
        table.setRoot(root);
        table.setShowRoot(false);
        table.setEditable(true);


    }

    private void construct() {

        tableListeners();

    }


}
