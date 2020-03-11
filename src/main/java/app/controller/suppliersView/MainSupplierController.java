package app.controller.suppliersView;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainSupplierController implements Initializable {

    @FXML
    StackPane stackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SupplierDrawerController drawerController = new SupplierDrawerController();

        FXMLLoader drawerLoader = new FXMLLoader(
                getClass().getResource(
                        "/suppliers/supplierDrawer.fxml"
                )
        );
        drawerLoader.setController(drawerController);


        JFXDrawer leftDrawer = new JFXDrawer();

        try {

            StackPane form = new StackPane();
            AnchorPane drawer = drawerLoader.load();
            leftDrawer.setSidePane(drawer);

            stackPane.getChildren().add(form);

            leftDrawer.setDefaultDrawerSize(275);
            leftDrawer.setResizeContent(true);
            leftDrawer.setOverLayVisible(false);
            leftDrawer.setResizableOnDrag(false);
            leftDrawer.setMiniDrawerSize(10);
            JFXDrawersStack drawersStack = new JFXDrawersStack();

            drawersStack.setContent(form);
            drawersStack.toggle(leftDrawer);
            String ID = "LEFT";
            leftDrawer.setId(ID);


            drawerController.selected(drawersStack);
            stackPane.getChildren().add(drawersStack);

        }
        catch (IOException e) {
            System.out.println("luzta");
            e.printStackTrace();
        }


    }
}
