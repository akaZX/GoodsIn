package app.controller.goodsIn.schedule;


import app.controller.sql.dao.*;
import app.controller.utils.LabelWithIcons;
import app.controller.utils.Messages;
import app.controller.utils.ValidateInput;
import app.model.OrderMaterials;
import app.pojos.*;
import app.view.custom_nodes.DateTimeInput;
import app.model.ScheduleEntry;
import app.view.table_columns.OrderDetailsColumns;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static java.lang.String.valueOf;


public class FormController  implements Initializable {

    private ScheduleEntry scheduleEntry;
    private Node node;
    POTableTab tableTab = null;

    Messages msg = new Messages();

    @FXML private GridPane gridPane;

    private static final ObservableList<String> BAY_NAMES = FXCollections.observableArrayList("BAY01", "BAY02", "BAY03", "BAY04");

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
    private final Label poDetailsLabel = new Label("New schedule entry:");
    private final TextArea commentsBox = new TextArea();
    private final JFXComboBox<Suppliers> supplierBox = new JFXComboBox<>();
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
    private final JFXTreeTableView<OrderMaterials> orderDetailsTable = new JFXTreeTableView<>();
    private final JFXButton getArrivedTime = new JFXButton("Get Arrival Time");
    private final JFXButton getDepartedTime = new JFXButton("Get departure Time");
    private final JFXButton getBookedInTime = new JFXButton("Get booked in Time");
    private final JFXButton submitForm = new JFXButton("Save");


    private final Label[] labels = {
            dPoint,supplier,poNumber,haulier, pallets,
            expectedArrivalTime, unloadingTime, poDetails,
            trailerNo, comments, arrived, departed, bookedIn
    };

    private final Node [] leftLeftList = {
            dPoint, poNumber, supplier, haulier, pallets, unloadingTime,
            expectedArrivalTime, poDetails};

    private final Node[] leftRightList = {
            bays, poField, supplierBox, haulierField, palletsField, approxUnloadField,
            expectedETA};

    private final Node[] rightLeftList = {
            trailerNo,comments,new Label(),new Label(),arrived,arrivedDate,
            departed,departedDate,bookedIn,bookedInDate};

    private final Node [] rightRightList = {
            trailerNoField, commentsBox,new Label(),new Label(), new Label(), getArrivedTime, new Label(),
            getDepartedTime,new Label(), getBookedInTime};


    public FormController() {
        scheduleEntry = null;
        addBaysToChoiceBox();
    }

    public FormController(Node node, POTableTab tableTab){
        this();
        this.scheduleEntry = null;
        this.node = node;
        this.tableTab = tableTab;
    }

    public FormController(ScheduleEntry scheduleEntry, Node node, POTableTab tableTab) {
        this();
        this.scheduleEntry = scheduleEntry;
        this.node = node;
        this.tableTab = tableTab;
    }

    private void loadOrderDetails() {

        if (scheduleEntry != null) {

            poDetailsLabel.setText(scheduleEntry.getSupplier().getSupplierName() + "  " + scheduleEntry.getOrder().getPoNumber());
            poField.setText(scheduleEntry.getOrder().getPoNumber());
            poField.setEditable(false);

            supplierBox.getSelectionModel().select(scheduleEntry.getSupplier());
            supplierBox.setDisable(true);

            bays.setValue(scheduleEntry.getDetails().getBay());
            haulierField.setText(scheduleEntry.getDetails().getHaulier());

            if (scheduleEntry.getDetails().getPallets() > 0) {
                palletsField.setText(String.valueOf(scheduleEntry.getDetails().getPallets()));
            }

            if (scheduleEntry.getDetails().getDuration() > 0) {
                approxUnloadField.setText(String.valueOf(scheduleEntry.getDetails().getDuration()));
            }

            trailerNoField.setText(scheduleEntry.getDetails().getRegistrationNo());

            commentsBox.setText(scheduleEntry.getDetails().getComments());

            if (scheduleEntry.getDetails().getArrived() != null) {
                arrivedDate.setLocalDateTime(scheduleEntry.getDetails().getArrived());
            }
            if (scheduleEntry.getDetails().getEta() != null) {
                expectedETA.setLocalDateTime(scheduleEntry.getDetails().getEta());
            }else{
                expectedETA.setDate(scheduleEntry.getDetails().getOrderDate());
            }
            if (scheduleEntry.getDetails().getDeparted() != null) {
                departedDate.setLocalDateTime(scheduleEntry.getDetails().getDeparted());
            }
            if (scheduleEntry.getDetails().getBookedIn() != null) {
                bookedInDate.setLocalDateTime(scheduleEntry.getDetails().getBookedIn());
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadOrderDetails();
        //validation
        addTextfieldValidation();


         //adds autocomplete for fields
        hauliersField(new HaulierDao(), haulierField);
        suppliersBox();

        initializeForm();
        initializeOrderDetailsTable();

        Platform.runLater(bays::requestFocus);
    }



    private void initializeForm(){

        addCssClassForLabels();

        supplierBox.setMaxWidth(Double.MAX_VALUE);

        gridPane.add(poDetailsLabel, 1, 0,3,1);

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



        gridPane.add(submitForm, 5, 12);
        gridPane.add(orderDetailsTable, 1, 9,2,4);


//        submitForm.minWidthProperty().bind(gridPane.minWidthProperty().multiply(0.4));
//        leftLeftList[6].minWidthProperty().bind(gridPane.minWidthProperty().multiply(0.2));
        addNumberFormat(palletsField);
        addNumberFormat(approxUnloadField);

        addActionsForButtons();

    }


    private void addCssClassForLabels() {

        commentsBox.getStyleClass().add("sched-form-text-area");
        commentsBox.setWrapText(true);
        poDetailsLabel.getStyleClass().add("sched-form-label-top");
        for (Label label : labels) {
            label.getStyleClass().add("sched-form-label");
        }
    }


    private void suppliersBox(){
        supplierBox.setItems(FXCollections.observableArrayList(new SuppliersDao().getAll()));


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

        boolean error = false;
        String errorMessage = "";

        if (bays.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "\n Please select Bay";
            error = true;
            bays.validate();
        }
       if(poField.getText() == null || poField.getText().length() < 7){
           poField.validate();
           errorMessage += "\n PO field is left blank or it is too short";
           error = true;
       }
        if (supplierBox.getSelectionModel().getSelectedItem() == null) {
            supplierBox.validate();
            errorMessage += "\n Supplier field is blank";
            error = true;
        }

        if (haulierField.getText() == null || haulierField.getText().length() == 0) {
            errorMessage += "\n Haulier field is blank";
            haulierField.validate();
            error = true;
        }
        if (expectedETA.getLocalDateTime() == null) {
            errorMessage += "\n Expected ETA  field is blank";
            error = true;
        }
        try {
            Integer.parseInt(approxUnloadField.getText());

        }catch (NumberFormatException e) {
            errorMessage += "\n Unloading time field is blank";
            approxUnloadField.validate();
            error = true;
        }
        try {
            Integer.parseInt(palletsField.getText());

        }catch (NumberFormatException e) {
            errorMessage += "\n Pallets field is empty ";
            palletsField.validate();
            error = true;
        }



        if(!error){


            if(scheduleEntry == null){


                SupplierOrders supplierOrders = getSupplierOrder();
                ScheduleDetails scheduleDetails = getScheduleDetails();
                new SupplierOrderDao().save(supplierOrders);

//                System.out.println("saving new entry: \n" + getScheduleDetails().toString());
                boolean saved = new ScheduleDetailsDao().save(scheduleDetails);
//                System.out.println(saved);

            }else{
//                System.out.println("updating order: " + getScheduleDetails().toString());
                msg.continueAlert(node, LabelWithIcons.midCheckIconLabel("Entry created/Updated"), new Label(""));
                new ScheduleDetailsDao().update(getScheduleDetails());

            }
            new HaulierDao().save(new Hauliers(haulierField.getText()));
            tableTab.listAllRecords();
            ((Node)(event.getSource())).getScene().getWindow().hide();

        }else{
            msg.continueAlert(dPoint, LabelWithIcons.midWarningIconLabel("Failed to create/update entry"), new Label("Errors: " + errorMessage ));

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

        if (scheduleEntry != null) {
            try {
                    final TreeItem<OrderMaterials> root = new RecursiveTreeItem<>(getOrderDetails(), RecursiveTreeObject::getChildren);
                    orderDetailsTable.setRoot(root);
            }
            catch (NullPointerException e) {
//                System.out.println("klaida kraunant scheduleform/order materials table");
            }
        }


    }


    private ScheduleDetails getScheduleDetails(){
        ScheduleDetails      details = new ScheduleDetails();
        details.setEta(expectedETA.getLocalDateTime());
        details.setArrived(arrivedDate.getLocalDateTime());
        details.setDeparted(departedDate.getLocalDateTime());
        details.setBookedIn(bookedInDate.getLocalDateTime());
        details.setBay(bays.getSelectionModel().getSelectedItem());
        details.setComments(commentsBox.getText());
        details.setRegistrationNo(trailerNoField.getText());
        details.setHaulier(haulierField.getText());
        details.setPallets(Integer.parseInt(palletsField.getText()));
        details.setDuration(Integer.parseInt(approxUnloadField.getText()));
        if (scheduleEntry != null) {
            details.setRowid(scheduleEntry.getDetails().getRowid());
            details.setPo(scheduleEntry.getOrder().getPoNumber());

        }else{
            details.setOrderDate(expectedETA.getLocalDate());
            details.setPo(poField.getText().toUpperCase().trim());
        }
        return details;
    }

    private SupplierOrders getSupplierOrder(){
        SupplierOrders supplierOrders = new SupplierOrders();
        supplierOrders.setSuppCode(supplierBox.getSelectionModel().getSelectedItem().getSupplierCode());
        supplierOrders.setPoNumber(poField.getText().trim().toUpperCase());
        supplierOrders.setOrderDate(expectedETA.getLocalDate());
        return supplierOrders;
    }

    //add number formatting for textfield
    private void addNumberFormat(JFXTextField field){

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


    private void addTextfieldValidation(){
        ValidateInput.requiredFieldValidation(haulierField, "Select haulier", true,true);
        ValidateInput.requiredFieldValidation(poField, "Missing order number", true,true);
        ValidateInput.requiredFieldValidation(approxUnloadField, "Missing estimated time", true,true);
        ValidateInput.requiredFieldValidation(palletsField, "Missing estimated pallets", true,true);
        ValidateInput.requiredFieldValidation(poField, "Missing order number", true,true);

        ValidateInput.requiredFieldValidation(bays,"Please select Bay", true,true);
        ValidateInput.requiredFieldValidation(supplierBox,"Please select supplier", true, true);
    }


    //adds autocomplete functionality for textfield with data pulled from DB
    private <T> void hauliersField(Dao<T> dao, JFXTextField field){

        JFXAutoCompletePopup<T> autoCompletePopup = new JFXAutoCompletePopup<>();
        List<T> list = dao.getAll();
        autoCompletePopup.getSuggestions().addAll(list);

        autoCompletePopup.setSelectionHandler(event -> field.setText(event.getObject().toString()));

        // filtering options
        field.textProperty().addListener(observable -> {

            autoCompletePopup.filter(obj -> obj.toString().toUpperCase().contains(field.getText().toUpperCase()));
            if (autoCompletePopup.getFilteredSuggestions().isEmpty() || field.getText().isEmpty()) {
                autoCompletePopup.hide();
            }
            else {
                autoCompletePopup.show(field);
            }
        });
    }

    //TODO Below is double checked
    //Load order data to table view if PO field has order number
    private ObservableList<OrderMaterials> getOrderDetails(){

        ObservableList<OrderMaterials> materialList   = FXCollections.observableArrayList();
        String                      poNumber = poField.getText().isEmpty() ? "" : poField.getText();

        List<PoMaterials> list = new PoMaterialsDao().getAll(poNumber);

        list.forEach(poMaterials -> {
            OrderMaterials temp = new OrderMaterials();
            temp.setPoMaterials(poMaterials);
            temp.setMaterial(new MaterialsDao().get(poMaterials.getMCode()));
            materialList.add(temp);
        });

        return materialList;
    }

    public void displayForm(JFXTreeTableView<ScheduleEntry> table){

        FXMLLoader     loader = new FXMLLoader(getClass().getResource("/deliveryForm.fxml"));
        loader.setController(this);

        JFXAlert<String> alert = new JFXAlert<>((Stage) table.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setAnimation(JFXAlertAnimation.BOTTOM_ANIMATION);
        JFXDialogLayout layout = new JFXDialogLayout();

        try {
            StackPane pane = loader.load();
            pane.setMinSize(850, 700);
            layout.setBody(pane);
            alert.setContent(layout)    ;
            alert.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}