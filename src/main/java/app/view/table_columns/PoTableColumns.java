package app.view.table_columns;


import app.model.ScheduleEntry;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public  class PoTableColumns {

//    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");


    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final double dateColConstraints = 0.08;

    private final JFXTreeTableView<ScheduleEntry> table;

    public PoTableColumns(JFXTreeTableView<ScheduleEntry> table) {

        this.table = table;
    }


    public  JFXTreeTableColumn<ScheduleEntry, String> supplierCol(){

        JFXTreeTableColumn<ScheduleEntry, String> supplierCol = new JFXTreeTableColumn<>();
        setColumnTooltip(supplierCol, "SUPPLIER","Supplier name");
        supplierCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<ScheduleEntry, String> param) ->{
            if(supplierCol.validateValue(param)) {
                String supplier = param.getValue().getValue().getSupplier().getSupplierName();
                return new SimpleStringProperty(supplier);
            } else return supplierCol.getComputedValue(param);
        });
        columnConstraints(supplierCol, 0.17, 0.15);

        return supplierCol;
    }

    public  JFXTreeTableColumn<ScheduleEntry, String> poCol(){

        JFXTreeTableColumn<ScheduleEntry, String> poCol = new JFXTreeTableColumn<>();
        setColumnTooltip(poCol, "ORDER \nNUMBER","Purchase order number");
        poCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<ScheduleEntry, String> param) ->{
            if(poCol.validateValue(param)) {
                String poNumber = param.getValue().getValue().getOrder().getPoNumber();
                return new SimpleStringProperty(poNumber);
            } else return poCol.getComputedValue(param);
        });
        columnConstraints(poCol, 0.07, 0.07);

        return poCol;
    }

    public  JFXTreeTableColumn<ScheduleEntry, String> haulierCol(){

        JFXTreeTableColumn<ScheduleEntry, String> haulierCol = new JFXTreeTableColumn<>();
        setColumnTooltip(haulierCol,"HAULIER" , "Haulage company delivering materials for order");
        haulierCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<ScheduleEntry, String> param) ->{
            if(haulierCol.validateValue(param)) {
                String haulier = param.getValue().getValue().getDetails().getHaulier();
                return new SimpleStringProperty(haulier);
            } else return haulierCol.getComputedValue(param);
        });
        columnConstraints(haulierCol, 0.07, 0.07);

        return haulierCol;
    }

    public  JFXTreeTableColumn<ScheduleEntry, String> bayCol(){

        JFXTreeTableColumn<ScheduleEntry, String> bayCol = new JFXTreeTableColumn<>();
        setColumnTooltip(bayCol, "BAY", "Assigned bay for delivery");
        bayCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<ScheduleEntry, String> param) ->{
            if(bayCol.validateValue(param)) {
                String bay = param.getValue().getValue().getDetails().getBay();
                return new SimpleStringProperty(bay);
            } else return bayCol.getComputedValue(param);
        });
        columnConstraints(bayCol, 0.05, 0.05);

        return bayCol;
    }

    public  JFXTreeTableColumn<ScheduleEntry, Integer> palletsCol(){

        JFXTreeTableColumn<ScheduleEntry, Integer> palletsCol = new JFXTreeTableColumn<>();
        setColumnTooltip(palletsCol, "PALLETS", "Expected pallets");
        palletsCol.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getValue().getDetails().getPallets()).asObject());
        formatIntColumns(palletsCol);

        columnConstraints(palletsCol, 0.05, 0.05);

        return palletsCol;
    }

    public  JFXTreeTableColumn<ScheduleEntry, Integer> unloadingTimeCol(){

        JFXTreeTableColumn<ScheduleEntry, Integer> unloadingTimeCol = new JFXTreeTableColumn<>();
        setColumnTooltip(unloadingTimeCol, "UNLOAD \nTIME", "Expected time for lorry to be unloaded");
        unloadingTimeCol.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getValue().getDetails().getDuration()).asObject());
        formatIntColumns(unloadingTimeCol);
        columnConstraints(unloadingTimeCol, 0.06, 0.06);


        return unloadingTimeCol;
    }

    public  JFXTreeTableColumn<ScheduleEntry, LocalDateTime> expectedETACol(){

        JFXTreeTableColumn<ScheduleEntry, LocalDateTime> expectedETACol = new JFXTreeTableColumn<>("ETA");
        expectedETACol.setCellValueFactory(cellData -> cellData.getValue().getValue().getDetails().getEtaProperty());

        columnConstraints(expectedETACol, 0.08, 0.08);
        formatDateCells(expectedETACol);

        return expectedETACol;
    }

    public  JFXTreeTableColumn<ScheduleEntry, LocalDateTime> arrivedCol(){


        JFXTreeTableColumn<ScheduleEntry, LocalDateTime> arrivedCol = new JFXTreeTableColumn<>("ARRIVED");
        arrivedCol.setCellValueFactory(cellData -> cellData.getValue().getValue().getDetails().getArrivedProperty());

        columnConstraints(arrivedCol, dateColConstraints,dateColConstraints);
        formatDateCells(arrivedCol);

        return arrivedCol;

    }

    public  JFXTreeTableColumn<ScheduleEntry, LocalDateTime> departedCol(){

        JFXTreeTableColumn<ScheduleEntry, LocalDateTime> departedCol = new JFXTreeTableColumn<>("DEPARTED");
        departedCol.setCellValueFactory(cellData -> cellData.getValue().getValue().getDetails().getDepartedPoperty());

        columnConstraints(departedCol, dateColConstraints,dateColConstraints);
        formatDateCells(departedCol);

        return departedCol;

    }

    public  JFXTreeTableColumn<ScheduleEntry, LocalDateTime> bookedInCol(){


        JFXTreeTableColumn<ScheduleEntry, LocalDateTime> bookedInCol = new JFXTreeTableColumn<>();
        setColumnTooltip(bookedInCol, "BOOKED IN", "Time when raw materials delivered was booked in");
        bookedInCol.setCellValueFactory(cellData -> cellData.getValue().getValue().getDetails().getBookedinProperty());


        columnConstraints(bookedInCol, dateColConstraints,dateColConstraints);
        formatDateCells(bookedInCol);

        return bookedInCol;

    }

    public  JFXTreeTableColumn<ScheduleEntry, String> commentsCol(){

        JFXTreeTableColumn<ScheduleEntry, String> commentsCol = new JFXTreeTableColumn<>();
        setColumnTooltip(commentsCol, "COMMENTS","Additional information");
        commentsCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<ScheduleEntry, String> param) ->{
            if(commentsCol.validateValue(param)) {
                String comments = param.getValue().getValue().getDetails().getComments();
                return new SimpleStringProperty(comments);
            } else return commentsCol.getComputedValue(param);
        });
        columnConstraints(commentsCol, 0.1,0.1);

        return commentsCol;
    }

    public  JFXTreeTableColumn<ScheduleEntry, String> registrationCol(){

        JFXTreeTableColumn<ScheduleEntry, String> regCol = new JFXTreeTableColumn<>();
        setColumnTooltip(regCol, "REGISTRATION \nNUMBER","Trailer registration or container number");
        regCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<ScheduleEntry, String> param) ->{
            if(regCol.validateValue(param)) {
                String trailerNo = param.getValue().getValue().getDetails().getRegistrationNo();
                return new SimpleStringProperty(trailerNo);
            } else return regCol.getComputedValue(param);
        });
        columnConstraints(regCol, 0.08, 0.08);

        return regCol;
    }



    // removes 'T' from LocalDateTime
    private void formatDateCells(JFXTreeTableColumn<ScheduleEntry, LocalDateTime> column){

        column.setCellFactory(this::call);
    }

    //removes 0 values from column
    private void formatIntColumns(JFXTreeTableColumn<ScheduleEntry, Integer> column){
        column.setCellFactory(col -> new JFXTreeTableCell<ScheduleEntry, Integer>() {
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

    //adds column size constraints and removes group/ungroup pop up
    private void columnConstraints(JFXTreeTableColumn<ScheduleEntry, ?> column, double pref, double min){

        column.setContextMenu(null);

        column.prefWidthProperty().bind(table.widthProperty().multiply(pref));
        column.minWidthProperty().bind(table.widthProperty().multiply(min));

    }

    // adds label and tooltip message for provided column
    private void setColumnTooltip(JFXTreeTableColumn<ScheduleEntry, ?> column, String columnTitle, String tooltipMsg){

        Label columnLabel = new Label(columnTitle);
        columnLabel.setTooltip(new Tooltip(tooltipMsg));
        column.setGraphic(columnLabel);
    }


    private TreeTableCell<ScheduleEntry, LocalDateTime> call(TreeTableColumn<ScheduleEntry, LocalDateTime> col) {

        return new JFXTreeTableCell<ScheduleEntry, LocalDateTime>() {

            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {

                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                }
                else {
                    setText(item.format(formatter));
                }
            }
        };
    }
}
