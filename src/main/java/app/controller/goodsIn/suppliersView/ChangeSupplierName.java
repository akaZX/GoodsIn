package app.controller.goodsIn.suppliersView;

import app.controller.sql.dao.SuppliersDao;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChangeSupplierName {

    private static final Messages msg = new Messages();


    public static void changeName(SuppliersProfileController supplierController, Node node, Suppliers supplier, SupplierDrawerController supplierDrawerController) {


        JFXAlert<String> alert = new JFXAlert<>((Stage) node.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Change supplier name"));


        Label supp   = new Label("New name: ");
        HBox  spacer = new HBox();
        HBox.setHgrow(
                spacer,
                Priority.SOMETIMES
        );
        JFXTextField suppName = new JFXTextField();
        ValidateInput.requiredFieldValidation(suppName, "Required field");
        HBox supplierBox = new HBox();
//        supplierBox.setMinHeight(50);
        supplierBox.getChildren().addAll(supp, spacer, suppName);

        layout.setBody(supplierBox);
        JFXButton continueBtn = new JFXButton("SAVE");
        JFXButton cancel      = new JFXButton("CANCEL");

        continueBtn.setOnAction(e -> {


            if (suppName.validate()) {


                supplier.setSupplierName(suppName.getText().trim().replaceAll("'", ""));
                suppName.setText(suppName.getText().trim().replaceAll("'", ""));


                if (! new SuppliersDao().update(supplier)) {
                    msg.continueAlert(layout, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Failed to update supplier name"));
                }
                else {

                    supplierController.loadSupplier();
                    alert.hideWithAnimation();
                    supplierDrawerController.loadList();
                    msg.continueAlert(node, LabelWithIcons.largeCheckIconLabel("Supplier saved"), new Label(""));
                    msg.continueAlert(suppName, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Unknown error"));


                }
            }
            else {
                msg.continueAlert(suppName, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Fields left blank"));
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
