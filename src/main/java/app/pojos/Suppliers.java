package app.pojos;


import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.util.Objects;

public class Suppliers extends RecursiveTreeObject<Suppliers> {

    private String supplierName;

    private String supplierCode;

    private int rowID;


    public Suppliers() {

    }


    public Suppliers(String supplierName, String supplierCode, int rowID) {

        this.supplierName = supplierName;
        this.supplierCode = supplierCode;
        this.rowID = rowID;
    }


    public int getRowID() {

        return rowID;
    }


    public void setRowID(int rowID) {

        this.rowID = rowID;
    }


    @Override
    public int hashCode() {

        return Objects.hash(supplierName);
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Suppliers suppliers = (Suppliers) o;
        return (supplierCode + supplierName).equals(suppliers.supplierCode + suppliers.supplierName);
    }


    @Override
    public String toString() {

        return getSupplierName() + "  " + getSupplierCode();
    }


    public String getSupplierName() {

        return supplierName;
    }


    public void setSupplierName(String supplierName) {

        this.supplierName = supplierName;
    }


    public String getSupplierCode() {

        return supplierCode;
    }


    public void setSupplierCode(String supplierCode) {

        this.supplierCode = supplierCode;
    }
}
