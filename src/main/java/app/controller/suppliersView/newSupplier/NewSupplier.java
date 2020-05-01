package app.controller.suppliersView.newSupplier;

import app.controller.sql.dao.SuppliersDao;
import app.controller.suppliersView.SupplierDrawerController;
import app.controller.utils.LabelWithIcons;
import app.controller.utils.Messages;
import app.controller.utils.ValidateInput;
import app.pojos.Suppliers;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewSupplier {

    private static Messages msg = new Messages();


    public static void addNewSupplier(SupplierDrawerController supplierDrawer, Node node) {


        JFXAlert<String> alert = new JFXAlert<>((Stage) node.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Add new order"));


        Label supp   = new Label("Supplier name: ");
        HBox  spacer = new HBox();
        HBox.setHgrow(
                spacer,
                Priority.SOMETIMES
        );
        JFXTextField suppName = new JFXTextField();
        ValidateInput.requiredFieldValidation(suppName, "Missing supplier name");
        HBox supplierBox = new HBox();
//        supplierBox.setMinHeight(50);
        supplierBox.getChildren().addAll(supp, spacer, suppName);


        Label po = new Label("Supplier code");

        HBox spacer2 = new HBox();
        HBox.setHgrow(
                spacer2,
                Priority.SOMETIMES
        );
        JFXTextField suppCodeField = new JFXTextField();
        ValidateInput.requiredFieldValidation(suppCodeField, "Missing supplier code");

        HBox orderBox = new HBox();
        orderBox.getChildren().addAll(po, spacer2, suppCodeField);


        VBox box = new VBox();
        box.setSpacing(35);
        box.getChildren().addAll(supplierBox, orderBox);
        layout.setBody(box);
        JFXButton continueBtn = new JFXButton("SAVE");
        JFXButton cancel      = new JFXButton("CANCEL");

        continueBtn.setOnAction(e -> {

            boolean save = true;

            if (! suppCodeField.validate()) {
                save = false;
            }
            if (! suppName.validate()) {
                save = false;
            }

            if (save) {

                Suppliers suppliers = new Suppliers();
                suppliers.setSupplierCode(suppCodeField.getText().toUpperCase().trim().replaceAll("[^a-zA-Z0-9]", ""));
                suppliers.setSupplierName(suppName.getText().trim().replaceAll("[^a-zA-Z0-9]", ""));

                Suppliers found = new SuppliersDao().get(suppliers.getSupplierCode());

                if (found.getSupplierCode() != null) {
                    msg.continueAlert(layout, LabelWithIcons.largeWarningIconLabel("Supplier exists"), new Label(""));
                }
                else {
                    boolean saved = new SuppliersDao().save(suppliers);
                    if (saved) {
                        supplierDrawer.loadList();
                        alert.hideWithAnimation();
                        msg.continueAlert(node, LabelWithIcons.largeCheckIconLabel("Supplier saved"), new Label(""));
                    }
                    else {
                        msg.continueAlert(orderBox, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Unknown error"));
                    }
                }
            }
            else {
                msg.continueAlert(orderBox, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Fields left blank"));
            }

        });

        cancel.setOnAction(b -> {
            alert.hideWithAnimation();
        });
        layout.setActions(continueBtn, cancel);
        alert.setContent(layout);
        alert.show();

    }

}
