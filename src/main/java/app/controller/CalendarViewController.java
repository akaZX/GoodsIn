package app.controller;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class CalendarViewController{

    @FXML
    static Tab calendarViewTab = new Tab("Calendar");

    static StackPane calendarViewPane = new StackPane();


    public static Tab loadCalendar(){
        final CalendarView calendarView = new CalendarView();
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowDeveloperConsole(true);
        Calendar katja = new Calendar("Katja");
        Calendar dirk = new Calendar("Dirk");
        Calendar philip = new Calendar("Philip");
        Calendar jule = new Calendar("Jule");
        katja.setShortName("K");
        dirk.setShortName("D");
        philip.setShortName("P");
        jule.setShortName("J");
        katja.setStyle(Calendar.Style.STYLE1);
        dirk.setStyle(Calendar.Style.STYLE2);
        philip.setStyle(Calendar.Style.STYLE3);
        jule.setStyle(Calendar.Style.STYLE4);
        CalendarSource familyCalendarSource = new CalendarSource("Family");
        familyCalendarSource.getCalendars().addAll(new Calendar[]{katja, dirk, philip, jule});
        calendarView.getCalendarSources().setAll(new CalendarSource[]{familyCalendarSource});
        calendarView.setRequestedTime(LocalTime.now());
        calendarViewPane.getChildren().addAll(calendarView);
        calendarViewTab.setContent(calendarViewPane);
        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            public void run() {
                while(true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });

                    try {
                        System.out.println("atnaujina");
                        sleep(10000L);
                    } catch (InterruptedException var2) {
                        var2.printStackTrace();
                    }
                }
            }
        };
        updateTimeThread.setPriority(1);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
        return calendarViewTab;
    }
}