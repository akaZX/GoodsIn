package app.controller.rmt.materials;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.sun.org.apache.bcel.internal.generic.ARETURN;
import com.sun.org.apache.bcel.internal.generic.DRETURN;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMaterialProfileController implements Initializable {
    @FXML
    StackPane stackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        MaterialListDrawerController drawerController = new MaterialListDrawerController();

        FXMLLoader drawerLoader = new FXMLLoader(
                getClass().getResource(
                        "/rmt/MaterialDrawer.fxml"
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
            JFXDrawersStack drawersStack = new JFXDrawersStack();

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
