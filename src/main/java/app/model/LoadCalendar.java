package app.model;



import app.controller.CalendarViewController;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import javafx.application.Platform;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import java.time.LocalDate;
import java.time.LocalTime;

public  class LoadCalendar {


    public static Tab loadCalendar(StackPane calendarPane, Tab calendarTab){
        final CalendarView calendarView = new CalendarView();
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowDeveloperConsole(true);
        Calendar katja = new Calendar("Bay01");
        Calendar dirk = new Calendar("Bay02");
        Calendar philip = new Calendar("Bay03");
        Calendar jule = new Calendar("Bay04");
        katja.setShortName("Bay01");
        dirk.setShortName("Bay02");
        philip.setShortName("Bay03");
        jule.setShortName("Bay04");
        CalendarViewController.initializeCalendars(calendarView, katja, dirk, philip, jule, calendarPane, calendarTab);

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            public void run() {
                while(true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });

                    try {
                        sleep(999999L);
                    } catch (InterruptedException var2) {
                        var2.printStackTrace();
                    }
                }
            }
        };
        updateTimeThread.setPriority(1);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();

        return calendarTab;
    }

}