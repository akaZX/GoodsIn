package app.pojos;


import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

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
