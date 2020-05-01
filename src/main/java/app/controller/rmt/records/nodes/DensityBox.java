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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class DensityBox extends VBox {

    TextFieldInput textFieldInput = new TextFieldInput();

    @FXML
    private JFXTextField diam1;
    @FXML
    private JFXTextField diam2;
    @FXML
    private JFXTextField diam3;
    @FXML
    private JFXTextField diam4;
    @FXML
    private JFXTextField diam5;
    @FXML
    private JFXTextField densityField;
    @FXML
    private JFXTextField grossField;
    @FXML
    private Label densityLabel;

    private double density;

    private final ChangeListener<String> LISTENER = (observable, oldValue, newValue) -> {
        calculateDensity();
        densityField.setText(String.valueOf(density));
        densityField.validate();
    };




    public DensityBox() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("rmt/qualityRecords/DensityBox.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        intFields();
        addListeners();
    }


    public DensityBox(String min, String max) {
        this();
        setTooltip(min, max);
    }



    private void intFields(){
        textFieldInput.intField(diam1, "");
        textFieldInput.intField(diam2, "");
        textFieldInput.intField(diam3, "");
        textFieldInput.intField(diam4, "");
        textFieldInput.intField(diam5, "");
        textFieldInput.intField(grossField, true);
        textFieldInput.doubleTextField(densityField, "");
    }

    private void calculateDensity(){
        try {
            double avgHeadWeight = (double)getGrossWeight() / 5;
            density = round(avgHeadWeight / ((double) getAverageDiameter()), 2);
        }
        catch (NumberFormatException e) {
            density = 0;
        }
    }

    private void addListeners(){

        diam1.textProperty().addListener(LISTENER);
        diam2.textProperty().addListener(LISTENER);
        diam3.textProperty().addListener(LISTENER);
        diam4.textProperty().addListener(LISTENER);
        diam5.textProperty().addListener(LISTENER);
        grossField.textProperty().addListener(LISTENER);

    }


    public void setTooltip(String min, String max) {
        String msg = "Min density: " + min + "\nMax density: " + max;
        Tooltip tooltip = new Tooltip(msg);
        densityLabel.setTooltip(tooltip);
    }

    public void setDiameter(List<Integer> diameter){

        if (diameter != null) {
            diam1.setText(String.valueOf(diameter.get(0)));
            diam2.setText(String.valueOf(diameter.get(1)));
            diam3.setText(String.valueOf(diameter.get(2)));
            diam4.setText(String.valueOf(diameter.get(3)));
            diam5.setText(String.valueOf(diameter.get(4)));
        }

    }

    private int getTotalDiameter(){

        return Integer.parseInt(diam1.getText()) + Integer.parseInt(diam2.getText()) +
               Integer.parseInt(diam3.getText()) + Integer.parseInt(diam4.getText()) +
               Integer.parseInt(diam5.getText());
    }

    public List<Integer> getDiameters(){

        List<Integer> list = new ArrayList<>();
        try {
            list.add(Integer.parseInt(diam1.getText()));
            list.add(Integer.parseInt(diam2.getText()));
            list.add(Integer.parseInt(diam3.getText()));
            list.add(Integer.parseInt(diam4.getText()));
            list.add(Integer.parseInt(diam5.getText()));

        }
        catch (NumberFormatException e) {
            return null;
        }

        return list;
    }

    private int getAverageDiameter(){
        return getTotalDiameter() / 5;
    }


    public void setGrossWeight(int weight){
        grossField.setText(String.valueOf(weight));
    }


    public int getGrossWeight() {
        try {
            return Integer.parseInt(grossField.getText());
        }
        catch (NumberFormatException e) {
//            System.out.println("Error at DensityBox.getGrossWeight()");
        }
        return 0;
    }

    public double getDensity(){
        return density;
    }


//    https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
