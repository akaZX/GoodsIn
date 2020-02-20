package app.controller;


import app.controller.sql.SQLiteJDBC;
import app.controller.sql.dao.SuppliersDAO;
import app.pojos.PoMaterials;
import app.pojos.Suppliers;
import app.view.custom_nodes.DateTimeInput;
import app.model.ScheduleEntry;
import app.view.table_columns.OrderDetailsColumns;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.lang.String.valueOf;


public class FormController  implements Initializable {

    private ScheduleEntry scheduleEntry;

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

    private final JFXTreeTableView<PoMaterials> orderDetailsTable = new JFXTreeTableView<>();


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

    public FormController(ScheduleEntry scheduleEntry) {
        this();
        this.scheduleEntry = scheduleEntry;
        loadOrderDetails(scheduleEntry);

    }


    private void loadOrderDetails(ScheduleEntry order) {

        poField.setText(order.getPo());
        supplierField.setText(order.getSupplier());
        bays.setValue(order.getScheduleDetails().getBay());
        haulierField.setText(order.getScheduleDetails().getHaulier());

        if (order.getScheduleDetails().getPallets() > 0) {
            palletsField.setText(String.valueOf(order.getScheduleDetails().getPallets()));
        }

        if (order.getScheduleDetails().getDuration() > 0) {
            approxUnloadField.setText(String.valueOf(order.getScheduleDetails().getDuration()));
        }

        trailerNoField.setText(order.getScheduleDetails().getRegistrationNo());

        commentsBox.setText(order.getScheduleDetails().getComments());

        if (order.getScheduleDetails().getArrived() != null) {
            arrivedDate.setLocalDateTime(order.getScheduleDetails().getArrived());
        }
        if (order.getScheduleDetails().getEta() != null) {
            expectedETA.setLocalDateTime(order.getScheduleDetails().getEta());
        }
        if (order.getScheduleDetails().getDeparted() != null) {
            arrivedDate.setLocalDateTime(order.getScheduleDetails().getDeparted());
        }
        if (order.getScheduleDetails().getBookedIn() != null) {
            arrivedDate.setLocalDateTime(order.getScheduleDetails().getBookedIn());
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addTextfieldValidation(haulierField, "missing Haulier");
        addComboBoxValidation(bays);


         //adds autocomplete for fields
        addDataToAutocompleteField("HAULIERS", "name", haulierField);


        addDataToSupp(supplierField);

        initializeForm();
        initializeOrderDetailsTable();

        Platform.runLater(() -> bays.requestFocus());
    }



    public void addDataToSupp(JFXTextField field) {

        List<Suppliers> list = new SuppliersDAO().getAll();
        List<String> names = new ArrayList<>();


        for (Suppliers supp : list) {
            names.add(supp.getSupplierName());
        }

        Collections.sort(names);

        addAutocomplete(field, names);

    }

    public void addDataToAutocompleteField(String table, String nameField, JFXTextField field) {

        List<String> list = new ArrayList<>();
        String    query = "SELECT * FROM " + table + " ORDER BY " + nameField + " DESC;";
        ResultSet rs    = SQLiteJDBC.selectQuery(query);

        try {
            while (rs.next()){
                list.add(rs.getString(nameField));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
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

        for(int i = 0; i < leftLeftList.length; i++){
            gridPane.add(leftLeftList[i], 1, i+1 );
            addCssClassNamesForNodes(leftLeftList[i]);
        }
        for(int i = 0; i < leftRightList.length; i++){
            gridPane.add(leftRightList[i], 2, i+1 );
            addCssClassNamesForNodes(leftRightList[i]);
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
        for(int i = 0; i < rightLeftList.length; i++){

            gridPane.add(rightLeftList[i], 4, i+1 );
            addCssClassNamesForNodes(rightLeftList[i]);
        }



        gridPane.add(submitForm, 5, 13);
        gridPane.add(orderDetailsTable, 1, 9,2,5);

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

        if (bays.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "\n Please select Bay";
            error = true;
            bays.validate();
        }

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
            haulierField.validate();
            error = true;
        }else{
            //TODO add code snippet to add new haulier
        }
        if (expectedETA.getLocalDateTime() == null) {
            errorMessage += "\n Expected ETA  field is blank";
            error = true;
        }



        if(!error){


            if(scheduleEntry.getScheduleDetails().getRowid() > 0){

            }else{
                System.out.println("updating order");
              //TODO add code to update order
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
        //noinspection unchecked
        orderDetailsTable.getColumns().setAll(columns.mCodeCol(), columns.descCol(),
                columns.expectedCol(), columns.bookedCol());

        //TODO needs testing inside Bakkavor

        if((scheduleEntry.getPo() != null) || ! scheduleEntry.getPo().equals("")){
            final TreeItem<PoMaterials> root = new RecursiveTreeItem<>(getOrderDetails(), RecursiveTreeObject::getChildren);
            orderDetailsTable.setRoot(root);
        }

    }

    //Load order data to table view if it is has an PO number
    private ObservableList<PoMaterials> getOrderDetails(){

        ObservableList<PoMaterials> orders   = FXCollections.observableArrayList();
        String                      poNumber = poField.getText().isEmpty() ? "" : poField.getText();

        ResultSet rs = SQLiteJDBC.selectQuery("Select * from  PO_MATERIALS where po ='" + poNumber + "'");

        try {
            while (rs.next()){
                orders.add(
                        new PoMaterials(
                                rs.getString("po") , rs.getString("m_code"),
                                rs.getDouble("expected_quantity"),
                                 rs.getDouble("arrived_quantity")));

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

    private void addCssClassNamesForNodes(Node node) {

        if (node instanceof JFXButton) {
            node.getStyleClass().add("form-button");
        }
        if (node instanceof Label) {
            node.getStyleClass().add("form-label");
        }

    }

    private void addComboBoxValidation(JFXComboBox<?> node){
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Please select Bay");
        bays.getValidators().add(validator);


        bays.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                bays.validate();
            }
        });
    }

    private void addTextfieldValidation(JFXTextField node, String message){
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage(message);
        node.getValidators().add(validator);


        node.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                node.validate();
            }

        });
    }


}