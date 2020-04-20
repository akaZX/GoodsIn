package app.controller.utils;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Label;


public class LabelWithIcons {


//Warning icons
    public static Label warningIconLabel(String message, int size){
        return label(message, triangleIcon(size));
    }

    public static Label smallWarningIconLabel(String message){
        return label(message, triangleIcon(15));
    }
    public static Label midWarningIconLabel(String message){
        return label(message, triangleIcon(20));
    }
    public static Label largeWarningIconLabel(String message){
        return label(message, triangleIcon(25));
    }

    public static Label checkIconLabel(String message, int size){
        return label(message, checkIcon(size));
    }

    public static Label smallCheckIconLabel(String message){
        return label(message, checkIcon(15));
    }
    public static Label midCheckIconLabel(String message){
        return label(message, checkIcon(20));
    }
    public static Label largeCheckIconLabel(String message){
        return label(message, checkIcon(25));
    }

//  MAIN ICON METHODS
    private static FontAwesomeIconView triangleIcon(int size){
        return iconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE, "red", size);
    }
    private static FontAwesomeIconView checkIcon(int size){
        return iconView(FontAwesomeIcon.CHECK, "green", size);
    }

    private static FontAwesomeIconView iconView(FontAwesomeIcon icon, String color, int size){
        FontAwesomeIconView iconView = new FontAwesomeIconView(icon);
        iconView.setStyle("-fx-fill: " + color + "; -glyph-size: "+ size + "px");
        return iconView;
    }

    private static Label label(String text, FontAwesomeIconView icon){
        Label label = new Label();
        label.setGraphic(icon);
        label.setText(text);
        return label;
    }

}
