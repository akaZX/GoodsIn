package app.controller.sql.serviceClasses;

import app.controller.sql.dao.ScheduleDetailsDao;
import app.controller.sql.dao.SupplierOrderDao;
import app.controller.sql.dao.SuppliersDao;
import app.model.ScheduleEntry;
import app.pojos.ScheduleDetails;
import app.pojos.SupplierOrders;
import app.pojos.Suppliers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScheduleEntryService {

    //gets entries and returns them in sorted list
    public static List<ScheduleEntry> getDeliveriesFromDb(LocalDate date1) {

        List<ScheduleDetails> orders  = new ScheduleDetailsDao().getAllByDate(date1);
        return getScheduleEntries(orders);
    }

    public static List<ScheduleEntry> getDeliveriesFromDb(LocalDate low, LocalDate high) {

        List<ScheduleDetails> orders  = new ScheduleDetailsDao().getAllByDates(low, high);
        return getScheduleEntries(orders);
    }


    @NotNull
    private static List<ScheduleEntry> getScheduleEntries(List<ScheduleDetails> orders) {

        List<ScheduleEntry>   entries = new ArrayList<>();

        orders.forEach(i ->{
            SupplierOrders order    = new SupplierOrderDao().getByPo(i.getPo());
            Suppliers      supplier = new SuppliersDao().get(order.getSuppCode());
            ScheduleEntry  entry    = new ScheduleEntry();
            entry.setDetails(i);
            entry.setOrder(order);
            entry.setSupplier(supplier);
            entries.add(entry);

        });
        entries.sort(Comparator.comparing(o -> o.getSupplier().getSupplierName()));
        return entries;
    }


}
