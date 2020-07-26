package app.controller.goodsIn.qaReport;

import app.controller.sql.dao.*;
import app.controller.utils.LabelWithIcons;
import app.controller.utils.Messages;
import app.controller.utils.pdf.PDFFile;
import app.pojos.RmtQaRecords;
import app.pojos.SupplierOrders;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ReportView extends AnchorPane {

    private final Messages msg = new Messages();

    private final ToggleGroup group = new ToggleGroup();

    private final JFXTextField searchField = new JFXTextField();

    private final JFXDatePicker datePicker = new JFXDatePicker();

    private final String email = new UsersDao().get(System.getProperty("user.name")).getEmail();

    SupplierOrders record;

    private String password = "";


    @FXML
    private Label searchLabel;

    @FXML
    private JFXRadioButton dateRadio;

    @FXML
    private JFXRadioButton poRadio;

    @FXML
    private HBox searchNodeBox;

    @FXML
    private JFXListView<SupplierOrders> listView;

    @FXML
    private JFXListView<QARecordListBox> orderQaRecordsList;

    @FXML
    private Label qaRecordLabel;

    @FXML
    private Label qaRecordDetailsLabel;

    @FXML
    private Label topLabel;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton sendReportBtn;


    public ReportView() {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("goodsIn/ReportView.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {

            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        datePicker.setValue(LocalDate.now());
        toggleGroup();
        setListViewEvents();
        listView.setEditable(true);
        Event.fireEvent(searchLabel, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null));

    }


    private void setListViewEvents() {

        orderQaRecordsList.setOnMouseClicked(event -> {
            loadRecordDetails();

        });

        orderQaRecordsList.setOnKeyReleased(event -> {
            loadRecordDetails();
        });

        orderQaRecordsList.setCellFactory(this::recordsListCellFactory);

        listView.setCellFactory(this::ordersListCellFactory);


        listView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                listViewEvent();
            }
        });
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    listViewEvent();

                }
            }
        });
    }


    private void loadRecordDetails() {

        if (orderQaRecordsList.getSelectionModel().getSelectedItem() != null) {
            qaRecordLabel.setText(new MaterialsDao().get(
                    orderQaRecordsList.getSelectionModel().getSelectedItem().getRecord().getmCode()).getName());
            qaRecordDetailsLabel.setText(orderQaRecordsList.getSelectionModel().getSelectedItem().getRecord().getDetails().toString());
        }
    }


    private void listViewEvent() {

        qaRecordDetailsLabel.setText("");
        qaRecordLabel.setText("");

        if (listView.getSelectionModel().getSelectedItem() != null) {
            record = listView.getSelectionModel().getSelectedItem();

            topLabel.setText(record.getPoNumber() +
                             " - " + new SuppliersDao().get(record.getSuppCode()) + "  " + record.getOrderDate());
            orderQaRecordsList.setItems(FXCollections.observableArrayList(getRecordBoxes(record.getPoNumber())));


        }
    }


    private List<QARecordListBox> getRecordBoxes(String po) {

        List<RmtQaRecords>    records = new RmtQaRecordsDao().getAll(po);
        List<QARecordListBox> boxes   = new ArrayList<>();

        if (records.size() > 0) {
            sendReportBtn.setVisible(true);
            saveBtn.setVisible(true);
            records.forEach(rmtQaRecords -> {
                boxes.add(new QARecordListBox(rmtQaRecords));
            });
        }
        else {
            topLabel.setText(listView.getSelectionModel().getSelectedItem().getPoNumber() + " has no QA records");
            saveBtn.setVisible(false);
            sendReportBtn.setVisible(false);
        }

        return boxes;
    }


    private ListCell<SupplierOrders> ordersListCellFactory(ListView<SupplierOrders> param) {


        return new JFXListCell<SupplierOrders>() {

            @Override
            protected void updateItem(SupplierOrders item, boolean empty) {

                super.updateItem(item, empty);

                if (item == null) {
                    setText("");
                }
                else {
                    if (new RmtQaRecordsDao().getAll(item.getPoNumber()).size() == 0) {
                        setStyle("-fx-background-color: rgb(255,203,207)");
                    }
                }
            }

        };

    }


    private ListCell<QARecordListBox> recordsListCellFactory(ListView<QARecordListBox> param) {

        return new JFXListCell<QARecordListBox>() {

            @Override
            protected void updateItem(QARecordListBox item, boolean empty) {

                super.updateItem(item, empty);

                if (item == null) {
                    setText("");
                }
                else {
                    orderQaRecordsList.getSelectionModel().selectFirst();

                    if (item.getDecision().equalsIgnoreCase("reject")) {
                        setStyle("-fx-background-color: rgb(255,203,207)");

                    }
                    else if (item.getDecision().equalsIgnoreCase("hold")) {
                        setStyle("-fx-background-color: rgb(255,255,202)");
                    }
                    else if (item.getDecision().equalsIgnoreCase("accept")) {
                        setStyle("-fx-background-color: rgb(200,255,200)");

                    }
                    else {
                        setStyle("-fx-background-color: rgb(250,203,250)");
                    }
                }

            }
        };
    }


    private void toggleGroup() {


        dateRadio.setToggleGroup(group);
        poRadio.setToggleGroup(group);
        toggleGroupListener(group);
        dateRadio.setSelected(true);
        dateRadio.requestFocus();


    }


    private void toggleGroupListener(ToggleGroup group) {

        group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (group.getSelectedToggle() != null) {
                if (group.getSelectedToggle() == dateRadio) {
                    dateSearch();
                }
                else {
                    textFieldSearch();
                }
            }
        });


    }


    private void dateSearch() {

        searchNodeBox.getChildren().clear();
        searchNodeBox.getChildren().add(datePicker);

        setSearchLabelAction(event -> {
            List<SupplierOrders> list = new SupplierOrderDao().getAllBy(datePicker.getValue(), "order_date");
            if (list.size() > 0) {
                listView.setItems(FXCollections.observableArrayList(list));

            }
//            else {
//                msg.continueAlert(
//                        this,
//                        LabelWithIcons.largeWarningIconLabel("Error"),
//                        new Label("No records found"));
//            }
        });


    }


    private void setSearchLabelAction(EventHandler<MouseEvent> event) {

        searchLabel.setOnMouseClicked(event);
    }


    private void textFieldSearch() {

        searchNodeBox.getChildren().clear();
        searchNodeBox.getChildren().add(searchField);

        setSearchLabelAction(event -> {
            SupplierOrders order = new SupplierOrderDao().getBy(searchField.getText().toUpperCase(), "po");
            if (order != null) {
                listView.setItems(FXCollections.observableArrayList(order));

            }
            else {
                msg.continueAlert(
                        this,
                        LabelWithIcons.largeWarningIconLabel("Error"),
                        new Label("No records matches criteria: '" + searchField.getText() + "'"));
                searchField.clear();
            }
        });
    }


    @FXML
    private void sendReports() {

        setPassword(listView.getItems());

    }


    private void setPassword(List<SupplierOrders> list) {

        JFXAlert<String> alert = new JFXAlert<>((Stage) this.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Enter email password"));

        JFXPasswordField passwordField = new JFXPasswordField();
        Label            label         = new Label("Password:  ");
        HBox             box           = new HBox();
        box.getChildren().addAll(label, passwordField);


        layout.setBody(box);
        JFXButton delete = new JFXButton("CONTINUE");
        JFXButton cancel = new JFXButton("CANCEL");

        delete.setOnAction(e -> {
            password = passwordField.getText();
            alert.hideWithAnimation();
            sendReport(list);
//            System.out.println(password);
        });

        cancel.setOnAction(b -> {
            alert.hideWithAnimation();
        });
        layout.setActions(delete, cancel);
        alert.setContent(layout);
        alert.show();
    }


    private void sendReport(List<SupplierOrders> list) {

        JFXProgressBar bar = new JFXProgressBar();

        JFXAlert<String> alert = new JFXAlert<>((Stage) this.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setAnimation(JFXAlertAnimation.BOTTOM_ANIMATION);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Sending QA reports"));
        layout.setBody(bar);
        alert.setContent(layout);

        alert.show();
        bar.setProgress(0);

        Thread thread = new Thread(() -> {
            buttonThread(alert, bar, list);
        });
        thread.setDaemon(true);
        thread.start();

    }


    private void buttonThread(JFXAlert<String> alert, JFXProgressBar bar, List<SupplierOrders> list) {

        double                  total    = list.size();
        AtomicReference<Double> progress = new AtomicReference<>((double) 0);
        Map<String, String>     map      = new HashMap<>();
        list.forEach(supplierOrders -> {
            progress.updateAndGet(v -> v + 1);
            bar.setProgress(progress.get() / total);
            PDFFile.createPDFFile(email, password, supplierOrders.getPoNumber(), map);

        });
        alert.hideWithAnimation();

        Platform.runLater(() -> {

            if (map.size() > 0) {
                StringBuilder errors = new StringBuilder();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String s  = entry.getKey();
                    String s2 = entry.getValue();
                    errors.append(s).append(":  ").append(s2).append("\n");
                }

                ScrollPane pane = new ScrollPane();
                pane.setContent(new Label(errors.toString()));
                pane.setMaxHeight(250);
                msg.continueAlert(this, LabelWithIcons.largeWarningIconLabel("Errors occurred"), pane);
//                System.out.println("Printing all errors: " + errors);
            }
            else {

                msg.continueAlert(this, LabelWithIcons.largeCheckIconLabel("Emails sent"), new Label(""));
            }
        });

    }


    @FXML
    private void sendSingleReport() {

        if (record != null) {
//            System.out.println(record.getPoNumber());
            setPassword(Collections.singletonList(record));
        }

    }


    @FXML
    private void saveWeights() {

        if (orderQaRecordsList.getItems().size() > 0) {
            AtomicBoolean saved = new AtomicBoolean(true);

            orderQaRecordsList.getItems().forEach(qaRecordListBox -> {
                if (! qaRecordListBox.saveWeights()) {
                    saved.set(false);
                }
            });
            if (saved.get()) {
                msg.continueAlert(this, LabelWithIcons.largeCheckIconLabel(""), new Label("Weights and boxes data saved"));

            }
            else {
                msg.continueAlert(this, LabelWithIcons.largeWarningIconLabel("Error"), new Label("Failed to save/update "));

            }
        }
    }
}
