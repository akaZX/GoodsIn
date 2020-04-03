package app.controller.schedule;


import app.controller.sql.SQLiteJDBC;
import app.controller.sql.SQLiteProteanClone;
import app.controller.sql.dao.*;
import app.controller.sql.serviceClasses.ScheduleEntryService;
import app.model.ScheduleEntry;
import app.pojos.*;
import app.view.table_columns.PoTableColumns;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.intellij.lang.annotations.Language;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class POTableTab {


    public POTableTab() {

    }


    private final Tab tab = new Tab("Orders List");

    private final JFXTreeTableView<ScheduleEntry> table = new JFXTreeTableView<>();

    private final StackPane pane = new StackPane();

    private final BorderPane bPane = new BorderPane();

    private final JFXButton importEntries = new JFXButton("Import Orders");

    private final JFXButton listOrders = new JFXButton("List");

    private final JFXButton deleteEntry = new JFXButton("Delete");

    private final JFXButton duplicateOrder = new JFXButton("Duplicate");

    private final JFXDatePicker dateField = new JFXDatePicker();

    private final Label selectedDateLabel = new Label();

    private final ToolBar toolBar = new ToolBar();


    Tab createTable() {

        final Pane leftSpacer = new Pane();
        HBox.setHgrow(
                leftSpacer,
                Priority.SOMETIMES
        );

        toolBar.getItems().addAll(
                selectedDateLabel,
                leftSpacer,
                dateField,
                listOrders,
                importEntries,
                deleteEntry,
                duplicateOrder
        );


        toolBar.setPadding(new Insets(15, 25, 15, 25));


        bPane.setTop(toolBar);
        bPane.setCenter(table);

        dateField.setValue(LocalDate.of(2020, 1, 24));

        pane.getChildren().addAll(bPane);
        tab.setContent(pane);

        addColumnsToTable();
        tableListeners();
        initialListLoad();

        return tab;
    }


    private void addColumnsToTable() {

        PoTableColumns tableColumns = new PoTableColumns(table);

        table.columnResizePolicyProperty();
        table.getColumns().addAll(tableColumns.supplierCol(), tableColumns.poCol(),
                tableColumns.haulierCol(), tableColumns.expectedETACol(), tableColumns.bayCol(),
                tableColumns.unloadingTimeCol(), tableColumns.palletsCol(), tableColumns.registrationCol(),
                tableColumns.arrivedCol(), tableColumns.departedCol(),
                tableColumns.bookedInCol());

        table.setShowRoot(false);
        table.setEditable(false);
    }

    private void initialListLoad(){
        dateField.setValue(LocalDate.of(2020,01,24));
        listAllRecords();
    }

    //    adds action listeners for buttons and table rows
    private void tableListeners() {

        //TODO prideti likusias knopkes

        listOrders.setOnAction(event ->listAllRecords());

        importEntries.setOnAction(event -> {

            SQLiteProteanClone.getOrderDetailsFromProtean(dateField.getValue());
            table.setRoot(populateTreeItems());

        });

        deleteEntry.setOnAction(event -> {
//         TODO patvarkyti sita kad trintu ka reikia
            new ScheduleDetailsDao().delete(table.getSelectionModel().getSelectedItem().getValue().getDetails());

            table.setRoot(populateTreeItems());
        });

        duplicateOrder.setOnAction(event -> {
            //TODO padaryti kad forma loadintu tik is PO be entry details
            new ScheduleDetailsDao().saveFromProtean(table.getSelectionModel().getSelectedItem().getValue().getDetails());
            table.setRoot(populateTreeItems());
        });


        table.setRowFactory(tr -> {
            JFXTreeTableRow<ScheduleEntry> row = new JFXTreeTableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty())) {
                    ScheduleEntry order = table.getSelectionModel().getSelectedItem().getValue();
                    selectedDateLabel.setText(
                            dateField.getValue().toString() + "  " + order.getSupplier().getSupplierName());
                }
                if (event.getClickCount() == 2 && (! row.isEmpty())) {
                    ScheduleEntry order = table.getSelectionModel().getSelectedItem().getValue();
                    System.out.println(order.getOrder().getSuppCode());
                    System.out.println(order.getSupplier().getSupplierName() + " " + order.getSupplier().getSupplierCode());
                    System.out.println(order.getOrder().getPoNumber().equals(order.getDetails().getPo()));
                    loadOrderForm(order);
                }
                //TODO add right click functionality after main buttons will be sorted
                if (event.getButton() == MouseButton.SECONDARY && ! row.isEmpty()) {
                    System.out.println("Right button clicked");
                }
            });
            return row;
        });

    }

    private void listAllRecords(){

        try {
            table.setRoot(populateTreeItems());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        selectedDateLabel.setText(String.valueOf(dateField.getValue()));
    }

    //loads delivery form with order details
    private void loadOrderForm(ScheduleEntry order) {

        FXMLLoader     loader = new FXMLLoader(getClass().getResource("/deliveryForm.fxml"));
        FormController contr  = new FormController(order);
        loader.setController(contr);

        try {
            Scene scene     = new Scene(loader.load(), 950, 750);
            Stage formStage = new Stage();
            formStage.setScene(scene);
            formStage.show();
            URL url = this.getClass().getResource("/style.css");
            if (url == null) {
                System.out.println("Resource not found. Aborting.");
                System.exit(- 1);
            }
            String css = url.toExternalForm();
            formStage.getScene().getStylesheets().add(css);
            //reloads table with updated data
            formStage.setOnHiding(event -> {
                table.setRoot(populateTreeItems());
                SQLiteJDBC.close();
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }


    private TreeItem<ScheduleEntry> populateTreeItems() {

        return new RecursiveTreeItem<>(FXCollections.observableArrayList(ScheduleEntryService.getDeliveriesFromDb(dateField.getValue())), RecursiveTreeObject::getChildren);
    }




}