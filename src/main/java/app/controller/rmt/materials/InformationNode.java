package app.controller.rmt.materials;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.io.IOException;

public class InformationNode extends HBox {

    @FXML
    Label title;


    public InformationNode() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("rmt/informationNode.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {

            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        this.setMinHeight(60);
        this.setMinWidth(150);
        this.setPrefHeight(60);
        this.setPrefWidth(150);
        this.setMaxHeight(60);
        this.setMaxWidth(150);
    }

    public InformationNode(String title){
        this();
        this.title.setText(title);
    }
}
