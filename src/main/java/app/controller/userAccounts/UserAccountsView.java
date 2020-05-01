package app.controller.userAccounts;

import app.controller.sql.dao.UsersDao;
import app.controller.utils.LabelWithIcons;
import app.controller.utils.Messages;
import app.controller.utils.ValidateInput;
import app.pojos.Users;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class UserAccountsView extends AnchorPane {


    private final Messages msg = new Messages();

    private Users updateUser;

    @FXML
    private JFXListView<Users> listView;

    @FXML
    private Label formLabel;

    @FXML
    private JFXCheckBox adminCb;

    @FXML
    private JFXCheckBox securityCb;

    @FXML
    private JFXCheckBox fruitCb;

    @FXML
    private JFXCheckBox rmtAdminCb;

    @FXML
    private JFXCheckBox rmtGoodsInCb;

    @FXML
    private JFXCheckBox goodsInCb;

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXTextField firstNameField;

    @FXML
    private JFXTextField lastNameField;

    @FXML
    private JFXTextField emailField;


    public UserAccountsView() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("userAccounts/UserAccountsView.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        loadAllUsers();
        listListeners();
        validateFields();
    }


    private void validateFields() {

        ValidateInput.requiredFieldValidation(usernameField, "Required field", true, true);
        ValidateInput.requiredFieldValidation(firstNameField, "Required field", true, true);
        ValidateInput.requiredFieldValidation(lastNameField, "Required field", true, true);
        ValidateInput.emailBox(emailField);

    }


    private void listListeners() {

        listView.setOnMouseClicked(event -> {
            if (listView.getSelectionModel().getSelectedItem() != null) {
                if (event.getClickCount() == 2) {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        loadUser(listView.getSelectionModel().getSelectedItem());


                    }
                }

            }
        });

        listView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (listView.getSelectionModel().getSelectedItem() != null) {
                    loadUser(listView.getSelectionModel().getSelectedItem());

                }
            }
        });


    }


    private void loadUser(Users user) {

        updateUser = user;
        setTopLabel(user);
        setUsernameField(user);
        setFirstNameField(user);
        setLastNameField(user);
        setEmailField(user);
        setGoodsInCb(user);
        setRmtGoodsInCb(user);
        setRmtAdminCb(user);
        setSecurityCb(user);
        setFruitCb(user);
        setAdminCb(user);
    }


    private void setFruitCb(Users user) {

        fruitCb.selectedProperty().setValue(user.getFruit());
    }


    private void setSecurityCb(Users user) {

        securityCb.selectedProperty().setValue(user.getSecurity());
    }


    private void setGoodsInCb(Users user) {

        goodsInCb.selectedProperty().setValue(user.getGoodsIn());
    }


    private void setRmtGoodsInCb(Users user) {

        rmtGoodsInCb.selectedProperty().setValue(user.getRmtGoodsIn());
    }


    private void setRmtAdminCb(Users user) {

        rmtAdminCb.selectedProperty().setValue(user.getRmtAdmin());
    }


    private void setAdminCb(Users user) {

        adminCb.selectedProperty().setValue(user.getAdmin());
    }


    private void setFirstNameField(Users user) {

        firstNameField.setText(user.getName());
    }


    private void setEmailField(Users user) {

        emailField.setText(user.getEmail());
    }


    private void setLastNameField(Users user) {

        lastNameField.setText(user.getSurname());
    }


    private void setUsernameField(Users user) {

        usernameField.setText(user.getUsername());
        usernameField.setEditable(false);
    }


    private void setTopLabel(Users user) {

        formLabel.setText(user.getName() + " " + user.getSurname());
    }


    private void loadAllUsers() {

        listView.setItems(FXCollections.observableArrayList(new UsersDao().getAll()));
    }


    @FXML
    private void save() {

        if (validateInputs()) {
            boolean saved;
            if (updateUser == null) {
                saved = new UsersDao().save(getUser());
            }
            else {
                saved = new UsersDao().update(getUser());
            }
            if (saved) {
                if (updateUser == null) {
                    msg.continueAlert(this, LabelWithIcons.largeCheckIconLabel("Success"), new Label("New user saved"));
                }
                else {
                    msg.continueAlert(this, LabelWithIcons.largeCheckIconLabel("Success"), new Label("User was updated"));
                }
            }
            else {
                if (updateUser == null) {
                    msg.continueAlert(this, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Failed to save new user"));
                }
                else {
                    msg.continueAlert(this, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Failed to update user"));
                }
            }

        }
        else {
            msg.continueAlert(this, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Failed to validate input fields"));
        }

        loadAllUsers();
    }


    private boolean validateInputs() {

        boolean valid = true;
        if (! usernameField.validate()) {
            valid = false;
        }
        if (! firstNameField.validate()) {
            valid = false;
        }
        if (! lastNameField.validate()) {
            valid = false;
        }
        if (! emailField.validate()) {
            valid = false;
        }

        return valid;
    }


    private Users getUser() {

        Users user = new Users();
        user.setUsername(usernameField.getText().trim());
        user.setName(firstNameField.getText().trim());
        user.setSurname(lastNameField.getText().trim());
        user.setEmail(emailField.getText());
        user.setGoodsIn((goodsInCb.selectedProperty().getValue() ? 1 : 0));
        user.setRmtGoodsIn((rmtGoodsInCb.selectedProperty().getValue() ? 1 : 0));
        user.setRmtAdmin((rmtAdminCb.selectedProperty().getValue() ? 1 : 0));
        user.setSecurity((securityCb.selectedProperty().getValue() ? 1 : 0));
        user.setFruit((fruitCb.selectedProperty().getValue() ? 1 : 0));
        user.setAdmin((adminCb.selectedProperty().getValue() ? 1 : 0));

        return user;
    }


    @FXML
    private void add() {

        clearForm();
    }


    private void clearForm() {

        formLabel.setText("New user");
        updateUser = null;
        usernameField.setEditable(true);
        usernameField.clear();
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();

        goodsInCb.selectedProperty().setValue(false);
        rmtGoodsInCb.selectedProperty().setValue(false);
        rmtAdminCb.selectedProperty().setValue(false);
        securityCb.selectedProperty().setValue(false);
        fruitCb.selectedProperty().setValue(false);
        adminCb.selectedProperty().setValue(false);
    }


    @FXML
    private void delete() {

        if (listView.getSelectionModel().getSelectedItem() != null) {

            boolean bool = new UsersDao().delete(listView.getSelectionModel().getSelectedItem());
            if (bool) {
                msg.continueAlert(this, LabelWithIcons.largeCheckIconLabel("User deleted"), new Label(""));
            }
            else {
                msg.continueAlert(this, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Failed to delete user"));
            }
        }
        else {
            msg.continueAlert(this, LabelWithIcons.largeWarningIconLabel("Error"), new Label("No user is selected"));
        }
        loadAllUsers();
    }


}

