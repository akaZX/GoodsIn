package app.model;


import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeTableColumn;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public  class PoTableColumns {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


    private final double dateColConstraints = 0.09;



    private  JFXTreeTableView<PurchaseOrder> table;

    public PoTableColumns(JFXTreeTableView<PurchaseOrder> table) {

        this.table = table;
    }

    public  JFXTreeTableColumn<PurchaseOrder, Integer> idCol(){

        JFXTreeTableColumn<PurchaseOrder, Integer> idCol = new JFXTreeTableColumn<>();
        setColumnTooltip(idCol, "ID", "ID used in DB");
        idCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, Integer> param) ->{
            if(idCol.validateValue(param)) {
                int id = param.getValue().getValue().getId();
                return new SimpleIntegerProperty(id).asObject();
            } else return idCol.getComputedValue(param);
        });

        columnSizeConstraints(idCol, 0.03, 0.03);
        return idCol;
    }

    public  JFXTreeTableColumn<PurchaseOrder, String> supplierCol(){

        JFXTreeTableColumn<PurchaseOrder, String> supplierCol = new JFXTreeTableColumn<>();
        setColumnTooltip(supplierCol, "Supplier","Supplier name");
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

        JFXTreeTableColumn<PurchaseOrder, String> poCol = new JFXTreeTableColumn<>("PO Number");
        poCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, String> param) ->{
            if(poCol.validateValue(param)) {
                String poNumber = param.getValue().getValue().getOrderNumber();
                return new SimpleStringProperty(poNumber);
            } else return poCol.getComputedValue(param);
        });
        columnSizeConstraints(poCol, 0.08, 0.08);

        return poCol;
    }

    public  JFXTreeTableColumn<PurchaseOrder, String> haulierCol(){

        JFXTreeTableColumn<PurchaseOrder, String> haulierCol = new JFXTreeTableColumn<>("Haulier");
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

        JFXTreeTableColumn<PurchaseOrder, String> bayCol = new JFXTreeTableColumn<>("Bay");
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

        JFXTreeTableColumn<PurchaseOrder, Integer> palletsCol = new JFXTreeTableColumn<>("Pallets");
        palletsCol.setCellValueFactory(cellData -> cellData.getValue().getValue().getPallets());
        formatIntColumns(palletsCol);

        columnSizeConstraints(palletsCol, 0.055, 0.055);

        return palletsCol;
    }

    public  JFXTreeTableColumn<PurchaseOrder, Integer> unloadingTimeCol(){

        JFXTreeTableColumn<PurchaseOrder, Integer> unloadingTimeCol = new JFXTreeTableColumn<>();
        setColumnTooltip(unloadingTimeCol, "Unloading", "Expected unloading time");
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


        JFXTreeTableColumn<PurchaseOrder, LocalDateTime> bookedInCol = new JFXTreeTableColumn<>("Booked In");
        bookedInCol.setCellValueFactory(cellData -> cellData.getValue().getValue().getBooked());

        columnSizeConstraints(bookedInCol, dateColConstraints,dateColConstraints);
        formatDateCells(bookedInCol);

        return bookedInCol;

    }

    // removes 'T' from LocalDateTime so it can be easily read
    private void formatDateCells(JFXTreeTableColumn<PurchaseOrder, LocalDateTime> column){

        column.setCellFactory(col -> new JFXTreeTableCell<PurchaseOrder, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {

                super.updateItem(item, empty);
                if (item == null)
                    setText(null);
                else
                    setText(item.format(formatter));
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
