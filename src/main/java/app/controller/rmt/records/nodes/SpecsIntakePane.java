package app.controller.rmt.records.nodes;

import app.controller.sql.dao.*;
import app.controller.utils.LabelWithIcons;
import app.controller.utils.Messages;
import app.controller.utils.TextFieldInput;
import app.pojos.*;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


public class SpecsIntakePane extends AnchorPane {

    private final TextFieldInput TEXTFIELDINPUT = new TextFieldInput();

    private final Messages msg = new Messages();

    private final String[] DECISIONLIST = {"ACCEPT", "REJECT", "HOLD", "ASTY", "FACTORY ASTY"};

    private final JFXCheckBox[] criticalCbList = new JFXCheckBox[6];

    private final JFXCheckBox[] minorCbList = new JFXCheckBox[6];

    private final JFXTextField growerField = new JFXTextField();

    private final JFXTextField harvestDateField = new JFXTextField();

    private final JFXTextField lotField = new JFXTextField();

    private final JFXTextField materialTempField = new JFXTextField();

    private final JFXTextField lorryTempField = new JFXTextField();

    private final JFXTextField dayField = new JFXTextField();

    private final JFXTextField roomField = new JFXTextField();

    private final JFXTextField rtaField = new JFXTextField();

    private final JFXTextField ggnField = new JFXTextField();

    private final JFXTextField twaField = new JFXTextField();

    private final JFXTextField healthMarkField = new JFXTextField();

    private final JFXTextField expiryDateField = new JFXTextField();

    private final JFXTextField countField = new JFXTextField();

    private final JFXTextField containerNoField = new JFXTextField();

    private final JFXComboBox<String> countryCombo = new JFXComboBox<>();

    private final JFXComboBox<String> varietyCombo = new JFXComboBox<>();

    Map<String, Node> map = new HashMap<>();

    private RmtQaRecords intakeRecord ;

    private DecisionBox decisionBox = new DecisionBox();

    private MaterialSpecs specs;

    @FXML
    private GridPane topGridPane;

    private ParamBox brixBox;

    private ParamBox pressureBox;

    private ParamBox lengthBox;

    private ParamBox widthBox;

    private ParamBox colorStageBox;

    private ParamBox headWeightBox;

    private DensityBox densityBox;

    private final YieldBox yieldBox = new YieldBox();

    @FXML
    private JFXComboBox<String> decisionCombo;

    //DEFECTS BOXES
    @FXML
    private JFXCheckBox majorMain;

    @FXML
    private JFXCheckBox majorChemicals;

    @FXML
    private JFXCheckBox majorAllergen;

    @FXML
    private JFXCheckBox majorOther;

    @FXML
    private JFXCheckBox majorInsects;

    @FXML
    private JFXCheckBox majorSoiling;

    @FXML
    private JFXCheckBox majorGlass;

    private final JFXCheckBox[] majorCbList = {
            majorChemicals, majorAllergen, majorOther, majorInsects,
            majorSoiling, majorGlass
    };

    @FXML
    private HBox majorBox;

    @FXML
    private Label majorValueLabel;

    @FXML
    private JFXCheckBox criticalMain;

    @FXML
    private JFXCheckBox criticalWet;

    @FXML
    private JFXCheckBox criticalPest;

    @FXML
    private JFXCheckBox criticalMould;

    @FXML
    private JFXCheckBox criticalSlimey;

    @FXML
    private JFXCheckBox criticalSpongy;

    @FXML
    private JFXCheckBox criticalOther;

    @FXML
    private HBox criticalBox;

    @FXML
    private Label criticalValueLabel;

    @FXML
    private JFXCheckBox minorMain;

    @FXML
    private JFXCheckBox minorHarvest;

    @FXML
    private JFXCheckBox minorDiscoloured;

    @FXML
    private JFXCheckBox minorBrowning;

    @FXML
    private JFXCheckBox minorDry;

    @FXML
    private JFXCheckBox minorWaterSpotting;

    @FXML
    private JFXCheckBox minorOther;

    @FXML
    private HBox minorBox;

    @FXML
    private Label minorValueLabel;

    @FXML
    private JFXComboBox<Integer> samplesComboBox;


    @FXML
    private TextArea commentsField;

    @FXML
    private VBox vBox;

    @FXML
    private HBox intakeResultBox;

    @FXML
    private VBox bottomLeftBox;

    @FXML
    private ScrollPane topScrollPane;

    @FXML
    private JFXTextArea suggestionTextArea;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private JFXButton showPopUpBtn;
    SpecsIntakePaneView parent;


    public SpecsIntakePane(RmtQaRecords intakeRecord, SpecsIntakePaneView parent) {

        this();
        this.specs = new MaterialSpecsDao().get(intakeRecord.getmCode());
        this.intakeRecord = intakeRecord;
        this.parent = parent;
//        System.out.println("naujas : " + intakeRecord.getPo());

        if (this.specs != null) {
            addTopParamsToMap();
            addAdditionalParamBoxes();
            setQualityBoxPercentageLabels();

            populateSingleEntrySpecs();
            initComboBoxes();

        }

        try {
            loadRecord();
        }
        catch (NullPointerException ignored) {

        }
        getSuggestion(false);
    }


    public SpecsIntakePane() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("rmt/qualityRecords/SpecsIntakePane.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        intakeResultBox.getChildren().add(decisionBox);

        initCheckBoxesArray();
        disableEnableQualityBoxes();

        addListeners();

    }


    private void addListeners() {

        //Stack pane event filter listeners: listens for mouse and keyboard activity and updates decision upon inputs
        this.addEventFilter(MouseEvent.MOUSE_RELEASED, (MouseEvent event) -> getSuggestion(false));
        this.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent event) -> getSuggestion(false));

        deleteBtn.setOnAction(event -> deleteIntakeRecord());


        showPopUpBtn.setOnAction(event -> {
            getSuggestion(true);
        });


        saveBtn.setOnAction(event -> saveIntakeRecord());
    }


    private void deleteIntakeRecord() {

        JFXAlert<String> alert = new JFXAlert<>((Stage) this.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("You are about to delete following entry:"));
        layout.setBody(new Label(intakeRecord.getPo() + "  " + intakeRecord.getmCode() + " - " +
                                 new MaterialsDao().get(intakeRecord.getmCode())));
        JFXButton delete = new JFXButton("DELETE");
        JFXButton cancel = new JFXButton("CANCEL");

        delete.setOnAction(e -> {
            if (new RmtQaRecordsDao().delete(intakeRecord)) {
                msg.continueAlert(this, LabelWithIcons.largeCheckIconLabel("Success"), new Label("Record deleted"));
                parent.removeSpecsPane(this);
            }

            else {
                msg.continueAlert(this, LabelWithIcons.largeWarningIconLabel("Failed"), new Label("Failed to delete record"));
            }
            alert.hideWithAnimation();

        });

        cancel.setOnAction(b -> {
            alert.hideWithAnimation();
        });
        layout.setActions(delete, cancel);
        alert.setContent(layout);
        alert.show();
    }


    private void saveIntakeRecord() {

        intakeRecord.setDetails(getIntakeDetails());
//        System.out.println("po: "+ intakeRecord.getPo());
        intakeRecord.setDate(LocalDateTime.now());
        intakeRecord.setDecision(decisionCombo.getSelectionModel().getSelectedItem());
        intakeRecord.setAuthor(System.getProperty("user.name"));

        //will show pop up message if it will fail to validate all present fields
        if (validateAllFields()) {
            boolean update = new RmtQaRecordsDao().update(intakeRecord);
            boolean save   = false;
//            System.out.println(intakeRecord.getRowid());
            if (! update) {
//                    System.out.println("saving....");
                save = new RmtQaRecordsDao().save(intakeRecord);
            }
            if (update && ! save) {
                msg.continueAlert(this, LabelWithIcons.largeCheckIconLabel("Success"), new Label("Material intake data updated"));
            }
            else if (! update && save) {
                msg.continueAlert(this, LabelWithIcons.largeCheckIconLabel("Success"), new Label("Material intake data saved"));
            }
            else {
                msg.continueAlert(this, LabelWithIcons.largeWarningIconLabel("Failed"), new Label("Error occurred while saving/updating intake data"));
            }
        }
    }


    //Validates text fields are they left blank or not
    private boolean validateAllFields() {

        boolean valid   = true;
        String  message = "";

        try {
            if (specs.getPressure() == 1 && pressureBox.checkFields()) {
                message += "\nPressure: Missing inputs";
                valid = false;
            }
            if (specs.getBrix() == 1 && brixBox.checkFields()) {
                message += "\nBrix: Missing inputs";
                valid = false;
            }
            if (specs.getLength() == 1 && lengthBox.checkFields()) {
                message += "\nLength: Missing inputs";
                valid = false;
            }
            if (specs.getWidth() == 1 && widthBox.checkFields()) {
                message += "\nWidth: Missing inputs";
                valid = false;
            }
            if (specs.getColorStage() == 1 && colorStageBox.checkFields()) {
                message += "\nColour stage: Missing inputs";
                valid = false;
            }
            if (specs.getHeadWeight() == 1 && headWeightBox.checkFields()) {
                message += "\nHead weight: Missing inputs";
                valid = false;
            }
            if (specs.getYield() == 1 && yieldBox.getYield() == 0) {
                message += "\nYield: Missing/Wrong inputs";
                valid = false;
            }
            if (specs.getDensity() == 1 && densityBox.getDensity() == 0) {
                message += "\nDensity: Missing/Wrong inputs";
                valid = false;
            }

            if (specs.getLorryTemp() == 1) {
                try {
                    Double.parseDouble(lorryTempField.getText());
                }
                catch (NumberFormatException e) {
                    valid = false;
                    message += "\nLorry temperature: Input field is blank or input format is incorrect";
                }
            }
            if (specs.getMaterialTemp() == 1) {
                try {
                    Double.parseDouble(materialTempField.getText());
                }
                catch (NumberFormatException e) {
                    message += "\nMaterial temperature: Input field is blank or input format is incorrect";
                    valid = false;
                }
            }
            //validates combo boxes
            if (specs.getVariety() == 1) {
                if (varietyCombo.getSelectionModel().isEmpty()) {
                    valid = false;
                    message += "\nVariety: Not selected";
                }
            }
            if (specs.getCountry() == 1) {
                if (countryCombo.getSelectionModel().isEmpty()) {
                    valid = false;
                    message += "\nCountry: Not selected";

                }
            }


            // validates text fields
            if (specs.getContainerNo() == 1 && containerNoField.getText().length() <= 0) {
                valid = false;
                message += "\nContainer number: Field left blank";
            }
            if (specs.getCount() == 1 && countField.getText().length() <= 0) {
                valid = false;
                message += "\nCount: Field left blank";
            }
            if (specs.getGrowerId() == 1 && growerField.getText().length() <= 0) {
                valid = false;
                message += "\nGrower ID: Field left blank";
            }
            if (specs.getHarvestDate() == 1 && harvestDateField.getText().length() <= 0) {
                valid = false;
                message += "\nHarvest date: Field left blank";
            }
            if (specs.getLotNumber() == 1 && lotField.getText().length() <= 0) {
                valid = false;
                message += "\nBatch number: Field left blank";
            }
            if (specs.getDay() == 1 && dayField.getText().length() <= 0) {
                valid = false;
                message += "\nDay: Field left blank";
            }
            if (specs.getRoom() == 1 && roomField.getText().length() <= 0) {
                valid = false;
                message += "\nRoom: Field left blank";
            }
            if (specs.getRtaNumber() == 1 && rtaField.getText().length() <= 0) {
                valid = false;
                message += "\nRTA certificate: Field left blank";
            }
            if (specs.getGgn() == 1 && ggnField.getText().length() <= 0) {
                valid = false;
                message += "\nGGN number: Field left blank";
            }
            if (specs.getTwa() == 1 && twaField.getText().length() <= 0) {
                valid = false;
                message += "\nTWA certificate: Field left blank";
            }
            if (specs.getHealthMark() == 1 && healthMarkField.getText().length() <= 0) {
                valid = false;
                message += "\nHealth mark: Field left blank";
            }
            if (specs.getExpiryDate() == 1 && expiryDateField.getText().length() <= 0) {
                valid = false;
                message += "\nExpiry date: Field left blank";
            }

            if (majorMain.selectedProperty().getValue()) {
                if (! checkCheckBoxes(majorCbList)) {
                    valid = false;
                    message += "\nMajor foreign bodies: Select one of provided options";
                }
            }

            if (minorMain.selectedProperty().getValue()) {
                if (! checkCheckBoxes(minorCbList)) {
                    valid = false;
                    message += "\nNon-Critical quality defects: Select one of provided options";
                }
            }

            if (criticalMain.selectedProperty().getValue()) {
                if (! checkCheckBoxes(criticalCbList)) {
                    valid = false;
                    message += "\nCritical quality defects: Select one of provided options";
                }
            }
        }
        catch (Exception ignored) {
            valid = false;
            message += "\nMaterial Quality specs has not been provided";
        }


        if (! valid) {
            msg.continueAlert(this, LabelWithIcons.largeWarningIconLabel("Error!"), new Label(message));
        }

        return valid;
    }


    //  GENERATES RMT INTAKE DETAILS OBJECT
    private RmtQaIntakeDetails getIntakeDetails() {

        RmtQaIntakeDetails intakeQaDetails = new RmtQaIntakeDetails();

        intakeQaDetails.setSamples(samplesComboBox.getSelectionModel().getSelectedItem());
        intakeQaDetails.setComments((commentsField.getText() == null ? "" : commentsField.getText()));


        if (specs.getCount() == 1) {
            intakeQaDetails.setCount(countField.getText());
        }
        if (specs.getLorryTemp() == 1) {
            try {
                intakeQaDetails.setLorryTemp(Double.parseDouble(lorryTempField.getText()));
            }
            catch (NumberFormatException e) {

            }
        }
        if (specs.getMaterialTemp() == 1) {
            try {
                intakeQaDetails.setMaterialTemp(Double.parseDouble(materialTempField.getText()));
            }
            catch (NumberFormatException e) {

            }
        }
        if (specs.getExpiryDate() == 1) {
            intakeQaDetails.setExpiryDate(expiryDateField.getText());
        }
        if (specs.getHealthMark() == 1) {
            intakeQaDetails.setHealthMark(healthMarkField.getText());
        }
        if (specs.getTwa() == 1) {
            intakeQaDetails.setTwa(twaField.getText());
        }
        if (specs.getGgn() == 1) {
            intakeQaDetails.setGgn(ggnField.getText());
        }
        if (specs.getRtaNumber() == 1) {
            intakeQaDetails.setRtaNumber(rtaField.getText());
        }
        if (specs.getRoom() == 1) {
            intakeQaDetails.setRoom(roomField.getText());
        }
        if (specs.getDay() == 1) {
            intakeQaDetails.setDay(dayField.getText());
        }
        if (specs.getLotNumber() == 1) {
            intakeQaDetails.setLotNumber(lotField.getText());
        }
//        if (specs.getLikeForLike() == 1) {
////            intakeSpec.setLikeForLike(harvestDateField.getText());
//        }

        if (specs.getContainerNo() == 1) {
            intakeQaDetails.setContainerNo(containerNoField.getText());
        }

        if (specs.getHarvestDate() == 1) {
            intakeQaDetails.setHarvestDate(harvestDateField.getText());
        }

        if (specs.getGrowerId() == 1) {
            intakeQaDetails.setGrower(growerField.getText());
        }

        if (specs.getCountry() == 1) {
            intakeQaDetails.setCountry(countryCombo.getSelectionModel().getSelectedItem());
        }

        if (specs.getVariety() == 1) {
            intakeQaDetails.setVariety(varietyCombo.getSelectionModel().getSelectedItem());
        }

        if (specs.getHeadWeight() == 1) {
            intakeQaDetails.setHeadWeight(headWeightBox.getIntList());
        }

        if (specs.getColorStage() == 1) {
            intakeQaDetails.setColorStage(colorStageBox.getIntList());
        }

        if (specs.getWidth() == 1) {
            intakeQaDetails.setWidth(widthBox.getIntList());
        }
        if (specs.getLength() == 1) {
            intakeQaDetails.setLength(lengthBox.getIntList());
        }

        if (specs.getPressure() == 1) {
            intakeQaDetails.setPressure(pressureBox.getDoubleList());
        }
        if (specs.getBrix() == 1) {
            intakeQaDetails.setBrix(brixBox.getDoubleList());
        }
        if (specs.getDensity() == 1) {
            intakeQaDetails.setDensity(densityBox.getDensity());
            intakeQaDetails.setDensityGrossWeight(densityBox.getGrossWeight());
            intakeQaDetails.setDensityDiameters(densityBox.getDiameters());
        }
        if (commentsField.getText().length() > 0) {
            intakeQaDetails.setComments(commentsField.getText());
        }

        if (specs.getYield() == 1) {
            intakeQaDetails.setYield(yieldBox.getYield());
            intakeQaDetails.setYieldGross(yieldBox.getGrossWeight());
            intakeQaDetails.setYieldNet(yieldBox.getNetWeight());
        }
        intakeQaDetails.setMinor(minorMain.selectedProperty().getValue());
        if (minorMain.selectedProperty().getValue()) {
            if (checkCheckBoxes(minorCbList)) {
                getMinorParams(intakeQaDetails);
                intakeQaDetails.setMinorText(getSelectedMinorDefectsString());
            }
        }
        intakeQaDetails.setCritical(criticalMain.selectedProperty().getValue());
        if (criticalMain.selectedProperty().getValue()) {
            if (checkCheckBoxes(criticalCbList)) {
                getCriticalParams(intakeQaDetails);
                intakeQaDetails.setCriticalText(getSelectedCriticalDefectsString());
            }
        }

        intakeQaDetails.setMajor(majorMain.selectedProperty().getValue());
        if (majorMain.selectedProperty().getValue()) {
            if (checkCheckBoxes(majorCbList)) {
                getMajorParams(intakeQaDetails);
                intakeQaDetails.setMajorText(getSelectedMajorDefectsString());
            }
        }


        return intakeQaDetails;

    }


    private String getSelectedMinorDefectsString() {

        String message = "";
        if (minorMain.isSelected()) {
            if (minorHarvest.isSelected()) {
                message += "Harvest/Handling damage<br/>";
            }
            if (minorDiscoloured.isSelected()) {
                message += "Discoloured/Scarring<br/>";

            }
            if (minorBrowning.isSelected()) {
                message += "Browning/translucency<br/>";

            }
            if (minorDry.isSelected()) {
                message += "Dry/dehydrated<br/>";

            }
            if (minorWaterSpotting.isSelected()) {
                message += "Water spotting<br/>";

            }
            if (minorOther.isSelected()) {
                message += "Other: check comments section";
            }
        }
        else {
            message = "None";
        }
        return message;
    }


    private String getSelectedMajorDefectsString() {

        String message = "";
        if (majorMain.isSelected()) {
            if (majorGlass.isSelected()) {
                message += "Glass or plastic<br/>";
            }
            if (majorSoiling.isSelected()) {
                message += "Heavy soiling<br/>";

            }
            if (majorInsects.isSelected()) {
                message += "Insects or pests<br/>";

            }
            if (majorChemicals.isSelected()) {
                message += "Chemicals<br/>";

            }
            if (majorAllergen.isSelected()) {
                message += "Allergens<br/>";

            }
            if (majorOther.isSelected()) {
                message += "Other: check comments section";
            }
        }
        else {
            message = "None";
        }
        return message;
    }


    private String getSelectedCriticalDefectsString() {

        String message = "";
        if (criticalMain.isSelected()) {
            if (criticalWet.isSelected()) {
                message += "Excessively wet<br/>";
            }
            if (criticalPest.isSelected()) {
                message += "Pest or disease damage<br/>";

            }
            if (criticalMould.isSelected()) {
                message += "Breakdown or mould<br/>";

            }
            if (criticalSlimey.isSelected()) {
                message += "Slimey<br/>";

            }
            if (criticalSpongy.isSelected()) {
                message += "Spongy or pithy<br/>";

            }
            if (criticalOther.isSelected()) {
                message += "Other: check comments section";
            }
        }
        else {
            message = "None";
        }
        return message;
    }


    private void getMinorParams(RmtQaIntakeDetails record) {

        record.setMinor1(minorCbList[0].selectedProperty().getValue());
        record.setMinor2(minorCbList[1].selectedProperty().getValue());
        record.setMinor3(minorCbList[2].selectedProperty().getValue());
        record.setMinor4(minorCbList[3].selectedProperty().getValue());
        record.setMinor5(minorCbList[4].selectedProperty().getValue());
        record.setMinor6(minorCbList[5].selectedProperty().getValue());
    }


    private void getMajorParams(RmtQaIntakeDetails record) {

        record.setMajor1(majorCbList[0].selectedProperty().getValue());
        record.setMajor2(majorCbList[1].selectedProperty().getValue());
        record.setMajor3(majorCbList[2].selectedProperty().getValue());
        record.setMajor4(majorCbList[3].selectedProperty().getValue());
        record.setMajor5(majorCbList[4].selectedProperty().getValue());
        record.setMajor6(majorCbList[5].selectedProperty().getValue());

    }


    private void getCriticalParams(RmtQaIntakeDetails record) {

        record.setCritical1(criticalCbList[0].selectedProperty().getValue());
        record.setCritical2(criticalCbList[1].selectedProperty().getValue());
        record.setCritical3(criticalCbList[2].selectedProperty().getValue());
        record.setCritical4(criticalCbList[3].selectedProperty().getValue());
        record.setCritical5(criticalCbList[4].selectedProperty().getValue());
        record.setCritical6(criticalCbList[5].selectedProperty().getValue());
    }


    private boolean getSuggestion(boolean showPopUp) {


        String defects1 = "missing additional defects/bodies choices";
        String defects2 = "exceeds allowed limit";

        boolean suggestion = true;
        String  message    = "";


        if (specs.getPressure() == 1 && pressureBox.paramsInSpec(specs.getMinPressure(), specs.getMaxPressure())) {
            suggestion = false;
            message += "\nPressure : does not meet specs. Spec: " + specs.getMinPressure() + " - " +
                       specs.getMaxPressure();
        }
        if (specs.getBrix() == 1 && brixBox.paramsInSpec(specs.getMinBrix(), specs.getMaxBrix())) {
            suggestion = false;
            message += "\nBrix : does not meet specs. Spec: " + specs.getMinBrix() + " - " +
                       specs.getMaxBrix();
        }
        if (specs.getLength() == 1 && lengthBox.paramsInSpec(specs.getMinLength(), specs.getMaxLength())) {
            suggestion = false;
            message += "\nLength : does not meet specs. Spec: " + specs.getMinLength() + "mm - " +
                       specs.getMaxLength() + "mm";
        }
        if (specs.getWidth() == 1 && widthBox.paramsInSpec(specs.getMinWidth(), specs.getMaxWidth())) {
            suggestion = false;
            message += "\nWidth : does not meet specs. Spec: " + specs.getMinWidth() + "mm - " +
                       specs.getMaxWidth() + "mm";
        }
        if (specs.getColorStage() == 1 &&
            colorStageBox.paramsInSpec(specs.getMinColorStage(), specs.getMaxColorStage())) {
            suggestion = false;
            message += "\nColour stage: does not meet specs. Spec: " + specs.getMinColorStage() + " - " +
                       specs.getMaxColorStage();
        }
        if (specs.getHeadWeight() == 1 &&
            headWeightBox.paramsInSpec(specs.getMinHeadWeight(), specs.getMaxHeadWeight())) {
            suggestion = false;
            message += "\nHead weight: does not meet specs. Spec: " + specs.getMinHeadWeight() + "g - " +
                       specs.getMaxHeadWeight() + "g";
        }
        if (specs.getYield() == 1 &&
            (yieldBox.getYield() < specs.getMinYield() || yieldBox.getYield() > specs.getMaxYield())) {
            suggestion = false;
            message += "\nYield: " + yieldBox.getYield();
            message += "% is " + (yieldBox.getYield() < specs.getMinYield() ? "below " : "above ") + " specs: "
                       + specs.getMinYield() + "% - " + specs.getMaxYield() + "%";
        }
        if (specs.getDensity() == 1 &&
            (densityBox.getDensity() < specs.getMinDensity() || densityBox.getDensity() > specs.getMaxDensity())) {
            suggestion = false;
            message += "\nDensity: " + densityBox.getDensity();
            message += " is " + (densityBox.getDensity() < specs.getMinDensity() ? "below " : "above ") + " specs: "
                       + specs.getMinDensity() + " - " + specs.getMaxDensity();
        }

        if (specs.getLorryTemp() == 1) {
            double temp = 0;
            try {
                temp = Double.parseDouble(lorryTempField.getText());
            }
            catch (NumberFormatException ignored) {
            }
            if (temp < specs.getMinLorryTemp() || temp > specs.getMaxLorryTemp()) {
                suggestion = false;
                message += "\nLorry temp : " + temp + "°C does not meet specs. Spec: " + specs.getMinLorryTemp() +
                           "°C - " +
                           specs.getMaxLorryTemp() + "°C";
            }
        }

        if (specs.getMaterialTemp() == 1) {
            double temp = 0;
            try {
                temp = Double.parseDouble(materialTempField.getText());
            }
            catch (NumberFormatException ignored) {
            }
            if (temp < specs.getMinMaterialTemp() || temp > specs.getMaxMaterialTemp()) {
                suggestion = false;
                message += "\nMaterial temp : " + temp + "°C does not meet specs. Spec: " + specs.getMinMaterialTemp() +
                           "°C - " +
                           specs.getMaxMaterialTemp() + "°C";
            }
        }

        if (majorMain.selectedProperty().getValue()) {
            suggestion = false;
            message += "\nMajor foreign bodies: ";
            if (checkCheckBoxes(majorCbList)) {
                message += defects2;
            }
            else {
                message += defects1;
            }
        }
        if (criticalMain.selectedProperty().getValue()) {
            message += "\nCritical quality defects: ";
            if (checkCheckBoxes(criticalCbList)) {
                message += defects2;
            }
            else {
                message += defects1;
            }

            suggestion = false;
        }
        if (minorMain.selectedProperty().getValue()) {
            suggestion = false;
            message += "\nNon-critical quality defects: ";
            if (checkCheckBoxes(minorCbList)) {
                message += defects2;
            }
            else {
                message += defects1;
            }
        }

        if (suggestion) {
            if (showPopUp) {
                msg.continueAlert(this, LabelWithIcons.largeCheckIconLabel("Suggestion: ACCEPT"), new Label("Material specs is in good standards"));

            }
            decisionBox.setAccept();
        }
        else {
            if (showPopUp) {
                msg.continueAlert(this, LabelWithIcons.largeWarningIconLabel("Suggestion: REJECT"), new Label(
                        "Details:" + message));
            }
            decisionBox.setReject();
        }

        suggestionTextArea.setText(message);


        return suggestion;
    }


    private boolean checkCheckBoxes(JFXCheckBox[] list) {

        boolean b = false;
        for (JFXCheckBox jfxCheckBox : list) {
            if (jfxCheckBox.selectedProperty().getValue()) {
                b = true;
            }
        }
//        System.out.println(b);
        return b;
    }


    private void initCheckBoxesArray() {

        initMajorArray();
        initMinorArray();
        initCriticalArray();
    }


    // ADDS CHECK BOXES TO ARRAYS / LEFT SIDE THEN RIGHT
    private void initMinorArray() {

        minorCbList[0] = minorHarvest;
        minorCbList[1] = minorDiscoloured;
        minorCbList[2] = minorBrowning;
        minorCbList[3] = minorDry;
        minorCbList[5] = minorOther;
        minorCbList[4] = minorWaterSpotting;
    }


    private void initCriticalArray() {

        criticalCbList[0] = criticalWet;
        criticalCbList[1] = criticalPest;
        criticalCbList[2] = criticalMould;
        criticalCbList[5] = criticalOther;
        criticalCbList[4] = criticalSpongy;
        criticalCbList[3] = criticalSlimey;

    }


    private void initMajorArray() {

        majorCbList[0] = majorGlass;
        majorCbList[1] = majorSoiling;
        majorCbList[2] = majorInsects;
        majorCbList[3] = majorChemicals;
        majorCbList[4] = majorAllergen;
        majorCbList[5] = majorOther;
    }


    private void disableEnableQualityBoxes() {

        disableCheckBox(minorMain, minorBox);
        disableCheckBox(criticalMain, criticalBox);
        disableCheckBox(majorMain, majorBox);
    }


    private void disableCheckBox(JFXCheckBox cb, HBox box) {

        cb.selectedProperty().addListener((observable, oldValue, newValue) -> box.setDisable(! newValue));
    }


    private void initComboBoxes() {

        List<String> stringList = new ArrayList<>(Arrays.asList(DECISIONLIST));
        decisionCombo.setItems(FXCollections.observableArrayList(stringList));
        decisionCombo.getSelectionModel().select(0);

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(i + 1);
        }
        samplesComboBox.setItems(FXCollections.observableArrayList(list));
        samplesComboBox.getSelectionModel().select(4);
        loadParamBoxes(5);
        samplesComboBox.valueProperty().addListener((observable, oldValue, newValue) -> loadParamBoxes(newValue));
    }


    private void loadParamBoxes(int count) {

        vBox.getChildren().clear();

        if (specs.getPressure() == 1) {
            pressureBox = new ParamBox("Pressure:");
            loadParamBoxesHelper(pressureBox, String.valueOf(specs.getMinPressure()), String.valueOf(specs.getMaxPressure()), false, count);
        }


        if (specs.getBrix() == 1) {
            brixBox = new ParamBox("Brix:");
            loadParamBoxesHelper(brixBox, String.valueOf(specs.getMinBrix()), String.valueOf(specs.getMaxBrix()), false, count);

        }

        if (specs.getLength() == 1) {
            lengthBox = new ParamBox("Length:");
            loadParamBoxesHelper(lengthBox, String.valueOf(specs.getMinLength()), String.valueOf(specs.getMaxLength()), true, count);

        }


        if (specs.getWidth() == 1) {
            widthBox = new ParamBox("Width:");
            loadParamBoxesHelper(widthBox, String.valueOf(specs.getMinWidth()), String.valueOf(specs.getMaxWidth()), false, count);

        }

        if (specs.getColorStage() == 1) {
            colorStageBox = new ParamBox("Colour stage:");
            loadParamBoxesHelper(colorStageBox, String.valueOf(specs.getMinColorStage()), String.valueOf(specs.getMaxColorStage()), true, count);
        }


        if (specs.getHeadWeight() == 1) {
            headWeightBox = new ParamBox("Head weight:");
            loadParamBoxesHelper(headWeightBox, String.valueOf(specs.getMinHeadWeight()), String.valueOf(specs.getMaxHeadWeight()), true, count);

        }

    }


    private void loadParamBoxesHelper(ParamBox box, String minValue, String maxValue, boolean integer, int noOfInputs) {

        box.setTooltip(minValue, maxValue);
        box.addParamBoxes(noOfInputs, integer);
        vBox.getChildren().add(box);
    }


    private void addAdditionalParamBoxes() {

        if (specs.getDensity() == 1) {
            densityBox = new DensityBox(String.valueOf(specs.getMinDensity()), String.valueOf(specs.getMaxDensity()));
            bottomLeftBox.getChildren().add(densityBox);
        }

        if (specs.getYield() == 1) {
            yieldBox.setTooltip(String.valueOf(specs.getMinYield()), String.valueOf(specs.getMaxYield()));
            bottomLeftBox.getChildren().add(yieldBox);
        }

    }


    // populates single input fields
    private void populateSingleEntrySpecs() {

        if (map.size() > 0) {
            topScrollPane.setContent(topGridPane);
            int row = 0;
            for (Map.Entry<String, Node> entry : map.entrySet()) {
                addNodeToGridPane(entry.getKey(), entry.getValue(), row);
                row += 2;
            }
        }

    }


    private void addNodeToGridPane(String labelText, Node node, int row) {

        Label label = new Label(labelText);
        label.getStyleClass().add("sched-form-label");
        topGridPane.add(label, 0, row);
        topGridPane.add(node, 1, row);
        topGridPane.add(new Separator(), 0, row + 1, 2, 1);
    }


    private void addTopParamsToMap() {

//        System.out.println("addTopParams: " + specs);

        if (specs.getCount() == 1) {

            TextFieldInput.intField(countField, true);
            map.put("Count:", countField);
        }
        if (specs.getContainerNo() == 1) {
            TextFieldInput.addValidator(containerNoField);
            map.put("Container No:", containerNoField);
        }

        if (specs.getVariety() == 1) {

            addVarieties();
        }
        if (specs.getCountry() == 1) {

            addCountries();

        }
        if (specs.getGrowerId() == 1) {
            TextFieldInput.addValidator(growerField);
            map.put("Grower ID:", growerField);
        }
        if (specs.getHarvestDate() == 1) {
            TextFieldInput.addValidator(harvestDateField);
            map.put("Harvest date:", harvestDateField);
        }
        if (specs.getLotNumber() == 1) {
            TextFieldInput.addValidator(lotField);
            map.put("Batch number:", lotField);
        }
        if (specs.getDay() == 1) {
            TextFieldInput.addValidator(dayField);
            map.put("Material day:", dayField);
        }
        if (specs.getRoom() == 1) {
            TextFieldInput.addValidator(roomField);
            map.put("Material room:", roomField);
        }
        if (specs.getRtaNumber() == 1) {
            TextFieldInput.addValidator(rtaField);
            map.put("RTA certificate:", rtaField);
        }
        if (specs.getGgn() == 1) {
            TextFieldInput.addValidator(ggnField);
            map.put("GGN number:", ggnField);
        }
        if (specs.getTwa() == 1) {
            TextFieldInput.addValidator(twaField);
            map.put("TWA certificate:", twaField);
        }
        if (specs.getHealthMark() == 1) {
            TextFieldInput.addValidator(healthMarkField);
            map.put("Health mark:", healthMarkField);
        }
        if (specs.getExpiryDate() == 1) {
            TextFieldInput.addValidator(expiryDateField);
            map.put("Expiry date:", expiryDateField);
        }
        if (specs.getLorryTemp() == 1) {
            TextFieldInput.doubleTextField(lorryTempField, true);
            map.put("Lorry temp:", lorryTempField);
        }

        if (specs.getMaterialTemp() == 1) {
            TextFieldInput.doubleTextField(materialTempField, true);
            map.put("Material temp:", materialTempField);
        }
    }


    private void addVarieties() {

        List<String> varietyList = new ArrayList<>();
        for (MaterialVarieties variety : new MaterialVarietiesDao().getAll(specs.getMCode())) {
            varietyList.add(variety.getVariety());
        }

        ObservableList<String> varieties = FXCollections.observableArrayList(varietyList);
        varietyCombo.setItems(varieties);
        varietyCombo.getSelectionModel().selectFirst();
        map.put("Variety:", varietyCombo);
    }


    private void addCountries() {

        List<String> countryList = new ArrayList<>();
        for (MaterialCountries country : new MaterialCountryDao().getAll(specs.getMCode())) {
            countryList.add(country.getCountry());
        }
        ObservableList<String> countries = FXCollections.observableArrayList(countryList);
        countryCombo.setItems(countries);
        countryCombo.getSelectionModel().selectFirst();
        map.put("Country:", countryCombo);
    }


    private void setQualityBoxPercentageLabels() {

        if (specs != null) {
            criticalValueLabel.setText(String.valueOf(specs.getMaxCritical()));
            majorValueLabel.setText(String.valueOf(specs.getMaxMajor()));
            minorValueLabel.setText(String.valueOf(specs.getMaxMinor()));
        }

    }


    private void loadRecord() {

        RmtQaIntakeDetails details = intakeRecord.getDetails();

        decisionCombo.getSelectionModel().select(intakeRecord.getDecision());

        if (details.getSamples() > 0) {
            samplesComboBox.getSelectionModel().select(details.getSamples() - 1);
        }

        commentsField.setText(details.getComments());


        if (specs.getCount() == 1) {
            countField.setText(details.getCount());
        }
        if (specs.getLorryTemp() == 1) {
            lorryTempField.setText("" + details.getLorryTemp());
        }
        if (specs.getMaterialTemp() == 1) {
            materialTempField.setText("" + details.getMaterialTemp());
        }
        if (specs.getExpiryDate() == 1) {
            expiryDateField.setText(details.getExpiryDate());
        }
        if (specs.getHealthMark() == 1) {
            healthMarkField.setText(details.getHealthMark());
        }
        if (specs.getTwa() == 1) {
            twaField.setText(details.getTwa());

        }
        if (specs.getGgn() == 1) {
            ggnField.setText(details.getGgn());
        }
        if (specs.getRtaNumber() == 1) {
            rtaField.setText(details.getRtaNumber());
        }
        if (specs.getRoom() == 1) {
            roomField.setText(details.getRoom());
        }
        if (specs.getDay() == 1) {
            dayField.setText(details.getDay());
        }
        if (specs.getLotNumber() == 1) {
            lotField.setText(details.getLotNumber());
        }

        if (specs.getContainerNo() == 1) {
            containerNoField.setText(details.getContainerNo());
        }

        if (specs.getHarvestDate() == 1) {
            harvestDateField.setText(details.getHarvestDate());
        }

        if (specs.getGrowerId() == 1) {
            growerField.setText(details.getGrower());

        }


        if (specs.getCountry() == 1 && details.getCountry() != null) {

            countryCombo.setValue(new MaterialCountries(specs.getMCode(), details.getCountry()).toString());
        }
        if (specs.getVariety() == 1 && details.getVariety() != null) {
            varietyCombo.setValue(new MaterialVarieties(specs.getMCode(), details.getVariety()).toString());
        }


        if (specs.getHeadWeight() == 1) {
            headWeightBox.setFieldsData(details.getHeadWeight());
        }

        if (specs.getColorStage() == 1) {
            colorStageBox.setFieldsData(details.getColorStage());
        }

        if (specs.getWidth() == 1) {
            widthBox.setFieldsData(details.getWidth());
        }
        if (specs.getLength() == 1) {
            lengthBox.setFieldsData(details.getLength());
        }

        if (specs.getPressure() == 1) {
            pressureBox.setFieldsData(details.getPressure());

        }
        if (specs.getBrix() == 1) {
            brixBox.setFieldsData(details.getBrix());
        }
        if (specs.getDensity() == 1) {
            densityBox.setDiameter(details.getDensityDiameters());
            densityBox.setGrossWeight(details.getDensityGrossWeight());
        }

        if (specs.getYield() == 1) {
            yieldBox.setGrossWeightField(details.getYieldGross());
            yieldBox.setNetWeightField(details.getYieldNet());
        }

        setMinorParams(details);
        setMajorParams(details);
        setCriticalParams(details);

    }


    private void setCriticalParams(RmtQaIntakeDetails record) {

        criticalMain.selectedProperty().setValue(record.isCritical());
        if (record.isCritical()) {
            criticalCbList[0].selectedProperty().setValue(record.isCritical1());
            criticalCbList[1].selectedProperty().setValue(record.isCritical2());
            criticalCbList[2].selectedProperty().setValue(record.isCritical3());
            criticalCbList[3].selectedProperty().setValue(record.isCritical4());
            criticalCbList[4].selectedProperty().setValue(record.isCritical5());
            criticalCbList[5].selectedProperty().setValue(record.isCritical6());

        }
    }


    private void setMajorParams(RmtQaIntakeDetails record) {

        majorMain.selectedProperty().setValue(record.isMajor());
        if (record.isMajor()) {
            majorCbList[0].selectedProperty().setValue(record.isMajor1());
            majorCbList[1].selectedProperty().setValue(record.isMajor2());
            majorCbList[2].selectedProperty().setValue(record.isMajor3());
            majorCbList[3].selectedProperty().setValue(record.isMajor4());
            majorCbList[4].selectedProperty().setValue(record.isMajor5());
            majorCbList[5].selectedProperty().setValue(record.isMajor6());
        }
    }


    private void setMinorParams(RmtQaIntakeDetails record) {

        minorMain.selectedProperty().setValue(record.isMinor());
        if (record.isMinor()) {
            minorCbList[0].selectedProperty().setValue(record.isMinor1());
            minorCbList[1].selectedProperty().setValue(record.isMinor2());
            minorCbList[2].selectedProperty().setValue(record.isMinor3());
            minorCbList[3].selectedProperty().setValue(record.isMinor4());
            minorCbList[4].selectedProperty().setValue(record.isMinor5());
            minorCbList[5].selectedProperty().setValue(record.isMinor6());
        }
    }


}
