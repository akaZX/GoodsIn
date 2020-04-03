package app.model;


import app.pojos.ScheduleDetails;
import app.pojos.SupplierOrders;
import app.pojos.Suppliers;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;


public class ScheduleEntry extends RecursiveTreeObject<ScheduleEntry> {

    private SupplierOrders order = null;

    private Suppliers supplier = null;

    private ScheduleDetails details = null;


    public ScheduleEntry() {

    }


    public ScheduleEntry(SupplierOrders order, Suppliers supplier) {

        this.order = order;
        this.supplier = supplier;
    }


    public ScheduleEntry(SupplierOrders order, Suppliers supplier, ScheduleDetails scheduleDetails) {

        this.order = order;
        this.supplier = supplier;
        this.details = scheduleDetails;
    }


    public SupplierOrders getOrder() {

        return order;
    }


    public void setOrder(SupplierOrders order) {

        this.order = order;
    }


    public Suppliers getSupplier() {

        return supplier;
    }


    public void setSupplier(Suppliers supplier) {

        this.supplier = supplier;
    }


    public ScheduleDetails getDetails() {

        return details;
    }


    public void setDetails(ScheduleDetails details) {

        this.details = details;
    }


    public  Entry<?> generateCalendarEntry() {

        String pallets = this.getDetails().getPallets() + (this.getDetails().getPallets() > 1 ? " pallets"
                : " pallet");
        String comments            = this.getDetails().getComments() == null ? "" : (" | " + this.getDetails().getComments());
        String trailerRegistration = this.getDetails().getRegistrationNo() == null ? "" : (" | " + this.getDetails().getRegistrationNo());
        String entryTitle          =
                this.getSupplier().getSupplierName() + " | " + this.getOrder().getPoNumber() + " | " + this.getDetails().getHaulier();

        Interval interval = new Interval(this.getDetails().getEta(),
                this.getDetails().getEta().plusMinutes(this.getDetails().getDuration()));


        Entry<?> entry = new Entry<>(entryTitle, interval);

        entry.setLocation(pallets + comments + trailerRegistration);
        entry.setId(String.valueOf(this.getDetails().getRowid()));
        return entry;
    }

}