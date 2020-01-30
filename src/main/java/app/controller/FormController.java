package app.controller;


import app.view.custom_nodes.DateTimeInput;
import app.model.Haulier;
import app.model.OrderDetails;
import app.model.PurchaseOrder;
import app.model.Supplier;
import app.view.table_columns.OrderDetailsColumns;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.lang.String.valueOf;


public class FormController  implements Initializable {

    private  int id = 0;

    @FXML private GridPane gridPane;

    private final ObservableList<String> BAY_NAMES = FXCollections.observableArrayList("BAY01", "BAY02", "BAY03", "BAY04");

    //form labels
    private final Label dPoint = new Label("Delivery point:");

    private final Label supplier = new Label("Supplier:");

    private final Label poNumber = new Label("Order Number::");

    private final Label haulier = new Label("Haulier:");

    private final Label pallets = new Label("Pallets:");

    private final Label expectedArrivalTime = new Label("Expected ETA:");

    private final Label unloadingTime = new Label("Unloading Time:");

    private final Label poDetails = new Label("PO Details:");

    private final Label trailerNo = new Label("Trailer registration:");

    private final Label comments = new Label("Comments:");

    private final Label arrived = new Label("Arrived:");

    private final Label departed = new Label("Departed:");

    private final Label bookedIn = new Label("Booked In:");

    private final Label poDetailsLabel = new Label();

    private final TextArea commentsBox = new TextArea();

    private final JFXTextField supplierField = new JFXTextField();

    private final JFXTextField poField = new JFXTextField();

    private final JFXTextField haulierField = new JFXTextField();

    private final JFXTextField palletsField = new JFXTextField();

    private final JFXTextField approxUnloadField = new JFXTextField();

    private final JFXTextField trailerNoField = new JFXTextField();


    private final JFXComboBox<String> bays = new JFXComboBox<>();


    private final DateTimeInput expectedETA = new DateTimeInput();

    private final DateTimeInput arrivedDate = new DateTimeInput();

    private final DateTimeInput departedDate = new DateTimeInput();

    private final DateTimeInput bookedInDate = new DateTimeInput();

    private final JFXTreeTableView<OrderDetails> orderDetailsTable = new JFXTreeTableView<>();



    private final JFXButton getArrivedTime = new JFXButton("Get Arrival Time");

    private final JFXButton getDepartedTime = new JFXButton("Get departure Time");

    private final JFXButton getBookedInTime = new JFXButton("Get booked in Time");

    private final JFXButton submitForm = new JFXButton("Submit");


    private final Node [] leftLeftList = {
            dPoint, poNumber, supplier, haulier, pallets, unloadingTime,
            expectedArrivalTime, poDetails};

    private final Node[] leftRightList = {
            bays, poField, supplierField, haulierField, palletsField, approxUnloadField,
            expectedETA};

    private final Node[] rightLeftList = {
            trailerNo,comments,new Label(),new Label(),arrived,arrivedDate,
            departed,departedDate,bookedIn,bookedInDate};

    private final Node [] rightRightList = {
            trailerNoField, commentsBox,new Label(),new Label(), new Label(), getArrivedTime, new Label(),
            getDepartedTime,new Label(), getBookedInTime};


    public FormController() {
        addBaysToChoiceBox();

    }

    public FormController(PurchaseOrder order) {
        this();
        this.id = order.getId();
        loadOrderDetails(order);

    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {


        try {
         //adds autocomplete for fields
            addDataToAutocompleteField("HAULIERS", "DESC", haulierField);
            addDataToAutocompleteField("SUPPLIERS", "DESC", supplierField);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        initializeForm();
        initializeOrderDetailsTable();
    }

    public void addDataToAutocompleteField(String table, String nameField, JFXTextField field) throws SQLException{


        List<String> list = new ArrayList<>();

        String    query = "SELECT * FROM " + table + " ORDER BY DESC";
        ResultSet rs    = AccessDatabase.selectQuery(query);

        while (rs.next()){
            list.add(rs.getString(nameField));
        }

        Collections.sort(list);

        addAutocomplete(field, list);

    }


    private void addAutocomplete(JFXTextField field, List<String> list) {


        JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();

        autoCompletePopup.getSuggestions().addAll(list);

        autoCompletePopup.setSelectionHandler(event -> {
            field.setText(event.getObject());


        });

        // filtering options
        field.textProperty().addListener(observable -> {

            autoCompletePopup.filter(string -> string.toUpperCase().contains(field.getText().toUpperCase()));
            if (autoCompletePopup.getFilteredSuggestions().isEmpty() || field.getText().isEmpty()) {
                autoCompletePopup.hide();

            }
            else {
                autoCompletePopup.show(field);
            }
        });
    }




    private void initializeForm(){


        gridPane.add(poDetailsLabel, 1, 0,5,1);
        gridPane.add(orderDetailsTable, 1, 9,2,5);

        for(int i = 0; i < leftLeftList.length; i++){
            gridPane.add(leftLeftList[i], 1, i+1 );
            addCssClassNamesForNodes(leftLeftList[i]);
        }
        for(int i = 0; i < leftRightList.length; i++){
            gridPane.add(leftRightList[i], 2, i+1 );
            addCssClassNamesForNodes(leftRightList[i]);
        }
        for(int i = 0; i < rightLeftList.length; i++){

            gridPane.add(rightLeftList[i], 4, i+1 );
            addCssClassNamesForNodes(rightLeftList[i]);
        }
        for(int i = 0; i < rightRightList.length; i++){
            if(i+1 == 2){
                gridPane.add(rightRightList[i], 5, i+1,1,3);
                addCssClassNamesForNodes(rightRightList[i]);
                i+=2;
                continue;
            }
            gridPane.add(rightRightList[i], 5, i+1 );
            addCssClassNamesForNodes(rightRightList[i]);
        }


        gridPane.add(submitForm, 5, 13);
//        submitForm.minWidthProperty().bind(gridPane.minWidthProperty().multiply(0.4));
//        leftLeftList[6].minWidthProperty().bind(gridPane.minWidthProperty().multiply(0.2));
        addNumberFormatt(palletsField);
        addNumberFormatt(approxUnloadField);

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

        submitForm.setOnAction(this::validateNewOrderInputs );
    }

    private void validateNewOrderInputs(ActionEvent event){

        System.out.println("haulier field value: " + haulierField.getText());

        boolean error = false;
        String errorMessage = "";

       if(palletsField.getText() == null){
           errorMessage += "\n Pallets field is empty ";
           error = true;
       }
       if(poField.getText() == null){
           errorMessage += "\n PO field is blank";
           error = true;
       }
        if (supplierField.getText() == null) {
            errorMessage += "\n Supplier field is blank";
            error = true;
        }
        if (approxUnloadField.getText() == null) {
            errorMessage += "\n Unloading time field is blank";
            error = true;
        }
        if (haulierField.getText().isEmpty()) {
            errorMessage += "\n Haulier field is blank";
            error = true;
        }else{
            AccessDatabase.insertHaulier(haulierField.getText().toUpperCase());
        }
        if (expectedETA.getLocalDateTime() == null) {
            errorMessage += "\n Expected ETA  field is blank";
            error = true;
        }



        if(!error){


            if(id <= 0){
                AccessDatabase.insertNewOrderFromForm(generatePurchaseOrder());
            }else{
                System.out.println("updating order");
                AccessDatabase.updateOrder(generatePurchaseOrder(), id);
                ((Node)(event.getSource())).getScene().getWindow().hide();

            }

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Failed to create new order");
            alert.setHeaderText("Following fields missing");
            alert.setContentText(errorMessage);

            alert.showAndWait();
        }

    }

    private void addBaysToChoiceBox(){
        bays.setItems(BAY_NAMES);
        bays.getSelectionModel().selectFirst();
    }

    private void initializeOrderDetailsTable(){


        orderDetailsTable.columnResizePolicyProperty();

        orderDetailsTable.setShowRoot(false);
        orderDetailsTable.setEditable(false);
        OrderDetailsColumns columns = new OrderDetailsColumns(orderDetailsTable);
        orderDetailsTable.getColumns().setAll(columns.mCodeCol(), columns.descCol(),
                columns.expectedCol(), columns.bookedCol());

        //TODO needs testing inside Bakkavor

//        if(!poField.getText().isEmpty()){
////            final TreeItem<OrderDetails> root = new RecursiveTreeItem<>(getOrderDetails(), RecursiveTreeObject::getChildren);
////            orderDetailsTable.setRoot(root);
////        }

    }

    //Load order data to table view if it is has an PO number
    private ObservableList<OrderDetails> getOrderDetails(){

        ObservableList<OrderDetails> orders   = FXCollections.observableArrayList();
        String                       poNumber = poField.getText().isEmpty() ? "" : poField.getText();

        ResultSet rs = ProteanDBConnection.querySQL("Select * from INEXPECTRECEIPT where ExpectRcptDocNum='"+ poNumber +"'");

        try {
            while (rs.next()){
                orders.add(new OrderDetails(rs.getString(5), rs.getString(38),
                        rs.getString(48), rs.getString(39)));

            }
        } catch (SQLException e) {
            System.out.println("getOrderDetails inside FormController");
            e.printStackTrace();
        }

        return orders;
    }
    //add number formatting for textfield
    private void addNumberFormatt(JFXTextField field){

        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            field.setText(newValue.replaceAll("[^\\d]", ""));
        });

    }

    private PurchaseOrder generatePurchaseOrder(){
        return new PurchaseOrder(
                poField.getText(), supplierField.getText(), haulierField.getText().toUpperCase(),
                bays.getValue(), commentsBox.getText(), trailerNoField.getText(),
                Integer.parseInt(palletsField.getText()), Integer.parseInt(approxUnloadField.getText()),
                Date.valueOf(expectedETA.getLocalDate()), expectedETA.getLocalDateTime(),arrivedDate.getLocalDateTime(),
                departedDate.getLocalDateTime(), bookedInDate.getLocalDateTime());

    }

    private void addCssClassNamesForNodes(Node node) {

        if (node instanceof JFXButton) {
            node.getStyleClass().add("form-button");
        }
        if (node instanceof Label) {
            node.getStyleClass().add("form-label");
        }

    }

    private void loadOrderDetails(PurchaseOrder order) {

        String odersDetailsLabel = order.getSupplierName() + "      " + order.getOrderNumber();

        poDetailsLabel.setText(odersDetailsLabel);

        if (order.getExpectedEta().getValue() == null) {
            expectedETA.setDate(order.getPoDate().toLocalDate());
        }
        else {
            expectedETA.setLocalDateTime(order.getExpectedEta().getValue());
        }
        if (order.getBooked().getValue() != null) {
            bookedInDate.setLocalDateTime(order.getBooked().getValue());
        }
        if (order.getArrived().getValue() != null) {
            arrivedDate.setLocalDateTime(order.getArrived().getValue());
        }
        if (order.getDeparted().getValue() != null) {
            departedDate.setLocalDateTime(order.getDeparted().getValue());
        }

        poField.setText(order.getOrderNumber());
        if (! order.getOrderNumber().isEmpty()) {
//            TODO remove comment to get order details
            System.out.println("Order details is loading...");
//            getOrderDetails(order.getOrderNumber());
        }
        supplierField.setText(order.getSupplierName());
        haulierField.setText(order.getHaulier());
        trailerNoField.setText(order.getTrailerNo());
        commentsBox.setText(order.getComments());

        if (order.getPallets().getValue() > 0) {
            palletsField.setText(valueOf(order.getPallets().getValue()));
        }
        else {
            palletsField.setText(null);
        }

        if (order.getUnloadingTime().getValue() > 0) {
            approxUnloadField.setText(valueOf(order.getUnloadingTime().getValue()));
        }
        else {
            approxUnloadField.setText(null);
        }


        if (order.getBay() != null) {
            bays.setValue(order.getBay());
            bays.getSelectionModel().select(order.getBay());
        }
        else {
            bays.getSelectionModel().selectFirst();
        }


    }

}