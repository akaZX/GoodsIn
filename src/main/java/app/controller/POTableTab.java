package app.controller;


import app.model.PoTableColumns;
import app.model.PurchaseOrder;
import app.model.Supplier;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class POTableTab{


    public POTableTab() {

    }

    @FXML
    JFXTabPane mainTabPane;

    private  Tab tab = new Tab("Orders List");

    private JFXTreeTableView<PurchaseOrder> table = new JFXTreeTableView<>();

    private  StackPane pane = new StackPane();
    private  BorderPane bPane = new BorderPane();

    private  JFXButton importOrders = new JFXButton("Import Orders");
    private  JFXButton listOrders = new JFXButton("List");
    private  JFXButton deleteOrder = new JFXButton("Delete");
    private  JFXButton duplicateOrder = new JFXButton("Duplicate");

    private  JFXDatePicker dateField = new JFXDatePicker();

    private Label selectedDateLabel = new Label();

    private ToolBar toolBar = new ToolBar();




    public  Tab createTable(){

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
                importOrders,
                deleteOrder,
                duplicateOrder
        );


        toolBar.setPadding(new Insets(15, 25, 15, 25));


        bPane.setTop(toolBar);
        bPane.setCenter(table);

        dateField.setValue(LocalDate.now());

        pane.getChildren().addAll(bPane);
        tab.setContent(pane);

        addColumnsToTable();
        tableViewActionListeners();

        return tab;
    }



    private void addColumnsToTable(){

        PoTableColumns tableColumns = new PoTableColumns(table);



        table.columnResizePolicyProperty();
        table.getColumns().addAll(tableColumns.idCol(), tableColumns.supplierCol(), tableColumns.poCol(),
                tableColumns.haulierCol(), tableColumns.expectedETACol(), tableColumns.bayCol(),
                tableColumns.unloadingTimeCol(), tableColumns.palletsCol(), tableColumns.arrivedCol(),
                tableColumns.departedCol(), tableColumns.bookedInCol());


        table.setShowRoot(false);
        table.setEditable(false);
    }


//    adds action listeners for buttons and table rows
    private  void tableViewActionListeners(){

        //TODO prideti likusias knopkes

        listOrders.setOnAction(event -> {
            table.setRoot(populateTreeItems());
            selectedDateLabel.setText(String.valueOf(dateField.getValue()));

        });

        importOrders.setOnAction(event -> {
            getOrderFromProtean();
            table.setRoot(populateTreeItems());

        });

        table.setRowFactory( tr -> {
            TreeTableRow<PurchaseOrder> row = new TreeTableRow<>();



            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty()) ) {
                    PurchaseOrder order = table.getSelectionModel().getSelectedItem().getValue();
                    selectedDateLabel.setText(dateField.getValue().toString() + "  " + order.getSupplierName());
                }
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    PurchaseOrder order = table.getSelectionModel().getSelectedItem().getValue();
                    loadOrderForm(order);
                }
            });
            return row ;
        });
    }

    //loads delivery form with order details
    private void loadOrderForm(PurchaseOrder order) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/deliveryForm.fxml"));
        FormController contr = new FormController(order);
        loader.setController(contr);

        try {
            Scene scene = new Scene(loader.load(),950,750);
            Stage formstage = new Stage();
            formstage.setScene(scene);
            formstage.show();
            URL url = this.getClass().getResource("/style.css");
            if (url == null) {
                System.out.println("Resource not found. Aborting.");
                System.exit(-1);
            }
            String css = url.toExternalForm();
            formstage.getScene().getStylesheets().add(css);
            //reloads table with updated data
            formstage.setOnHiding(event -> {
                table.setRoot(populateTreeItems());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private TreeItem<PurchaseOrder> populateTreeItems(){

        final TreeItem<PurchaseOrder> root = new RecursiveTreeItem<>(getOrdersFromAccessDB(), RecursiveTreeObject::getChildren);

        return root;
    }

    private  ObservableList<PurchaseOrder> getOrdersFromAccessDB(){

        ObservableList<PurchaseOrder> orders =
                FXCollections.observableArrayList();

        String query = "SELECT * FROM ORDERS WHERE PO_DATE = #" + dateField.getValue()+ "# AND VISIBLE = 1 ORDER BY SUPPLIER;";
        ResultSet rs = AccessDatabase.accessConnectionSelect(query);

        try {
            while (rs.next()){

                PurchaseOrder temp = new PurchaseOrder(
                        rs.getInt("ID"), rs.getString("PO_NUMBER"),
                        rs.getString("SUPPLIER"),  rs.getString("SUPPLIER_ID"),
                        rs.getString("HAULIER"), rs.getInt("PALLETS"),
                        rs.getInt("UNLOADING_TIME"), rs.getDate("PO_DATE"),
                        rs.getTimestamp("EXPECTED_ETA"), rs.getTimestamp("ARRIVED"),
                        rs.getTimestamp("DEPARTED"), rs.getTimestamp("BOOKED_IN"),
                        rs.getString("BAY"), rs.getString("COMMENTS"),
                        rs.getString("TRAILER_NO"));
                orders.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;

    }



    private  void getOrderFromProtean(){

        Timestamp date1 = Timestamp.valueOf(LocalDateTime.of(dateField.getValue(), LocalTime.of(0, 0, 0)));
        Timestamp date2 = Timestamp.valueOf(LocalDateTime.of(dateField.getValue(), LocalTime.of(23, 59, 59)));


        final String proteanQuery =
                "SELECT MAX(CONTACT) AS CONTACT , EXPECTRCPTDOCNUM, MAX(EXPARRIVALDATE) AS DATE," +
                        " MAX(SUPCUSTNAME) AS SUPPLIER,MAX(SUPCUSTID) AS SUPPID FROM INEXPECTRECEIPT " +
                        "WHERE EXPECTRCPTDOCNUM LIKE 'B%' AND  EXPARRIVALDATE>='" + date1 + "' AND EXPARRIVALDATE<='" + date2
                        + "' GROUP BY EXPECTRCPTDOCNUM, CONTACT ORDER BY CONTACT";

        ResultSet rs = SQLDatabase.querySQL(proteanQuery);
        try{
            while(rs.next()){
//   removes all '  from supplier names as it might break Access insert query and inconsistent results might appear
                String supplierName = rs.getString("SUPPLIER").replaceAll("'", "");

                PurchaseOrder temp = new PurchaseOrder(
                        rs.getString("EXPECTRCPTDOCNUM"),
                        rs.getDate("DATE"),
                        supplierName,
                        rs.getString("SUPPID"));

                AccessDatabase.insertOrder(temp);

                AccessDatabase.insertSupplier(new Supplier(temp.getSupplierName(), temp.getSupplierID()));
            }
        }catch(SQLException e){

            e.printStackTrace();
        }

    }






}