package app.CustomGUI;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import java.io.IOException;

public class DateTimeInput extends HBox {

@FXML private JFXDatePicker date;
@FXML private JFXTextField hours;
@FXML private JFXTextField minutes;



    public DateTimeInput() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("dateTimeField.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


    }




}
