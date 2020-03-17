package app.controller.rmt;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class MinMaxInput extends StackPane {


    @FXML
    private JFXTextField minField;

    @FXML
    private JFXTextField maxField;

    @FXML
    private Label title;


    public MinMaxInput() {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("materialProfile/numericInputBox.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {

            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.setStyle("-fx-background-color: #58b8ff; -fx-border-radius: 5 5 5 5; -fx-border-color: #0069a6;");

        setTooltips();
        validateRequired(this.getMinField());
        validateRequired(this.getMaxField());
    }

    public MinMaxInput(String title) {

        this();
        this.title.setText(title);
    }

    public MinMaxInput(String title, String tooltip){
        this();
        this.title.setText(title);
        this.title.setTooltip(new Tooltip(tooltip));
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

        Pattern pattern = Pattern.compile("\\d*|\\d+\\.\\d*");
        TextFormatter<Pattern> formatter = new TextFormatter<>(change -> pattern.matcher(change.getControlNewText()).matches() ? change : null);

        field.setTextFormatter(formatter);

    }


    public double getMin() {

        return Double.parseDouble(this.getMinField().getText());
    }


    public double getMax() {

        return Double.parseDouble(this.getMaxField().getText());
    }


    public void setMin(String min) {

        minField.setText(min);
    }

    public void setMax(String max) {

        minField.setText(max);
    }

    private void setTooltips() {

        Tooltip minFieldTooltip = new Tooltip("Enter min value");
        minField.setTooltip(minFieldTooltip);
        Tooltip maxFieldTooltip = new Tooltip("Enter max value");
        maxField.setTooltip(maxFieldTooltip);
        Tooltip labelTooltip = new Tooltip("Input type :" + title.getText());
        title.setTooltip(labelTooltip);
    }


    public void changeTitle(String title) {

        this.title.setText(title);
    }


    public JFXTextField getMinField() {

        return minField;
    }


    public void disableMinField() {

        minField.setDisable(true);
    }


    public JFXTextField getMaxField() {

        return maxField;
    }


    public void setMaxField(JFXTextField maxField) {

        this.maxField = maxField;
    }


    public Label getTitle() {

        return title;
    }


    public void setTitle(Label title) {

        this.title = title;
    }
}
