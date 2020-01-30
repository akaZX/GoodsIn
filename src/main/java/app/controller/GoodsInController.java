package app.controller;


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
        //adds calendar tab to tabpane
       //mainTabPane.getTabs().addAll(CalendarViewController.loadCalendar());



        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupplierView.fxml"));
        SuppliersView contr = new SuppliersView();
        loader.setController(contr);
        mainTabPane.getTabs().add(loadTab(loader, new Tab("Suppliers")));

        mainTabPane.getTabs().add(new POTableTab().createTable());
    }


    private Tab loadTab(FXMLLoader loader, Tab tab) {

       // FormController contr = new FormController();
       // loader.setController(contr);
        try {
            tab.setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }


}