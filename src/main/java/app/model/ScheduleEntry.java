package app.model;


import app.pojos.ScheduleDetails;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.time.LocalDate;


public class ScheduleEntry extends RecursiveTreeObject<ScheduleEntry> {

    private int rowId ;
    public String supplier;
    private String suppCode;
    private String po;
    private LocalDate orderDate;
    private ScheduleDetails scheduleDetails = null;


    public ScheduleEntry(String supplier, String suppCode, String po, String orderDate) {

        this.supplier = supplier;
        this.suppCode = suppCode;
        this.po = po;
        this.orderDate = LocalDate.parse(orderDate);
    }


    public ScheduleEntry(
            int rowId, String supplier, String suppCode, String po, String orderDate) {

        this.rowId = rowId;
        this.supplier = supplier;
        this.suppCode = suppCode;
        this.po = po;
        this.orderDate = LocalDate.parse(orderDate);
    }


    public ScheduleDetails getScheduleDetails() {

        return scheduleDetails;
    }


    public void setScheduleDetails(ScheduleDetails scheduleDetails) {

        this.scheduleDetails = scheduleDetails;
    }


    public int getRowId() {

        return rowId;
    }


    public String getSupplier() {

        return supplier;
    }


    public void setSupplier(String supplier) {

        this.supplier = supplier;
    }


    public String getSuppCode() {

        return suppCode;
    }


    public void setSuppCode(String suppCode) {

        this.suppCode = suppCode;
    }


    public String getPo() {

        return po;
    }


    public void setPo(String po) {

        this.po = po;
    }


    public LocalDate getOrderDate() {

        return orderDate;
    }


    public void setOrderDate(LocalDate orderDate) {

        this.orderDate = orderDate;
    }
}