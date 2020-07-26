package app.controller.rmt.records.nodes;

import app.controller.utils.TextFieldInput;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.commons.math3.util.Precision;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParamBox extends VBox {

    @FXML
    private HBox box;

    private final TextFieldInput textFieldInput = new TextFieldInput();

    private int numberOfBoxes = 0;


    @FXML
    private Label title;

    private final List<JFXTextField> fields = new ArrayList<>();


    public ParamBox(int boxes, String paramTitle, boolean integer) {

        this();
        addParamBoxes(boxes, integer);
        title.setText(paramTitle);
    }


    public ParamBox() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("rmt/qualityRecords/ParamBox.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addParamBoxes(int number, boolean integer) {

        numberOfBoxes = number;
        if (number > 0) {
            for (int i = 0; i < number; i++) {
                fields.add(newField(i + 1, integer));
            }
            box.getChildren().addAll(fields);
        }
    }


    private JFXTextField newField(int i, boolean integer) {

        JFXTextField field = new JFXTextField();
        field.setPrefWidth(50);

        field.setPromptText(String.valueOf(i));
        textFieldInputFormat(field, integer);
        return field;

    }


    private void textFieldInputFormat(JFXTextField field, boolean integer) {

        if (! integer) {
            TextFieldInput.doubleTextField(field, true);

        }
        else {
            TextFieldInput.intField(field, true);
        }
    }


    public ParamBox(String paramTitle) {

        this();
        title.setText(paramTitle);
    }


    public List<Integer> getIntList() {

        List<Integer> list = new ArrayList<>();
        for (JFXTextField field : fields) {
            try {
                list.add(Integer.parseInt(field.getText()));

            }
            catch (NumberFormatException e) {
                list.add(0);
//                System.out.println("Error at: ParamBox.getIntList()");
            }
        }
        return list;
    }


    public List<Double> getDoubleList() {

        List<Double> list = new ArrayList<>();
        for (JFXTextField field : fields) {
            try {
                list.add(Double.parseDouble(field.getText()));

            }
            catch (NumberFormatException e) {
                list.add(0.0);
//                System.out.println("Error at: ParamBox.getDoubleList()");
            }
        }
        return list;
    }


    public <T extends Number> void setFieldsData(List<T> list) {

        if (list != null) {
            if (fields.size() == list.size()) {
                for (int i = 0; i < fields.size(); i++) {
                    fields.get(i).setText(list.get(i).toString());
                }
            }
        }

    }


    public double getTotalInteger() {

        return (int) getTotalDouble();
    }


    public double getTotalDouble() {

        double total;
        if (numberOfBoxes > 0) {
            total = fields.stream().mapToDouble(field -> Double.parseDouble(field.getText())).sum();
        }
        else {
            total = - 1;
        }
        return total;
    }


    public boolean checkFields() {

        boolean filled = true;

        for (JFXTextField field : fields) {
            if (field.getText().length() < 1) {
                filled = false;
            }
        }

        return ! filled;
    }


    public boolean paramsInSpec(int min, int max) {

        double limit     = Precision.round((fields.size() * 0.2), 0);
        int    outOfSpec = 0;

        try {
            for (JFXTextField field : fields) {
                int fieldValue = Integer.parseInt(field.getText());

                if (fieldValue < min || fieldValue > max) {
                    outOfSpec++;
                }
            }
        }
        catch (NumberFormatException e) {
            return true;
        }

        return outOfSpec > limit;
    }


    public boolean paramsInSpec(double min, double max) {

        double limit     = Precision.round((fields.size() * 0.2), 0);
        int    outOfSpec = 0;

        try {
            for (JFXTextField field : fields) {
                double fieldValue = Double.parseDouble(field.getText());

                if (fieldValue < min || fieldValue > max) {
                    outOfSpec++;
                }
            }
        }
        catch (NumberFormatException e) {
            return true;
        }

        return outOfSpec > limit;
    }


    public void setTooltip(String minValue, String maxValue) {

        String  msg     = "Min: " + minValue + "\nMax: " + maxValue;
        Tooltip tooltip = new Tooltip(msg);
        title.setTooltip(tooltip);
    }

}
