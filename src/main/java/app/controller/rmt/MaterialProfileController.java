package app.controller.rmt;

import app.controller.sql.dao.Dao;
import app.controller.sql.dao.MaterialCountryDAO;
import app.controller.sql.dao.MaterialVarietiesDao;
import app.pojos.MaterialCountries;
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

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class MaterialProfileController implements Initializable {

    Materials material = new Materials();

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
                addImage(path);
            }
            catch (MalformedURLException e) {
                e.printStackTrace();

            }
        });

        fileChooserBtn.setOnAction(event -> {


        });

        Platform.runLater(() -> scrollPane.requestLayout());


        addListItems();
        relateCheckToNode();

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

    private void relateCheckToNode(){

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
        MaterialCountryDAO dao = new MaterialCountryDAO();

        box.reloadList(dao.getAll("M888"));
        box.removeBtn.setOnAction(event -> {
            if (box.getSelectedItem() != null) {
                deleteAlertBox(box, new MaterialCountryDAO());
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




}
