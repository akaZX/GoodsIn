package app.pojos;

import java.time.LocalDate;

public class PoMaterials {


  private String poNumber;
  private String mCode;
  private String lineNo;
  private LocalDate expectedDate;
  private double expectedQuantity;
  private double arrivedQuantity;
  private int proteanEntry;




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


  public LocalDate getExpectedDate() {
    return expectedDate;
  }

  public void setExpectedDate(LocalDate expectedDate) {
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


  public int getProteanEntry() {
    return proteanEntry;
  }

  public void setProteanEntry(int proteanEntry) {
    this.proteanEntry = proteanEntry;
  }

}
