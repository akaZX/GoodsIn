package app.controller.suppliersView;

import app.controller.sql.SQLiteJDBC;
import app.controller.sql.dao.MaterialsDao;
import app.controller.sql.dao.SuppEmailsDao;
import app.controller.sql.dao.SupplierMaterialsDao;
import app.controller.utils.LabelWithIcons;
import app.controller.utils.Messages;
import app.controller.utils.ValidateInput;
import app.pojos.*;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RegexValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

import java.net.URL;
import java.util.ResourceBundle;


public class SuppliersProfileController implements Initializable {

    private static final String FX_LABEL_FLOAT_TRUE = "-fx-label-float:true;";

    private static final String ERROR = "error";

    Messages msg = new Messages();

    Suppliers supplier = null;

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


    @FXML
    Label materialLabel;
    @FXML
    JFXButton addMaterialBtn;
    @FXML
    JFXButton updateWeightBtn;
    @FXML
    JFXComboBox<Materials> materialsComboBox;


    public SuppliersProfileController() {

    }


    public SuppliersProfileController(Suppliers supplier) {

        this.supplier = supplier;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        buttonsListeners();
        emailBox();

        if (supplier != null) {
            loadSupplier();
        }

    }

    private void loadSupplier(){
        String supp = supplier.getSupplierCode() + " - " + supplier.getSupplierName();
        supplierName.setText(supp);
        loadMaterials();
        loadEmails();
    }


    private void buttonsListeners() {

        deleteEmail.setOnAction(event -> {
            if (emailsList.getSelectionModel().getSelectedItem() != null) {
                new SuppEmailsDao().delete(emailsList.getSelectionModel().getSelectedItem());
                loadEmails();
            }
        });
        saveNewEmail.setOnAction(event -> {
            if (newEmailField.validate()) {
                saveNewEmail();
                loadEmails();
            }
            else {

                String heading = "Incorrect email address";
                String body = "Email: '" + newEmailField.getText() + "' is not valid";
                msg.continueAlert(saveNewEmail, LabelWithIcons.midWarningIconLabel(heading), new Label(body));
                newEmailField.clear();
                newEmailField.setPromptText("Enter valid email address");
            }
        });
    }


    public void emailBox() {

        ValidateInput.emailBox(newEmailField);

    }





    private void loadMaterials() {

        SQLiteJDBC.close();


        ObservableList<SupplierMaterials> list = FXCollections.observableArrayList(new SupplierMaterialsDao().getAll(supplier.getSupplierCode()));
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

                    String weight;
                    materialsList.setDepth(5);
                    materialsList.setExpanded(true);
                    materialsList.getSelectionModel().selectFirst();

                    if (item.getPalletWeight() <= 0) {
                        weight = "MISSING!";
                        FontAwesomeIconView iconView = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION);
                        iconView.setStyle("-fx-fill: red");
                        setStyle("-fx-background-color: rgb(255,174,179)");
                        setGraphic(iconView);
                    }else{
                        weight = item.getPalletWeight() + "Kg";
                    }

                    Materials material = new MaterialsDao().get(item.getmCode());
                    String displayString = material.getMCode() + " | " + material.getName() + " | " + weight;
                    setText(displayString);
                    content.setText("Material: " + new MaterialsDao().get(item.getmCode()).getName() +
                                    "\nM code: " + item.getmCode());
                    setTooltip(content);
                }

            }
        });

    }


    private void loadEmails() {

        emailsList.getItems().clear();
        ObservableList<SuppEmails> list = FXCollections.observableArrayList(new SuppEmailsDao().getAll(supplier.getSupplierCode()));
        emailsList.getItems().addAll(list);
    }


    private void saveNewEmail() {

        SuppEmails email = new SuppEmails();
        email.setEmail(newEmailField.getText());
        email.setSuppCode(supplier.getSupplierCode());
        new SuppEmailsDao().save(email);

    }

}