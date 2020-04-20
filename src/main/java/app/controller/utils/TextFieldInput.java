package app.controller.utils;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.scene.control.TextFormatter;



public class TextFieldInput {

    private final String MESSAGE ="Missing";

    private RequiredFieldValidator validator;



    public void doubleTextField(JFXTextField field) {

        doubleField(field);
    }


    public void doubleTextField(JFXTextField field, String validatorMessage) {

        doubleField(field);
        addValidation(field, validatorMessage);

    }


    public void doubleTextField(JFXTextField field, boolean defaultValidatorMessage) {

        if (defaultValidatorMessage) {
            doubleTextField(field, MESSAGE);
        }
        else {
            doubleTextField(field);
        }

    }


    private void doubleField(JFXTextField field) {

        field.setTextFormatter(new TextFormatter<>(c -> {
            if (c.getControlNewText().length() > 0) {
                try {
                    Double.parseDouble(c.getControlNewText());
                    return c;
                }
                catch (NumberFormatException | NullPointerException e) {

                    return null;
                }
            }
            else {
                return c;
            }
        }));
    }


//    private void validateField(JFXTextField field, RequiredFieldValidator requiredFieldValidator) {
//
//        field.setValidators(requiredFieldValidator);
//        field.textProperty().addListener((observable, oldValue, newValue) -> {
//            field.validate();
//        });
//        field.validate();
//    }


    public static void mainIntField(JFXTextField field) {

        field.setTextFormatter(new TextFormatter<>(c -> {
            if (c.getControlNewText().length() > 0) {
                try {
                    Integer.parseInt(c.getControlNewText());
                    return c;
                }
                catch (NumberFormatException | NullPointerException e) {

                    return null;
                }
            }
            else {
                return c;
            }
        }));

    }


    public void intField(JFXTextField field, String validatorMessage) {

        mainIntField(field);
        addValidation(field,validatorMessage);
    }



    public void intField(JFXTextField field, boolean defaultValidatorMessage) {

        if (defaultValidatorMessage) {
            intField(field, MESSAGE);
        }
        else {
            mainIntField(field);
        }
    }


    public void addValidator(JFXTextField field) {

        addValidation(field, MESSAGE);
    }
    public void addValidator(JFXTextField field, String message) {

        addValidation(field, message);
    }

    private void addValidation(JFXTextField field, String message) {
        validator = new RequiredFieldValidator(message);
        field.setValidators(validator);
        field.textProperty().addListener(((observable, oldValue, newValue) -> {
                field.validate();

        }));
        field.validate();

    }


}
