package app.controller.utils;

import app.controller.rmt.materials.ParamListController;
import app.controller.sql.dao.Dao;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Messages extends JFXAlert<String> {




    public <T> void materialProfileControllerSaveMessage(ParamListController box, Dao<T> dao, Object obj, String mCode){
        JFXAlert<String> alert = new JFXAlert<>((Stage) box.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("You are about to add following entry:"));
        layout.setBody(new Label(box.getText()));
        JFXButton continueBtn = new JFXButton("CONTINUE");
        JFXButton cancel      = new JFXButton("CANCEL");

        continueBtn.setOnAction(e -> {
            dao.save((T) obj);
            box.clearList();
            box.clearText();
            box.reloadList(dao.getAll(mCode));
            alert.hideWithAnimation();

        });

        cancel.setOnAction(b -> {
            alert.hideWithAnimation();
        });
        layout.setActions(continueBtn, cancel);
        alert.setContent(layout);
        alert.show();
    }

    public <T> void materialProfileControllerDelete(ParamListController box, Dao<T> dao, String mCode){

        JFXAlert<String> alert = new JFXAlert<>((Stage) box.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("You are about to delete following entry:"));
        layout.setBody(new Label(box.getSelectedItem().toString()));
        JFXButton delete = new JFXButton("CONTINUE");
        JFXButton cancel = new JFXButton("CANCEL");

        delete.setOnAction(e -> {
            dao.delete((T) box.getSelectedItem());
            box.clearList();
            box.clearSelectedLabel();
            box.removeBtn.setDisable(true);
            box.reloadList(dao.getAll(mCode));
            alert.hideWithAnimation();

        });

        cancel.setOnAction(b -> {
            alert.hideWithAnimation();
        });
        layout.setActions(delete, cancel);
        alert.setContent(layout);
        alert.show();
    }

    public void continueAlert(Node box, String heading, String body) {

        JFXAlert<String> alert = new JFXAlert<>((Stage) box.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(heading));
        layout.setBody(new Label(body));
        JFXButton continueBtn = new JFXButton("CONTINUE");


        continueBtn.setOnAction(e -> {
            alert.hideWithAnimation();
        });

        layout.setActions(continueBtn);
        alert.setContent(layout);
        alert.show();
    }

    public void showAlert(Node node, String title, String message){
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(title));
        layout.setBody(new Label(message));
        JFXAlert<Void> alert = new JFXAlert<>((Stage) node.getScene().getWindow());
        alert.setOverlayClose(true);
        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alert.setContent(layout);
        alert.initModality(Modality.APPLICATION_MODAL);
        JFXButton continueBtn = new JFXButton("Continue");
        continueBtn.getStyleClass().add("dialog-accept");
        continueBtn.setOnAction(event -> {
            alert.hideWithAnimation();
        });
        layout.setActions(continueBtn);
        alert.showAndWait();
    }
}
