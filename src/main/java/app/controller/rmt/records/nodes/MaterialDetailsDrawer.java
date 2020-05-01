package app.controller.rmt.records.nodes;

import app.controller.utils.FileUtil;
import app.controller.utils.LabelWithIcons;
import app.controller.utils.Messages;
import app.pojos.Materials;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class MaterialDetailsDrawer extends AnchorPane {

    Messages msg = new Messages();

    Materials material;

    @FXML
    private Label mCodeLabel;

    @FXML
    private Label materialNameLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private JFXTextArea descriptionArea;


    public MaterialDetailsDrawer(Materials material) {

        this();
        this.material = material;
        if (this.material != null) {
            load();
        }
        else {
            msg.continueAlert(this, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Material not found"));
            mCodeLabel.setText("Material not found in database");
        }
    }


    public MaterialDetailsDrawer() {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("rmt/qualityRecords/MaterialDetailsDrawer.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {

            loader.load();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void load() {

        //try{
        mCodeLabel.setText(material.getMCode());
        materialNameLabel.setText(material.getName());
        descriptionArea.setText(material.getDescription());
        imageView.setImage(new FileUtil().getImage(material.getImagePath()));


    }


    @FXML
    private void openMaterialDescription(MouseEvent event) {

        try {
            FileUtil.openFile(material.getDocLink());
        }
        catch (Exception e) {
            msg.continueAlert(this, LabelWithIcons.largeWarningIconLabel("Error"), new Label("File not found"));
        }

    }

}
