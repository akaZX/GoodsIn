package app.view;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;


public class SupplierDetailsPane extends StackPane {

    private GridPane gridPane = new GridPane();

    private Label supplierName = new Label("Supplier Name");
    private Label supplierCode = new Label("Supplier Code");


    public SupplierDetailsPane() {

    }

    public SupplierDetailsPane(Node... children) {
        super(children);
        gridPane.add(supplierCode,0,1);
        gridPane.add(supplierName,0,2);
        this.getChildren().addAll(gridPane);
    }



}
