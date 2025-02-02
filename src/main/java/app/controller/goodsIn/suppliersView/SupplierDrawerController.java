package app.controller.goodsIn.suppliersView;

import app.controller.sql.dao.SuppliersDao;
import app.pojos.Suppliers;
import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SupplierDrawerController implements Initializable {

    List<Suppliers> list;

    @FXML
    JFXTextField searchField;

    @FXML
    private JFXListView<Suppliers> supplierListView;

    @FXML
    private JFXButton addNewSupplier;

    @FXML
    private AnchorPane listas;

    @FXML
    private JFXHamburger hamburger;


    @FXML
    private void refresh() {

        searchField.clear();
        loadList();
    }


    public void loadList() {

        supplierListView.getItems().clear();
        list = new SuppliersDao().getAll();
        supplierListView.setItems(FXCollections.observableArrayList(list));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadList();
        nodesListeners();
    }


    private void nodesListeners() {

//        Listens for search box changes and calls searchSuppliers function
        searchField.textProperty().addListener(
                (ChangeListener) (observable, oldVal, newVal) -> searchSuppliers((String) oldVal, (String) newVal));


        // Adds listView cell formatter to show supp names and adds tooltip for each row
        supplierListView.setCellFactory(param -> new JFXListCell<Suppliers>() {

            final Tooltip content = new Tooltip();


            @Override
            protected void updateItem(Suppliers item, boolean empty) {

                super.updateItem(item, empty);

                if (item == null) {
                    setText("");
                    content.setText("");
                }
                else {
                    setText(item.getSupplierCode() + " - " + item.getSupplierName());
                    content.setText("Supplier code: " + item.getSupplierCode() + " \n" + item.getSupplierName());
                    setTooltip(content);
                }
            }
        });


    }


    public void searchSuppliers(String oldVal, String newVal) {

        if (oldVal == null || (newVal.length() < oldVal.length())) {
            supplierListView.setItems(FXCollections.observableArrayList(list));
        }
        ObservableList<Suppliers> results = FXCollections.observableArrayList();
        for (Suppliers entry : supplierListView.getItems()) {

            String entryText = entry.getSupplierName() + entry.getSupplierCode();
            if (entryText.toUpperCase().contains(newVal.toUpperCase().trim())) {
                results.add(entry);
            }
        }
        supplierListView.setItems(results);
    }


    public void selected(JFXDrawersStack drawersStack, JFXDrawer leftDrawer) {

        if (list.size() > 0) {
            supplierListView.getSelectionModel().selectFirst();
            drawersStack.setContent(initializeSuppForm(false));
        }


        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            toggle(drawersStack, leftDrawer);
        });


        addNewSupplier.setOnAction(event -> NewSupplier.addNewSupplier(this, addNewSupplier));


        supplierListView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                drawersStack.setContent(initializeSuppForm(false));
            }
        });
        supplierListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    drawersStack.setContent(initializeSuppForm(false));


                }
            }
        });
    }


    private void toggle(JFXDrawersStack drawersStack, JFXDrawer leftDrawer) {

        drawersStack.toggle(leftDrawer);
    }


    private AnchorPane initializeSuppForm(boolean bool) {

        SuppliersProfileController suppProfileController;

        suppProfileController = new SuppliersProfileController(supplierListView.getSelectionModel().getSelectedItem(), this);

        return loadSupplierForm(suppProfileController);
    }


    private AnchorPane loadSupplierForm(SuppliersProfileController controller) {

        FXMLLoader supplierForm = new FXMLLoader(
                getClass().getResource(
                        "/suppliers/supplierProfile.fxml"
                )
        );
        supplierForm.setController(controller);

        try {
            return supplierForm.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
