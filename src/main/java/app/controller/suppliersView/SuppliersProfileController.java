package app.controller.suppliersView;

import app.controller.sql.SQLiteJDBC;
import app.controller.sql.dao.MaterialsDAO;
import app.controller.sql.dao.SupplierMaterialsDAO;
import app.pojos.SuppEmails;
import app.pojos.SuppNumbers;
import app.pojos.SupplierMaterials;
import app.pojos.Suppliers;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RegexValidator;
import com.sun.org.apache.xpath.internal.objects.XNodeSet;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class SuppliersProfileController implements Initializable {

    private static final String FX_LABEL_FLOAT_TRUE = "-fx-label-float:true;";
    private static final String ERROR = "error";

    JFXDialog dialog = new JFXDialog();

    Suppliers supplier;

    @FXML
    Label supplierName;
    @FXML
    Label materialName;

    @FXML
    JFXListView<SuppEmails> emailsList;
    @FXML
    JFXTextField newEmailField;
    @FXML
    JFXButton saveNewEmail;
    @FXML
    JFXButton deleteEmail;


    @FXML
    JFXListView<SupplierMaterials> materialsList;


    @FXML
    JFXListView<SuppNumbers> numbersList;
    @FXML
    JFXTextField contactNameField;
    @FXML
    JFXTextField newPhoneNumber;
    @FXML
    JFXButton deletePhoneNumber;
    @FXML
    JFXButton addContact;





    public SuppliersProfileController() {

    }


    public SuppliersProfileController(Suppliers supplier) {
        this.supplier = supplier;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        supplierName.setText(supplier.getSupplierName());


        loadMaterials();

        buttonsListeners();
        emailBox();


    }

    private void buttonsListeners(){
        saveNewEmail.setOnAction(event -> {
            if(newEmailField.validate()){
                System.out.println("veikia emailai");
            }else{




                JFXAlert<Void> alert = new JFXAlert<>((Stage) saveNewEmail.getScene().getWindow());
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setOverlayClose(false);
                JFXDialogLayout layout = new JFXDialogLayout();
                Label label = new Label("Incorrect email address");
                FontAwesomeIconView iconView = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
                iconView.setStyle("-fx-fill: red; -glyph-size: 20px");

                label.setGraphic(iconView);
                label.setStyle("-fx-font-size: 15px; -fx-cell-size: 100px");
                layout.setHeading(label);
                layout.setBody(new Label(( newEmailField.getText())));
                JFXButton closeButton = new JFXButton("ACCEPT");

                closeButton.getStyleClass().add("dialog-accept");
                closeButton.setOnAction(e -> {
                    alert.hideWithAnimation();
                    newEmailField.clear();
                    newEmailField.setPromptText("Enter valid email address");
                });
                layout.setActions(closeButton);
                alert.setContent(layout);
                alert.show();

            }
        });
    }





    public void emailBox(){

        newEmailField.setStyle(FX_LABEL_FLOAT_TRUE);

        RegexValidator valid = new RegexValidator();

        valid.setRegexPattern("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                              + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION);
        icon.setStyle("-fx-fill: red");
        Label label = new Label("Invalid email!");
        label.setGraphic(icon);
        valid.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        icon.setStyleClass(ERROR);
        valid.setIcon(label);
        newEmailField.getValidators().add(valid);

        this.doEmailValidate();

    }

    private void doEmailValidate(){

        newEmailField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                newEmailField.validate();
            }
            if (oldValue) {
                newEmailField.validate();
            }
        });


    }

    private void loadMaterials(){

        SQLiteJDBC.close();


        ObservableList<SupplierMaterials> list = FXCollections.observableArrayList(new SupplierMaterialsDAO().getAllSupplierMaterials(supplier.getSupplierCode()));
        materialsList.setItems(list);

        materialsList.setCellFactory(param -> new JFXListCell<SupplierMaterials>() {

            final Tooltip content = new Tooltip();

            @Override
            protected void updateItem(SupplierMaterials item, boolean empty) {

                super.updateItem(item, empty);


                if (item == null) {
                    setText("");
                    content.setText("");
                }
                else {

                    materialsList.setDepth(5);
                    materialsList.setExpanded(true);
                    materialsList.getSelectionModel().selectFirst();

                    if(item.getPalletWeight()<=0){
                        FontAwesomeIconView iconView = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION);
                        iconView.setStyle("-fx-fill: red");
                        setStyle("-fx-background-color: rgb(255,174,179)");
                        setGraphic(iconView);
                    }


                    setText(new MaterialsDAO().get(item.getmCode()).getName());
                    content.setText("Material: " + new MaterialsDAO().get(item.getmCode()).getName() +
                                    "\nM code: " + item.getmCode());
                    setTooltip(content);
                }

            }
        });

    }


    private void showAllertMessage(){

    }


}