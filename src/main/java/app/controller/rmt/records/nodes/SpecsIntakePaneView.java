package app.controller.rmt.records.nodes;

import app.controller.sql.dao.*;
import app.controller.utils.LabelWithIcons;
import app.controller.utils.Messages;
import app.pojos.PoMaterials;
import app.pojos.RmtQaRecords;
import app.pojos.Suppliers;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXHamburger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class SpecsIntakePaneView extends AnchorPane {

    private Messages msg = new Messages();

    private JFXDrawersStack drawersStack;
    private JFXDrawer detailsDrawer;

    private final List<RmtQaRecords> records = new ArrayList<>();


    private PoMaterials poMaterial;

    private int minRows = 0;
    private int row = 0;

    @FXML
    private Label topLabel;

    @FXML
    private GridPane gridPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private JFXHamburger rightHamburger;
    @FXML
    private HBox bottomButtons;


    @FXML
    private void addNewRecord() {

        try {
            if (new MaterialSpecsDao().get(poMaterial.getMCode())!= null) {
                RmtQaRecords record = new RmtQaRecords();
                record.setPo(poMaterial.getPo());
                record.setmCode(poMaterial.getMCode());
                gridPane.add(new SpecsIntakePane(record, this), 0, row);
                row++;
            }else {
                msg.continueAlert(this, LabelWithIcons.largeWarningIconLabel("Missing material specs"), new Label());

            }
        }
        catch (Exception e) {
            msg.continueAlert(this, LabelWithIcons.largeWarningIconLabel("Select material"), new Label());
        }
    }

    @FXML
    private void removeRecord(ActionEvent event) {

        if(minRows < row){

            ObservableList<Node> childrens = gridPane.getChildren();
            for(Node node : childrens) {
                if(GridPane.getRowIndex(node) == (row - 1)) {
                    SpecsIntakePane specsPane= (SpecsIntakePane)node;
                    gridPane.getChildren().remove(specsPane);
                    break;
                }
            }
            if (row > 0) {
                row--;
            }
            scrollPane.setContent(gridPane);
        }else{
            msg.continueAlert(
                    this,
                    LabelWithIcons.largeWarningIconLabel("Error"),
                    new Label("Can not delete saved records from here,\n use delete button on intake form")
            );
        }

    }

    public SpecsIntakePaneView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("rmt/qualityRecords/MainSpecsPane.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        showButtons(false);
    }



    public SpecsIntakePaneView(PoMaterials poMaterial, JFXDrawersStack drawersStack, JFXDrawer detailsDrawer) {
        this();
        this.poMaterial = poMaterial;
        this.drawersStack = drawersStack;
        this.detailsDrawer = detailsDrawer;
        setRightHamburger();
        List<RmtQaRecords> allRecords = new RmtQaRecordsDao().getAll(poMaterial.getPo());

        for (RmtQaRecords record : allRecords) {
            if (record.getmCode().equalsIgnoreCase(poMaterial.getMCode())) {
                records.add(record);
            }
        }
        showButtons(true);

        setTopLabel();
        loadSpecPanes();

    }


    private void setRightHamburger(){
        rightHamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{
            Thread thread = new Thread(()->{

                Platform.runLater(()->{
                     drawersStack.toggle(detailsDrawer);
                });
            });
            thread.setPriority(10);
            thread.start();
        });

    }

    private void setTopLabel(){

        Suppliers suppliers = new SuppliersDao().get(new SupplierOrderDao().getBy(poMaterial.getPo(), "po").getSuppCode());

        String label = poMaterial.getPo() + " - " + suppliers.getSupplierName() +
                "\n" + poMaterial.getMCode() + " - " + new MaterialsDao().get(poMaterial.getMCode()).getName();
        topLabel.setText(label);

    }

    private void loadSpecPanes(){

        gridPane.getChildren().removeAll();


                for(RmtQaRecords record : records) {
                    gridPane.add(new SpecsIntakePane(record, this), 0, row);
                    row++;
                    minRows++;
                    scrollPane.setContent(gridPane);

                }

//                System.out.println("Rows po visko: "+ minRows);

    }


    public void load(PoMaterials poMaterial){


        this.poMaterial = poMaterial;
        List<RmtQaRecords> allRecords = new RmtQaRecordsDao().getAll(poMaterial.getPo());

        for (RmtQaRecords record : allRecords) {
            if (record.getmCode().equalsIgnoreCase(poMaterial.getMCode())) {
                records.add(record);
            }
        }
        setTopLabel();
        loadSpecPanes();
    }

    public void removeSpecsPane(SpecsIntakePane pane){
        gridPane.getChildren().remove(pane);
    }


    private void showButtons(boolean show){

        bottomButtons.setVisible(show);
        rightHamburger.setVisible(show);

    }
}
