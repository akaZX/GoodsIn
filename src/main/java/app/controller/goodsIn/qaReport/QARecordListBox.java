package app.controller.goodsIn.qaReport;

import app.controller.sql.dao.MaterialsDao;
import app.controller.sql.dao.QaRecordWeightDao;
import app.controller.utils.TextFieldInput;
import app.pojos.QaRecordWeight;
import app.pojos.RmtQaRecords;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class QARecordListBox extends HBox {

    RmtQaRecords record;

    @FXML
    private Label leftLabel;

    @FXML
    private JFXTextField weightField;

    @FXML
    private JFXTextField boxField;


    public QARecordListBox(RmtQaRecords record) {

        this();
        this.record = record;

        setLeftLabel();
        loadWeight();
    }


    public QARecordListBox() {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("goodsIn/QARecordBox.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


        intFields();


    }


    private void intFields() {

        TextFieldInput.mainIntField(boxField);
        TextFieldInput.doubleTextField(weightField, false);
    }


    private void setLeftLabel() {

        leftLabel.setText(
                record.getmCode() + " - " + new MaterialsDao().get(record.getmCode()).getName() + "\nDecision: " +
                record.getDecision());
    }


    private void loadWeight() {

        QaRecordWeight weight = new QaRecordWeightDao().get(record.getRowid());
        if (weight != null) {
            setWeight(weight.getWeight());
            setBox(weight.getBoxes());
        }

    }


    private void setBox(int boxes) {

        boxField.setText(String.valueOf(boxes));
    }


    public String getDecision() {

        return record.getDecision();
    }


    public RmtQaRecords getRecord() {

        return record;
    }


    //TODO issaugot weights
    public boolean saveWeights() {

        QaRecordWeight weight = new QaRecordWeight();
        weight.setRowid(record.getRowid());
        weight.setWeight(getWeight());
        weight.setBoxes(getBoxes());

        return new QaRecordWeightDao().update(weight);
    }


    public int getBoxes() {

        return Integer.parseInt(boxField.getText());
    }


    public double getWeight() {

        return Double.parseDouble(weightField.getText());
    }


    private void setWeight(double weight) {

        weightField.setText(String.valueOf(weight));
    }
}
