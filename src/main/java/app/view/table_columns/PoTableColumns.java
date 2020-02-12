package app.view.table_columns;


import app.model.PoScheduleEntry;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public  class PoTableColumns {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");


    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final double dateColConstraints = 0.09;

    private final JFXTreeTableView<PoScheduleEntry> table;

    public PoTableColumns(JFXTreeTableView<PoScheduleEntry> table) {

        this.table = table;
    }

    public  JFXTreeTableColumn<PoScheduleEntry, String> supplierCol(){

        JFXTreeTableColumn<PoScheduleEntry, String> supplierCol = new JFXTreeTableColumn<>();
        setColumnTooltip(supplierCol, "SUPPLIER","Supplier name");
        supplierCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PoScheduleEntry, String> param) ->{
            if(supplierCol.validateValue(param)) {
                String supplier = param.getValue().getValue().getSupplier();
                return new SimpleStringProperty(supplier);
            } else return supplierCol.getComputedValue(param);
        });
        columnSizeConstraints(supplierCol, 0.2, 0.15);


        return supplierCol;

    }

    public  JFXTreeTableColumn<PoScheduleEntry, String> poCol(){

        JFXTreeTableColumn<PoScheduleEntry, String> poCol = new JFXTreeTableColumn<>();
        setColumnTooltip(poCol, "PO NUMBER","Purchase order number");
        poCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PoScheduleEntry, String> param) ->{
            if(poCol.validateValue(param)) {
                String poNumber = param.getValue().getValue().getPo();
                return new SimpleStringProperty(poNumber);
            } else return poCol.getComputedValue(param);
        });
        columnSizeConstraints(poCol, 0.03, 0.08);

        return poCol;
    }

    public  JFXTreeTableColumn<PoScheduleEntry, String> haulierCol(){

        JFXTreeTableColumn<PoScheduleEntry, String> haulierCol = new JFXTreeTableColumn<>();
        setColumnTooltip(haulierCol,"HAULIER" , "Haulage company delivering materials for order");
        haulierCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PoScheduleEntry, String> param) ->{
            if(haulierCol.validateValue(param)) {
                String haulier = param.getValue().getValue().getScheduleDetails().getHaulier();
                return new SimpleStringProperty(haulier);
            } else return haulierCol.getComputedValue(param);
        });
        columnSizeConstraints(haulierCol, 0.1, 0.09);

        return haulierCol;
    }

    public  JFXTreeTableColumn<PoScheduleEntry, String> bayCol(){

        JFXTreeTableColumn<PoScheduleEntry, String> bayCol = new JFXTreeTableColumn<>();
        setColumnTooltip(bayCol, "BAY", "Assigned bay for delivery");
        bayCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PoScheduleEntry, String> param) ->{
            if(bayCol.validateValue(param)) {
                String bay = param.getValue().getValue().getScheduleDetails().getBay();
                return new SimpleStringProperty(bay);
            } else return bayCol.getComputedValue(param);
        });
        columnSizeConstraints(bayCol, 0.05, 0.05);

        return bayCol;
    }

    public  JFXTreeTableColumn<PoScheduleEntry, Integer> palletsCol(){

        JFXTreeTableColumn<PoScheduleEntry, Integer> palletsCol = new JFXTreeTableColumn<>();
        setColumnTooltip(palletsCol, "PALLETS", "Pallets expected to arrive");
        palletsCol.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getValue().getScheduleDetails().getPallets()).asObject());
        formatIntColumns(palletsCol);

        columnSizeConstraints(palletsCol, 0.055, 0.055);

        return palletsCol;
    }

    public  JFXTreeTableColumn<PoScheduleEntry, Integer> unloadingTimeCol(){

        JFXTreeTableColumn<PoScheduleEntry, Integer> unloadingTimeCol = new JFXTreeTableColumn<>();
        setColumnTooltip(unloadingTimeCol, "UNLOADING \nTIME", "Expected time for lorry to be unloaded");
        unloadingTimeCol.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getValue().getScheduleDetails().getDuration()).asObject());
        formatIntColumns(unloadingTimeCol);
        columnSizeConstraints(unloadingTimeCol, 0.06, 0.06);


        return unloadingTimeCol;
    }

    public  JFXTreeTableColumn<PoScheduleEntry, LocalDateTime> expectedETACol(){

        JFXTreeTableColumn<PoScheduleEntry, LocalDateTime> expectedETACol = new JFXTreeTableColumn<>("Expected");
        expectedETACol.setCellValueFactory(cellData -> cellData.getValue().getValue().getScheduleDetails().getEtaProperty());

        columnSizeConstraints(expectedETACol, dateColConstraints,dateColConstraints);
        formatDateCells(expectedETACol);

        return expectedETACol;

    }

    public  JFXTreeTableColumn<PoScheduleEntry, LocalDateTime> arrivedCol(){


        JFXTreeTableColumn<PoScheduleEntry, LocalDateTime> arrivedCol = new JFXTreeTableColumn<>("Arrived");
        arrivedCol.setCellValueFactory(cellData -> cellData.getValue().getValue().getScheduleDetails().getArrivedProperty());

        columnSizeConstraints(arrivedCol, dateColConstraints,dateColConstraints);
        formatDateCells(arrivedCol);

        return arrivedCol;

    }

    public  JFXTreeTableColumn<PoScheduleEntry, LocalDateTime> departedCol(){

        JFXTreeTableColumn<PoScheduleEntry, LocalDateTime> departedCol = new JFXTreeTableColumn<>("Departed");
        departedCol.setCellValueFactory(cellData -> cellData.getValue().getValue().getScheduleDetails().getDepartedPoperty());

        columnSizeConstraints(departedCol, dateColConstraints,dateColConstraints);
        formatDateCells(departedCol);

        return departedCol;

    }

    public  JFXTreeTableColumn<PoScheduleEntry, LocalDateTime> bookedInCol(){


        JFXTreeTableColumn<PoScheduleEntry, LocalDateTime> bookedInCol = new JFXTreeTableColumn<>();
        setColumnTooltip(bookedInCol, "BOOKED IN", "Time when raw materials delivered was booked in");
        bookedInCol.setCellValueFactory(cellData -> cellData.getValue().getValue().getScheduleDetails().getBookedinProperty());


        columnSizeConstraints(bookedInCol, dateColConstraints,dateColConstraints);
        formatDateCells(bookedInCol);

        return bookedInCol;

    }

    public  JFXTreeTableColumn<PoScheduleEntry, String> commentsCol(){

        JFXTreeTableColumn<PoScheduleEntry, String> commentsCol = new JFXTreeTableColumn<>();
        setColumnTooltip(commentsCol, "COMMENTS","Additional information");
        commentsCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PoScheduleEntry, String> param) ->{
            if(commentsCol.validateValue(param)) {
                String comments = param.getValue().getValue().getScheduleDetails().getComments();
                return new SimpleStringProperty(comments);
            } else return commentsCol.getComputedValue(param);
        });
        columnSizeConstraints(commentsCol, 0.08, 0.07);

        return commentsCol;
    }

    public  JFXTreeTableColumn<PoScheduleEntry, String> registrationCol(){

        JFXTreeTableColumn<PoScheduleEntry, String> regCol = new JFXTreeTableColumn<>();
        setColumnTooltip(regCol, "TRAILER/CONTAINER \nNUMBER","Trailer registration or container number");
        regCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PoScheduleEntry, String> param) ->{
            if(regCol.validateValue(param)) {
                String trailerNo = param.getValue().getValue().getScheduleDetails().getRegistrationNo();
                return new SimpleStringProperty(trailerNo);
            } else return regCol.getComputedValue(param);
        });
        columnSizeConstraints(regCol, 0.08, 0.08);

        return regCol;
    }


    // removes 'T' from LocalDateTime
    private void formatDateCells(JFXTreeTableColumn<PoScheduleEntry, LocalDateTime> column){

        column.setCellFactory(col -> new JFXTreeTableCell<PoScheduleEntry, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {

                super.updateItem(item, empty);
                if (item == null)
                    setText(null);
                else
                    setText(item.format(dateTimeFormatter));
            }
        });
    }

    //removes 0 values from column
    private void formatIntColumns(JFXTreeTableColumn<PoScheduleEntry, Integer> column){
        column.setCellFactory(col -> new JFXTreeTableCell<PoScheduleEntry, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {

                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else {
                    if (item <= 0) {
                        setText(null);
                    }else{
                        setText(item.toString());

                    }

                }
            }
        });
    }

    //adds width constraints
    private void columnSizeConstraints(JFXTreeTableColumn<PoScheduleEntry, ?> column, double pref, double min){

        column.prefWidthProperty().bind(table.widthProperty().multiply(pref));
        column.minWidthProperty().bind(table.widthProperty().multiply(min));

    }

    // adds label and tooltip message for provided column
    private void setColumnTooltip(JFXTreeTableColumn<PoScheduleEntry, ?> column, String columnTitle, String tooltipMsg){

        Label columnLabel = new Label(columnTitle);
        columnLabel.setTooltip(new Tooltip(tooltipMsg));
        column.setGraphic(columnLabel);
    }

}
