package app.controller;


import app.model.PurchaseOrder;
import app.model.Supplier;
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

    private final Tab tab = new Tab("Orders List");

    private final JFXTreeTableView<PurchaseOrder> table = new JFXTreeTableView<>();
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

        dateField.setValue(LocalDate.now());

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
            getOrderFromProtean();
//            table.setRoot(populateTreeItems());

        });

        deleteOrder.setOnAction(event -> {

            deleteOrder();
            table.setRoot(populateTreeItems());
        });


        table.setRowFactory( tr -> {

            JFXTreeTableRow<PurchaseOrder> row = new JFXTreeTableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty()) ) {
                    PurchaseOrder order = table.getSelectionModel().getSelectedItem().getValue();
                    selectedDateLabel.setText(dateField.getValue().toString() + "  " + order.getSupplierName());
                }
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    PurchaseOrder order = table.getSelectionModel().getSelectedItem().getValue();
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

        return new RecursiveTreeItem<>(getOrdersFromAccessDB(), RecursiveTreeObject::getChildren);
    }


    private void deleteOrder() {

        table.getSelectionModel().getSelectedItem().getValue();

        System.out.println(table.getSelectionModel().getSelectedItem().getValue().getId());
        String query = "UPDATE orders SET visible = 0 WHERE id = " +
                       table.getSelectionModel().getSelectedItem().getValue().getId() + ";";
        AccessDatabase.insert(query);
    }


    private  ObservableList<PurchaseOrder> getOrdersFromAccessDB(){

        ObservableList<PurchaseOrder> orders =
                FXCollections.observableArrayList();

        String query = "SELECT * FROM ORDERS WHERE PO_DATE = " + dateField.getValue()+ " ORDER BY SUPPLIER;";
//        orders.addAll(SQLiteJDBC.getOrdersFromDB(query));

        return orders;

    }


//originally it should get data from Protean SQL database
    private  void getOrderFromProtean(){

        LocalDate date1 = dateField.getValue();
        System.out.println(date1.toString());



        final String proteanQuery =
                "SELECT DISTINCT PO, DATE, SUPP_NAME, SUPP_CODE FROM PROTEAN WHERE PO LIKE 'B%' AND  DATE ='"+ date1 +"' AND m_code LIKE 'M%' GROUP BY PO order by supp_name";

        ResultSet rs = SQLiteProteanClone.query(proteanQuery);
        try{
            assert rs != null;
            while(rs.next()){
//   removes all '  from supplier names as it might break Access insert query and inconsistent results might appear
                String supplierName = rs.getString("SUPP_NAME").replaceAll("'", " ");


                SupplierOrders temp = new SupplierOrders();
//                temp.setOrderDate(rs.getString("date"));
//                temp.setPoNumber(rs.getString("po"));
//                temp.setSuppCode(rs.getString("supp_code"));
//                SQLiteJDBC.insertOrder(temp);

                Suppliers tempSupp = new Suppliers();
                tempSupp.setSupplierCode(rs.getString("supp_code"));
                tempSupp.setSupplierName(supplierName);

                System.out.println(tempSupp.getSupplierName() + "   " + tempSupp.getSupplierCode());

               SQLiteJDBC.insertSupplier(tempSupp);

            }
            System.out.println("baigta !!!");
        }catch(SQLException e){

            e.printStackTrace();
        }

    }






}