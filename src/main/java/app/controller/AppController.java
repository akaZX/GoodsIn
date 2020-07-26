package app.controller;


import app.controller.goodsIn.qaReport.ReportView;
import app.controller.goodsIn.schedule.CalendarViewController;
import app.controller.goodsIn.schedule.POTableTab;
import app.controller.goodsIn.suppliersView.MainSupplierController;
import app.controller.rmt.materials.MainMaterialProfileController;
import app.controller.rmt.records.MainRmtIntakeController;
import app.controller.sql.dao.UsersDao;
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

//        restrictedAccess();

        noRestrictionsAccess();
    }


    //ignores user access levels
    private void noRestrictionsAccess() {

        restrictedAccess();
        //adds Calendar view
//        mainTabPane.getTabs().addAll(CalendarViewController.loadCalendar());
//
//
//        //adds Schedule orders list
//        mainTabPane.getTabs().add(new POTableTab().createTable(false));
//
//
//        //adds supplier profiles view
//        FXMLLoader             loader = new FXMLLoader(getClass().getResource("/common/blankStackPane.fxml"));
//        MainSupplierController contr  = new MainSupplierController();
//        loader.setController(contr);
//        mainTabPane.getTabs().add(loadTab(loader, new Tab("Supplier Profiles")));
//
//        //QA Records view
//        ReportView reportView = new ReportView();
//        Tab        reportTab  = new Tab("QA Reports");
//        reportTab.setContent(reportView);
//        mainTabPane.getTabs().add(reportTab);
//
//
//        //Materials view
//        FXMLLoader              drawer = new FXMLLoader(getClass().getResource("/common/blankStackPane.fxml"));
//        MainRmtIntakeController c      = new MainRmtIntakeController();
//        drawer.setController(c);
//        mainTabPane.getTabs().add(loadTab(drawer, new Tab("Material Intake")));
//
//
//        FXMLLoader                    matProf    = new FXMLLoader(getClass().getResource("/common/blankStackPane.fxml"));
//        MainMaterialProfileController controller = new MainMaterialProfileController();
//        matProf.setController(controller);
//        mainTabPane.getTabs().add(loadTab(matProf, new Tab("Material Profiles")));
//
//
//        UserAccountsView view = new UserAccountsView();
//        Tab              tab  = new Tab("Users");
//        tab.setContent(view);
//        mainTabPane.getTabs().add(tab);

    }


    private void restrictedAccess() {

        Users users = new UsersDao().get(System.getProperty("user.name"));
        if (users.getUsername() == null) {
            System.out.println("User not recognised");
            System.exit(- 1);
        }
        if (users.getGoodsIn() || users.getSecurity()) {
            //adds Calendar view
            mainTabPane.getTabs().addAll(CalendarViewController.loadCalendar());
            //adds Schedule orders list

            boolean security = users.getSecurity();
            if (users.getGoodsIn()) {
                security = false;
            }

            mainTabPane.getTabs().add(new POTableTab().createTable(security));
        }


        if (users.getGoodsIn()) {

            //adds supplier profiles view
            FXMLLoader             loader = new FXMLLoader(getClass().getResource("/common/blankStackPane.fxml"));
            MainSupplierController contr  = new MainSupplierController();
            loader.setController(contr);
            mainTabPane.getTabs().add(loadTab(loader, new Tab("Supplier Profiles")));

            //QA Records view
            ReportView reportView = new ReportView();
            Tab        reportTab  = new Tab("QA Records");
            reportTab.setContent(reportView);
            mainTabPane.getTabs().add(reportTab);

        }


        if (users.getRmtGoodsIn() || users.getRmtAdmin()) {

            //Materials view
            FXMLLoader              drawer = new FXMLLoader(getClass().getResource("/common/blankStackPane.fxml"));
            MainRmtIntakeController c      = new MainRmtIntakeController();
            drawer.setController(c);
            mainTabPane.getTabs().add(loadTab(drawer, new Tab("Material Intake")));


            FXMLLoader                    matProf    = new FXMLLoader(getClass().getResource("/common/blankStackPane.fxml"));
            MainMaterialProfileController controller = new MainMaterialProfileController();
            matProf.setController(controller);
            mainTabPane.getTabs().add(loadTab(matProf, new Tab("Material Profiles")));

        }

        if (users.getAdmin()) {
            UserAccountsView view = new UserAccountsView();
            Tab              tab  = new Tab("Users");
            tab.setContent(view);
            mainTabPane.getTabs().add(tab);
        }
    }

    private Tab loadTab(FXMLLoader loader, Tab tab) {

        try {
            tab.setContent(loader.load());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }


}