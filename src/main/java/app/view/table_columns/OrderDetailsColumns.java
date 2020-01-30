package app.view.table_columns;

import app.model.OrderDetails;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.scene.control.TreeTableColumn;

public class OrderDetailsColumns {

    final JFXTreeTableView<OrderDetails> orderDetailsTable;

    public OrderDetailsColumns(JFXTreeTableView<OrderDetails> orderDetailsTable) {
        this.orderDetailsTable = orderDetailsTable;
    }

    public JFXTreeTableColumn<OrderDetails, String> mCodeCol(){

        JFXTreeTableColumn<OrderDetails, String> mCodeColumn = new JFXTreeTableColumn<>("M Code");
        mCodeColumn.prefWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.2));
        mCodeColumn.minWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.2));
        mCodeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<OrderDetails, String> param) ->{
            if (mCodeColumn.validateValue(param)) {
                return param.getValue().getValue().mCodeProperty();
            }
            else return mCodeColumn.getComputedValue(param);
        });

        return mCodeColumn;
    }

    public JFXTreeTableColumn<OrderDetails, String> descCol(){

        JFXTreeTableColumn<OrderDetails, String> descColumn = new JFXTreeTableColumn<>("Description");
        descColumn.prefWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.4));
        descColumn.minWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.39));
        descColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<OrderDetails, String> param) ->{
            if (descColumn.validateValue(param)) {
                return param.getValue().getValue().descriptionProperty();
            }
            else return descColumn.getComputedValue(param);
        });
        return descColumn;
    }

    public JFXTreeTableColumn<OrderDetails, String> expectedCol(){

        JFXTreeTableColumn<OrderDetails, String> expectedColumn = new JFXTreeTableColumn<>("Expected");
        expectedColumn.prefWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.2));
        expectedColumn.minWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.2));
        expectedColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<OrderDetails, String> param) ->{
            if (expectedColumn.validateValue(param)) {
                return param.getValue().getValue().expectedProperty();
            }
            else return expectedColumn.getComputedValue(param);
        });
        return expectedColumn;
    }

    public JFXTreeTableColumn<OrderDetails, String> bookedCol(){


                JFXTreeTableColumn<OrderDetails, String> bookedColumn = new JFXTreeTableColumn<>("Booked");
                bookedColumn.prefWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.19));
                bookedColumn.minWidthProperty().bind(orderDetailsTable.widthProperty().multiply(0.19));
                bookedColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<OrderDetails, String> param) ->{
                    if (bookedColumn.validateValue(param)) {
                        return param.getValue().getValue().bookedInProperty();
                    }
                    else return bookedColumn.getComputedValue(param);
                });
        return bookedColumn;
    }



}
