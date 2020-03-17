package app.controller.rmt;

import app.controller.sql.dao.Dao;
import app.controller.sql.dao.MaterialCountryDao;
import app.controller.sql.dao.MaterialSpecsDao;
import app.controller.sql.dao.MaterialVarietiesDao;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class MaterialProfileController implements Initializable {

    Materials material = new Materials();

    private MinMaxInput majorBox = new MinMaxInput("Major Foreign bodies", "Glass/Wood/Plastic\nAllergen\nChemicals\nHeavy soiling\nInsects/pests");
    private MinMaxInput minorBox = new MinMaxInput("Non-Critical Defects", "Dry/Dehydrated\nDiscoloured/Scarring\nHarvest or Handling damage\nBrowning/Translucency\nWater spotting");
    private MinMaxInput criticalBox = new MinMaxInput("Critical Quality Defects", "Slimy\nExcessively wet\nSpongy/Pithy\nPest damage/Disease\nBreakdown/Mould");

    private JFXCheckBox density = new JFXCheckBox("Density");
    private MinMaxInput densityBox = new MinMaxInput("Density");

    private JFXCheckBox lorryTemp = new JFXCheckBox("Lorry temperature");
    private MinMaxInput lorryTempBox = new MinMaxInput("Lorry temperature");

    private JFXCheckBox materialTemp = new JFXCheckBox("Material temperature");
    private MinMaxInput materialTempBox = new MinMaxInput("Material temperature");

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

    FileChooser chooser = new FileChooser();

    @FXML
    private JFXMasonryPane masonryPane;

    @FXML
    private StackPane staticContent;

    @FXML
    private JFXListView<JFXCheckBox> paramList;

    @FXML
    private JFXButton fileChooserBtn;

    @FXML
    private JFXButton imageBtn;

    @FXML
    private JFXTextField documentPath;

    @FXML
    private ImageView materialImage = new ImageView();

    @FXML
    ScrollPane scrollPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        imageBtn.setOnAction(event -> {
            try {
                String path = chooser.showOpenDialog(null).toURI().toURL().toString();
                try {
                    addImage(path);
                }
                catch (Exception ignored) {
                }
            }
            catch (MalformedURLException e) {
                e.printStackTrace();

            }
        });

        fileChooserBtn.setOnAction(event -> {
          testas();

        });

        Platform.runLater(() -> scrollPane.requestLayout());


        addListItems();
        relateCheckBoxToNode();
        qualityBoxTooltip();


    }
    private void testas(){
        System.out.println("veikia");
        MaterialSpecs specs = new MaterialSpecsDao().get("M801");
        System.out.println(specs.toStringz());
    }


    private void addListItems() {

        paramList.getItems().addAll(density, lorryTemp, materialTemp, brix, pressure, length,
                width, colorStage, headWeight, yield, variety, country, growerID, harvestDate, likeForLike,
                lotNumber, day, room, redTractorNumber, ggn, twa, healthMark, expiryDate);
    }


    private void addImage(String path) {

        Image image1 = new Image(path);
        materialImage.setImage(image1);

    }

    private void relateCheckBoxToNode(){

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
            if (newValue) {
                Platform.runLater(() ->  masonryPane.getChildren().add(node));

            }
            else {
                masonryPane.getChildren().remove(node);
            }
        });
    }

        //TODO add proper m code extraction
    private ParamListController countryBox(){

        ParamListController box = new ParamListController("Country");
        MaterialCountryDao  dao = new MaterialCountryDao();

        box.reloadList(dao.getAll("M888"));
        box.removeBtn.setOnAction(event -> {
            if (box.getSelectedItem() != null) {
                deleteAlertBox(box, new MaterialCountryDao());
            }
        });

        box.addBtn.setOnAction(event -> {
            MaterialCountries variety = new MaterialCountries("M888", box.getText());
            saveAlertBox(box, dao, variety);

        });
        return box;
    }


    private ParamListController varietyBox(){

        ParamListController box = new ParamListController("Varieties");
        MaterialVarietiesDao dao = new MaterialVarietiesDao();

        box.reloadList(dao.getAll("M888"));
        box.removeBtn.setOnAction(event -> {
            if (box.getSelectedItem() != null ) {
                deleteAlertBox(box, dao);
            }
        });
        box.addBtn.setOnAction(event -> {
            MaterialVarieties variety = new MaterialVarieties("M888", box.getText());
            saveAlertBox(box, dao, variety);
        });
        return box;
    }





    private <T> void deleteAlertBox(ParamListController box, Dao<T> dao){

        JFXAlert<String> alert = new JFXAlert<>((Stage) box.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("You are about to delete following entry:"));
        layout.setBody(new Label(box.getSelectedItem().toString()));
        JFXButton delete = new JFXButton("CONTINUE");
        JFXButton cancel = new JFXButton("CANCEL");

        delete.setOnAction(e ->{
            dao.delete((T) box.getSelectedItem());
            box.clearList();
            box.clearSelectedLabel();
            box.removeBtn.setDisable(true);
            box.reloadList(dao.getAll("M888"));
            alert.hideWithAnimation();

        });

        cancel.setOnAction(b->{
            alert.hideWithAnimation();
        });
        layout.setActions(delete,cancel);
        alert.setContent(layout);
        alert.show();
    }

    private <T> void saveAlertBox(ParamListController box, Dao<T> dao, Object obj){

        JFXAlert<String> alert = new JFXAlert<>((Stage) staticContent.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("You are about to add following entry:"));
        layout.setBody(new Label(box.getText()));
        JFXButton continueBtn = new JFXButton("CONTINUE");
        JFXButton cancel = new JFXButton("CANCEL");

        continueBtn.setOnAction(e ->{
            dao.save((T) obj);
            box.clearList();
            box.clearText();
            box.reloadList(dao.getAll("M888"));
            alert.hideWithAnimation();

        });

        cancel.setOnAction(b->{
            alert.hideWithAnimation();
        });
        layout.setActions(continueBtn,cancel);
        alert.setContent(layout);
        alert.show();
    }


    private void qualityBoxTooltip(){

        minorBox.setMin("0");
        minorBox.disableMinField();
        majorBox.setMin("0");
        majorBox.disableMinField();
        criticalBox.setMin("0");
        criticalBox.disableMinField();

    }




}
