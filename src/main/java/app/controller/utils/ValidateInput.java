package app.controller.utils;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import jdk.internal.dynalink.beans.StaticClass;
import org.omg.CORBA.PUBLIC_MEMBER;


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

    public  static void emailBox(JFXTextField field) {
        String FX_LABEL_FLOAT_TRUE = "-fx-label-float:true;";

        RegexValidator valid = new RegexValidator();
        //Regex expression to check for emails
        valid.setRegexPattern("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                              + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");



        field.setStyle(FX_LABEL_FLOAT_TRUE);

        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION);
        icon.getStyleClass().add(ERROR);
        Label label = new Label("Invalid email!");
        label.setGraphic(icon);
        valid.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        icon.setStyleClass(ERROR);
        valid.setIcon(label);
        field.getValidators().add(valid);

        doEmailValidate(field);

    }


    private static void doEmailValidate(JFXTextField field) {

        field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                field.validate();
            }
            if (oldValue) {
                field.validate();
            }
        });


    }





}
