package app.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class GoodsInController implements Initializable {



    @FXML
    TabPane mainTabPane;
    @FXML
    AnchorPane testas;
    @FXML
    GridPane deliveryFormGrid;



    Tab calendarTab = new Tab("Calendar");

    Tab form = new Tab("Add delivery");

    StackPane calendarPane = new StackPane();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //adds calendar tab to tabpane
        mainTabPane.getTabs().addAll(CalendarViewController.loadCalendar());


        mainTabPane.getTabs().add(loadTab(new FXMLLoader(getClass().getClassLoader().getResource("deliveryForm.fxml")), form));
        mainTabPane.getTabs().add(new POTableTab().createTable());


        //pane.getTabs().addAll((Tab)FXMLLoader.load(this.getClass().getResource("MyTab.fxml")));




    }

    public Tab loadTab(FXMLLoader loader, Tab tab) {

        FormController contr = new FormController();
        loader.setController(contr);
        try {
            tab.setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }


}