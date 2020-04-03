package app.controller.rmt.materials;

import app.controller.sql.dao.MaterialsDao;
import app.pojos.Materials;
import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
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



public class MaterialListDrawerController implements Initializable {

    public List<Materials> list = null;
    @FXML
    private JFXListView<Materials> listView = new JFXListView<>();

    @FXML
    private JFXButton addNewMaterial = new JFXButton();
    @FXML
    JFXTextField searchField;
    @FXML
    private AnchorPane listas;

    @FXML
    private JFXHamburger hamburger;

    private boolean aBoolean = true;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

       loadList();
        addNewMaterial.setVisible(false);
        nodesListeners();
    }


    public void loadList(){
        list = new MaterialsDao().getAll();
        listView.setItems(FXCollections.observableArrayList(list));
    }

    private void nodesListeners() {

//        Listens for search box changes and calls searchSuppliers function
        searchField.textProperty().addListener(
                (ChangeListener) (observable, oldVal, newVal) -> searchMaterials((String) oldVal, (String) newVal));


        // Adds listView cell formatter to show supp names and adds tooltip for each row
        listView.setCellFactory(param -> new JFXListCell<Materials>() {

            final Tooltip content = new Tooltip();


            @Override
            protected void updateItem(Materials item, boolean empty) {

                super.updateItem(item, empty);

                if (item == null) {
                    setText("");
                    content.setText("");
                }
                else {
                    setText(item.getMCode() + " - " + item.getName());
                    content.setText("Material name: " + item.getName() + " \nMaterial M code: " + item.getMCode());
                    setTooltip(content);
                }
            }
        });
        //Listens for mouse clicks

    }

    public void selected(JFXDrawersStack drawersStack, JFXDrawer leftDrawer) {

        HamburgerBasicCloseTransition burgerTask1 = new HamburgerBasicCloseTransition(hamburger);

        burgerTask1.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
            burgerTask1.setRate(burgerTask1.getRate() * -1);
            burgerTask1.play();
            toggle(drawersStack, leftDrawer);

        });


        addNewMaterial.setOnAction(event ->  drawersStack.setContent(initializeSuppForm(true)));


        listView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                drawersStack.setContent(initializeSuppForm(false));
            }
        });
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    drawersStack.setContent(initializeSuppForm(false));

                }
            }
        });
    }

    private void toggle(JFXDrawersStack drawersStack, JFXDrawer leftDrawer){
        addNewMaterial.setVisible(aBoolean);
        drawersStack.toggle(leftDrawer);
        aBoolean = !aBoolean;
    }




    private AnchorPane loadMaterialForm(MaterialProfileController controller){

        FXMLLoader supplierForm = new FXMLLoader(
                getClass().getResource(
                        "/rmt/MaterialProfileView.fxml"
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

    private AnchorPane initializeSuppForm(boolean bool){
        MaterialProfileController suppProfileController;
        if(bool){
            suppProfileController = new MaterialProfileController(this);
        }else{
            suppProfileController = new MaterialProfileController(listView.getSelectionModel().getSelectedItem(), this);
        }
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
