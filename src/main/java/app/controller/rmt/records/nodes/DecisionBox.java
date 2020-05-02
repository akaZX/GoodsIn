package app.controller.rmt.records.nodes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class DecisionBox extends HBox {


    @FXML
    private Label label;


    public DecisionBox() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("rmt/qualityRecords/DecisionBox.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        setReject();
    }


    public void setReject() {

        this.setStyle("-fx-background-color: #f6cccc");
        label.setText("Reject");
    }


    public void setAccept() {

        this.setStyle("-fx-background-color: #bbffad");
        label.setText("Accept");
    }


}
