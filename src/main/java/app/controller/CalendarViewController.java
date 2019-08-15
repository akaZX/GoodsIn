package app.controller;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import java.time.LocalDate;
import java.time.LocalTime;

public class CalendarViewController{

    @FXML
    static Tab calendarViewTab = new Tab("Calendar");

    static StackPane calendarViewPane = new StackPane();


    public static Tab loadCalendar(){
        final CalendarView calendarView = new CalendarView();
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowDeveloperConsole(true);
        Calendar bayOne = new Calendar("BAY 1");
        Calendar bayTwo = new Calendar("BAY 2");
        Calendar bayThree = new Calendar("BAY 3");
        Calendar bayFour = new Calendar("BAY 4");

        bayOne.setShortName("BAY 1");
        bayTwo.setShortName("BAY 2");
        bayThree.setShortName("BAY 3");
        bayFour.setShortName("BAY 4");

        bayOne.setStyle(Calendar.Style.STYLE1);
        bayTwo.setStyle(Calendar.Style.STYLE2);
        bayThree.setStyle(Calendar.Style.STYLE3);
        bayFour.setStyle(Calendar.Style.STYLE4);

        CalendarSource goodsInBayCalendarSource = new CalendarSource("Family");
        goodsInBayCalendarSource.getCalendars().addAll(bayOne, bayTwo, bayThree, bayFour);
        calendarView.getCalendarSources().setAll(goodsInBayCalendarSource);
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