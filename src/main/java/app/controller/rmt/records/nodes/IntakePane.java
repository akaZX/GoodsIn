package app.controller.rmt.records.nodes;

import app.controller.sql.dao.MaterialsDao;
import app.pojos.PoMaterials;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class IntakePane  {

    private final String RIGHT = "RIGHT";

    PoMaterials poMaterials;

    private final StackPane stackPane = new StackPane();

    private final JFXDrawersStack drawersStack = new JFXDrawersStack();


    public IntakePane() {

    }


    public IntakePane(PoMaterials poMaterials) {
        this();
        this.poMaterials = poMaterials;
        loadForm();
    }



    private void loadForm(){

        JFXDrawer           rightDrawer = new JFXDrawer();
        MaterialDetailsDrawer        detailsDrawer = new MaterialDetailsDrawer(new MaterialsDao().get(poMaterials.getMCode()));
        rightDrawer.setSidePane(detailsDrawer);
        SpecsIntakePaneView form       = new SpecsIntakePaneView(poMaterials, drawersStack, rightDrawer);
        rightDrawer.setId(RIGHT);
        rightDrawer.setDirection(JFXDrawer.DrawerDirection.RIGHT);
        stackPane.getChildren().add(form);

        rightDrawer.setDefaultDrawerSize(250);
        rightDrawer.setResizeContent(true);
        rightDrawer.setOverLayVisible(false);
        rightDrawer.setResizableOnDrag(false);
        rightDrawer.setMiniDrawerSize(5);
        drawersStack.setContent(form);
        stackPane.getChildren().add(drawersStack);
        drawersStack.toggle(rightDrawer);
        drawersStack.toggle(rightDrawer);


    }

    public StackPane getPane(){
        return stackPane;
    }
}



