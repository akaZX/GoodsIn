package app.controller.rmt.materials.nodes;

import app.controller.sql.dao.MaterialsDao;
import app.controller.utils.LabelWithIcons;
import app.controller.utils.Messages;
import app.controller.utils.ValidateInput;
import app.pojos.Materials;
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

public class MaterialsService {

    private static final Messages msg = new Messages();


    public static void addNewMaterial(Node node, MaterialListDrawerController drawerController) {


        JFXAlert<String> alert = new JFXAlert<>((Stage) node.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Add new material"));


        Label supp   = new Label("Material name: ");
        HBox  spacer = new HBox();
        HBox.setHgrow(
                spacer,
                Priority.SOMETIMES
        );
        JFXTextField materialName = new JFXTextField();
        ValidateInput.requiredFieldValidation(materialName, "Missing material name");
        HBox materialBox = new HBox();
//        supplierBox.setMinHeight(50);
        materialBox.getChildren().addAll(supp, spacer, materialName);


        Label po = new Label("Material code");

        HBox spacer2 = new HBox();
        HBox.setHgrow(
                spacer2,
                Priority.SOMETIMES
        );
        JFXTextField materialCodeField = new JFXTextField();
        ValidateInput.requiredFieldValidation(materialCodeField, "Missing material code");

        HBox orderBox = new HBox();
        orderBox.getChildren().addAll(po, spacer2, materialCodeField);


        VBox box = new VBox();
        box.setSpacing(35);
        box.getChildren().addAll(materialBox, orderBox);
        layout.setBody(box);
        JFXButton continueBtn = new JFXButton("SAVE");
        JFXButton cancel      = new JFXButton("CANCEL");

        continueBtn.setOnAction(e -> {


            if (materialCodeField.validate() && materialName.validate()) {

                Materials material = new Materials();
                material.setMCode(materialCodeField.getText().toUpperCase().trim().replaceAll("'", ""));
                material.setName(materialName.getText().trim().replaceAll("'", ""));


                if (! new MaterialsDao().save(material)) {
                    msg.continueAlert(layout, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Material exists"));
                }
                else {

                    msg.continueAlert(node, LabelWithIcons.largeCheckIconLabel("Material saved"), new Label(""));
                    alert.hideWithAnimation();
                    drawerController.loadList();

                }
            }
            else {
                msg.continueAlert(layout, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Fields left blank"));
            }

        });

        cancel.setOnAction(b -> {
            alert.hideWithAnimation();
        });
        layout.setActions(continueBtn, cancel);
        alert.setContent(layout);
        alert.show();

    }


    public static void deleteMaterial(Node node, MaterialListDrawerController drawerController, Materials material) {


        JFXAlert<String> alert = new JFXAlert<>((Stage) node.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Delete material"));

        layout.setBody(new Label("Do you want to delete: " + material.getName() + " ?"));
        JFXButton continueBtn = new JFXButton("DELETE");
        JFXButton cancel      = new JFXButton("CANCEL");

        continueBtn.setOnAction(e -> {


            if (new MaterialsDao().delete(material)) {


                msg.continueAlert(node, LabelWithIcons.largeCheckIconLabel("Material deleted"), new Label(""));


            }
            else {
                msg.continueAlert(node, LabelWithIcons.largeWarningIconLabel("Error"), new Label(""));
            }
            alert.hideWithAnimation();
            drawerController.loadList();
        });

        cancel.setOnAction(b -> {
            alert.hideWithAnimation();
        });
        layout.setActions(continueBtn, cancel);
        alert.setContent(layout);
        alert.show();


    }


}
