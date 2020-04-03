package app.controller.utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class OpenFile {

    public static void openFile(String path){

        File file = new File(path);
        //first check if Desktop is supported by Platform or not
        if(! Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
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
}
