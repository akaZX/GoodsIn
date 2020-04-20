package app.controller.utils;

import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.Node;
import javafx.scene.control.Label;



public class ValidateInput{
    private static final String ERROR = "error-icon";

    public  static void requiredFieldValidation(Node node, String message, boolean iconB, boolean focusListener){

        ((IFXValidatableControl)node).getValidators().add(validator(message, iconB));
        if(focusListener){
            nodeFocusedListener(node);
        }

    }

    public  static void requiredFieldValidation(Node node, String message){
        requiredFieldValidation(node, message, true, false);
    }


    private static RequiredFieldValidator validator(String message, boolean iconB){
        RequiredFieldValidator validator = new RequiredFieldValidator();
        Label label = new Label();

        if(iconB){
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION);
            icon.getStyleClass().add(ERROR);
            label.setGraphic(icon);
        }
        label.setText(message);
        validator.setIcon(label);
        return validator;
    }

    private static void nodeFocusedListener(Node node){
        node.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                if(node instanceof IFXValidatableControl){
                    ((IFXValidatableControl) node).validate();
                }

            }
        });
    }

}
