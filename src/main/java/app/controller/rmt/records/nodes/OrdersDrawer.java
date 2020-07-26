package app.controller.rmt.records.nodes;

import app.controller.sql.dao.SupplierOrderDao;
import app.controller.sql.dao.SuppliersDao;
import app.controller.utils.LabelWithIcons;
import app.controller.utils.Messages;
import app.controller.utils.ValidateInput;
import app.pojos.SupplierOrders;
import app.pojos.Suppliers;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
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
import java.time.LocalDate;


public class OrdersDrawer extends AnchorPane {

    Messages msg = new Messages();

    private JFXDrawer leftDrawer;

    private JFXDrawersStack drawersStack;


    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXListView<SupplierOrders> ordersList;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private Label searchIcon;

    private boolean toggle;


    public OrdersDrawer(JFXDrawer leftDrawer, JFXDrawersStack drawersStack) {

        this();
        this.leftDrawer = leftDrawer;
        this.drawersStack = drawersStack;
        swap();
    }


    public OrdersDrawer() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("rmt/qualityRecords/OrdersDrawer.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        datePicker.setValue(LocalDate.now());

        loadOrders(datePicker.getValue());
    }


    private void loadOrders(LocalDate date) {

        ordersList.getItems().removeAll();
        ordersList.setItems(FXCollections.observableArrayList(new SupplierOrderDao().getAllBy(date, "order_date")));

    }

    public void swap() {

        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            drawersStack.toggle(leftDrawer);

        });

        ordersList.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                swapDrawer(ordersList.getSelectionModel().getSelectedItem());
            }
        });

        ordersList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    swapDrawer(ordersList.getSelectionModel().getSelectedItem());

                }
            }
        });

    }


    private void swapDrawer(SupplierOrders selectedItem) {

        if (selectedItem != null) {
            leftDrawer.getSidePane().remove(this);
            OrderMaterialsDrawer orderMaterialsDrawer = new OrderMaterialsDrawer(selectedItem, drawersStack, leftDrawer, this, ordersList.getSelectionModel().getSelectedItem().getPoNumber(), toggle);
            leftDrawer.setSidePane(orderMaterialsDrawer);

        }
    }


    @FXML
    private void hover(MouseEvent event) {

        searchIcon.getStyleClass().add("label-button:hover");
    }


    @FXML
    private void search(MouseEvent event) {

        loadOrders(datePicker.getValue());
//        System.out.println();
    }


    @FXML
    private void addNewOrder() {
        //TODO prideti Allert window su maza forma orderiui
        addNewOrderForm();

    }


    private void addNewOrderForm() {

        JFXAlert<String> alert = new JFXAlert<>((Stage) this.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Add new order"));


        Label supp   = new Label("Supplier");
        HBox  spacer = new HBox();
        HBox.setHgrow(
                spacer,
                Priority.SOMETIMES
        );
        JFXComboBox<Suppliers> comboBox = new JFXComboBox<>();
        ValidateInput.requiredFieldValidation(comboBox, "Select supplier");
        comboBox.setItems(FXCollections.observableArrayList(new SuppliersDao().getAll()));
        HBox supplierBox = new HBox();
//        supplierBox.setMinHeight(50);
        comboBox.setLabelFloat(true);
        supplierBox.getChildren().addAll(supp, spacer, comboBox);


        Label po = new Label("Po number");

        HBox spacer2 = new HBox();
        HBox.setHgrow(
                spacer2,
                Priority.SOMETIMES
        );
        JFXTextField poField = new JFXTextField();
        ValidateInput.requiredFieldValidation(poField, "Missing PO number");

        HBox orderBox = new HBox();
        orderBox.getChildren().addAll(po, spacer2, poField);

        VBox box = new VBox();
        box.setSpacing(35);
        Label infoLabel = new Label("PO date will be set at: " + datePicker.getValue());
        box.getChildren().addAll(supplierBox, orderBox, infoLabel);
        layout.setBody(box);
        JFXButton continueBtn = new JFXButton("CONTINUE");
        JFXButton cancel      = new JFXButton("CANCEL");

        continueBtn.setOnAction(e -> {

            boolean save  = true;
            boolean saved = false;

            if (! comboBox.validate()) {
                save = false;
            }
            if (! poField.validate()) {
                save = false;
            }

            if (save) {
                SupplierOrders orders = new SupplierOrders();
                orders.setOrderDate(datePicker.getValue());
                orders.setSuppCode(comboBox.getSelectionModel().getSelectedItem().getSupplierCode());
                orders.setPoNumber(poField.getText().toUpperCase().trim().replaceAll("'", ""));

                SupplierOrders exists = new SupplierOrderDao().getBy(poField.getText().toUpperCase().trim().replaceAll("'", ""), "po");

                System.out.println("exists: " + exists);


                if (exists != null) {
                    System.out.println("tuscias");
                    saved = true;

                }


                if (! saved) {
                    new SupplierOrderDao().save(orders);
                    alert.hideWithAnimation();
                    msg.continueAlert(this, LabelWithIcons.largeCheckIconLabel("Order saved"), new Label(""));

                    loadOrders(datePicker.getValue());
                }
                else {
                    msg.continueAlert(this, LabelWithIcons.largeWarningIconLabel("Failed to save order"), new Label(""));
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


    public void setToggle(boolean toggleButton) {

        this.toggle = toggleButton;
    }
}
