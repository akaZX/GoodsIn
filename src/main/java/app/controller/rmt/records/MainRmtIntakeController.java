package app.controller.rmt.records;

import app.controller.rmt.records.nodes.OrdersDrawer;
import app.controller.rmt.records.nodes.SpecsIntakePaneView;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainRmtIntakeController implements Initializable {


    private final JFXDrawersStack drawersStack = new JFXDrawersStack();

    @FXML
    private StackPane stackPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        leftDrawer();

    }


    private void leftDrawer() {

        JFXDrawer           leftDrawer   = new JFXDrawer();
        SpecsIntakePaneView form         = new SpecsIntakePaneView();
        OrdersDrawer        ordersDrawer = new OrdersDrawer(leftDrawer, drawersStack);
        leftDrawer.setSidePane(ordersDrawer);

        String LEFT = "LEFT";
        leftDrawer.setId(LEFT);
        leftDrawer.setDirection(JFXDrawer.DrawerDirection.LEFT);
        stackPane.getChildren().add(form);

        leftDrawer.setDefaultDrawerSize(250);
        leftDrawer.setResizeContent(true);
        leftDrawer.setOverLayVisible(false);
        leftDrawer.setResizableOnDrag(false);
        leftDrawer.setMiniDrawerSize(3);

        drawersStack.setContent(form);
        drawersStack.toggle(leftDrawer);

        stackPane.getChildren().add(drawersStack);
    }
}
