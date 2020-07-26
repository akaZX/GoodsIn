package app.controller.rmt.materials.nodes;

import app.controller.sql.dao.MaterialSpecsDao;
import app.controller.sql.dao.MaterialsDao;
import app.pojos.Materials;
import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class MaterialListDrawerController implements Initializable {

    public List<Materials> list = null;

    @FXML
    private JFXListView<Materials> listView;

    @FXML
    private JFXButton addNewMaterial;

    @FXML
    private Label refreshIcon;

    @FXML
    private JFXTextField searchField;

    @FXML
    private AnchorPane listas;

    @FXML
    private JFXHamburger hamburger;


    @FXML
    private void hover(MouseEvent event) {

        refreshIcon.getStyleClass().add("label-button:hover");
    }


    @FXML
    private void deleteMaterial() {


        if (listView.getSelectionModel().getSelectedItem() != null) {
            MaterialsService.deleteMaterial(listView, this, listView.getSelectionModel().getSelectedItem());
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadList();
        nodesListeners();


    }


    public void loadList() {

        list = new MaterialsDao().getAll();
        listView.setItems(FXCollections.observableArrayList(list));
    }


    private void nodesListeners() {

//        Listens for search box changes and calls searchSuppliers function
        searchField.textProperty().addListener(
                (ChangeListener) (observable, oldVal, newVal) -> searchMaterials((String) oldVal, (String) newVal));

        refreshIcon.setOnMouseClicked(event -> {
            searchField.clear();
            loadList();
        });


    }


    public void selected(JFXDrawersStack drawersStack, JFXDrawer leftDrawer) {

        if (list.size() > 0) {
            listView.getSelectionModel().selectFirst();
            drawersStack.setContent(initializeSuppForm());
        }

        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            drawersStack.toggle(leftDrawer);

        });

        addNewMaterial.setOnAction(event -> MaterialsService.addNewMaterial(addNewMaterial, this));


        listView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                drawersStack.setContent(initializeSuppForm());
            }
        });
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    drawersStack.setContent(initializeSuppForm());

                }
            }
        });

        listView.setCellFactory(this::materialsListCellFactory);
    }


    private ListCell<Materials> materialsListCellFactory(ListView<Materials> param) {


        return new JFXListCell<Materials>() {

            @Override
            protected void updateItem(Materials item, boolean empty) {

                super.updateItem(item, empty);

                if (item == null) {
                    setText("");
                }
                else {
                    if (new MaterialSpecsDao().get(item.getMCode()) == null) {
                        setStyle("-fx-background-color: rgb(255,203,207)");
                    }
                }
            }

        };

    }


    private AnchorPane loadMaterialForm(MaterialProfileController controller) {

        FXMLLoader form = new FXMLLoader(
                getClass().getResource(
                        "/rmt/materialProfiles/MaterialProfileView.fxml"
                )
        );
        form.setController(controller);

        try {
            return form.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private AnchorPane initializeSuppForm() {

        MaterialProfileController suppProfileController = new MaterialProfileController(listView.getSelectionModel().getSelectedItem(), this);

        return loadMaterialForm(suppProfileController);
    }


    public void searchMaterials(String oldVal, String newVal) {

        if (oldVal == null || (newVal.length() < oldVal.length())) {
            listView.setItems(FXCollections.observableArrayList(list));
        }
        ObservableList<Materials> subentries = FXCollections.observableArrayList();
        for (Materials entry : listView.getItems()) {
            String entryText = entry.getMCode() + entry.getName();
            if (entryText.toUpperCase().contains(newVal.toUpperCase().trim())) {
                subentries.add(entry);
            }
        }
        listView.setItems(subentries);
    }
}
