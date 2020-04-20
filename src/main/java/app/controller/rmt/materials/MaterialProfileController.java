package app.controller.rmt.materials;

import app.controller.rmt.records.SpecsIntakePane;
import app.controller.sql.dao.*;
import app.controller.utils.LabelWithIcons;
import app.controller.utils.Messages;
import app.controller.utils.OpenFile;
import app.model.Material;
import app.pojos.MaterialCountries;
import app.pojos.MaterialSpecs;
import app.pojos.MaterialVarieties;
import app.pojos.Materials;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;


public class MaterialProfileController implements Initializable {


    private String imagePath;

    private Material material = null;

    private MaterialListDrawerController drawerController = null;

    private Messages msg = new Messages();


    private MinMaxInput majorBox = new MinMaxInput("Major Foreign bodies", "Glass/Wood/Plastic\nAllergen\nChemicals\nHeavy soiling\nInsects/pests");

    private MinMaxInput minorBox = new MinMaxInput("Non-Critical Defects", "Dry/Dehydrated\nDiscoloured/Scarring\nHarvest or Handling damage\nBrowning/Translucency\nWater spotting");

    private MinMaxInput criticalBox = new MinMaxInput("Critical Quality Defects", "Slimy\nExcessively wet\nSpongy/Pithy\nPest damage/Disease\nBreakdown/Mould");

    private JFXCheckBox density = new JFXCheckBox("Density");

    private MinMaxInput densityBox = new MinMaxInput("Density", "Density parameter will be required to record during quality checks");

    private JFXCheckBox lorryTemp = new JFXCheckBox("Lorry temperature");

    private MinMaxInput lorryTempBox = new MinMaxInput("Lorry temperature", "Min and Max values is required");

    private JFXCheckBox materialTemp = new JFXCheckBox("Material temperature");

    private MinMaxInput materialTempBox = new MinMaxInput("Material temperature", "Material temperature check\n min and max values will used during quality checks");

    private JFXCheckBox brix = new JFXCheckBox("Brix");

    private MinMaxInput brixBox = new MinMaxInput("Brix");

    private JFXCheckBox pressure = new JFXCheckBox("Pressure");

    private MinMaxInput pressureBox = new MinMaxInput("Pressure");

    private JFXCheckBox length = new JFXCheckBox("Length");

    private MinMaxInput lengthBox = new MinMaxInput("Length");

    private JFXCheckBox width = new JFXCheckBox("Width");

    private MinMaxInput widthBox = new MinMaxInput("Width");

    private JFXCheckBox colorStage = new JFXCheckBox("Color stage");

    private MinMaxInput colorStageBox = new MinMaxInput("Color stage");

    private JFXCheckBox headWeight = new JFXCheckBox("Head weight");

    private MinMaxInput headWeightBox = new MinMaxInput("Head weight");

    private JFXCheckBox yield = new JFXCheckBox("Yield");

    private MinMaxInput yieldBox = new MinMaxInput("Yield");

    private JFXCheckBox variety = new JFXCheckBox("Variety");

    private JFXCheckBox country = new JFXCheckBox("Country");

    private JFXCheckBox growerID = new JFXCheckBox("Grower ID");

    private JFXCheckBox harvestDate = new JFXCheckBox("Harvest date");

    private JFXCheckBox lotNumber = new JFXCheckBox("Lot number");

    private JFXCheckBox day = new JFXCheckBox("Day");

    private JFXCheckBox room = new JFXCheckBox("Room");

    private JFXCheckBox likeForLike = new JFXCheckBox("Like for like");

    private JFXCheckBox redTractorNumber = new JFXCheckBox("RTA number");

    private JFXCheckBox ggn = new JFXCheckBox("GGN number");

    private JFXCheckBox twa = new JFXCheckBox("TWA");

    private JFXCheckBox healthMark = new JFXCheckBox("Health mark");

    private JFXCheckBox expiryDate = new JFXCheckBox("Expiry date");

    private JFXCheckBox count = new JFXCheckBox("Material count");



    FileChooser fileChooser = new FileChooser();

    @FXML
    private Label paramLabel;

    @FXML
    private JFXMasonryPane masonryPane;

    @FXML
    private StackPane staticContent;

    @FXML
    private StackPane paramPane;

    @FXML
    private VBox paramListBox;

    @FXML
    private JFXListView<JFXCheckBox> paramList;

    @FXML
    private JFXButton fileChooserBtn;

    @FXML
    private JFXButton openDocBtn;

    @FXML
    private JFXButton imageBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton saveParamBtn;


    @FXML
    private JFXTextField documentPathField = new JFXTextField();

    @FXML
    private JFXTextField mCodeField = new JFXTextField();

    @FXML
    private JFXTextField materialNameField = new JFXTextField();

    @FXML
    private JFXTextArea shortDescription = new JFXTextArea();


    @FXML
    private ImageView materialImage = new ImageView();

    @FXML
    ScrollPane scrollPane;


    public MaterialProfileController(MaterialListDrawerController drawer) {

        this.drawerController = drawer;


    }


    public MaterialProfileController(Materials materials, MaterialListDrawerController drawer) {

        this.drawerController = drawer;
        this.material = new Material();
        this.material.setMaterial(new MaterialsDao().get(materials.getMCode()));
        this.material.setSpecs(new MaterialSpecsDao().get(materials.getMCode()));
        imagePath = this.material.getMaterial().getImagePath();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addListItems();

        relateCheckBoxToNode();

        qualityBoxTooltip();

        btnActions();


        if (material != null) {
            loadMaterialDetails();
            initializeCheckBoxes(material.getSpecs());
            mCodeField.setDisable(true);

        }
        else {
            paramPane.setDisable(true);
            paramListBox.setDisable(true);
        }

        Platform.runLater(() -> scrollPane.requestLayout());
    }


    //  CHECKBOX'ES AND APPROPRIATE NODES
    private void addListItems() {

        paramList.getItems().addAll(density, lorryTemp, materialTemp, brix, pressure, length,
                width, colorStage, headWeight, yield, variety, country, count, growerID, harvestDate, likeForLike,
                lotNumber, day, room, redTractorNumber, ggn, twa, healthMark, expiryDate);
    }


    private void relateCheckBoxToNode() {

        masonryPane.getChildren().add(majorBox);
        masonryPane.getChildren().add(minorBox);
        masonryPane.getChildren().add(criticalBox);
        addParamNode(density, densityBox);
        addParamNode(lorryTemp, lorryTempBox);
        addParamNode(materialTemp, materialTempBox);
        addParamNode(brix, brixBox);
        addParamNode(pressure, pressureBox);
        addParamNode(length, lengthBox);
        addParamNode(width, widthBox);
        addParamNode(colorStage, colorStageBox);
        addParamNode(headWeight, headWeightBox);
        addParamNode(yield, yieldBox);
        addParamNode(country, countryBox());
        addParamNode(variety, varietyBox());
        addParamNode(twa, new InformationNode("TWA input will be required"));
        addParamNode(count, new InformationNode("Material count will be required"));
        addParamNode(redTractorNumber, new InformationNode("RTA input will be required"));
        addParamNode(ggn, new InformationNode("GGN number will be required"));
        addParamNode(growerID, new InformationNode("Grower ID will be required"));
        addParamNode(expiryDate, new InformationNode("Expiry date will be required"));
        addParamNode(harvestDate, new InformationNode("Harvest date will be required"));
        addParamNode(lotNumber, new InformationNode("Dry stock batch number will be required"));
        addParamNode(day, new InformationNode("Material day will be required"));
        addParamNode(room, new InformationNode("Material room will be required"));
        addParamNode(healthMark, new InformationNode("Health mark will be required"));
        addParamNode(likeForLike, new InformationNode("\"Like for like\" check will be required"));


    }


    private void addParamNode(JFXCheckBox radioButton, Node node) {
        radioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            minorBox.getMaxField().requestFocus();
            if (newValue) {
                Platform.runLater(() -> scrollPane.requestLayout());
                masonryPane.getChildren().add(node);
            }
            else {
                masonryPane.getChildren().remove(node);
            }
            scrollPane.requestFocus();

        });

    }


    private ParamListController countryBox() {

        ParamListController    box = new ParamListController("Countries");
        Dao<MaterialCountries> dao = new MaterialCountryDao();

        if (material != null) {
            String mCode = material.getMaterial().getMCode();

            box.reloadList(dao.getAll(mCode));
            box.removeBtn.setOnAction(event -> {
                if (box.getSelectedItem() != null) {
                    msg.materialProfileControllerDelete(box, dao, mCode);
                }
            });

            box.addBtn.setOnAction(event -> {
                if (box.getText().trim().length() <= 1) {
                    showAlertWindow(box);
                    box.clearText();
                }
                else {
                    MaterialCountries country = new MaterialCountries(mCode, box.getText());
                    msg.materialProfileControllerSaveMessage(box, dao, country, mCode);
                }

            });
        }


        return box;
    }


    private ParamListController varietyBox() {

        ParamListController  box = new ParamListController("Varieties");
        MaterialVarietiesDao dao = new MaterialVarietiesDao();


        if (material != null) {
            String mCode = material.getMaterial().getMCode();

            box.reloadList(dao.getAll(mCode));
            box.removeBtn.setOnAction(event -> {
                if (box.getSelectedItem() != null) {
                    msg.materialProfileControllerDelete(box, dao, mCode);
                }
            });
            box.addBtn.setOnAction(event -> {
                if (box.getText().trim().length() <= 1) {
                    showAlertWindow(box);
                    box.clearText();
                }
                else {
                    MaterialVarieties variety = new MaterialVarieties(mCode, box.getText());
                    msg.materialProfileControllerSaveMessage(box, dao, variety, mCode);
                }
            });
        }

        return box;
    }


    private void showAlertWindow(ParamListController box) {

        final String HEADING = "Failed to add following entry:";
        final String BODY = "'" + box.getText() +
                            "'\nentry is ether to short (min 2 characters, excluding blank spaces) or input field was left blank";
        msg.continueAlert(saveBtn, HEADING, BODY);
    }


    //  DEFECTS BOXes
    private void qualityBoxTooltip() {

        minorBox.setMin("0");
        minorBox.disableMinField();
        majorBox.setMin("0");
        majorBox.disableMinField();
        criticalBox.setMin("0");
        criticalBox.disableMinField();

    }


    // RE-SELECTS CHECK BOXes AND ASSIGNS VALUES TO MIN_MAX BOXes
    private void initializeCheckBoxes(MaterialSpecs specs) {

        if (specs != null) {

            majorBox.setMax(String.valueOf(specs.getMaxMajor()));
            minorBox.setMax(String.valueOf(specs.getMaxMinor()));
            criticalBox.setMax(String.valueOf(specs.getMaxCritical()));

            if (specs.getDensity() == 1) {
                density.selectedProperty().setValue(true);
                initMinMaxBox(densityBox, specs.getMaxDensity(), specs.getMinDensity());

            }
            if (specs.getLorryTemp() == 1) {
                lorryTemp.selectedProperty().setValue(true);
                initMinMaxBox(lorryTempBox, specs.getMaxLorryTemp(), specs.getMinLorryTemp());
            }
            if (specs.getMaterialTemp() == 1) {
                materialTemp.selectedProperty().setValue(true);
                initMinMaxBox(materialTempBox, specs.getMaxMaterialTemp(), specs.getMinMaterialTemp());
            }
            if (specs.getBrix() == 1) {
                brix.selectedProperty().setValue(true);
                initMinMaxBox(brixBox, specs.getMaxBrix(), specs.getMinBrix());
            }
            if (specs.getPressure() == 1) {
                pressure.selectedProperty().setValue(true);
                initMinMaxBox(pressureBox, specs.getMaxPressure(), specs.getMinPressure());
            }
            if (specs.getLength() == 1) {
                length.selectedProperty().setValue(true);
                initMinMaxBox(lengthBox, specs.getMaxLength(), specs.getMinLength());
            }
            if (specs.getWidth() == 1) {
                width.selectedProperty().setValue(true);
                initMinMaxBox(widthBox, specs.getMaxWidth(), specs.getMinWidth());
            }
            if (specs.getColorStage() == 1) {
                colorStage.selectedProperty().setValue(true);
                initMinMaxBox(colorStageBox, specs.getMaxColorStage(), specs.getMinColorStage());
            }
            if (specs.getHeadWeight() == 1) {
                headWeight.selectedProperty().setValue(true);
                initMinMaxBox(headWeightBox, specs.getMaxHeadWeight(), specs.getMinHeadWeight());
            }
            if (specs.getYield() == 1) {
                yield.selectedProperty().setValue(true);
                initMinMaxBox(yieldBox, specs.getMaxYield(), specs.getMinYield());

            }
            if (specs.getVariety() == 1) {
                variety.selectedProperty().setValue(true);

            }
            if (specs.getCountry() == 1) {
                country.selectedProperty().setValue(true);
            }
            if (specs.getGrowerId() == 1) {
                growerID.selectedProperty().setValue(true);
            }
            if (specs.getHarvestDate() == 1) {
                harvestDate.selectedProperty().setValue(true);
            }
            if (specs.getLikeForLike() == 1) {
                likeForLike.selectedProperty().setValue(true);
            }
            if (specs.getLotNumber() == 1) {
                lotNumber.selectedProperty().setValue(true);
            }
            if (specs.getDay() == 1) {
                day.selectedProperty().setValue(true);
            }
            if (specs.getRoom() == 1) {
                room.selectedProperty().setValue(true);
            }
            if (specs.getRtaNumber() == 1) {
                redTractorNumber.selectedProperty().setValue(true);
            }
            if (specs.getGgn() == 1) {
                ggn.selectedProperty().setValue(true);
            }
            if (specs.getTwa() == 1) {
                twa.selectedProperty().setValue(true);
            }
            if (specs.getHealthMark() == 1) {
                healthMark.selectedProperty().setValue(true);
            }
            if (specs.getExpiryDate() == 1) {
                expiryDate.selectedProperty().setValue(true);
            }
            if(specs.getCount() == 1){
                count.selectedProperty().setValue(true);
            }

        }
    }


    private <T> void initMinMaxBox(MinMaxInput minMax, T max, T min) {

        minMax.setMax(String.valueOf(max));
        minMax.setMin(String.valueOf(min));
    }


    //  LOADS MATERIAL DATA
    private void loadMaterialDetails() {

        Materials mat = this.material.getMaterial();
        if (mat != null) {
            if (mat.getName() != null && ! mat.getName().equalsIgnoreCase("")) {
                paramLabel.setText(mat.getName() + " details");
            }

            setDocLink(mat.getDocLink());
            if (mat.getMCode().length() > 2) {
                setMCode(mat.getMCode());
            }

            setMatName(mat.getName());
            setImage(mat.getImagePath());
            setShortDescription(mat.getDescription());
        }

    }


    private void setDocLink(String docLink) {

        if (docLink != null) {
            documentPathField.clear();
            documentPathField.setText(docLink);
        }
    }


    private void setMCode(String mCode) {

        if (mCode != null) {
            mCodeField.setText(mCode);
        }

    }


    private void setMatName(String name) {

        if (name != null) {
            materialNameField.setText(name);
        }
    }


    private void setImage(String path) {

        String placeholder = "";

        String url;
        try {
            placeholder = getClass().getResource("/images/missing_image_placeholder.jpg").toURI().toURL().toString();

            if (path != null && path.length() > 20) {
                url = new File(path).toURI().toURL().toString();
            }
            else {
                url = getClass().getResource("/images/missing_image_placeholder.jpg").toURI().toURL().toString();
            }

        }
        catch (MalformedURLException | NullPointerException | URISyntaxException e) {
            System.out.println("Error at: MaterialProfileController.setImage()");
            url = placeholder;

        }
        Image image = new Image(url);
        materialImage.setImage(image);
    }


    private void setShortDescription(String description) {

        shortDescription.clear();
        shortDescription.setText(description);

    }


    //  BUTTON LISTENERS
    private void btnActions() {
        //adds image
        imageBtn.setOnAction(event -> {

            try {
                String path = fileChooser.showOpenDialog(null).toString();
                imagePath = path;
                setImage(path);
                material.getMaterial().setImagePath(path);
            }
            catch (Exception ignored) {
                System.out.println("Canceled adding image...");
            }
        });

        //adds file
        fileChooserBtn.setOnAction(event -> {
            try {
                String path = fileChooser.showOpenDialog(null).toString();
                material.getMaterial().setDocLink(path);
                setDocLink(path);
            }
            catch (Exception e) {
                System.out.println("Canceled adding file...");

                e.printStackTrace();
            }
        });
        // opens file
        openDocBtn.setOnAction(event -> OpenFile.openFile(documentPathField.getText()));

        //SAVES MATERIAL DATA
        saveBtn.setOnAction(event -> {
            saveMaterial();
            drawerController.loadList();
        });
        saveParamBtn.setOnAction(event -> saveSpec());

    }


    private void saveMaterial() {

        boolean saved;
        Label   heading;
        Label   body;
        if (material != null) {
            Materials temp = generateMaterial();
            saved = new MaterialsDao().update(temp);
            body = new Label(temp.getName() + ": was updated");
        }
        else {
            Materials temp = generateMaterial();
            saved = new MaterialsDao().save(generateMaterial());
            body = new Label(temp.getName() + ": was saved");
        }
        if (saved) {
            heading = LabelWithIcons.largeCheckIconLabel("Success");

        }
        else {
            heading = LabelWithIcons.largeWarningIconLabel("Error");
            body = new Label(
                    "Failed to validate material details provided, please ensure there is no underlying errors" +
                    "\n Most likely M code is used by another material");

        }
        msg.continueAlert(saveParamBtn, heading, body);
    }


    private Materials generateMaterial() {

        Materials mat = new Materials();
        mat.setMCode(mCodeField.getText().trim().toUpperCase());
        if (shortDescription.getText() != null) {
            mat.setDescription(shortDescription.getText().toUpperCase().trim());
        }
        mat.setName(materialNameField.getText().toUpperCase().trim());
        mat.setDocLink(documentPathField.getText().trim());
        mat.setImagePath(imagePath);

        return mat;
    }


    private void saveSpec() {

        MaterialSpecs specs = generateSpecs();

        if (specs == null) {
            final String HEADING = "Error";
            final String BODY    = "Failed to validate specs provided, please ensure there is no underlying errors";
            msg.continueAlert(saveParamBtn, HEADING, BODY);
        }

        else {
            boolean b = new MaterialSpecsDao().save(specs);
            if (! b) {
                boolean c = new MaterialSpecsDao().update(specs);
                if (! c) {
                    final String HEADING = "Error";
                    final String BODY    = "Failed to save specs";
                    msg.continueAlert(saveBtn, HEADING, BODY);
                }
                else {
                    final String HEADING = "Success";
                    final String BODY    = "Successfully updated specification records";
                    msg.continueAlert(saveBtn, HEADING, BODY);
                }
            }
            else {
                final String HEADING = "Success";
                final String BODY    = "Successfully saved specification records";
                msg.continueAlert(saveBtn, HEADING, BODY);
            }
        }
    }


    //checks for errors in specs fields, returns specs object if no errors recorded else returns null
    private MaterialSpecs generateSpecs() {

        MaterialSpecs specs = new MaterialSpecs();
        boolean       error = false;


        if (mCodeField.getText() != null) {
            specs.setMCode(mCodeField.getText());
        }
        else {
            error = true;
        }

        if (majorBox.getMaxInt() > 0) {
            specs.setMaxMajor(majorBox.getMaxInt());
        }
        else {
            error = true;
        }

        if (criticalBox.getMaxInt() > 0) {
            specs.setMaxCritical(criticalBox.getMaxInt());
        }
        else {
            error = true;
        }
        if (minorBox.getMaxInt() > 0) {
            specs.setMaxMinor(minorBox.getMaxInt());
        }
        else {
            error = true;
        }

        specs.setCountry(boolToInt(country.isSelected()));
        specs.setVariety(boolToInt(variety.isSelected()));
        specs.setTwa(boolToInt(twa.isSelected()));
        specs.setCount(boolToInt(count.isSelected()));
        specs.setHealthMark(boolToInt(healthMark.isSelected()));
        specs.setExpiryDate(boolToInt(expiryDate.isSelected()));
        specs.setGgn(boolToInt(ggn.isSelected()));
        specs.setRtaNumber(boolToInt(redTractorNumber.isSelected()));
        specs.setRoom(boolToInt(room.isSelected()));
        specs.setDay(boolToInt(day.isSelected()));
        specs.setLotNumber(boolToInt(lotNumber.isSelected()));
        specs.setLikeForLike(boolToInt(likeForLike.isSelected()));
        specs.setHarvestDate(boolToInt(harvestDate.isSelected()));
        specs.setGrowerId(boolToInt(growerID.isSelected()));


        specs.setHeadWeight(boolToInt(headWeight.isSelected()));
        specs.setYield(boolToInt(yield.isSelected()));
        specs.setColorStage(boolToInt(colorStage.isSelected()));
        specs.setWidth(boolToInt(width.isSelected()));
        specs.setLength(boolToInt(length.isSelected()));
        specs.setPressure(boolToInt(pressure.isSelected()));
        specs.setBrix(boolToInt(brix.isSelected()));
        specs.setMaterialTemp(boolToInt(materialTemp.isSelected()));
        specs.setLorryTemp(boolToInt(lorryTemp.isSelected()));
        specs.setDensity(boolToInt(density.isSelected()));


        if (yield.isSelected()) {

            if (yieldBox.getMinInt() >= 0 && yieldBox.getMaxInt() > yieldBox.getMinInt()) {
                specs.setMinYield(yieldBox.getMinInt());
                specs.setMaxYield(yieldBox.getMaxInt());
            }
            else {
                error = true;
            }
        }

        if (headWeight.isSelected()) {
            if (headWeightBox.getMinInt() >= 0 && headWeightBox.getMaxInt() > headWeightBox.getMinInt()) {
                specs.setMinHeadWeight(headWeightBox.getMinInt());
                specs.setMaxHeadWeight(headWeightBox.getMaxInt());
            }
            else {
                error = true;
            }
        }

        if (colorStage.isSelected()) {
            if (colorStageBox.getMinInt() >= 0 && colorStageBox.getMaxInt() > colorStageBox.getMinInt()) {
                specs.setMinColorStage(colorStageBox.getMinInt());
                specs.setMaxColorStage(colorStageBox.getMaxInt());
            }
            else {
                error = true;
            }
        }

        if (width.isSelected()) {
            if (widthBox.getMinInt() >= 0 && widthBox.getMaxInt() > widthBox.getMinInt()) {
                specs.setMinWidth(widthBox.getMinInt());
                specs.setMaxWidth(widthBox.getMaxInt());
            }
            else {
                error = true;
            }
        }

        if (length.isSelected()) {
            if (lengthBox.getMinInt() >= 0 && lengthBox.getMaxInt() > lengthBox.getMinInt()) {
                specs.setMinLength(lengthBox.getMinInt());
                specs.setMaxLength(lengthBox.getMaxInt());
            }
            else {
                error = true;
            }
        }

        if (pressure.isSelected()) {
            if (pressureBox.getMin() >= 0 && pressureBox.getMax() > pressureBox.getMin()) {
                specs.setMinPressure(pressureBox.getMin());
                specs.setMaxPressure(pressureBox.getMax());
            }
            else {
                error = true;
            }
        }

        if (brix.isSelected()) {
            if (brixBox.getMin() >= 0 && brixBox.getMax() > brixBox.getMin()) {
                specs.setMinBrix(brixBox.getMin());
                specs.setMaxBrix(brixBox.getMax());
            }
            else {
                error = true;
            }
        }

        if (materialTemp.isSelected()) {
            if (materialTempBox.getMin() >= 0 &&
                materialTempBox.getMax() > materialTempBox.getMin()) {
                specs.setMinMaterialTemp(materialTempBox.getMin());
                specs.setMaxMaterialTemp(materialTempBox.getMax());
            }
            else {
                error = true;
            }
        }

        if (lorryTemp.isSelected()) {
            if (lorryTempBox.getMin() >= 0 && lorryTempBox.getMax() > lorryTempBox.getMin()) {
                specs.setMinLorryTemp(lorryTempBox.getMin());
                specs.setMaxLorryTemp(lorryTempBox.getMax());
            }
            else {
                error = true;
            }
        }

        if (brix.isSelected()) {
            if (brixBox.getMin() >= 0 && brixBox.getMax() > brixBox.getMin()) {
                specs.setMinBrix(brixBox.getMin());
                specs.setMaxBrix(brixBox.getMax());
            }
            else {
                error = true;
            }
        }

        if (density.isSelected()) {
            if (densityBox.getMinInt() >= 0 && densityBox.getMaxInt() > densityBox.getMinInt()) {
                specs.setMinDensity(densityBox.getMinInt());
                specs.setMaxDensity(densityBox.getMaxInt());
            }
            else {
                error = true;
            }
        }


        if (! error) {
            return specs;
        }
        else {
            return null;
        }

    }


    //  changes bool to int // true = 1, false = 0
    private int boolToInt(boolean b) {

        return b ? 1 : 0;
    }

}
