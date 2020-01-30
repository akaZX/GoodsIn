package app.pojos;

public class PoMaterials {

  private String supplierCode;
  private String poNumber;
  private String mCode;
  private String lineNo;
  private java.sql.Timestamp expectedDate;
  private double expectedQuantity;
  private double arrivedQuantity;
  private long proteanEntry;


  public String getSupplierCode() {
    return supplierCode;
  }

  public void setSupplierCode(String supplierCode) {
    this.supplierCode = supplierCode;
  }


  public String getPoNumber() {
    return poNumber;
  }

  public void setPoNumber(String poNumber) {
    this.poNumber = poNumber;
  }


  public String getMCode() {
    return mCode;
  }

  public void setMCode(String mCode) {
    this.mCode = mCode;
  }


  public String getLineNo() {
    return lineNo;
  }

  public void setLineNo(String lineNo) {
    this.lineNo = lineNo;
  }


  public java.sql.Timestamp getExpectedDate() {
    return expectedDate;
  }

  public void setExpectedDate(java.sql.Timestamp expectedDate) {
    this.expectedDate = expectedDate;
  }


  public double getExpectedQuantity() {
    return expectedQuantity;
  }

  public void setExpectedQuantity(double expectedQuantity) {
    this.expectedQuantity = expectedQuantity;
  }


  public double getArrivedQuantity() {
    return arrivedQuantity;
  }

  public void setArrivedQuantity(double arrivedQuantity) {
    this.arrivedQuantity = arrivedQuantity;
  }


  public long getProteanEntry() {
    return proteanEntry;
  }

  public void setProteanEntry(long proteanEntry) {
    this.proteanEntry = proteanEntry;
  }

}
