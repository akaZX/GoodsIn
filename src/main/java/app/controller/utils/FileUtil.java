package app.controller.utils;


import javafx.scene.image.Image;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class FileUtil {

    public static void openFile(String path){

        File file = new File(path);
        System.out.println(file.getName());

        //is Desktop supported
        if(! Desktop.isDesktopSupported()){
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        if (file.exists()) {
            try {
                desktop.open(file);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public  Image getImage(String path){

        String placeholder = "";

        String url;

        try {
            placeholder = getClass().getResource("/images/missing_image_placeholder.jpg").toURI().toURL().toString();

            if (path != null && path.length() > 20) {
                url = new File(path).toURI().toURL().toString();
            }
            else {
                url = getClass().getResource("/images/missing_image_placeholder.jpg").toURI().toURL().toString();
            }
        }
        catch (MalformedURLException | NullPointerException | URISyntaxException e) {
            System.out.println("Error at: FileUtil.getImage()");
            url = placeholder;
        }
        return new Image(url);
    }





}
