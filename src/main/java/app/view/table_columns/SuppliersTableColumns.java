package app.view.table_columns;


import app.pojos.Suppliers;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeTableColumn;

public class SuppliersTableColumns {


    public static JFXTreeTableColumn<Suppliers, String> getNameCol(JFXTreeTableView<Suppliers> table) {

        JFXTreeTableColumn<Suppliers, String> nameCol = new JFXTreeTableColumn<>();

        Label columnLabel = new Label("Supplier Name");
        columnLabel.setTooltip(new Tooltip("Supplier name"));
        nameCol.setGraphic(columnLabel);

        nameCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Suppliers, String> param) -> {
            if (nameCol.validateValue(param)) {
                return new SimpleStringProperty(param.getValue().getValue().getSupplierName());
            }
            else {
                return nameCol.getComputedValue(param);
            }
        });

        nameCol.prefWidthProperty().bind(table.widthProperty().multiply(0.95));
        nameCol.maxWidthProperty().bind(table.widthProperty().multiply(0.95));
        nameCol.minWidthProperty().bind(table.widthProperty().multiply(0.92));


        return nameCol;

    }


}
