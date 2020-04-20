package app.controller.rmt.records;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SuppOrdersDrawer extends AnchorPane {

    @FXML
    JFXButton addBtn;
    @FXML
    JFXListView<?> listView;
    @FXML
    JFXTextField searchField;
    @FXML
    JFXDatePicker datePicker;
    @FXML
    JFXButton loadBtn;


    public SuppOrdersDrawer() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("rmt/records/OrdersList.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {

            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }




}
