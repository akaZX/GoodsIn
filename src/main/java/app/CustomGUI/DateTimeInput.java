package app.CustomGUI;



import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;



public class DateTimeInput extends HBox {

@FXML private JFXDatePicker date;
@FXML private JFXTextField hours;
@FXML private JFXTextField minutes;



    public DateTimeInput() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("dateTimeField.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        formatTextFields(hours, 23);
        formatTextFields(minutes, 59);
    }

    private void formatTextFields(JFXTextField field, int limit) {

//TODO patvarkyti kad leistu ivesti valandas su vienu skaicium
        field.textProperty().addListener((observable, oldValue, newValue) ->{
            if(oldValue.length()> newValue.length()){
                field.setText(newValue);

            }else{
                if (newValue.length() == 1){
                    if(newValue.matches("[\\d]")){
                        if (Integer.parseInt(field.getText()) > limit/10){
                            field.setText(oldValue);
                        }
                        return;
                    }else{
                        field.setText(oldValue);
                    }
                }
                if(newValue.length() == 2){

                    if(newValue.matches("[\\d]{2}")){
                        if(Integer.parseInt(field.getText()) > limit){
                            field.setText(oldValue);
                        }else{
                            Robot robot = null;
                            try {
                                robot = new Robot();
                            } catch (AWTException e) {
                                e.printStackTrace();
                            }
                            robot.keyPress(KeyEvent.VK_TAB);
                            robot.keyRelease(KeyEvent.VK_TAB);
                        }

                    }else{
                        field.setText(oldValue);
                    }
                }
            }
        });

        //for validating input and leaving it in two digit format
        field.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (!newPropertyValue)
                {
                    System.out.println("out of focus");
                }
            }
        });

    }



    public LocalDateTime getLocalDateTime(){
        LocalDateTime time = null;

        if(!hours.getText().equalsIgnoreCase("") && !minutes.getText().equalsIgnoreCase("")){
            try{
                LocalDate date = this.date.getValue();
                int hour = Integer.parseInt(hours.getText());
                int minute = Integer.parseInt(minutes.getText());
                time = date.atTime(hour, minute);

            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }



        return time;
    }

    public void setCurrentTime(){
        LocalTime time = LocalTime.now();
        int hour = time.getHour();
        int minute = time.getMinute();

        hours.setText(String.valueOf(hour));
        minutes.setText(String.valueOf(minute));
        date.setValue(LocalDate.now());
    }


    public LocalTime getLocalTime(){
        return  LocalTime.of(getHours(), getMinutes());
    }

    public LocalDate getLocalDate(){
        return date.getValue();
    }

    public int getHours(){
        int hour = Integer.parseInt(hours.getText());
        return hour;
    }

    public int getMinutes(){
        int minutes = Integer.parseInt(this.minutes.getText());
        return minutes;
    }

    public void setDate(LocalDate date){
        this.date.setValue(date);
    }

    public void setLocalDateTime(LocalDateTime localDateTime){
        setDate(localDateTime.toLocalDate());
        hours.setText(String.valueOf(localDateTime.getHour()));
        minutes.setText(String.valueOf(localDateTime.getMinute()));
    }


}
