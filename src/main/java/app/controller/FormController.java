package app.controller;



import app.CustomGUI.DateTimeInput;
import app.model.*;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.lang.String.valueOf;


public class FormController  implements Initializable {

    private HashMap<Integer, Haulier> hauliers = new HashMap<>();
    private HashMap<Integer, Supplier> suppliers = new HashMap<>();
    private  int id = 0;

    @FXML private GridPane gridPane;

    private final ObservableList<String> BAY_NAMES = FXCollections.observableArrayList("BAY01", "BAY02", "BAY03", "BAY04");

    //form labels
    private Label dPoint = new Label("Delivery point:"), supplier = new Label("Supplier:"),
            poNumber = new Label("Order Number::"), haulier = new Label("Haulier:"),
            pallets = new Label("Pallets:"), expectedArrivalTime = new Label("Expected ETA:"),
            unloadingTime = new Label("Unloading Time:"), poDetails = new Label("PO Details:"),
            trailerNo = new Label("Trailer registration:"), comments = new Label("Comments:"),
            arrived = new Label("Arrived:"), departed = new Label("Departed:"),
            bookedIn = new Label("Booked In:"), poDetailsLabel = new Label();

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
//            querryAccessDB("hauliersList", hauliers);
            getSuppliersList("suppliers", suppliers);
            getHauliersList("hauliers", hauliers);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        initializeForm();
        initializeOrderDetailsTable();
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
        if (haulierField.getText() == null) {
            errorMessage += "\n Haulier field is blank";
            error = true;
        }else{
            AccessDatabase.insertHaulier(haulierField.getText());
        }
        if (expectedETA.getLocalDateTime() == null) {
            errorMessage += "\n Expected ETA  field is blank";
            error = true;
        }



        if(!error){


            if(id == 0){
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


        //TODO pakesti  query y protean kad trauktu orderiu detales jeigu PO yra nustatytas



        orderDetailsTable.columnResizePolicyProperty();

        orderDetailsTable.setShowRoot(false);
        orderDetailsTable.setEditable(false);
        OrderDetailsColumns columns = new OrderDetailsColumns(orderDetailsTable);
        orderDetailsTable.getColumns().setAll(columns.mCodeCol(), columns.descCol(),
                columns.expectedCol(), columns.bookedCol());
        //TODO uncomment to load have functionality to see PO details

//        if(poField.getText() != null){
//            final TreeItem<OrderDetails> root = new RecursiveTreeItem<>(getOrderDetails(), RecursiveTreeObject::getChildren);
//            orderDetailsTable.setRoot(root);
//        }



//        Label size = new Label();
//        size.textProperty().bind(Bindings.createStringBinding(()->orderDetailsTable.getCurrentItemsCount()+"",
//                orderDetailsTable.currentItemsCountProperty()));


    }


    //Load order data to table view if it is has an PO number
    private ObservableList<OrderDetails> getOrderDetails(){
        //TODO change logic so it queries database if po number exists
        ObservableList<OrderDetails> orders = FXCollections.observableArrayList();
        String poNumber = poField.getText().isEmpty()? "" : poField.getText();

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
        getHaulierAutocomplete();
    }


    private void getHaulierAutocomplete(){

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

        String odersDetailsLabel = order.getSupplierName()+ "      " + order.getOrderNumber();

        poDetailsLabel.setText(odersDetailsLabel);

        if(order.getExpectedEta().getValue() == null){
            expectedETA.setDate(order.getPoDate().toLocalDate());
        }else{
            expectedETA.setLocalDateTime(order.getExpectedEta().getValue());
        }
        if(order.getBooked().getValue() != null){
            bookedInDate.setLocalDateTime(order.getBooked().getValue());
        }
        if(order.getArrived().getValue() != null){
            arrivedDate.setLocalDateTime(order.getArrived().getValue());
        }
        if(order.getDeparted().getValue() != null){
            departedDate.setLocalDateTime(order.getDeparted().getValue());
        }

        poField.setText(order.getOrderNumber());
        if(!order.getOrderNumber().isEmpty()){
//            TODO remove comment to get order details
            System.out.println("Order details is loading...");
//            getOrderDetails(order.getOrderNumber());
        }
        supplierField.setText(order.getSupplierName());
        haulierField.setText(order.getHaulier());
        trailerNoField.setText(order.getTrailerNo());
        commentsBox.setText(order.getComments());

        if(order.getPallets().getValue() > 0){
            palletsField.setText(valueOf(order.getPallets().getValue()));
        }else{
            palletsField.setText(null);
        }

        if(order.getUnloadingTime().getValue() > 0){
            approxUnloadField.setText(valueOf(order.getUnloadingTime().getValue()));
        }else{
            approxUnloadField.setText(null);
        }


       if(order.getBay() != null){
           bays.setValue(order.getBay());
           bays.getSelectionModel().select(order.getBay());
       }else{
           bays.getSelectionModel().selectFirst();
       }



    }

    private void addCssClassNamesForNodes(Node node){

        if(node instanceof JFXButton){
            node.getStyleClass().add("form-button");
        }
        if(node instanceof Label){
            node.getStyleClass().add("form-label");
        }

    }

    private PurchaseOrder generatePurchaseOrder(){
        return new PurchaseOrder(
                poField.getText(), supplierField.getText(), haulierField.getText(),
                bays.getValue(), commentsBox.getText(), trailerNoField.getText(),
                Integer.parseInt(palletsField.getText()), Integer.parseInt(approxUnloadField.getText()),
                Date.valueOf(expectedETA.getLocalDate()), expectedETA.getLocalDateTime(),arrivedDate.getLocalDateTime(),
                departedDate.getLocalDateTime(), bookedInDate.getLocalDateTime());

    }



}