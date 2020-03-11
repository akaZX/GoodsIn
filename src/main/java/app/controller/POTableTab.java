package app.controller;


import app.controller.sql.SQLiteJDBC;
import app.controller.sql.SQLiteProteanClone;
import app.controller.sql.dao.*;
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
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.intellij.lang.annotations.Language;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class POTableTab{


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
                 tableColumns.arrivedCol(), tableColumns.departedCol(),
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

        importEntries.setOnAction(event -> {

            getOrderDetailsFromProtean();
            table.setRoot(populateTreeItems());

        });

        deleteEntry.setOnAction(event -> {
            new ScheduleEntryDAO().delete(table.getSelectionModel().getSelectedItem().getValue());
            table.setRoot(populateTreeItems());
        });


        table.setRowFactory( tr -> {
            JFXTreeTableRow<ScheduleEntry> row = new JFXTreeTableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty()) ) {
                    ScheduleEntry order = table.getSelectionModel().getSelectedItem().getValue();
                    selectedDateLabel.setText(dateField.getValue().toString() + "  " + order.getSupplier());
                }
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    ScheduleEntry order = table.getSelectionModel().getSelectedItem().getValue();
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
    private void loadOrderForm(ScheduleEntry order) {

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
                SQLiteJDBC.close();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private TreeItem<ScheduleEntry> populateTreeItems(){

        return new RecursiveTreeItem<>(getDeliveriesFromDb(), RecursiveTreeObject::getChildren);
    }



    private  ObservableList<ScheduleEntry> getDeliveriesFromDb(){

        return FXCollections.observableArrayList(new ScheduleEntryDAO().getAll(dateField.getValue()));
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

        }catch(Exception e){

            e.printStackTrace();
        }

    }

    private void insertSuppliers(ResultSet rs){

        try {
            String supplierName = rs.getString("SUPP_NAME").replaceAll("'", " ");

            Suppliers tempSupp = new Suppliers();
            tempSupp.setSupplierCode(rs.getString("supp_code"));
            tempSupp.setSupplierName(supplierName);

            new SuppliersDAO().save(tempSupp);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertOrders(ResultSet rs) {
            Dao<SupplierOrders> dao = new SupplierOrderDAO();

        try {

            dao.save(new SupplierOrders(rs.getString("supp_code"), rs.getString("po"), LocalDate.parse(rs.getString("date"))));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertNewMaterials(String po){

        Materials mat = new Materials();
        Dao<Materials> materialsDAO = new MaterialsDAO();

        @Language("SQLite")
        String query = "select m_code, material_name from protean where po ='" + po + "';";
        ResultSet rs = SQLiteProteanClone.query(query);

        try {
            assert rs != null;
            while (rs.next()) {

                mat.setMCode(rs.getString("m_code"));
                mat.setName(rs.getString("material_name"));

                materialsDAO.save(mat);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void insertNewSupplierMaterials(String po){
        Dao<SupplierMaterials> dao = new SupplierMaterialsDAO();
        SupplierMaterials material             = new SupplierMaterials();
        @Language("SQLite")
        String query = "select m_code, supp_code from protean where po ='" + po + "';";
        ResultSet rs = SQLiteProteanClone.query(query);

        try {
            assert rs != null;
            while (rs.next()) {

                material.setmCode(rs.getString("m_code"));
                material.setSuppCode(rs.getString("supp_code"));
                dao.save(material);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertNewPoMaterials(String po){

        Dao<PoMaterials> dao = new PoMaterialsDAO();
        PoMaterials temp = new PoMaterials();


        @Language("SQLite")
        String query = "select po, m_code, date, Round(expected_quantity, 2) as expected_quantity, round(booked_in, 2) as booked_in, line, delivery_no from protean where po ='" + po + "';";
        ResultSet rs = SQLiteProteanClone.query(query);

        try {
            assert rs != null;
            while (rs.next()) {

                temp.setPo(rs.getString("po"));
                temp.setMCode(rs.getString("m_code"));
                temp.setArrivedQuantity(rs.getDouble("booked_in"));
                temp.setExpectedQuantity(rs.getDouble("expected_quantity"));
                temp.setExpectedDate(LocalDate.parse(rs.getString("date")));
                temp.setDeliveryNo(rs.getInt("delivery_no"));
                temp.setLineNo(rs.getInt("line"));

                dao.save(temp);

            }
            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}