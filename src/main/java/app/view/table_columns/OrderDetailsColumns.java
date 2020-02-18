package app.view.table_columns;

import app.model.OrderDetails;
import app.pojos.PoMaterials;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TreeTableColumn;

public class OrderDetailsColumns {

    final JFXTreeTableView<PoMaterials> orderDetailsTable;

    public OrderDetailsColumns(JFXTreeTableView<PoMaterials> orderDetailsTable) {
        this.orderDetailsTable = orderDetailsTable;
    }

    public JFXTreeTableColumn<PoMaterials, String> mCodeCol(){

        JFXTreeTableColumn<PoMaterials, String> mCodeColumn = new JFXTreeTableColumn<>("M Code");
        mCodeColumn.prefWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.2));
        mCodeColumn.minWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.2));
        mCodeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<PoMaterials, String> param) ->{
            if (mCodeColumn.validateValue(param)) {
                String mCode = param.getValue().getValue().getMCode();
                return new SimpleStringProperty(mCode);
            }
            else return mCodeColumn.getComputedValue(param);
        });

        return mCodeColumn;
    }

    public JFXTreeTableColumn<PoMaterials, String> descCol(){

        JFXTreeTableColumn<PoMaterials, String> descColumn = new JFXTreeTableColumn<>("Description");
        descColumn.prefWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.4));
        descColumn.minWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.39));
        descColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<PoMaterials, String> param) ->{
            if (descColumn.validateValue(param)) {
                String s = param.getValue().getValue().getPoNumber();
                return new SimpleStringProperty(s);
            }
            else return descColumn.getComputedValue(param);
        });
        return descColumn;
    }

    public JFXTreeTableColumn<PoMaterials, String> expectedCol(){

        JFXTreeTableColumn<PoMaterials, String> expectedColumn = new JFXTreeTableColumn<>("Expected");
        expectedColumn.prefWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.2));
        expectedColumn.minWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.2));
        expectedColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<PoMaterials, String> param) ->{
            if (expectedColumn.validateValue(param)) {
                String expectedQuantity = String.valueOf(param.getValue().getValue().getExpectedQuantity());
                return new SimpleStringProperty(expectedQuantity);
            }
            else return expectedColumn.getComputedValue(param);
        });
        return expectedColumn;
    }

    public JFXTreeTableColumn<PoMaterials, String> bookedCol(){


                JFXTreeTableColumn<PoMaterials, String> bookedColumn = new JFXTreeTableColumn<>("Booked");
                bookedColumn.prefWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.19));
                bookedColumn.minWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.19));
                bookedColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<PoMaterials, String> param) ->{
                    if (bookedColumn.validateValue(param)) {
                        String expectedQuantity = String.valueOf(param.getValue().getValue().getArrivedQuantity());
                        return new SimpleStringProperty(expectedQuantity);
                    }
                    else return bookedColumn.getComputedValue(param);
                });
        return bookedColumn;
    }



}
