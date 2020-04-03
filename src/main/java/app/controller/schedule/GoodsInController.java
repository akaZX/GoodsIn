package app.controller.schedule;


import app.controller.rmt.materials.MainMaterialProfileController;
import app.controller.suppliersView.MainSupplierController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class GoodsInController implements Initializable {

    @FXML
    TabPane mainTabPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        adds calendar tab to tabpane
//       mainTabPane.getTabs().addAll(CalendarViewController.loadCalendar());

        FXMLLoader                    matProf    = new FXMLLoader(getClass().getResource("/common/blankStackPane.fxml"));
        MainMaterialProfileController controller = new MainMaterialProfileController();
        matProf.setController(controller);
        mainTabPane.getTabs().add(loadTab(matProf, new Tab("Testing")));


        FXMLLoader             loader = new FXMLLoader(getClass().getResource("/common/blankStackPane.fxml"));
        MainSupplierController contr  = new MainSupplierController();
        loader.setController(contr);
        mainTabPane.getTabs().add(loadTab(loader, new Tab("Supplier Profiles")));


//        FXMLLoader                mat         = new FXMLLoader(getClass().getResource("/rmt/MaterialProfileView.fxml"));
//        MaterialProfileController maControler = new MaterialProfileController();
//        mat.setController(maControler);
//        mainTabPane.getTabs().add(loadTab(mat, new Tab("Material Profiles")));

        mainTabPane.getTabs().add(new POTableTab().createTable());
    }


    private Tab loadTab(FXMLLoader loader, Tab tab) {

//        FormController contr = new FormController();
//        loader.setController(contr);
        try {
            tab.setContent(loader.load());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }


}