package app.controller;


import app.model.PoScheduleEntry;
import app.pojos.PoMaterials;
import app.pojos.SupplierOrders;
import app.pojos.Suppliers;
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
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class POTableTab{


    public POTableTab() {

    }

    private final Tab tab = new Tab("Orders List");

    private final JFXTreeTableView<PoScheduleEntry> table = new JFXTreeTableView<>();
    private final StackPane pane = new StackPane();
    private final BorderPane bPane = new BorderPane();
    private final JFXButton importOrders = new JFXButton("Import Orders");
    private final JFXButton listOrders = new JFXButton("List");
    private final JFXButton deleteOrder = new JFXButton("Delete");
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
                importOrders,
                deleteOrder,
                duplicateOrder
        );


        toolBar.setPadding(new Insets(15, 25, 15, 25));


        bPane.setTop(toolBar);
        bPane.setCenter(table);

        dateField.setValue(LocalDate.of(2020,1,24));

        pane.getChildren().addAll(bPane);
        tab.setContent(pane);

        addColumnsToTable();
        tableListeners();

        return tab;
    }



    private void addColumnsToTable(){

        PoTableColumns tableColumns = new PoTableColumns(table);

        table.columnResizePolicyProperty();
        table.getColumns().addAll(tableColumns.supplierCol(), tableColumns.poCol(),
                tableColumns.haulierCol(), tableColumns.expectedETACol(), tableColumns.bayCol(),
                tableColumns.unloadingTimeCol(), tableColumns.palletsCol(),tableColumns.registrationCol(),
                tableColumns.commentsCol(), tableColumns.arrivedCol(), tableColumns.departedCol(),
                tableColumns.bookedInCol());


        table.setShowRoot(false);
        table.setEditable(false);
    }


//    adds action listeners for buttons and table rows
    private  void tableListeners(){

        //TODO prideti likusias knopkes

        listOrders.setOnAction(event -> {
            table.setRoot(populateTreeItems());
            selectedDateLabel.setText(String.valueOf(dateField.getValue()));

        });

        importOrders.setOnAction(event -> {
            getOrderDetailsFromProtean();
//            table.setRoot(populateTreeItems());

        });

        deleteOrder.setOnAction(event -> {

            deleteOrder();
            table.setRoot(populateTreeItems());
        });


        table.setRowFactory( tr -> {

            JFXTreeTableRow<PoScheduleEntry> row = new JFXTreeTableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty()) ) {
                    PoScheduleEntry order = table.getSelectionModel().getSelectedItem().getValue();
                    selectedDateLabel.setText(dateField.getValue().toString() + "  " + order.getSupplier());
                }
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    PoScheduleEntry order = table.getSelectionModel().getSelectedItem().getValue();
                    loadOrderForm(order);
                }
              //TODO add right click functionality after main buttons will be sorted
                if (event.getButton() == MouseButton.SECONDARY && !row.isEmpty()){
                    System.out.println("Right button clicked");
                }
            });
            return row ;
        });

    }



    //loads delivery form with order details
    private void loadOrderForm(PoScheduleEntry order) {

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

    private TreeItem<PoScheduleEntry> populateTreeItems(){

        return new RecursiveTreeItem<>(getOrdersFromAccessDB(), RecursiveTreeObject::getChildren);
    }


    private void deleteOrder() {

        table.getSelectionModel().getSelectedItem().getValue();

        System.out.println(table.getSelectionModel().getSelectedItem().getValue().getRowId());
        String query = "UPDATE PO_SCHEDULE_DETAILS SET visible = 0 WHERE rowid = " +
                       table.getSelectionModel().getSelectedItem().getValue().getRowId() + ";";
//        AccessDatabase.insert(query);
    }


    private  ObservableList<PoScheduleEntry> getOrdersFromAccessDB(){

        ObservableList<PoScheduleEntry> orders =
                FXCollections.observableArrayList();

      String query = "SELECT sp.supp_name, so.rowid AS rowid, " +
                     "so.po, so.supp_code, so.order_date " +
                     "FROM SUPPLIER_ORDERS so " +
                     "INNER JOIN SUPPLIERS sp ON sp.supp_code = so.supp_code " +
                     "where so.ORDER_DATE ='" + dateField.getValue().toString()+ "'";



        ResultSet rs = SQLiteJDBC.query(query);
        System.out.println(dateField.getValue().toString());

        try {

            while (rs.next()) {
                PoScheduleEntry temp = new PoScheduleEntry(
                        rs.getInt("rowid"), rs.getString("supp_name"), rs.getString("supp_code"),
                        rs.getString("po"), rs.getString("order_date"));
                temp.setScheduleDetails(SQLiteJDBC.getDeliveryDetails(temp.getRowId()));
                orders.addAll(temp);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }



        return orders;

    }
    private ObservableList<?> getScheduleRows(){
        ObservableList<PoScheduleEntry> orders =
                FXCollections.observableArrayList();
        String query = "Select * from ";
        return  orders;
    }

//  Originally it should get data from Protean SQL database as it is located in company's VPN
//  some data was stored in separate SQLite database for demonstration purposes
    private  void getOrderDetailsFromProtean(){

        LocalDate date1 = dateField.getValue();


        final String proteanQuery =
                "SELECT Distinct PO, date(DATE) as date, SUPP_NAME, SUPP_CODE FROM PROTEAN WHERE PO LIKE 'B%' AND  DATE ='"+ date1 +"' AND M_CODE LIKE 'M%' GROUP BY PO ORDER BY SUPP_NAME";

        ResultSet rs = SQLiteProteanClone.query(proteanQuery);
        try{
            assert rs != null;
            while(rs.next()){
                String po = rs.getString("po");
                insertSuppliers(rs);
                insertOrders(rs);
                insertNewMaterials(po);
                insertNewSupplierMaterials(po);
                insertNewPoMaterials(po);

            }

        }catch(SQLException e){

            e.printStackTrace();
        }

    }

    private void insertSuppliers(ResultSet rs){

        try {
            String supplierName = rs.getString("SUPP_NAME").replaceAll("'", " ");

            Suppliers tempSupp = new Suppliers();
            tempSupp.setSupplierCode(rs.getString("supp_code"));
            tempSupp.setSupplierName(supplierName);

            System.out.println(tempSupp.getSupplierName() + "   " + tempSupp.getSupplierCode());

            SQLiteJDBC.insertSupplier(tempSupp);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertOrders(ResultSet rs) {

        try {
            SupplierOrders temp = new SupplierOrders();
            temp.setOrderDate(LocalDate.parse(rs.getString("date")));
            temp.setPoNumber(rs.getString("po"));
            temp.setSuppCode(rs.getString("supp_code"));

            SQLiteJDBC.insertOrder(temp);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertNewMaterials(String po){

        String query = "select m_code, material_name from protean where po ='" + po + "';";
        ResultSet rs = SQLiteProteanClone.query(query);
        String insert = "Insert into materials (m_code, name) values(?,?)";
        try {
            assert rs != null;
            while (rs.next()) {

                SQLiteJDBC.updateTwoColumns(insert,rs.getString("m_code"), rs.getString("material_name"));

            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void insertNewSupplierMaterials(String po){
        String query = "select m_code, supp_code from protean where po ='" + po + "';";
        ResultSet rs = SQLiteProteanClone.query(query);

        String insert = "insert into SUPPLIER_MATERIALS (m_code, supp_code) values(?,?)";

        try {
            assert rs != null;
            while (rs.next()) {
                SQLiteJDBC.updateTwoColumns(insert, rs.getString("m_code"), rs.getString("supp_code"));
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertNewPoMaterials(String po){
        PoMaterials temp = new PoMaterials();
        String query = "select po, m_code, date, Round(expected_quantity, 2) as expected_quantity, round(booked_in, 2) as booked_in from protean where po ='" + po + "';";
        ResultSet rs = SQLiteProteanClone.query(query);
        String insert = "insert into PO_MATERIALS" +
                        "(po, m_code, expected_date, expected_quantity, arrived_quantity)" +
                        " values(?, ?, ?, ?, ?)";
        try {
            while (rs.next()) {
                temp.setPoNumber(rs.getString("po"));
                temp.setMCode(rs.getString("m_code"));
                temp.setArrivedQuantity(rs.getDouble("booked_in"));
                temp.setExpectedQuantity(rs.getDouble("expected_quantity"));
                temp.setExpectedDate(LocalDate.parse(rs.getString("date")));
                SQLiteJDBC.insertPoMaterials(insert, temp);
            }
        }
        catch (SQLException e) {
            System.out.println("Klaida: ");
            e.printStackTrace();
        }
    }

}