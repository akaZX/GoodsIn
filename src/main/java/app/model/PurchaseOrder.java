package app.model;




import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.converter.TimeStringConverter;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class PurchaseOrder extends RecursiveTreeObject<PurchaseOrder> {

    private int id;
    private String orderNumber;

    private String supplierName;
    private String supplierID;
    private String haulier;
    private String bay;
    private String comments;
    private String trailerNo;


    private String haulierID;
    private int visible;
    private int fromProtean;
    private ObjectProperty<Integer> pallets = new SimpleObjectProperty<>();
    private ObjectProperty<Integer> unloadingTime = new SimpleObjectProperty<>();

    private Date poDate;
    private final ObjectProperty<LocalDateTime> expectedEta = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> arrived = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> departed = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> booked = new SimpleObjectProperty<>();



    // used to create object from protean and insert it to access database
    public PurchaseOrder(String orderNumber, Date expectedDate, String supplierName, String supplierID) {

        this.orderNumber = orderNumber;
        this.poDate = expectedDate;
        this.supplierName = supplierName;
        this.supplierID = supplierID;
    }



    // used to create object from Access database
    public PurchaseOrder(int id, String orderNumber, String supplierName, String supplierID,
                         String haulier, int pallets, int unloadingTime, Date poDate,
                         Timestamp expectedEta, Timestamp arrived, Timestamp departed,
                         Timestamp booked, String bay, String comments, String trailerNo) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.supplierName = supplierName;
        this.supplierID = supplierID;
        this.pallets.set(pallets);
        this.unloadingTime.set(unloadingTime);
        this.poDate = poDate;
        this.haulier = haulier;
        this.bay = bay;
        this.comments = comments;
        this.trailerNo = trailerNo;

        if(expectedEta != null){
            this.expectedEta.set(expectedEta.toLocalDateTime());
        }
        if(arrived != null){
            this.arrived.set(arrived.toLocalDateTime());
        }
        if(departed != null){
            this.arrived.set(departed.toLocalDateTime());
        }
        if(booked != null){
            this.arrived.set(booked.toLocalDateTime());
        }


    }

    public PurchaseOrder(String orderNumber, String supplierName,
                         String haulier, String bay, String comments, String trailerNo,
                         int pallets,
                         int unloadingTime, Date poDate, LocalDateTime expectedEta,
                         LocalDateTime arrived, LocalDateTime departed, LocalDateTime booked) {

        this.orderNumber = orderNumber;
        this.supplierName = supplierName;
        this.haulier = haulier;
        this.bay = bay;
        this.comments = comments;
        this.trailerNo = trailerNo;

        this.pallets.set(pallets);
        this.unloadingTime.set(unloadingTime);
        this.poDate = poDate;

        if(expectedEta != null){
            this.expectedEta.set(expectedEta);
        }
        if(arrived != null){
            this.arrived.set(arrived);
        }
        if(departed != null){
            this.departed.set(departed);
        }
        if(booked != null){
            this.booked.set(booked);
        }
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public int getId() {
        return id;
    }

    public String getHaulier() {
        return haulier;
    }

    public String getHaulierID() {
        return haulierID;
    }

    public ObjectProperty<Integer> getPallets() {
        return pallets;
    }

    public ObjectProperty<Integer> getUnloadingTime() {
        return unloadingTime;
    }

    public Date getPoDate() {
        return poDate;
    }

    public ObjectProperty<LocalDateTime> getExpectedEta() {
        return expectedEta;
    }

    public ObjectProperty<LocalDateTime> getArrived() {
        return arrived;
    }

    public ObjectProperty<LocalDateTime> getDeparted() {
        return departed;
    }

    public ObjectProperty<LocalDateTime> getBooked() {
        return booked;
    }

    public String getBay() { return bay; }

    public String getComments() {

        return comments;
    }

    public String getTrailerNo() {

        return trailerNo;
    }

    public void setExpectedEta(LocalDateTime expectedEta) {

        this.expectedEta.set(expectedEta);
    }

}