package app.view.table_columns;

import app.model.OrderMaterials;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TreeTableColumn;

public class OrderDetailsColumns {

    final JFXTreeTableView<OrderMaterials> orderDetailsTable;


    public OrderDetailsColumns(JFXTreeTableView<OrderMaterials> orderDetailsTable) {

        this.orderDetailsTable = orderDetailsTable;
    }


    public JFXTreeTableColumn<OrderMaterials, String> mCodeCol() {

        JFXTreeTableColumn<OrderMaterials, String> mCodeColumn = new JFXTreeTableColumn<>("M Code");
        mCodeColumn.prefWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.2));
        mCodeColumn.minWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.2));
        mCodeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<OrderMaterials, String> param) -> {
            if (mCodeColumn.validateValue(param)) {
                String mCode = param.getValue().getValue().getPoMaterials().getMCode();
                return new SimpleStringProperty(mCode);
            }
            else {
                return mCodeColumn.getComputedValue(param);
            }
        });

        return mCodeColumn;
    }


    public JFXTreeTableColumn<OrderMaterials, String> descCol() {

        JFXTreeTableColumn<OrderMaterials, String> descColumn = new JFXTreeTableColumn<>("Description");
        descColumn.prefWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.4));
        descColumn.minWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.39));
        descColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<OrderMaterials, String> param) -> {
            if (descColumn.validateValue(param)) {
                String s = param.getValue().getValue().getMaterial().getName();
                return new SimpleStringProperty(s);
            }
            else {
                return descColumn.getComputedValue(param);
            }
        });
        return descColumn;
    }


    public JFXTreeTableColumn<OrderMaterials, String> expectedCol() {

        JFXTreeTableColumn<OrderMaterials, String> expectedColumn = new JFXTreeTableColumn<>("Expected");
        expectedColumn.prefWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.2));
        expectedColumn.minWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.2));
        expectedColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<OrderMaterials, String> param) -> {
            if (expectedColumn.validateValue(param)) {
                String expectedQuantity = String.valueOf(param.getValue().getValue().getPoMaterials().getExpectedQuantity());
                return new SimpleStringProperty(expectedQuantity);
            }
            else {
                return expectedColumn.getComputedValue(param);
            }
        });
        return expectedColumn;
    }


    public JFXTreeTableColumn<OrderMaterials, String> bookedCol() {


        JFXTreeTableColumn<OrderMaterials, String> bookedColumn = new JFXTreeTableColumn<>("Booked");
        bookedColumn.prefWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.19));
        bookedColumn.minWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.19));
        bookedColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<OrderMaterials, String> param) -> {
            if (bookedColumn.validateValue(param)) {
                String expectedQuantity = String.valueOf(param.getValue().getValue().getPoMaterials().getArrivedQuantity());
                return new SimpleStringProperty(expectedQuantity);
            }
            else {
                return bookedColumn.getComputedValue(param);
            }
        });
        return bookedColumn;
    }


}
