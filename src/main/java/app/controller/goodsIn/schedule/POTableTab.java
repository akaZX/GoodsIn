package app.controller.goodsIn.schedule;


import app.controller.sql.SQLiteProteanClone;
import app.controller.sql.dao.*;
import app.controller.sql.serviceClasses.ScheduleEntryService;
import app.controller.utils.Messages;
import app.controller.utils.ValidateInput;
import app.model.ScheduleEntry;
import app.view.table_columns.PoTableColumns;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import java.time.LocalDate;


public class POTableTab {


    public POTableTab() {

    }


    private Messages msg = new Messages();

    private final Tab tab = new Tab("Orders List");

    private final JFXTreeTableView<ScheduleEntry> table = new JFXTreeTableView<>();

    private final StackPane pane = new StackPane();

    private final BorderPane bPane = new BorderPane();

    private final JFXButton importEntries = new JFXButton("Import Orders");

    private final JFXButton edit = new JFXButton("Edit Entry");

    private final JFXButton listOrders = new JFXButton("List");

    private final JFXButton deleteEntry = new JFXButton("Delete");

    private final JFXButton addEntry = new JFXButton("Add new");

    private final JFXButton build = new JFXButton("Build schedule");

    private final JFXButton duplicateOrder = new JFXButton("Duplicate");

    private final JFXDatePicker dateField = new JFXDatePicker();

    private final Label selectedDateLabel = new Label();

    private final ToolBar toolBar = new ToolBar();

    private Node[] nodes = {
            selectedDateLabel, dateField, listOrders, importEntries,
            edit, addEntry, deleteEntry, duplicateOrder
    };

    public Tab createTable() {

        final Pane leftSpacer = new Pane();
        HBox.setHgrow(
                leftSpacer,
                Priority.SOMETIMES
        );

        toolbarMargins();
        selectedDateLabel.getStyleClass().add("sched-form-label");

        toolBar.getItems().addAll(
                selectedDateLabel,
                leftSpacer,
                dateField,
                listOrders,
                importEntries,
                edit,
                addEntry,
                deleteEntry,
                duplicateOrder,
                build
        );

        toolBar.setPadding(new Insets(10, 35, 10, 35));


        ValidateInput.requiredFieldValidation(dateField,"Missing date!");

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


    private void toolbarMargins() {

        for (Node node : nodes
        ) {
            HBox.setMargin(node, new Insets(0,5,0,5));
        }
    }

    private void addColumnsToTable() {

        PoTableColumns tableColumns = new PoTableColumns(table);

        table.columnResizePolicyProperty();
        boolean b = table.getColumns().addAll(tableColumns.supplierCol(), tableColumns.poCol(),
                tableColumns.haulierCol(), tableColumns.expectedETACol(), tableColumns.bayCol(),
                tableColumns.unloadingTimeCol(), tableColumns.palletsCol(),tableColumns.commentsCol() ,
                tableColumns.arrivedCol(), tableColumns.departedCol(),
                tableColumns.bookedInCol(), tableColumns.registrationCol());

        table.setShowRoot(false);
        table.setEditable(false);
    }

    private void initialListLoad(){
        //TODO change back to today's value
        dateField.setValue(LocalDate.of(2020,01,24));
        listAllRecords();
    }

    //    adds action listeners for buttons and table rows
    private void tableListeners() {

        //TODO prideti likusias knopkes

        listOrders.setOnAction(event ->listAllRecords());

        importEntries.setOnAction(event -> {
            Thread thread = new Thread(() -> {
                SQLiteProteanClone.getOrderDetailsFromProtean(dateField.getValue());
                Platform.runLater(()->{
                    table.setRoot(populateTreeItems());
                });
            });
               thread.start();

        });

        edit.setOnAction(event -> {
            try {
                loadRow();
            }
            catch (NullPointerException e) {
                msg.continueAlert(table, "Error", "No rows were selected.");
            }

        });

        addEntry.setOnAction(event -> {
            loadOrderForm(null, dateField);
        });

        deleteEntry.setOnAction(event -> {

            try {
                ScheduleEntry record = table.getSelectionModel().getSelectedItem().getValue();
                boolean b = new ScheduleDetailsDao().delete(table.getSelectionModel().getSelectedItem().getValue().getDetails());
                if(b){
                    msg.continueAlert(table, "Success", "Entry was deleted");
                }else{

                    msg.continueAlert(table, "Error", "Failed to delete following entry:\n" + record.getSupplier().getSupplierName() + " " + record.getOrder().getPoNumber());
                }
            }
            catch (NullPointerException ignored) {
            }
            table.setRoot(populateTreeItems());
        });

        duplicateOrder.setOnAction(event -> {
            try {
                new ScheduleDetailsDao().saveFromProtean(table.getSelectionModel().getSelectedItem().getValue().getDetails());

            }
            catch (NullPointerException e) {
                msg.continueAlert(table, "Error", "No rows were selected.");
            }
            table.setRoot(populateTreeItems());
        });


        build.setOnAction(event -> {

            listAllRecords();
            ScheduleBuilder builder = new ScheduleBuilder(dateField.getValue());
            builder.buildSchedule(build, this);


        });


        table.setRowFactory(tr -> {
            JFXTreeTableRow<ScheduleEntry> row = new JFXTreeTableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    try {
                        ScheduleEntry order = table.getSelectionModel().getSelectedItem().getValue();
                        selectedDateLabel.setText(
                                dateField.getValue().toString() + "  " + order.getSupplier().getSupplierName());
                    }
                    catch (Exception ignored) {
                    }
                }
                if (event.getClickCount() == 2 && (! row.isEmpty())) {
                  loadRow();
                }
            });
            row.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER && !row.isEmpty()) {
                    loadRow();
                }
            });
            return row;
        });
    }

    public void listAllRecords(){

        try {
            table.setRoot(populateTreeItems());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        selectedDateLabel.setText(String.valueOf(dateField.getValue()));
    }

    private void loadRow(){
        ScheduleEntry order = table.getSelectionModel().getSelectedItem().getValue();
        loadOrderForm(order, dateField);
    }

    //loads delivery form with order details
    private void loadOrderForm(ScheduleEntry order, Node node) {
        FormController contr;
        if (order == null) {
            contr = new FormController(node, this);
        }else{
             contr  = new FormController(order, node, this);
        }
        contr.displayForm(table);

    }


    public TreeItem<ScheduleEntry> populateTreeItems() {
        if(dateField.getValue() != null){
            dateField.resetValidation();
            return new RecursiveTreeItem<>(FXCollections.observableArrayList(ScheduleEntryService.getDeliveriesFromDb(dateField.getValue())), RecursiveTreeObject::getChildren);
        }else{
            msg.continueAlert(table, "Error", "Date field is left blank or date format is incorrect.\nIt should be in dd/mm/yyyy form. ");
            dateField.validate();
            return null;
        }
    }

}