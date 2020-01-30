package app.controller;

import app.model.PurchaseOrder;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;

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
    private static final HashMap<PurchaseOrder, Entry<?>> orderEntryHashMap = new HashMap<>();


    static Tab loadCalendar() {

        final CalendarView calendarView = new CalendarView();
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowDeveloperConsole(true);



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
                        sleep(100000L);



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

        List<PurchaseOrder> orders = getAllOrders();
//        sleep(100);
        orders.forEach(listItem -> orderEntryHashMap.put(listItem, generateCalendarEntry(listItem)));
        selectCalendar(orderEntryHashMap);

    }

    private static void removeEntriesFromCalendars() throws NullPointerException{

        bayOne.clear();
        bayTwo.clear();
        bayThree.clear();
        bayFour.clear();
        orderEntryHashMap.clear();

    }

    private static void selectCalendar(HashMap<PurchaseOrder, Entry<?>> map){

        map.forEach((k,v)-> {

            if(k.getBay() != null){
                switch (k.getBay()) {

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

    private static Entry generateCalendarEntry(PurchaseOrder order) {

        String pallets = order.getPallets().getValue() + (order.getPallets().getValue() > 1 ? " pallets"
                : " pallet");
        String comments            = order.getComments() == null ? "" : (" | " + order.getComments());
        String trailerRegistration = order.getTrailerNo() == null ? "" : (" | " + order.getTrailerNo());
        String entryTitle          =
                order.getSupplierName() + " | " + order.getOrderNumber() + " | " + order.getHaulier();

        Interval interval = new Interval(order.getExpectedEta().getValue(),
                order.getExpectedEta().getValue().plusMinutes(order.getUnloadingTime().getValue()));


        Entry entry = new Entry(entryTitle, interval);

        entry.setLocation(pallets + comments + trailerRegistration);
        entry.setId(String.valueOf(order.getId()));

        return entry;
    }

    private static List<PurchaseOrder> getAllOrders(){

        String query = "SELECT * FROM ORDERS WHERE EXPECTED_ETA IS NOT NULL AND VISIBLE = 1 ORDER BY SUPPLIER;";
        return AccessDatabase.getOrdersFromDB(query);
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

        CalendarSource goodsInBayCalendarSource = new CalendarSource("Goods In");
        goodsInBayCalendarSource.getCalendars().addAll(bayOne, bayTwo, bayThree, bayFour);
        calendarView.getCalendarSources().setAll(goodsInBayCalendarSource);
        calendarView.setRequestedTime(LocalTime.now());
        calendarViewPane.getChildren().addAll(calendarView);
        calendarViewTab.setContent(calendarViewPane);
    }


}