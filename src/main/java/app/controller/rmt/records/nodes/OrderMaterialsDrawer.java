package app.controller.rmt.records.nodes;

import app.controller.sql.dao.MaterialsDao;
import app.controller.sql.dao.PoMaterialsDao;
import app.controller.sql.dao.RmtQaRecordsDao;
import app.controller.utils.LabelWithIcons;
import app.controller.utils.Messages;
import app.controller.utils.ValidateInput;
import app.pojos.Materials;
import app.pojos.PoMaterials;
import app.pojos.RmtQaRecords;
import app.pojos.SupplierOrders;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class OrderMaterialsDrawer extends AnchorPane {

    Messages msg = new Messages();

    SupplierOrders orders;

    @FXML
    JFXToggleButton hide;

    private JFXDrawer leftDrawer = null;

    private OrdersDrawer ordersDrawer = null;

    private JFXDrawersStack drawersStack = null;

    private String po;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXListView<PoMaterials> materialList;


    public OrderMaterialsDrawer(SupplierOrders orders, JFXDrawersStack drawersStack, JFXDrawer leftDrawer, OrdersDrawer ordersDrawer, String po, boolean toggle) {

        this();
        this.leftDrawer = leftDrawer;
        this.ordersDrawer = ordersDrawer;
        this.drawersStack = drawersStack;
        this.orders = orders;
        hide.setSelected(toggle);

        this.po = po;

        loadList();
    }


    public OrderMaterialsDrawer() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("rmt/qualityRecords/OrderMaterialsDrawer.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        listEvents();
    }


    private void listEvents() {

        materialList.setCellFactory(param -> new JFXListCell<PoMaterials>() {

            final Tooltip content = new Tooltip();


            @Override
            protected void updateItem(PoMaterials item, boolean empty) {

                super.updateItem(item, empty);

                if (item == null) {
                    setText("");
                    content.setText("");
                }
                else {
                    List<RmtQaRecords> list  = new RmtQaRecordsDao().getAll(item.getPo());
                    boolean            found = false;
                    for (RmtQaRecords records : list) {
                        if (records.getmCode().equalsIgnoreCase(item.getMCode())) {
                            found = true;
                            break;
                        }
                    }

                    if (! found) {
                        setStyle("-fx-background-color: rgb(255,203,207)");
                    }


                    setText(item.getMCode() + " - " + new MaterialsDao().get(item.getMCode()).getName());
                    content.setText(item.getMCode() + " - " + new MaterialsDao().get(item.getMCode()).getName());
                    setTooltip(content);
                }
            }
        });


        materialList.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (materialList.getSelectionModel().getSelectedItem() != null) {
                    loadMaterialRecords();
                }
            }
        });
        materialList.setOnMouseClicked(event -> {
            if (materialList.getSelectionModel().getSelectedItem() != null) {
                if (event.getClickCount() == 2) {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        loadMaterialRecords();
                    }
                }
            }
        });

        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (event -> {
            drawersStack.toggle(leftDrawer);
        }));


    }


    private void loadMaterialRecords() {


        PoMaterials materials = materialList.getSelectionModel().getSelectedItem();

        Thread thread = new Thread(() -> {

            Platform.runLater(() -> {
                IntakePane intakePane = new IntakePane(materials);
                drawersStack.setContent(intakePane.getPane());
            });
        });

        thread.setDaemon(true);
        Platform.runLater(() -> {
            if (hide.isSelected()) {
                drawersStack.toggle(leftDrawer);
            }
            thread.start();
        });
    }


    private void loadList() {

        materialList.getItems().removeAll();

        if (po != null) {
            List<PoMaterials> list = new PoMaterialsDao().getAll(po);
            materialList.setItems(FXCollections.observableArrayList(list));
        }

    }


    @FXML
    private void goBack(MouseEvent event) {

        ordersDrawer.setToggle(hide.isSelected());
        leftDrawer.setSidePane(ordersDrawer);

    }


    @FXML
    private void saveBtn(ActionEvent event) {

        addNewMaterial();
    }


    private void addNewMaterial() {

        JFXAlert<String> alert = new JFXAlert<>((Stage) this.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Add material"));


        Label supp   = new Label("Materials");
        HBox  spacer = new HBox();
        HBox.setHgrow(
                spacer,
                Priority.SOMETIMES
        );
        JFXComboBox<Materials> comboBox = new JFXComboBox<>();
        ValidateInput.requiredFieldValidation(comboBox, "Select material");
        comboBox.setItems(FXCollections.observableArrayList(new MaterialsDao().getAll()));
        HBox materialBox = new HBox();
        materialBox.getChildren().addAll(supp, spacer, comboBox);


        VBox box = new VBox();
        box.setSpacing(35);
        Label infoLabel = new Label("PO number: " + po);
        box.getChildren().addAll(materialBox, infoLabel);
        layout.setBody(box);
        JFXButton continueBtn = new JFXButton("CONTINUE");
        JFXButton cancel      = new JFXButton("CANCEL");

        continueBtn.setOnAction(e -> {

            if (comboBox.validate()) {
                Materials material = comboBox.getSelectionModel().getSelectedItem();

                PoMaterials poMaterials = new PoMaterials();
                poMaterials.setMCode(material.getMCode());
                poMaterials.setPo(po);
                poMaterials.setDeliveryNo(LocalDateTime.now().getNano());
                poMaterials.setDeliveryNo(LocalDateTime.now().getNano());

                poMaterials.setExpectedDate(orders.getOrderDate());


                boolean saved = new PoMaterialsDao().save(poMaterials);
                if (saved) {
                    alert.hideWithAnimation();
                    msg.continueAlert(this, LabelWithIcons.largeCheckIconLabel("Material added"), new Label(""));
                    loadList();

                }
                else {
                    msg.continueAlert(this, LabelWithIcons.largeWarningIconLabel("Failed to add material"), new Label(""));
                }
            }
        });

        cancel.setOnAction(b -> {
            alert.hideWithAnimation();
        });
        layout.setActions(continueBtn, cancel);
        alert.setContent(layout);
        alert.show();
    }


    @FXML
    private void deleteBtn(ActionEvent event) {

        boolean deleted = new PoMaterialsDao().delete(materialList.getSelectionModel().getSelectedItem());
        if (deleted) {
            msg.continueAlert(this, LabelWithIcons.largeCheckIconLabel("Material deleted"), new Label(""));
        }
        else {
            msg.continueAlert(this, LabelWithIcons.largeWarningIconLabel("Failed to delete material"), new Label(""));
        }
        loadList();
    }

}
