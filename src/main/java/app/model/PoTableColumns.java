package app.model;


import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import java.sql.Timestamp;


public  class PoTableColumns {


    private  JFXTreeTableView<PurchaseOrder> table;

    public PoTableColumns(JFXTreeTableView<PurchaseOrder> table) {

        this.table = table;
    }

    public  JFXTreeTableColumn<PurchaseOrder, Integer> idCol(){

        JFXTreeTableColumn<PurchaseOrder, Integer> idCol = new JFXTreeTableColumn<>("ID");
        idCol.setStyle("-fx-alignment: CENTER_RIGHT;");
        idCol.prefWidthProperty().bind(table.widthProperty().multiply(0.03));
        idCol.minWidthProperty().bind(table.widthProperty().multiply(0.03));
        idCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, Integer> param) ->{
            if(idCol.validateValue(param)) {
                int id = param.getValue().getValue().getId();
                return new SimpleIntegerProperty(id).asObject();
            } else return idCol.getComputedValue(param);
        });

        return idCol;
    }

    public  JFXTreeTableColumn<PurchaseOrder, String> supplierCol(){

        JFXTreeTableColumn<PurchaseOrder, String> supplierCol = new JFXTreeTableColumn<>("Supplier");
        supplierCol.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        supplierCol.minWidthProperty().bind(table.widthProperty().multiply(0.15));
        supplierCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, String> param) ->{
            if(supplierCol.validateValue(param)) {
                String supplier = param.getValue().getValue().getSupplierName();
                return new SimpleStringProperty(supplier);
            } else return supplierCol.getComputedValue(param);
        });

        return supplierCol;

    }

    public  JFXTreeTableColumn<PurchaseOrder, String> poCol(){

        JFXTreeTableColumn<PurchaseOrder, String> poCol = new JFXTreeTableColumn<>("PO Number");
        poCol.prefWidthProperty().bind(table.widthProperty().multiply(0.08));
        poCol.minWidthProperty().bind(table.widthProperty().multiply(0.08));
        poCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, String> param) ->{
            if(poCol.validateValue(param)) {
                String poNumber = param.getValue().getValue().getOrderNumber();
                return new SimpleStringProperty(poNumber);
            } else return poCol.getComputedValue(param);
        });

        return poCol;
    }

    public  JFXTreeTableColumn<PurchaseOrder, String> haulierCol(){

        JFXTreeTableColumn<PurchaseOrder, String> haulierCol = new JFXTreeTableColumn<>("Haulier");
        haulierCol.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
        haulierCol.minWidthProperty().bind(table.widthProperty().multiply(0.09));
        haulierCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, String> param) ->{
            if(haulierCol.validateValue(param)) {
                String haulier = param.getValue().getValue().getHaulier();
                return new SimpleStringProperty(haulier);
            } else return haulierCol.getComputedValue(param);
        });

        return haulierCol;
    }

    public  JFXTreeTableColumn<PurchaseOrder, Integer> palletsCol(){

        JFXTreeTableColumn<PurchaseOrder, Integer> palletsCol = new JFXTreeTableColumn<>("Pallets");
        palletsCol.prefWidthProperty().bind(table.widthProperty().multiply(0.055));
        palletsCol.minWidthProperty().bind(table.widthProperty().multiply(0.055));
        palletsCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, Integer> param) ->{
            if(palletsCol.validateValue(param)) {
                int pallets = param.getValue().getValue().getPallets();
                return new SimpleIntegerProperty(pallets).asObject();
            } else return palletsCol.getComputedValue(param);
        });

        return palletsCol;
    }

    public  JFXTreeTableColumn<PurchaseOrder, Integer> unloadinTimeCol(){

        JFXTreeTableColumn<PurchaseOrder, Integer> unloadingTimeCol = new JFXTreeTableColumn<>("Unloading Time");
        unloadingTimeCol.prefWidthProperty().bind(table.widthProperty().multiply(0.055));
        unloadingTimeCol.minWidthProperty().bind(table.widthProperty().multiply(0.055));
        unloadingTimeCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<PurchaseOrder, Integer> param) ->{
            if(unloadingTimeCol.validateValue(param)) {
                int pallets = param.getValue().getValue().getUnloadingTime();
                return new SimpleIntegerProperty(pallets).asObject();
            } else return unloadingTimeCol.getComputedValue(param);
        });

        return unloadingTimeCol;
    }

    public  TreeTableColumn<PurchaseOrder, Timestamp> expectedETACol(){

        TreeTableColumn<PurchaseOrder, Timestamp> expectedETACol = new TreeTableColumn<>("Expected");
        expectedETACol.prefWidthProperty().bind(table.widthProperty().multiply(0.07));
        expectedETACol.minWidthProperty().bind(table.widthProperty().multiply(0.07));
        expectedETACol.setCellValueFactory(new TreeItemPropertyValueFactory<>("expectedEta"));

        return expectedETACol;

    }


}
