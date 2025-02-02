package app.pojos;

import app.controller.sql.dao.SuppliersDao;

import java.time.LocalDate;


public class SupplierOrders {

    private int rowId;

    private String suppCode;

    private String poNumber;

    private LocalDate orderDate;


    public SupplierOrders() {

    }


    public SupplierOrders(int rowId, String suppCode, String poNumber, String orderDate) {

        this.rowId = rowId;
        this.suppCode = suppCode;
        this.poNumber = poNumber;

        if (orderDate != null) {
            setOrderDate(LocalDate.parse(orderDate));
        }
    }


    public SupplierOrders(String suppCode, String poNumber, LocalDate orderDate) {

        this.suppCode = suppCode;
        this.poNumber = poNumber;
        setOrderDate(orderDate);
    }


    public int getRowId() {

        return rowId;
    }


    public void setRowId(int rowId) {

        this.rowId = rowId;
    }


    public LocalDate getOrderDate() {

        return orderDate;
    }


    public void setOrderDate(LocalDate orderDate) {

        if (orderDate != null) {

            this.orderDate = orderDate;
        }
    }


    @Override
    public String toString() {

        return getPoNumber() + " - " + new SuppliersDao().get(getSuppCode()).getSupplierName();
    }


    public String getSuppCode() {

        return suppCode;
    }


    public void setSuppCode(String suppCode) {

        this.suppCode = suppCode;
    }


    public String getPoNumber() {

        return poNumber;
    }


    public void setPoNumber(String poNumber) {

        this.poNumber = poNumber;
    }
}
