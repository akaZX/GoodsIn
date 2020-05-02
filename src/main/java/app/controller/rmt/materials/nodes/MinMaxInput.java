package app.controller.rmt.materials.nodes;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.regex.Pattern;

public class MinMaxInput extends StackPane {


    @FXML
    private JFXTextField minField;

    @FXML
    private JFXTextField maxField;

    @FXML
    private Label title;

    private Tooltip tooltip;


    public MinMaxInput(String title) {

        this();
        this.title.setText(title);
    }


    public MinMaxInput() {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("rmt/materialProfiles/numericInputBox.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {

            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        setTooltips();
        validateRequired(this.getMinField());
        validateRequired(this.getMaxField());
    }


    public void validateRequired(JFXTextField field) {

        RequiredFieldValidator validator = new RequiredFieldValidator("Required");
        field.getValidators().add(validator);

        field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (! newValue) {
                field.validate();
            }
        });

        addDoubleValidator();
    }


    public void addDoubleValidator() {

        setNumericValidators(this.getMinField());
        setNumericValidators(this.getMaxField());
    }


    private static void setNumericValidators(JFXTextField field) {

        Pattern                pattern   = Pattern.compile("\\d*|\\d+\\.\\d*");
        TextFormatter<Pattern> formatter = new TextFormatter<>(change -> pattern.matcher(change.getControlNewText()).matches() ? change : null);

        field.setTextFormatter(formatter);

    }


    public JFXTextField getMinField() {

        return minField;
    }


    public JFXTextField getMaxField() {

        return maxField;
    }


    public void setMaxField(JFXTextField maxField) {

        this.maxField = maxField;
    }


    private void setTooltips() {

        Tooltip minFieldTooltip = new Tooltip("Enter min value");
        minField.setTooltip(minFieldTooltip);
        Tooltip maxFieldTooltip = new Tooltip("Enter max value");
        maxField.setTooltip(maxFieldTooltip);
        Tooltip labelTooltip = new Tooltip("Input type :" + title.getText());
        title.setTooltip(labelTooltip);
    }


    public MinMaxInput(String title, String tooltip) {

        this();
        this.title.setText(title);
        this.tooltip = new Tooltip(tooltip);
        wrapTooltip();
        this.title.setTooltip(this.tooltip);

    }


    private void wrapTooltip() {

        tooltip.setPrefWidth(200);
        tooltip.setWrapText(true);
    }


    public int getMinInt() {

        return (int) getMin();
    }


    public double getMin() {

        try {
            return Double.parseDouble(this.getMinField().getText());
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }


    public void setMin(String min) {

        minField.setText(min);
    }


    public int getMaxInt() {

        return (int) getMax();
    }


    public double getMax() {

        try {
            return Double.parseDouble(this.getMaxField().getText());
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }


    public void setMax(String max) {

        maxField.setText(max);
    }


    public void changeTitle(String title) {

        this.title.setText(title);
    }


    public void disableMinField() {

        minField.setDisable(true);
    }


    public Label getTitle() {

        return title;
    }


    public void setTitle(Label title) {

        this.title = title;
    }
}
