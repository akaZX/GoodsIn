package app.controller.rmt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.io.IOException;

public class InformationNode extends HBox {

    @FXML
    Label title;


    public InformationNode() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("materialProfile/informationNode.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {

            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.setStyle("-fx-background-color: #58b8ff; -fx-border-radius: 5 5 5 5; -fx-border-color: #0069a6;");

        this.setPrefHeight(40);
        this.setPrefWidth(150);
        this.setMaxHeight(40);
        this.setMaxWidth(150);
    }

    public InformationNode(String title){
        this();
        this.title.setText(title);
    }
}
