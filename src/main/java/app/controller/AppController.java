package app.controller;


import app.controller.goodsIn.qaReport.ReportView;
import app.controller.rmt.materials.MainMaterialProfileController;
import app.controller.goodsIn.schedule.POTableTab;
import app.controller.rmt.records.MainRmtIntakeController;
import app.controller.sql.dao.UsersDao;
import app.controller.suppliersView.MainSupplierController;
import app.controller.userAccounts.UserAccountsView;
import app.pojos.Users;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AppController implements Initializable {

    @FXML
    TabPane mainTabPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        adds calendar tab to tabpane

//        Users users = new UsersDao().get(System.getProperty("user.name"));
//        if(!users.getGoodsIn()){
//
//            mainTabPane.getTabs().addAll(CalendarViewController.loadCalendar());
//        }



        //Intake form

        FXMLLoader              drawer =  new FXMLLoader(getClass().getResource("/common/blankStackPane.fxml"));
        MainRmtIntakeController c      = new MainRmtIntakeController();
        drawer.setController(c);
        mainTabPane.getTabs().add( loadTab(drawer, new Tab("testas")));




        ReportView reportView = new ReportView();
        Tab        reportTab = new Tab("QA Records");
        reportTab.setContent(reportView);
        mainTabPane.getTabs().add(reportTab);


        UserAccountsView view = new UserAccountsView();
        Tab tabb = new Tab("Users");
        tabb.setContent(view);
        mainTabPane.getTabs().add(tabb);


        FXMLLoader                    matProf    = new FXMLLoader(getClass().getResource("/common/blankStackPane.fxml"));
        MainMaterialProfileController controller = new MainMaterialProfileController();
        matProf.setController(controller);
        mainTabPane.getTabs().add(loadTab(matProf, new Tab("Intake")));


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