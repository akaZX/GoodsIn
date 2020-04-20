package app.controller.suppliersView;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
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
        JFXDrawersStack drawersStack = new JFXDrawersStack();
        SupplierDrawerController drawerController = new SupplierDrawerController();

        FXMLLoader drawerLoader = new FXMLLoader(
                getClass().getResource(
                        "/suppliers/supplierDrawer.fxml"
                )
        );
        drawerLoader.setController(drawerController);



        JFXDrawer leftDrawer = new JFXDrawer();

        try {

            StackPane  form   = new StackPane();
            AnchorPane drawer = drawerLoader.load();
            leftDrawer.setSidePane(drawer);

            stackPane.getChildren().add(form);

            leftDrawer.setDefaultDrawerSize(275);
            leftDrawer.setResizeContent(true);
            leftDrawer.setOverLayVisible(false);
            leftDrawer.setResizableOnDrag(false);
            leftDrawer.setMiniDrawerSize(3);






            drawersStack.setContent(form);
            drawersStack.toggle(leftDrawer);
            drawersStack.toggle(leftDrawer);

            String ID = "LEFT";
            leftDrawer.setId(ID);


            drawerController.selected(drawersStack, leftDrawer);
            stackPane.getChildren().add(drawersStack);


        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
}
