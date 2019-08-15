package app.controller;


import app.model.PurchaseOrder;
import app.model.Supplier;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class POTableTab{



    private static Tab tab = new Tab("Orders List");
    private static TableView <PurchaseOrder> table = new TableView<>();
    private static StackPane pane = new StackPane();
    private static BorderPane bPane = new BorderPane();
    private static HBox controllBox = new HBox();


    private static JFXButton importOrders = new JFXButton("Import Orders");
    private static JFXButton listOrders = new JFXButton("List");
    private static JFXButton deleteOrder = new JFXButton("Delete");
    private static JFXButton duplicateOrder = new JFXButton("Duplicate");
    private static JFXDatePicker dateField = new JFXDatePicker();




    public static Tab createTab(){

        controllBox.setPadding(new Insets(15, 20, 15, 25));
        controllBox.setSpacing(25);
        controllBox.setAlignment(Pos.CENTER_RIGHT);
        controllBox.getChildren().addAll(dateField, importOrders, listOrders, duplicateOrder, deleteOrder);

        bPane.setTop(controllBox);
        bPane.setCenter(table);

        dateField.setValue(LocalDate.now());

        pane.getChildren().addAll(bPane);
        tab.setContent(pane);
        CreateTableView();
        initializeButtons();
        return tab;
    }



    private static void CreateTableView(){


        TableColumn<PurchaseOrder, Integer> idCol =
                new TableColumn<>("ID");
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn<PurchaseOrder, String> orderNumber =
                new TableColumn<>("PO Number");
        orderNumber.setCellValueFactory(
                new PropertyValueFactory<>("orderNumber"));

        TableColumn<PurchaseOrder, String> supplierNameCol =
                new TableColumn<>("POs Number");
        supplierNameCol.setCellValueFactory(
                new PropertyValueFactory<>("supplierName"));

        TableColumn<PurchaseOrder, LocalDate> supplierIDCol =
                new TableColumn<>("Supplier ID");
        supplierIDCol.setCellValueFactory(
                new PropertyValueFactory<>("supplierID"));

        TableColumn<PurchaseOrder, String> poDateCol =
                new TableColumn<>("PO Date");
        poDateCol.setCellValueFactory(
                new PropertyValueFactory<>("poDate"));


        TableColumn<PurchaseOrder, String> haulierCol =
                new TableColumn<>("Haulier");
        haulierCol.setCellValueFactory(
                new PropertyValueFactory<>("haulier"));

        TableColumn<PurchaseOrder, Integer> palletsCol =
                new TableColumn<>("Pallets");
        palletsCol.setCellValueFactory(
                new PropertyValueFactory<>("pallets"));

        TableColumn<PurchaseOrder, Integer> unloadingTimeCol =
                new TableColumn<>("Unloading Time");
        unloadingTimeCol.setCellValueFactory(
                new PropertyValueFactory<>("unloadingTime"));

        table.getColumns().addAll(idCol, orderNumber, supplierNameCol, supplierIDCol, poDateCol, haulierCol, palletsCol,unloadingTimeCol);


//TODO padaryti kad atidarytu forma su orderio detalemis...
        table.setRowFactory( tv -> {
            TableRow<PurchaseOrder> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {


                }
            });
            return row ;
        });


    }



    private static void initializeButtons(){

        //TODO prideti likusias knopkes

        listOrders.setOnAction(event -> {
            table.setItems(getOrdersFromAccessDB());
        });

        importOrders.setOnAction(event -> {
            getOrderFromProtean();
            table.setItems(getOrdersFromAccessDB());

        });
    }




    private static ObservableList<PurchaseOrder> getOrdersFromAccessDB(){

        ObservableList<PurchaseOrder> orders =
                FXCollections.observableArrayList();

        String query = "Select * from orders where PO_DATE = #" + dateField.getValue()+ "# and visible = 1 ORDER BY SUPPLIER;";
        ResultSet rs = AccessDatabase.accessConnectionSelect(query);

        try {
            while (rs.next()){

                PurchaseOrder temp = new PurchaseOrder(rs.getInt("ID"), rs.getString("PO_NUMBER"), rs.getString("SUPPLIER"),  rs.getString("SUPPLIER_ID"), rs.getString("HAULIER"), rs.getInt("PALLETS"), rs.getInt("UNLOADING_TIME"), rs.getDate("PO_DATE"));
                orders.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;

    }

    private static void getOrderFromProtean(){

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


    private static void insertSupplier(Supplier supplier){

        String insertSupplier =
                "INSERT INTO SUPPLIERS(DESC, [SUPPLIER_ID]) VALUES('"
                        + supplier.getName() + "','" + supplier.getSupplierId() + "')";


            AccessDatabase.accessConectionIsertUpdate(insertSupplier);


    }

    private static void insertOrdersInToAccessDB(PurchaseOrder order){

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