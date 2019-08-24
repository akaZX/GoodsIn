package app.controller;


import app.model.PurchaseOrder;
import app.model.Supplier;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
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
    private  HBox controllBox = new HBox();


    private  JFXButton importOrders = new JFXButton("Import Orders");
    private  JFXButton listOrders = new JFXButton("List");
    private  JFXButton deleteOrder = new JFXButton("Delete");
    private  JFXButton duplicateOrder = new JFXButton("Duplicate");
    private  JFXDatePicker dateField = new JFXDatePicker();




    public  Tab createTab(){

        controllBox.setPadding(new Insets(15, 20, 15, 25));
        controllBox.setSpacing(25);
        controllBox.setAlignment(Pos.CENTER_RIGHT);
        controllBox.getChildren().addAll(dateField, importOrders, listOrders, duplicateOrder, deleteOrder);

        bPane.setTop(controllBox);
        bPane.setCenter(table);

        dateField.setValue(LocalDate.now());

        pane.getChildren().addAll(bPane);
        tab.setContent(pane);

        createTableView();
        initializeButtons();

        return tab;
    }



    private void createTableView(){

        JFXTreeTableColumn<PurchaseOrder, Integer> idCol = new JFXTreeTableColumn<>("ID");
        idCol.setStyle("-fx-alignment: CENTER_RIGHT;");
        idCol.prefWidthProperty().bind(table.widthProperty().multiply(0.03));
        idCol.minWidthProperty().bind(table.widthProperty().multiply(0.03));
        idCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, Integer> param) ->{
            if(idCol.validateValue(param)) {
                int id = param.getValue().getValue().getId();
                return new SimpleIntegerProperty(id).asObject();
            } else return idCol.getComputedValue(param);
        });

        JFXTreeTableColumn<PurchaseOrder, String> supplierCol = new JFXTreeTableColumn<>("Supplier");
        supplierCol.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        supplierCol.minWidthProperty().bind(table.widthProperty().multiply(0.15));
        supplierCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, String> param) ->{
            if(supplierCol.validateValue(param)) {
                String supplier = param.getValue().getValue().getSupplierName();
                return new SimpleStringProperty(supplier);
            } else return supplierCol.getComputedValue(param);
        });

        JFXTreeTableColumn<PurchaseOrder, String> poCol = new JFXTreeTableColumn<>("PO Number");
        poCol.prefWidthProperty().bind(table.widthProperty().multiply(0.07));
        poCol.minWidthProperty().bind(table.widthProperty().multiply(0.07));
        poCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, String> param) ->{
            if(poCol.validateValue(param)) {
                String poNumber = param.getValue().getValue().getOrderNumber();
                return new SimpleStringProperty(poNumber);
            } else return poCol.getComputedValue(param);
        });


        JFXTreeTableColumn<PurchaseOrder, String> haulierCol = new JFXTreeTableColumn<>("Haulier");
        haulierCol.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
        haulierCol.minWidthProperty().bind(table.widthProperty().multiply(0.07));
        haulierCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, String> param) ->{
            if(haulierCol.validateValue(param)) {
                String haulier = param.getValue().getValue().getHaulier();
                return new SimpleStringProperty(haulier);
            } else return haulierCol.getComputedValue(param);
        });

        JFXTreeTableColumn<PurchaseOrder, Integer> palletsCol = new JFXTreeTableColumn<>("Pallets");
        palletsCol.prefWidthProperty().bind(table.widthProperty().multiply(0.04));
        palletsCol.minWidthProperty().bind(table.widthProperty().multiply(0.04));
        palletsCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, Integer> param) ->{
            if(palletsCol.validateValue(param)) {
                int pallets = param.getValue().getValue().getPallets();
                return new SimpleIntegerProperty(pallets).asObject();
            } else return palletsCol.getComputedValue(param);
        });

        JFXTreeTableColumn<PurchaseOrder, Integer> unloadingTimeCol = new JFXTreeTableColumn<>("Unloading Time");
        unloadingTimeCol.prefWidthProperty().bind(table.widthProperty().multiply(0.04));
        unloadingTimeCol.minWidthProperty().bind(table.widthProperty().multiply(0.04));
        unloadingTimeCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, Integer> param) ->{
            if(unloadingTimeCol.validateValue(param)) {
                int pallets = param.getValue().getValue().getUnloadingTime();
                return new SimpleIntegerProperty(pallets).asObject();
            } else return unloadingTimeCol.getComputedValue(param);
        });


        TreeTableColumn<PurchaseOrder, Timestamp> expectedETACol = new TreeTableColumn<>("Expected");
//        expectedETACol.prefWidthProperty().bind(table.widthProperty().multiply(0.04));
//        expectedETACol.minWidthProperty().bind(table.widthProperty().multiply(0.04));
        expectedETACol.setCellValueFactory(new TreeItemPropertyValueFactory<>("expectedEta"));



        table.columnResizePolicyProperty();
        table.getColumns().addAll(idCol, supplierCol, poCol, haulierCol, palletsCol, unloadingTimeCol, expectedETACol);
    }


//    adds action listeners for buttons and table rows
    private  void initializeButtons(){

        //TODO prideti likusias knopkes

        listOrders.setOnAction(event -> {
            table.setRoot(populateTreeItems());
            table.setShowRoot(false);
            table.setEditable(false);
        });

        importOrders.setOnAction(event -> {
            getOrderFromProtean();
            table.setRoot(populateTreeItems());

        });

        table.setRowFactory( tr -> {
            TreeTableRow<PurchaseOrder> row = new TreeTableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
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

        String query = "Select * from orders where PO_DATE = #" + dateField.getValue()+ "# and visible = 1 ORDER BY SUPPLIER;";
        ResultSet rs = AccessDatabase.accessConnectionSelect(query);

        try {
            while (rs.next()){

                PurchaseOrder temp = new PurchaseOrder(rs.getInt("ID"), rs.getString("PO_NUMBER"), rs.getString("SUPPLIER"),  rs.getString("SUPPLIER_ID"), rs.getString("HAULIER"), rs.getInt("PALLETS"), rs.getInt("UNLOADING_TIME"), rs.getDate("PO_DATE"), rs.getTimestamp("EXPECTED_ETA"));
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

                String supplierName = rs.getString("SUPPLIER");
                System.out.println("Supplier: " + supplierName.replaceAll("'", ""));

                PurchaseOrder temp = new PurchaseOrder(
                        rs.getString("EXPECTRCPTDOCNUM"),
                        rs.getTimestamp("DATE"),
                        supplierName.replaceAll("'", ""),
                        rs.getString("SUPPID"));

                insertOrdersInToAccessDB(temp);
                insertSupplier(new Supplier(temp.getSupplierName(), temp.getSupplierID()));
            }
        }catch(SQLException e){

            e.printStackTrace();
        }

    }


    private  void insertSupplier(Supplier supplier){

        String insertSupplier =
                "INSERT INTO SUPPLIERS(DESC, [SUPPLIER_ID]) VALUES('"
                        + supplier.getName() + "','" + supplier.getSupplierId() + "')";


            AccessDatabase.accessConectionIsertUpdate(insertSupplier);


    }

    private  void insertOrdersInToAccessDB(PurchaseOrder order){

        System.out.println(order.getOrderNumber());

        final String checkQuery =
                "Select * from ORDERS WHERE PO_NUMBER ='"
                        + order.getOrderNumber() + "' AND PROTEAN_ENTRY = 1;";

        String insert =
                "INSERT INTO ORDERS(SUPPLIER,[SUPPLIER_ID],[PO_DATE],[PO_NUMBER]) VALUES('"
                        + order.getSupplierName() + "','" + order.getSupplierID() + "',#"
                        + order.getExpectedDate() + "#,'" + order.getOrderNumber() + "')";
        ResultSet rs = AccessDatabase.accessConnectionSelect(checkQuery);

        try {

            if(rs.next() == false){

                AccessDatabase.accessConectionIsertUpdate(insert);
                System.out.println(order.getOrderNumber() + " added to database");
            }else{
                System.out.println(order.getOrderNumber() + " exists in database");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}