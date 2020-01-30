package app.view.table_columns;


import app.model.PurchaseOrder;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeTableColumn;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public  class PoTableColumns {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final double dateColConstraints = 0.09;

    private final JFXTreeTableView<PurchaseOrder> table;

    public PoTableColumns(JFXTreeTableView<PurchaseOrder> table) {

        this.table = table;
    }

    public  JFXTreeTableColumn<PurchaseOrder, String> supplierCol(){

        JFXTreeTableColumn<PurchaseOrder, String> supplierCol = new JFXTreeTableColumn<>();
        setColumnTooltip(supplierCol, "SUPPLIER","Supplier name");
        supplierCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, String> param) ->{
            if(supplierCol.validateValue(param)) {
                String supplier = param.getValue().getValue().getSupplierName();
                return new SimpleStringProperty(supplier);
            } else return supplierCol.getComputedValue(param);
        });
        columnSizeConstraints(supplierCol, 0.2, 0.15);


        return supplierCol;

    }

    public  JFXTreeTableColumn<PurchaseOrder, String> poCol(){

        JFXTreeTableColumn<PurchaseOrder, String> poCol = new JFXTreeTableColumn<>();
        setColumnTooltip(poCol, "PO NUMBER","Purchase order number");
        poCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, String> param) ->{
            if(poCol.validateValue(param)) {
                String poNumber = param.getValue().getValue().getOrderNumber();
                return new SimpleStringProperty(poNumber);
            } else return poCol.getComputedValue(param);
        });
        columnSizeConstraints(poCol, 0.03, 0.08);

        return poCol;
    }

    public  JFXTreeTableColumn<PurchaseOrder, String> haulierCol(){

        JFXTreeTableColumn<PurchaseOrder, String> haulierCol = new JFXTreeTableColumn<>();
        setColumnTooltip(haulierCol,"HAULIER" , "Haulage company delivering materials for order");
        haulierCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, String> param) ->{
            if(haulierCol.validateValue(param)) {
                String haulier = param.getValue().getValue().getHaulier();
                return new SimpleStringProperty(haulier);
            } else return haulierCol.getComputedValue(param);
        });
        columnSizeConstraints(haulierCol, 0.1, 0.09);

        return haulierCol;
    }

    public  JFXTreeTableColumn<PurchaseOrder, String> bayCol(){

        JFXTreeTableColumn<PurchaseOrder, String> bayCol = new JFXTreeTableColumn<>();
        setColumnTooltip(bayCol, "BAY", "Assigned bay for delivery");
        bayCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, String> param) ->{
            if(bayCol.validateValue(param)) {
                String bay = param.getValue().getValue().getBay();
                return new SimpleStringProperty(bay);
            } else return bayCol.getComputedValue(param);
        });
        columnSizeConstraints(bayCol, 0.05, 0.05);

        return bayCol;
    }

    public  JFXTreeTableColumn<PurchaseOrder, Integer> palletsCol(){

        JFXTreeTableColumn<PurchaseOrder, Integer> palletsCol = new JFXTreeTableColumn<>();
        setColumnTooltip(palletsCol, "PALLETS", "Pallets expected to arrive");
        palletsCol.setCellValueFactory(cellData -> cellData.getValue().getValue().getPallets());
        formatIntColumns(palletsCol);

        columnSizeConstraints(palletsCol, 0.055, 0.055);

        return palletsCol;
    }

    public  JFXTreeTableColumn<PurchaseOrder, Integer> unloadingTimeCol(){

        JFXTreeTableColumn<PurchaseOrder, Integer> unloadingTimeCol = new JFXTreeTableColumn<>();
        setColumnTooltip(unloadingTimeCol, "UNLOADING \nTIME", "Expected time for lorry to be unloaded");
        unloadingTimeCol.setCellValueFactory(cellData -> cellData.getValue().getValue().getUnloadingTime());
        formatIntColumns(unloadingTimeCol);
        columnSizeConstraints(unloadingTimeCol, 0.06, 0.06);


        return unloadingTimeCol;
    }

    public  JFXTreeTableColumn<PurchaseOrder, LocalDateTime> expectedETACol(){


        JFXTreeTableColumn<PurchaseOrder, LocalDateTime> expectedETACol = new JFXTreeTableColumn<>("Expected");
        expectedETACol.setCellValueFactory(cellData -> cellData.getValue().getValue().getExpectedEta());
        columnSizeConstraints(expectedETACol, dateColConstraints,dateColConstraints);
        formatDateCells(expectedETACol);

        return expectedETACol;

    }

    public  JFXTreeTableColumn<PurchaseOrder, LocalDateTime> arrivedCol(){


        JFXTreeTableColumn<PurchaseOrder, LocalDateTime> arrivedCol = new JFXTreeTableColumn<>("Arrived");
        arrivedCol.setCellValueFactory(cellData -> cellData.getValue().getValue().getArrived());

        columnSizeConstraints(arrivedCol, dateColConstraints,dateColConstraints);
        formatDateCells(arrivedCol);

        return arrivedCol;

    }

    public  JFXTreeTableColumn<PurchaseOrder, LocalDateTime> departedCol(){

        JFXTreeTableColumn<PurchaseOrder, LocalDateTime> departedCol = new JFXTreeTableColumn<>("Departed");
        departedCol.setCellValueFactory(cellData -> cellData.getValue().getValue().getDeparted());

        columnSizeConstraints(departedCol, dateColConstraints,dateColConstraints);
        formatDateCells(departedCol);

        return departedCol;

    }

    public  JFXTreeTableColumn<PurchaseOrder, LocalDateTime> bookedInCol(){


        JFXTreeTableColumn<PurchaseOrder, LocalDateTime> bookedInCol = new JFXTreeTableColumn<>();
        setColumnTooltip(bookedInCol, "BOOKED IN", "Time when raw materials delivered was booked in");
        bookedInCol.setCellValueFactory(cellData -> cellData.getValue().getValue().getBooked());

        columnSizeConstraints(bookedInCol, dateColConstraints,dateColConstraints);
        formatDateCells(bookedInCol);

        return bookedInCol;

    }

    public  JFXTreeTableColumn<PurchaseOrder, String> commentsCol(){

        JFXTreeTableColumn<PurchaseOrder, String> commentsCol = new JFXTreeTableColumn<>();
        setColumnTooltip(commentsCol, "COMMENTS","Additional information");
        commentsCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, String> param) ->{
            if(commentsCol.validateValue(param)) {
                String comments = param.getValue().getValue().getComments();
                return new SimpleStringProperty(comments);
            } else return commentsCol.getComputedValue(param);
        });
        columnSizeConstraints(commentsCol, 0.08, 0.07);

        return commentsCol;
    }

    public  JFXTreeTableColumn<PurchaseOrder, String> registrationCol(){

        JFXTreeTableColumn<PurchaseOrder, String> regCol = new JFXTreeTableColumn<>();
        setColumnTooltip(regCol, "TRAILER/CONTAINER \nNUMBER","Trailer registration or container number");
        regCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, String> param) ->{
            if(regCol.validateValue(param)) {
                String trailerNo = param.getValue().getValue().getTrailerNo();
                return new SimpleStringProperty(trailerNo);
            } else return regCol.getComputedValue(param);
        });
        columnSizeConstraints(regCol, 0.08, 0.08);

        return regCol;
    }


    // removes 'T' from LocalDateTime
    private void formatDateCells(JFXTreeTableColumn<PurchaseOrder, LocalDateTime> column){

        column.setCellFactory(col -> new JFXTreeTableCell<PurchaseOrder, LocalDateTime>() {
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
    private void formatIntColumns(JFXTreeTableColumn<PurchaseOrder, Integer> column){
        column.setCellFactory(col -> new JFXTreeTableCell<PurchaseOrder, Integer>() {
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
    private void columnSizeConstraints(JFXTreeTableColumn<PurchaseOrder, ?> column, double pref, double min){

        column.prefWidthProperty().bind(table.widthProperty().multiply(pref));
        column.minWidthProperty().bind(table.widthProperty().multiply(min));

    }

    // adds label and tooltip message for provided column
    private void setColumnTooltip(JFXTreeTableColumn<PurchaseOrder, ?> column, String columnTitle, String tooltipMsg){

        Label columnLabel = new Label(columnTitle);
        columnLabel.setTooltip(new Tooltip(tooltipMsg));
        column.setGraphic(columnLabel);
    }

}
