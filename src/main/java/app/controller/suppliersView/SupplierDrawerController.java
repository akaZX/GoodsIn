package app.controller.suppliersView;

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
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SupplierDrawerController implements Initializable {
    List<Suppliers> list = new SuppliersDao().getAll();
    @FXML
    private JFXListView<Suppliers> supplierListView = new JFXListView<>();

    @FXML
    private JFXButton addNewSupplier = new JFXButton();
    @FXML
    JFXTextField searchField;
    @FXML
    private AnchorPane listas;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        supplierListView.setItems(FXCollections.observableArrayList(list));

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
                    setText(item.getSupplierName());
                    content.setText("Supplier code: " + item.getSupplierCode() + " \n" + item.getSupplierName());
                    setTooltip(content);
                }
            }
        });


        //Listens for mouse clicks





    }


    public void selected(JFXDrawersStack drawersStack) {
        supplierListView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                drawersStack.setContent(loadDetails());
            }
        });
        supplierListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    drawersStack.setContent(loadDetails());

                }
            }
        });
    }


    public void searchSuppliers(String oldVal, String newVal) {
        if (oldVal == null || (newVal.length() < oldVal.length())) {
            supplierListView.setItems(FXCollections.observableArrayList(list));
        }
        ObservableList<Suppliers> subentries = FXCollections.observableArrayList();
        for (Suppliers entry : supplierListView.getItems()) {

            String entryText = entry.getSupplierName();
            if (entryText.toUpperCase().contains(newVal.toUpperCase().trim())) {
                subentries.add(entry);
            }
        }
        supplierListView.setItems(subentries);
    }



    private AnchorPane loadDetails(){

        Suppliers supplier = supplierListView.getSelectionModel().getSelectedItem();
        SuppliersProfileController suppProfileController = new SuppliersProfileController(supplier);
        FXMLLoader supplierForm = new FXMLLoader(
                getClass().getResource(
                        "/suppliers/supplierProfile.fxml"
                )
        );
        supplierForm.setController(suppProfileController);

        try {
            return supplierForm.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
