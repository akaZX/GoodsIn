package app;


import app.controller.GoodsInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.net.URL;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage)throws Exception{


        GoodsInController contr = new GoodsInController();

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("goodsInMenu.fxml"));
        loader.setController(contr);
        Parent root = loader.load();


        Scene scene = new Scene(root);


        primaryStage.setTitle("GI");

        primaryStage.setScene(scene);



//        new JMetro(JMetro.Style.LIGHT).applyTheme(scene);



        URL url = this.getClass().getResource("/style.css");
        if (url == null) {
            System.out.println("Resource not found. Aborting.");
            System.exit(-1);
        }
        String css = url.toExternalForm();
        primaryStage.getScene().getStylesheets().add(css);



        primaryStage.setMinHeight(700);
        primaryStage.setMinWidth(950);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}