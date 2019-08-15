package app.model;




import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;



public class PurchaseOrder {

    private int id;
    private String orderNumber;
    private LocalDate expectedDate;
    private String supplierName;
    private String supplierID;
    private String haulier;


    private String haulierID;
    private int visible;
    private int fromProtean;
    private int pallets;
    private int unloadingTime;
    private Date poDate;
    private Timestamp expectedEta;
    private Timestamp arrived;
    private Timestamp departed;
    private Timestamp booked;


    // used to create object from protean and insert it to access database
    public PurchaseOrder(String orderNumber, Timestamp expectedDate, String supplierName, String supplierID) {

        this.orderNumber = orderNumber;
        this.expectedDate = expectedDate.toLocalDateTime().toLocalDate();
        this.supplierName = supplierName;
        this.supplierID = supplierID;
    }



    // used to create object from Access database
    public PurchaseOrder(int id, String orderNumber, String supplierName, String supplierID, String haulier, int pallets, int unloadingTime, Date poDate) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.supplierName = supplierName;
        this.supplierID = supplierID;
        this.pallets = pallets;
        this.unloadingTime = unloadingTime;
        this.poDate = poDate;
        this.haulier = haulier;

    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Date getExpectedDate() {
        return Date.valueOf(expectedDate);
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

    public int getVisible() {
        return visible;
    }

    public int getFromProtean() {
        return fromProtean;
    }

    public int getPallets() {
        return pallets;
    }

    public int getUnloadingTime() {
        return unloadingTime;
    }

    public Date getPoDate() {
        return poDate;
    }

    public Timestamp getExpectedEta() {
        return expectedEta;
    }

    public Timestamp getArrived() {
        return arrived;
    }

    public Timestamp getDeparted() {
        return departed;
    }

    public Timestamp getBooked() {
        return booked;
    }
}