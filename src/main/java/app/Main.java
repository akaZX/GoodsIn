package app;

import app.controller.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage)throws Exception{


        AppController contr = new AppController();

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("goodsInMenu.fxml"));
        loader.setController(contr);
        Parent root = loader.load();

        Scene scene = new Scene(root);


        primaryStage.setTitle("RMT/GI");


        primaryStage.setScene(scene);

        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/bakkavor_log.jpeg")));


        URL url = this.getClass().getResource("/style.css");
        if (url == null) {
            System.out.println("Resource not found. Aborting.");
            System.exit(-1);
        }
        String       css       = url.toExternalForm();

        primaryStage.getScene().getStylesheets().add(css);


        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(1000);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}