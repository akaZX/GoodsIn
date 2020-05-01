package app.controller.suppliersView;

import app.controller.sql.dao.*;
import app.controller.utils.LabelWithIcons;
import app.controller.utils.Messages;
import app.controller.utils.TextFieldInput;
import app.controller.utils.ValidateInput;
import app.pojos.*;
import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import impl.com.calendarfx.view.NumericTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;


public class SuppliersProfileController implements Initializable {

    private static final String FX_LABEL_FLOAT_TRUE = "-fx-label-float:true;";

    private static final String ERROR = "error";

    Messages msg = new Messages();

    Suppliers supplier = null;

    @FXML
    Label supplierName;

    @FXML
    Label contactLabel;

    @FXML
    Label materialName;


    @FXML
    JFXTextField newEmailField;

    @FXML
    JFXButton saveNewEmail;

    @FXML
    JFXButton deleteMaterial;

    @FXML
    JFXButton deleteEmail;

    @FXML
    JFXListView<SuppEmails> emailsList;
    @FXML
    JFXListView<SupplierMaterials> materialsList;

    @FXML
    JFXListView<SuppNumbers> numbersList;

    @FXML
    JFXTextField contactNameField;

    @FXML
    JFXTextField avgTextField;

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




    @FXML
    private void changeName(){
        //TODO create small Pop up
    }


    public SuppliersProfileController() {

    }


    public SuppliersProfileController(Suppliers supplier) {

        this.supplier = supplier;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        buttonsListeners();


        if (supplier != null) {
            loadSupplier();
        }
        fieldsValidation();

    }



    private void loadSupplier(){
        String supp = supplier.getSupplierCode() + " - " + supplier.getSupplierName();
        supplierName.setText(supp);
        loadMaterials();
        loadEmails();
        loadContacts();
        listListeners();
        loadAllMaterials();
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

        addContact.setOnAction(event -> saveNewContact());

        deletePhoneNumber.setOnAction(event -> deleteContact());

        updateWeightBtn.setOnAction(event -> updateWeight());

        addMaterialBtn.setOnAction(event -> addNewMaterial());

        deleteMaterial.setOnAction(event -> deleteSupplierMaterial());
    }


    private void deleteSupplierMaterial() {


        if (materialsList.getSelectionModel().getSelectedItem() != null) {

            if(new SupplierMaterialsDao().delete(materialsList.getSelectionModel().getSelectedItem() )){

                msg.continueAlert(supplierName, LabelWithIcons.largeCheckIconLabel("Material deleted"), new Label(""));
                loadMaterials();
                avgTextField.clear();
                materialLabel.setText("");

            }else{
                msg.continueAlert(supplierName, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Failed to delete supplier material"));
            }
        }else{
            msg.continueAlert(supplierName, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Select material"));

        }

    }


    private void addNewMaterial() {
        if( materialsComboBox.validate() ){

            Materials material = materialsComboBox.getSelectionModel().getSelectedItem();

            SupplierMaterials supplierMaterials = new SupplierMaterials();
            supplierMaterials.setmCode(material.getMCode());
            supplierMaterials.setSuppCode(supplier.getSupplierCode());


            if (new SupplierMaterialsDao().save(supplierMaterials)) {
                msg.continueAlert(supplierName, LabelWithIcons.largeCheckIconLabel("Material added"), new Label(""));
                loadMaterials();
            }else {
                msg.continueAlert(supplierName, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Material exists"));

            }

        }else{
            msg.continueAlert(supplierName, LabelWithIcons.largeWarningIconLabel("Error"), new Label("No material selected"));

        }

    }


    private void updateWeight() {

        if (avgTextField.validate()) {
            SupplierMaterials material = materialsList.getSelectionModel().getSelectedItem();
            material.setPalletWeight(Integer.parseInt(avgTextField.getText()));
            if ( new SupplierMaterialsDao().update(material)){
                msg.continueAlert(supplierName, LabelWithIcons.largeCheckIconLabel("Weight updated"), new Label(""));

            }else{
                msg.continueAlert(supplierName, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Failed to update weight"));

            }

            loadMaterials();

        }
    }


    private void deleteContact() {

        if (numbersList.getSelectionModel().getSelectedItem() != null) {
            if(new SuppNumbersDao().delete(numbersList.getSelectionModel().getSelectedItem() )){
                msg.continueAlert(supplierName, LabelWithIcons.largeCheckIconLabel("Contact deleted"), new Label(""));
                loadContacts();
            }else{
                msg.continueAlert(supplierName, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Failed to delete contact"));
            }
        }

    }


    private void listListeners(){


        materialsList.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                showMaterialWeight();
            }
        });

        numbersList.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                    showContactName();
                }
        });
        numbersList.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                showContactName();
            }
        });


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

                    materialsList.getSelectionModel().selectFirst();

                    if (item.getPalletWeight() <= 0) {

                        setStyle("-fx-background-color: rgb(255,155,158)");

                    }

                    Materials material = new MaterialsDao().get(item.getmCode());
                    String displayString = material.getMCode() + " - " + material.getName();
                    setText(displayString);
                    content.setText("Material: " + new MaterialsDao().get(item.getmCode()).getName() +
                                    "\nM code: " + item.getmCode());
                    setTooltip(content);
                }

            }
        });
    }


    private void showMaterialWeight() {

        if(materialsList.getSelectionModel().getSelectedItem() != null){
            SupplierMaterials supplierMaterials = materialsList.getSelectionModel().getSelectedItem();
            materialLabel.setText(supplierMaterials.toString());
            if(supplierMaterials.getPalletWeight() > 0){
            avgTextField.setText(String.valueOf(supplierMaterials.getPalletWeight()));

            }else avgTextField.setText("");
        }

    }

    private void loadAllMaterials(){
        materialsComboBox.setItems(FXCollections.observableArrayList(new MaterialsDao().getAll()));

    }

    private void loadMaterials() {
        ObservableList<SupplierMaterials> list = FXCollections.observableArrayList(new SupplierMaterialsDao().getAll(supplier.getSupplierCode()));
        materialsList.setItems(list);
        
    }


    private void showContactName(){
        if(numbersList.getSelectionModel().getSelectedItem() != null){

        contactLabel.setText(numbersList.getSelectionModel().getSelectedItem().getDetails());
        }
    }

    private void fieldsValidation(){
        ValidateInput.requiredFieldValidation(newPhoneNumber, "Missing number");
        ValidateInput.requiredFieldValidation(contactNameField, "Missing contact details");
        ValidateInput.emailBox(newEmailField);
        TextFieldInput.intField(avgTextField, "Missing");
        ValidateInput.requiredFieldValidation(materialsComboBox, "Select material");
    }

    private void saveNewContact(){

        if(validContact()){
            SuppNumbers suppNumbers = new SuppNumbers();
            suppNumbers.setDetails(contactNameField.getText());
            suppNumbers.setPhoneNo(newPhoneNumber.getText());
            suppNumbers.setSuppCode(supplier.getSupplierCode());

            boolean saved = new SuppNumbersDao().save(suppNumbers);


            if (saved) {
                msg.continueAlert(supplierName, LabelWithIcons.largeCheckIconLabel("Contact added"), new Label(""));
                loadContacts();
                contactNameField.clear();
                newPhoneNumber.clear();

            }else{
                msg.continueAlert(supplierName, LabelWithIcons.largeWarningIconLabel("Contact exists"), new Label(""));
            }
        }else{
            msg.continueAlert(supplierName, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Fields missing input"));
        }

    }

    private boolean validContact(){

        boolean valid = true;
        if (!contactNameField.validate() || !newPhoneNumber.validate()) {
            valid=false;
        }

        return valid;
    }


    private void loadEmails() {

        emailsList.getItems().clear();
        ObservableList<SuppEmails> list = FXCollections.observableArrayList(new SuppEmailsDao().getAll(supplier.getSupplierCode()));
        emailsList.getItems().addAll(list);
    }

    private void loadContacts(){
        numbersList.getItems().clear();

        numbersList.getItems().addAll(FXCollections.observableArrayList(new SuppNumbersDao().getAll(supplier.getSupplierCode())));


    }


    private void saveNewEmail() {

        SuppEmails email = new SuppEmails();
        email.setEmail(newEmailField.getText());
        email.setSuppCode(supplier.getSupplierCode());
        if(new SuppEmailsDao().save(email)){
            msg.continueAlert(supplierName, LabelWithIcons.largeCheckIconLabel("Email added"), new Label(""));
        }else{
            msg.continueAlert(supplierName, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Failed to add email"));
        }
        newEmailField.clear();

    }

}