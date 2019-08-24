package app.controller;


import app.CustomGUI.DateTimeInput;
import app.model.Haulier;
import app.model.OrderDetails;
import app.model.PurchaseOrder;
import app.model.Supplier;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class FormController  implements Initializable {

    HashMap<Integer, Haulier> hauliers = new HashMap<>();
    HashMap<Integer, Supplier> suppliers = new HashMap<>();

    @FXML
    GridPane gridPane;

    private final ObservableList<String> BAY_NAMES = FXCollections.observableArrayList("Bay01", "Bay02", "Bay03", "Bay04");

    //form labels
    private Label dPoint = new Label("Delivery point:"), supplier = new Label("Supplier:"),
            poNumber = new Label("Order Number::"), haulier = new Label("Haulier:"),
            pallets = new Label("Pallets:"), expectedArrivalTime = new Label("Expected ETA:"),
            unloadingTime = new Label("Unloading Time:"), poDetails = new Label("PO Details:"),
            trailerNo = new Label("Trailer registration:"), comments = new Label("Comments:"),
            arrived = new Label("Arrived:"), departed = new Label("Departed:"),
            bookedIn = new Label("Booked In:"), poDetailsLabel = new Label(), placeHolder = new Label();

    private TextArea commentsBox = new TextArea();

    private JFXTextField supplierField = new JFXTextField(), poField = new JFXTextField(),
            haulierField = new JFXTextField(), palletsField = new JFXTextField(),
            approxUnloadField = new JFXTextField(), trailerNoField = new JFXTextField();


    private JFXComboBox<String> bays = new JFXComboBox<>();


    private DateTimeInput expectedETA = new DateTimeInput(), arrivedDate = new DateTimeInput(),
            departedDate = new DateTimeInput(), bookedInDate = new DateTimeInput();

    private JFXTreeTableView<OrderDetails> orderDetailsTable = new JFXTreeTableView<>();



    private JFXButton getArrivedTime = new JFXButton("Get Arrival Time"),
            getDepartedTime = new JFXButton("Get departure Time"),
            getBookedInTime = new JFXButton("Get booked in Time"),
            submitForm = new JFXButton("Submit");


    private Node [] leftLeftList = {
            dPoint, poNumber, supplier, haulier, pallets, unloadingTime,
            expectedArrivalTime, poDetails};

    private Node[] leftRightList = {
            bays, poField, supplierField, haulierField, palletsField, approxUnloadField,
            expectedETA};

    private Node[] rightLeftList = {
            trailerNo,comments,new Label(),new Label(),arrived,arrivedDate,
            departed,departedDate,bookedIn,bookedInDate};

    private Node [] rightRightList = {
            trailerNoField, commentsBox,new Label(),new Label(), new Label(), getArrivedTime, new Label(),
            getDepartedTime,new Label(), getBookedInTime};


    public FormController() {

    }

    public FormController(PurchaseOrder order) {

        loadOrderDetails(order);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
//            querryAccessDB("hauliersList", hauliers);
            getSuppliersList("suppliers", suppliers);
            getHauliersList("hauliers", hauliers);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        initializeOrderDetailsTable();
        initializeForm();
    }


    private void initializeForm(){

        getArrivedTime.getStyleClass().add("form-button");

        gridPane.add(poDetailsLabel, 1, 0,2,1);
        gridPane.add(orderDetailsTable, 1, 9,2,5);

        for(int i = 0; i < leftLeftList.length; i++){
            gridPane.add(leftLeftList[i], 1, i+1 );

        }
        for(int i = 0; i < leftRightList.length; i++){
            gridPane.add(leftRightList[i], 2, i+1 );
        }
        for(int i = 0; i < rightLeftList.length; i++){

            gridPane.add(rightLeftList[i], 4, i+1 );
        }
        for(int i = 0; i < rightRightList.length; i++){
            if(i+1 == 2){
                gridPane.add(rightRightList[i], 5, i+1,1,3);
                i+=2;
                continue;
            }
            gridPane.add(rightRightList[i], 5, i+1 );
        }



        gridPane.add(submitForm, 5, 13);
//        submitForm.minWidthProperty().bind(gridPane.minWidthProperty().multiply(0.4));
//        leftLeftList[6].minWidthProperty().bind(gridPane.minWidthProperty().multiply(0.2));
       addNumberFormatt(palletsField);
       addNumberFormatt(approxUnloadField);

        addBaysToChoiceBox();

        addActionsForButtons();

    }

//adds event handler for buttons
    private void addActionsForButtons(){

        getArrivedTime.setOnAction(event ->
                arrivedDate.setCurrentTime());

        getDepartedTime.setOnAction(event ->
                departedDate.setCurrentTime());

        getBookedInTime.setOnAction(event ->
            bookedInDate.setCurrentTime());

        submitForm.setOnAction(event ->

        System.out.println(expectedETA.getHours())
                );
    }


    private void addBaysToChoiceBox(){
        bays.setItems(BAY_NAMES);
        bays.getSelectionModel().selectFirst();

    }


    private void initializeOrderDetailsTable(){


        JFXTreeTableColumn<OrderDetails, String> mCodeColumn = new JFXTreeTableColumn<>("M Code");
        mCodeColumn.prefWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.2));
        mCodeColumn.minWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.2));
        mCodeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<OrderDetails, String> param) ->{
            if(mCodeColumn.validateValue(param)) return param.getValue().getValue().mCode;
            else return mCodeColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<OrderDetails, String> descColumn = new JFXTreeTableColumn<>("Description");

        descColumn.prefWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.4));
        descColumn.minWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.39));

        descColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<OrderDetails, String> param) ->{
            if(descColumn.validateValue(param)) return param.getValue().getValue().description;
            else return descColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<OrderDetails, String> expectedColumn = new JFXTreeTableColumn<>("Expected");
        expectedColumn.prefWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.2));
        expectedColumn.minWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.2));
        expectedColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<OrderDetails, String> param) ->{
            if(expectedColumn.validateValue(param)) return param.getValue().getValue().expected;
            else return expectedColumn.getComputedValue(param);
        });

        JFXTreeTableColumn<OrderDetails, String> bookedColumn = new JFXTreeTableColumn<>("Booked");
        bookedColumn.prefWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.19));
        bookedColumn.minWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.19));
        bookedColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<OrderDetails, String> param) ->{
            if(bookedColumn.validateValue(param)) return param.getValue().getValue().bookedIn;
            else return bookedColumn.getComputedValue(param);
        });

        mCodeColumn.setCellFactory((TreeTableColumn<OrderDetails, String> param) -> new GenericEditableTreeTableCell
                <>(new TextFieldEditorBuilder()));
        mCodeColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<OrderDetails, String> t)->{
            t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().mCode
                    .set(t.getNewValue());
        });


        //TODO prideti query y protean kad trauktu orderiu detales jeigu PO yra nustatytas

        ObservableList<OrderDetails> orders = FXCollections.observableArrayList();

        if(poField.getText().length() >= 7){

            try {
                orders =  getOrderDetails(poField.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }



        final TreeItem<OrderDetails> root = new RecursiveTreeItem<>(orders, RecursiveTreeObject::getChildren);


        orderDetailsTable.columnResizePolicyProperty();
        orderDetailsTable.setRoot(root);
        orderDetailsTable.setShowRoot(false);
        orderDetailsTable.setEditable(false);
        orderDetailsTable.getColumns().setAll(mCodeColumn, descColumn, expectedColumn, bookedColumn);



        Label size = new Label();
        size.textProperty().bind(Bindings.createStringBinding(()->orderDetailsTable.getCurrentItemsCount()+"",
                orderDetailsTable.currentItemsCountProperty()));


    }


    //Load order data to table view if it is has an PO number
    private ObservableList<OrderDetails> getOrderDetails(String poNumber) throws SQLException {

        ObservableList<OrderDetails> orders = FXCollections.observableArrayList();


        ResultSet rs = SQLDatabase.querySQL("Select * from suppliers where ExpectRcptDocNum='"+ poNumber +"'");

        while (rs.next()){
            orders.add(new OrderDetails(rs.getString(5), rs.getString(38),
                    rs.getString(48), rs.getString(39)));

        }
        return orders;
    }




    private void getSuppliersList(String table, HashMap<Integer, Supplier> suppliers) throws SQLException{
        String querry = "SELECT * FROM " + table + " ORDER BY Desc";
        ResultSet rs = AccessDatabase.accessConnectionSelect(querry);
        while(rs.next()){
            suppliers.put(rs.getInt(1), new Supplier(rs.getInt(1), rs.getString(2), rs.getString(3)));
        }
        rs.close();
        getSupplierAutocomplete();
    }

    private void getHauliersList(String table, HashMap<Integer, Haulier> hauliers) throws SQLException{
        String query = "SELECT * FROM " + table + " ORDER BY Desc";
        ResultSet rs = AccessDatabase.accessConnectionSelect(query);
        while(rs.next()){
            hauliers.put(rs.getInt(1), new Haulier( rs.getString(2), rs.getInt(1)));

        }
        rs.close();
        gethaulierAutocomplete();
    }


    private void gethaulierAutocomplete(){

        List<String> list = new ArrayList<>();

        for (Haulier value : hauliers.values()) {
            list.add(value.getName());
        }
        Collections.sort(list);

        addAutocomplete(haulierField, list);
    }


    private void getSupplierAutocomplete(){

        List<String> list = new ArrayList<>();

        for (Supplier value : suppliers.values()) {
            list.add(value.getName());
        }
        Collections.sort(list);

        addAutocomplete(supplierField, list);
    }

    //add number formatting for textfield
    private void addNumberFormatt(JFXTextField field){

        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            field.setText(newValue.replaceAll("[^\\d]", ""));
        });

    }


    private void addAutocomplete(TextField field, List<String> listas) {


        JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();

        autoCompletePopup.getSuggestions().addAll(listas);

        autoCompletePopup.setSelectionHandler(event -> {
            field.setText(event.getObject());

            // you can do other actions here when text completed
        });

        // filtering options
        field.textProperty().addListener(observable -> {

            autoCompletePopup.filter(string -> string.toUpperCase().contains(field.getText().toUpperCase()));
            if (autoCompletePopup.getFilteredSuggestions().isEmpty() || field.getText().isEmpty()) {
                autoCompletePopup.hide();
                // if you remove textField.getText.isEmpty() when text field is empty it suggests all options
                // so you can choose
            } else {
                autoCompletePopup.show(field);
            }
        });
    }

    private void loadOrderDetails(PurchaseOrder order){

        expectedETA.setDate(order.getPoDate().toLocalDate());
        supplierField.setText(order.getSupplierName());

    }

}