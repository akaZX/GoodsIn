package app.pojos;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.time.LocalDate;

public class PoMaterials extends RecursiveTreeObject<PoMaterials> {


  private String poNumber;
  private String mCode;
  private int lineNo;
  private LocalDate expectedDate;
  private double expectedQuantity;
  private double arrivedQuantity;
  private int proteanEntry;


  public PoMaterials() {

  }


  public PoMaterials(String poNumber, String mCode, int lineNo, double expectedQuantity, double arrivedQuantity) {

    this.poNumber = poNumber;
    this.mCode = mCode;
    this.lineNo = lineNo;
    this.expectedDate = expectedDate;
    this.expectedQuantity = expectedQuantity;
    this.arrivedQuantity = arrivedQuantity;
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


  public int getLineNo() {
    return lineNo;
  }

  public void setLineNo(int lineNo) {
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
