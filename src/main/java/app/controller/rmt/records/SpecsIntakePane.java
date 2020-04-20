package app.controller.rmt.records;

import app.controller.sql.dao.MaterialCountryDao;
import app.controller.sql.dao.MaterialSpecsDao;
import app.controller.sql.dao.MaterialVarietiesDao;
import app.controller.utils.LabelWithIcons;
import app.controller.utils.Messages;
import app.controller.utils.TextFieldInput;
import app.pojos.*;
import com.google.gson.Gson;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class SpecsIntakePane extends AnchorPane implements Initializable {

    private final TextFieldInput TEXTFIELDINPUT = new TextFieldInput();

    private final Messages msg = new Messages();

    private final RmtQaRecords qaRecord = new RmtQaRecords();

    MaterialSpecs specs = new MaterialSpecsDao().get("M00005");







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

    private final JFXComboBox<MaterialCountries> countryCombo = new JFXComboBox<>();

    private final JFXComboBox<MaterialVarieties> varietyCombo = new JFXComboBox<>();

    Map<String, Node> map = new HashMap<>();


    @FXML
    private GridPane topGridPane;

    @FXML
    private JFXButton btn;

    private ParamBox brixBox;

    private ParamBox pressureBox;

    private ParamBox lengthBox;

    private ParamBox widthBox;

    private ParamBox colorStageBox;

    private ParamBox headWeightBox;

    private DensityBox densityBox;

    private YieldBox yieldBox = new YieldBox();


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
    private VBox bottomLeftBox;

    @FXML
    private ScrollPane topScrollPane;


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

    }


    public SpecsIntakePane(MaterialSpecs specs) {
        this();
        this.specs = specs;

    }



    // adds check boxes to arrays / left side then right
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initCheckBoxesArray();

        disableEnableQualityBoxes();
        initComboBox();
        addAdditionalParamBoxes();
        setQualityBoxPercentageLabels(specs);
        btn.setOnAction(event -> {

            getSuggestion();

//            System.out.println(brixBox.paramsInSpec(4,7));
//            validateTextBoxes();

//            System.out.println(validateNumericalInputs());
            Gson   gson = new Gson();
            String json = gson.toJson(getIntakeDetails());
            System.out.println(json);
        });

        GridPane topGridPane = new GridPane();
        topGridPane.setHgap(15);
        topGridPane.setVgap(15);

        topScrollPane.setContent(topGridPane);
        addTopParamsToMap();
        populateSingleEntrySpecs(map);

    }

    private boolean validHelperDouble(List<Double> list, double minSpec, double maxSpec, boolean valid){

        for(Double input: list){
            if(input < minSpec || input > maxSpec){
               return false;
            }
        }  
        return valid;
    }

     private boolean validHelperInt(List<Integer> list, int minSpec, int maxSpec, boolean valid){

        for(Integer input: list){
            if(input < minSpec || input > maxSpec){
               return false;
            }
        }
        return valid;
    }


    //Compares specs with provided inputs
    private boolean validateNumericalInputs(){

        boolean valid = true;

        if (specs.getPressure() == 1) {
            valid = validHelperDouble(pressureBox.getDoubleList(), specs.getMinPressure(), specs.getMaxPressure(), valid);
        }
        if (specs.getBrix() == 1) {
            valid = validHelperDouble(brixBox.getDoubleList(), specs.getMinBrix(), specs.getMaxBrix(), valid);
        }
        if (specs.getLength() == 1) {
            valid = validHelperInt(lengthBox.getIntList(), specs.getMinLength(), specs.getMaxLength(), valid);
        }
        if (specs.getWidth() == 1) {
            valid = validHelperInt(widthBox.getIntList(), specs.getMinWidth(), specs.getMaxWidth(), valid);
        }
        if (specs.getColorStage() == 1) {
            valid = validHelperInt(colorStageBox.getIntList(), specs.getMinColorStage(), specs.getMaxColorStage(), valid);
        }
        if (specs.getHeadWeight() == 1) {
            valid = validHelperInt(headWeightBox.getIntList(), specs.getMinHeadWeight(), specs.getMaxHeadWeight(), valid);
        }
        if(specs.getYield() == 1){

            if(yieldBox.getYield()< specs.getMinYield() || yieldBox.getYield() > specs.getMaxYield()){
                valid = false;
            }
        }
        if(specs.getDensity() == 1) {
            if(densityBox.getDensity() < specs.getMinDensity() || densityBox.getDensity() > specs.getMaxDensity()){
                valid = false;
            }
        }

        if (specs.getLorryTemp() == 1) {
            try {
                double temp = Double.parseDouble(lorryTempField.getText());
                if (temp < specs.getMinLorryTemp() || temp > specs .getMaxLorryTemp()){
                    valid = false;
                }
            }
            catch (NumberFormatException e) {
                valid = false;
            }

        }
        if (specs.getMaterialTemp() == 1) {
            try {
                double temp = Double.parseDouble(materialTempField.getText());
                if (temp < specs.getMinMaterialTemp() || temp > specs .getMaxMaterialTemp()){
                    valid = false;
                }
            }
            catch (NumberFormatException e) {
                valid = false;
            }

        }

        return valid;
    }

    private boolean getSuggestion(){
        boolean suggestion = true;
        String message = "";

        if (specs.getPressure() == 1  && !pressureBox.paramsInSpec(specs.getMinPressure(), specs.getMaxPressure())) {
                suggestion = false;
                message += "\nPressure : does not meet specs. Min: " + specs.getMinPressure() + " / Max:" +
                       specs.getMaxPressure() + ".";
        }
        if (specs.getBrix() == 1  && !brixBox.paramsInSpec(specs.getMinBrix(), specs.getMaxBrix())) {
            suggestion = false;
            message += "\nBrix : does not meet specs. Min: " + specs.getMinBrix() + " / Max:" +
                       specs.getMaxBrix() + ".";
        }
        if (specs.getLength() == 1  && !lengthBox.paramsInSpec(specs.getMinLength(), specs.getMaxLength())) {
            suggestion = false;
            message += "\nLength : does not meet specs. Min: " + specs.getMinLength() + "mm / Max:" +
                       specs.getMaxLength() + "mm.";
        }
        if (specs.getWidth() == 1  && !widthBox.paramsInSpec(specs.getMinWidth(), specs.getMaxWidth())) {
            suggestion = false;
            message += "\nWidth : does not meet specs. Min: " + specs.getMinWidth() + "mm / Max:" +
                       specs.getMaxWidth() + "mm.";
        }
        if (specs.getColorStage() == 1  && !colorStageBox.paramsInSpec(specs.getMinColorStage(), specs.getMaxColorStage())) {
            suggestion = false;
            message += "\nColour stage: does not meet specs. Min: " + specs.getMinColorStage() + " / Max:" +
                       specs.getMaxColorStage() + ".";
        }
        if (specs.getHeadWeight() == 1  && !headWeightBox.paramsInSpec(specs.getMinHeadWeight(), specs.getMaxHeadWeight())) {
            suggestion = false;
            message += "\nHead weight: does not meet specs. Min: " + specs.getMinHeadWeight() + "g / Max:" +
                       specs.getMaxHeadWeight() + "g.";
        }
        if (specs.getYield() == 1  && (yieldBox.getYield() < specs.getMinYield() || yieldBox.getYield() > specs.getMaxYield())) {
            suggestion = false;
            message += "\nYield: " + yieldBox.getYield();
            message += "% is " + (yieldBox.getYield() < specs.getMinYield() ? "below " : "above ") + " specs: "
                       + specs.getMinYield() + "% - " + specs.getMaxYield() + "%.";
        }
        if (specs.getDensity() == 1  && (densityBox.getDensity() < specs.getMinDensity() || densityBox.getDensity() > specs.getMaxDensity())) {
            suggestion = false;
            message += "\nDensity: " + densityBox.getDensity();
            message += " is " + (densityBox.getDensity() < specs.getMinDensity() ? "below " : "above ") + " specs: "
                       + specs.getMinDensity() + " - " + specs.getMaxDensity() + ".";
        }

        if(specs.getLorryTemp() == 1){
            double temp = Double.parseDouble(lorryTempField.getText());
            if (temp < specs.getMinLorryTemp() || temp > specs.getMaxLorryTemp()){
                suggestion = false;
                message += "\nLorry temp : " + temp + "C, does not meet specs. Min: " + specs.getMinLorryTemp() + "C / Max:" +
                           specs.getMaxLorryTemp() + "C.";
            }
        }

        if(specs.getMaterialTemp() == 1){
            double temp = Double.parseDouble(materialTempField.getText());
            if (temp < specs.getMinMaterialTemp() || temp > specs.getMaxMaterialTemp()){
                suggestion = false;
                message += "\nMaterial temp : " + temp + "C, does not meet specs. Min: " + specs.getMinMaterialTemp() + "C / Max:" +
                           specs.getMaxMaterialTemp() + "C.";
            }
        }

        //TODO Prideti ir quality checkus

        if(!suggestion){
            msg.continueAlert(bottomLeftBox, LabelWithIcons.largeWarningIconLabel("Reject"), new Label("Reasons:\n" + message));
        }else{
            msg.continueAlert(bottomLeftBox, LabelWithIcons.largeCheckIconLabel("Accept"), new Label("Material meets quality standards."));
        }

        return suggestion;
    }

    //Validates text fields are they left blank or not
    private boolean validateAllFields(){

        boolean valid = true;
        String message = "";

        if (specs.getPressure() == 1) {
            message += "\nPressure: Missing inputs";
            valid = false;
        }
        if (specs.getBrix() == 1) {
            message += "\nBrix: Missing inputs";
            valid = false;
        }
        if (specs.getLength() == 1) {
            message += "\nLength: Missing inputs";
            valid = false;
        }
        if (specs.getWidth() == 1) {
            message += "\nWidth: Missing inputs";
            valid = false;
        }
        if (specs.getColorStage() == 1 && !colorStageBox.checkFields()) {
            message += "\nColour stage: Missing inputs";
            valid = false;
        }
        if (specs.getHeadWeight() == 1  && !headWeightBox.checkFields()) {
            message += "\nHead weight: Missing inputs";
            valid = false;
        }
        if(specs.getYield() == 1  && yieldBox.getYield() == 0){
            message +="\nYield: Missing/Wrong inputs";
            valid = false;
        }
        if(specs.getDensity() == 1 && densityBox.getDensity() == 0) {
                message +="\nDensity: Missing/Wrong inputs";
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
        if (specs.getVariety() == 1 ) {
            if(varietyCombo.getSelectionModel().isEmpty()) {
                valid = false;
                message += "\nVariety: Not selected";
            }
        }
        if (specs.getCountry() == 1 ) {
            if (countryCombo.getSelectionModel().isEmpty()) {
                valid = false;
                message += "\nCountry: Not selected";

            }
        }


        // validates text fields
        if (specs.getCount() == 1 && countField.getText().length() <= 0 ) {
            valid = false;
            message += "\nCount: Field left blank";
        }
        if (specs.getGrowerId() == 1 && growerField.getText().length() <= 0) {
            valid = false;
            message += "\nGrower ID: Field left blank";
        }
        if (specs.getHarvestDate() == 1 && harvestDateField.getText().length() <= 0 ) {
            valid = false;
            message += "\nHarvest date: Field left blank";
        }
        if (specs.getLotNumber() == 1 && lotField.getText().length() <= 0) {
            valid = false;
            message += "\nBatch number: Field left blank";
        }
        if (specs.getDay() == 1  && dayField.getText().length() <= 0) {
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
        if (specs.getHealthMark() == 1  && healthMarkField.getText().length() <= 0) {
            valid = false;
            message += "\nHealth mark: Field left blank";
        }
        if (specs.getExpiryDate() == 1  && expiryDateField.getText().length() <= 0) {
            valid = false;
            message += "\nExpiry date: Field left blank";
        }

        if(majorMain.selectedProperty().getValue()){
            if(checkCheckBoxes(majorCbList)){
                valid = false;
                message += "\nMajor foreign bodies: Select one of provided selections";
            }
        }

        if(minorMain.selectedProperty().getValue()){
            if(checkCheckBoxes(minorCbList)){
                valid = false;
                message += "\nNon-Critical quality defects: Select one of provided selections";
            }
        }

        if(criticalMain.selectedProperty().getValue()){
            if(checkCheckBoxes(criticalCbList)){
                valid = false;
                message += "\nCritical quality defects: Select one of provided selections";
            }
        }

        if(!valid){
            msg.continueAlert(expiryDateField, LabelWithIcons.largeWarningIconLabel("Error!"), new Label(message));
        }

        return valid;
    }



    private RmtQaIntakeDetails getIntakeDetails() {

        RmtQaIntakeDetails intakeQaDetails = new RmtQaIntakeDetails();

        intakeQaDetails.setSamples(samplesComboBox.getSelectionModel().getSelectedItem());
        intakeQaDetails.setComments((commentsField.getText() == null ? "" : commentsField.getText()));


        if (specs.getCount() == 1) {
            intakeQaDetails.setCount(countField.getText());
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



        if (specs.getHarvestDate() == 1) {
            intakeQaDetails.setHarvestDate(harvestDateField.getText());
        }

        if (specs.getGrowerId() == 1) {
            intakeQaDetails.setGrower(growerField.getText());
        }

        if (specs.getCountry() == 1) {
            intakeQaDetails.setCountry(countryCombo.getSelectionModel().getSelectedItem().getCountry());
        }

        if (specs.getVariety() == 1) {
            intakeQaDetails.setVariety(varietyCombo.getSelectionModel().getSelectedItem().getVariety());
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
        if (specs.getBrix() == 1){
            intakeQaDetails.setBrix(brixBox.getDoubleList());
        }
        if (specs.getDensity() == 1) {
            intakeQaDetails.setDensity(densityBox.getDensity());
            intakeQaDetails.setDensityGrossWeight(densityBox.getGrossWeight());
            intakeQaDetails.setDensityNetWeight(densityBox.getDiameters());
        }
        if (commentsField.getText().length() > 0) {
            intakeQaDetails.setComments(commentsField.getText());
        }

        if(specs.getYield() == 1){
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
                message += "Harvest/Handling damage\n";
            }
            if (minorDiscoloured.isSelected()) {
                message += "Discoloured/scarring\n";

            }
            if (minorBrowning.isSelected()) {
                message += "Browning/translucency\n";

            }
            if (minorDry.isSelected()) {
                message += "Dry/dehydrated\n";

            }
            if (minorWaterSpotting.isSelected()) {
                message += "Water spotting\n";

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
                message += "Glass or plastic\n";
            }
            if (majorSoiling.isSelected()) {
                message += "Heavy soiling\n";

            }
            if (majorInsects.isSelected()) {
                message += "Insects or pests\n";

            }
            if (majorChemicals.isSelected()) {
                message += "Chemicals\n";

            }
            if (majorAllergen.isSelected()) {
                message += "Allergens";

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
                message += "Excessively wet\n";
            }
            if (criticalPest.isSelected()) {
                message += "Pest or disease damage\n";

            }
            if (criticalMould.isSelected()) {
                message += "Breakdown or mould\n";

            }
            if (criticalSlimey.isSelected()) {
                message += "Slimey\n";

            }
            if (criticalSpongy.isSelected()) {
                message += "Spongy or pithy\n";

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


   
    private void disableCheckBox(JFXCheckBox cb, HBox box) {

        cb.selectedProperty().addListener((observable, oldValue, newValue) -> box.setDisable(!newValue));
    }


    private void initComboBox() {

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(i + 1);
        }
        samplesComboBox.setItems(FXCollections.observableArrayList(list));
        samplesComboBox.getSelectionModel().select(4);
        loadParamBoxes(5);
        samplesComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadParamBoxes(newValue);
        });
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

    private void addAdditionalParamBoxes(){

        if (specs.getDensity() == 1) {
            densityBox = new DensityBox(String.valueOf(specs.getMinDensity()), String.valueOf(specs.getMaxDensity()));
            bottomLeftBox.getChildren().add(densityBox);
        }

        if (specs.getYield() == 1) {
            yieldBox.setTooltip(String.valueOf(specs.getMinYield()), String.valueOf(specs.getMaxYield()));
            bottomLeftBox.getChildren().add(yieldBox);
        }



    }


    private void loadParamBoxesHelper(ParamBox box, String minValue, String maxValue, boolean integer, int noOfInputs) {

        box.setTooltip(minValue, maxValue);
        box.addParamBoxes(noOfInputs, integer);
        vBox.getChildren().add(box);
    }


    private void initCheckBoxesArray() {
        initMajorArray();
        initMinorArray();
        initCriticalArray();
    }


// populates single input fields 
    private void populateSingleEntrySpecs(Map<String, Node> map) {

        topScrollPane.setContent(topGridPane);
        int row = 0;
        for (Map.Entry<String, Node> entry : map.entrySet()) {
            addNodeToGridPane(entry.getKey(), entry.getValue(), 0, row);
            row += 2;
        }
    }


    private void addNodeToGridPane(String labelText, Node node, int col, int row) {

        Label label = new Label(labelText);
        label.getStyleClass().add("sched-form-label");
        topGridPane.add(label, col, row);
        topGridPane.add(node, col + 1, row);
        topGridPane.add(new Separator(), col, row+1,2,1);
    }


    private void addTopParamsToMap() {

        if (specs.getCount() == 1) {
            TEXTFIELDINPUT.intField(countField, true);
            map.put("Count:", countField);
        }
        if (specs.getVariety() == 1) {
            ObservableList<MaterialVarieties> varieties = FXCollections.observableArrayList(new MaterialVarietiesDao().getAll(specs.getMCode()));
            varietyCombo.setItems(varieties);
            map.put("Variety:", varietyCombo);
        }
        if (specs.getCountry() == 1) {
            ObservableList<MaterialCountries> countries = FXCollections.observableArrayList(new MaterialCountryDao().getAll(specs.getMCode()));
            countryCombo.setItems(countries);
            map.put("Country:", countryCombo);
        }
        if (specs.getGrowerId() == 1) {
            TEXTFIELDINPUT.addValidator(growerField);
            map.put("Grower ID:", growerField);
        }
        if (specs.getHarvestDate() == 1) {
            TEXTFIELDINPUT.addValidator(harvestDateField);
            map.put("Harvest date:", harvestDateField);
        }
        if (specs.getLotNumber() == 1) {
            TEXTFIELDINPUT.addValidator(lotField);
            map.put("Batch number:", lotField);
        }
        if (specs.getDay() == 1) {
            TEXTFIELDINPUT.addValidator(dayField);
            map.put("Material day:", dayField);
        }
        if (specs.getRoom() == 1) {
            TEXTFIELDINPUT.addValidator(roomField);
            map.put("Material room:", roomField);
        }
        if (specs.getRtaNumber() == 1) {
            TEXTFIELDINPUT.addValidator(rtaField);
            map.put("RTA certificate:", rtaField);
        }
        if (specs.getGgn() == 1) {
            TEXTFIELDINPUT.addValidator(ggnField);
            map.put("GGN number:", ggnField);
        }
        if (specs.getTwa() == 1) {
            TEXTFIELDINPUT.addValidator(twaField);
            map.put("TWA certificate:", twaField);
        }
        if (specs.getHealthMark() == 1) {
            TEXTFIELDINPUT.addValidator(healthMarkField);
            map.put("Health mark:", healthMarkField);
        }
        if (specs.getExpiryDate() == 1) {
            TEXTFIELDINPUT.addValidator(expiryDateField);
            map.put("Expiry date:", expiryDateField);
        }
        if (specs.getLorryTemp() == 1) {
            TEXTFIELDINPUT.doubleTextField(lorryTempField, true);
            map.put("Lorry temperature:", lorryTempField);
        }

        if (specs.getMaterialTemp() == 1) {
            TEXTFIELDINPUT.doubleTextField(materialTempField, true);
            map.put("Material temperature:", materialTempField);
        }


//        if (specs.getLikeForLike() == 1) {
//            map.put("Like for Like", null);
//        }

    }


    public void getQaRecord() {

        qaRecord.getDetails().setMajor(majorMain.selectedProperty().getValue());
        qaRecord.getDetails().setMinor(minorMain.selectedProperty().getValue());
        qaRecord.getDetails().setCritical(criticalMain.selectedProperty().getValue());

    }


    private void setQualityBoxPercentageLabels(MaterialSpecs specs) {

        criticalValueLabel.setText(String.valueOf(specs.getMaxCritical()));
        majorValueLabel.setText(String.valueOf(specs.getMaxMajor()));
        minorValueLabel.setText(String.valueOf(specs.getMaxMinor()));
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


    private void disableEnableQualityBoxes() {

        disableCheckBox(minorMain, minorBox);
        disableCheckBox(criticalMain, criticalBox);
        disableCheckBox(majorMain, majorBox);
    }



    private void loadComments(){

        commentsField.setText(qaRecord.getDetails().getComments());
    }


}
