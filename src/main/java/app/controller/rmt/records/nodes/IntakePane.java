package app.controller.rmt.records.nodes;

import app.controller.sql.dao.MaterialsDao;
import app.pojos.PoMaterials;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import javafx.scene.layout.StackPane;

public class IntakePane {

    private final String RIGHT = "RIGHT";

    private final StackPane stackPane = new StackPane();

    private final JFXDrawersStack drawersStack = new JFXDrawersStack();

    PoMaterials poMaterials;
    private OrderMaterialsDrawer leftDrawer;


    public IntakePane(PoMaterials poMaterials, OrderMaterialsDrawer drawer) {

        this();
        this.poMaterials = poMaterials;
        this.leftDrawer = drawer;
        loadForm();
    }


    public IntakePane() {

    }


    private void loadForm() {

        JFXDrawer             rightDrawer   = new JFXDrawer();
        MaterialDetailsDrawer detailsDrawer = new MaterialDetailsDrawer(new MaterialsDao().get(poMaterials.getMCode()));
        rightDrawer.setSidePane(detailsDrawer);
        SpecsIntakePaneView form = new SpecsIntakePaneView(poMaterials, drawersStack, rightDrawer, leftDrawer);
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


    public StackPane getPane() {

        return stackPane;
    }
}



