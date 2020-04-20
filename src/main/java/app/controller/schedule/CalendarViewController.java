package app.controller.schedule;

import app.controller.sql.SQLiteJDBC;
import app.controller.sql.serviceClasses.ScheduleEntryService;
import app.model.ScheduleEntry;
import app.pojos.ScheduleDetails;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

import static java.lang.Thread.sleep;

public class CalendarViewController {

    @FXML
    private  static final Tab calendarViewTab = new Tab("Calendar");
    private  static final StackPane calendarViewPane = new StackPane();
    private  static final Calendar bayOne = new Calendar("BAY 1");
    private static final Calendar bayTwo = new Calendar("BAY 2");
    private static final Calendar bayThree = new Calendar("BAY 3");
    private static final Calendar bayFour = new Calendar("BAY 4");
    private static final HashMap<ScheduleEntry, Entry<?>> orderEntryHashMap = new HashMap<>();


    static Tab loadCalendar() {

        final CalendarView calendarView = new CalendarView();



        initializeCalendars(calendarView, bayOne, bayTwo, bayThree, bayFour, calendarViewPane, calendarViewTab);
        //removes creating entries with double click
        calendarView.setEntryFactory(param -> null);


        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            public void run() {


                while (true) {
                    Platform.runLater(() -> {

                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });

                    try {
                        removeEntriesFromCalendars();
                        loadCalendarEntries();
                        sleep(5000L);



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        updateTimeThread.setPriority(1);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
        return calendarViewTab;

    }

    private static void loadCalendarEntries() throws InterruptedException {
//
        List<ScheduleEntry> orders = ScheduleEntryService.getDeliveriesFromDb(LocalDate.of(2020,1,22), LocalDate.of(2020,1,27));
//
        sleep(100);
        for (ScheduleEntry entry: orders) {
            if (entry.getDetails().getEta() != null) {
                orderEntryHashMap.put(entry, entry.generateCalendarEntry());
            }
        }
        System.out.println(orderEntryHashMap.size());
        selectCalendar();

    }

    private static void removeEntriesFromCalendars() throws NullPointerException{

        bayOne.clear();
        bayTwo.clear();
        bayThree.clear();
        bayFour.clear();
        orderEntryHashMap.clear();

    }

    private static void selectCalendar(){

        orderEntryHashMap.forEach((k,v)-> {

            if(k.getDetails().getBay() != null){
                switch (k.getDetails().getBay()) {

                    case "BAY01":
                        v.setCalendar(bayOne);
                        break;
                    case "BAY02":
                        v.setCalendar(bayTwo);

                        break;
                    case "BAY03":
                        v.setCalendar(bayThree);
                        break;
                    case "BAY04":
                        v.setCalendar(bayFour);
                        break;
                    default:
                        break;
                }
            }

        });
    }


    public static void initializeCalendars(CalendarView calendarView, Calendar bayOne, Calendar bayTwo, Calendar bayThree, Calendar bayFour, StackPane calendarViewPane, Tab calendarViewTab) {

        bayOne.setStyle(Calendar.Style.STYLE1);
        bayTwo.setStyle(Calendar.Style.STYLE2);
        bayThree.setStyle(Calendar.Style.STYLE3);
        bayFour.setStyle(Calendar.Style.STYLE4);


        bayOne.setShortName("BAY 1");
        bayTwo.setShortName("BAY 2");
        bayThree.setShortName("BAY 3");
        bayFour.setShortName("BAY 4");

        bayOne.setReadOnly(true);
        bayTwo.setReadOnly(true);
        bayThree.setReadOnly(true);
        bayFour.setReadOnly(true);


        CalendarSource goodsInBayCalendarSource = new CalendarSource("Goods In");
        goodsInBayCalendarSource.getCalendars().addAll(bayOne, bayTwo, bayThree, bayFour);
        calendarView.getCalendarSources().setAll(goodsInBayCalendarSource);
        calendarView.setRequestedTime(LocalTime.now());
        calendarViewPane.getChildren().addAll(calendarView);
        calendarViewTab.setContent(calendarViewPane);
        calendarView.setShowPrintButton(false);
        calendarView.setShowAddCalendarButton(false);
        calendarView.showDayPage();


    }


}