package app.controller.rmt.records.nodes;

import app.controller.utils.TextFieldInput;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class YieldBox extends VBox {


    @FXML
    private JFXTextField grossWeightField;

    @FXML
    private JFXTextField netWeightField;

    @FXML
    private JFXTextField yieldField;

    @FXML
    private Label yieldLabel;

    private int yield = 0;

    private final ChangeListener<String> LISTENER = (observable, oldValue, newValue) -> {
        calculateYield();
        if (getYield() != 0) {
            yieldField.setText(String.valueOf(getYield()));
        }
        else {
            yieldField.setText("");
        }
        yieldField.validate();
    };


    public YieldBox(String min, String max) {

        this();
        setTooltip(min, max);

    }


    public YieldBox() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("rmt/qualityRecords/YieldBox.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        intFields();
        listenTextFields();
    }


    private void intFields() {

        TextFieldInput.intField(grossWeightField, true);
        TextFieldInput.intField(netWeightField, true);
        TextFieldInput.intField(yieldField, "");
    }


    private void listenTextFields() {

        grossWeightField.textProperty().addListener(LISTENER);

        netWeightField.textProperty().addListener(LISTENER);
    }


    public void setTooltip(String min, String max) {

        String  minMsg  = "Min yield: " + min + "%";
        String  maxMsg  = "Max yield: " + max + "%";
        Tooltip tooltip = new Tooltip(minMsg + "\n" + maxMsg);

        yieldField.setTooltip(tooltip);
        yieldLabel.setTooltip(tooltip);
    }


    private void calculateYield() {

        if (grossWeightField.getText().length() > 0 && netWeightField.getText().length() > 0) {
            yield = 0;
            if (Integer.parseInt(grossWeightField.getText()) > 0) {
                yield = Integer.parseInt(netWeightField.getText()) * 100 / Integer.parseInt(grossWeightField.getText());
            }
//            System.out.println("yield viduje yra: " + yield);
        }
        else {
            yield = 0;
        }
    }


    public int getYield() {

        return yield;
    }


    public int getGrossWeight() {

        try {
            return Integer.parseInt(grossWeightField.getText());
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }


    public int getNetWeight() {

        try {
            return Integer.parseInt(netWeightField.getText());
        }
        catch (NumberFormatException | NullPointerException exception) {

            return 0;
        }
    }


    public void setGrossWeightField(int value) {

        grossWeightField.setText(String.valueOf(value));
    }


    public void setNetWeightField(int value) {

        netWeightField.setText(String.valueOf(value));
    }


}
