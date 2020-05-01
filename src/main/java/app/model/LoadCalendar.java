package app.model;



import app.controller.goodsIn.schedule.CalendarViewController;
import com.calendarfx.model.Calendar;
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
        Calendar bay01 = new Calendar("Bay01");
        Calendar bay02 = new Calendar("Bay02");
        Calendar bay03 = new Calendar("Bay03");
        Calendar bay04 = new Calendar("Bay04");
        bay01.setShortName("Bay01");
        bay02.setShortName("Bay02");
        bay03.setShortName("Bay03");
        bay04.setShortName("Bay04");
        CalendarViewController.initializeCalendars(calendarView, bay01, bay02, bay03, bay04, calendarPane, calendarTab);

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