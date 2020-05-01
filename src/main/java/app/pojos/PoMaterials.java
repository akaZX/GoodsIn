package app.pojos;


import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.time.LocalDate;

public class PoMaterials extends RecursiveTreeObject<PoMaterials> {

  private int rowid;
  private String po;
  private String mCode;
  private int lineNo;
  private int deliveryNo;
  private LocalDate expectedDate;
  private double expectedQuantity;
  private double arrivedQuantity;
  private int proteanEntry;


  public PoMaterials() {

  }


  public PoMaterials(int rowid, String po, String mCode, int lineNo, int deliveryNo, LocalDate expectedDate, double expectedQuantity, double arrivedQuantity) {

    this.rowid = rowid;
    this.po = po;
    this.mCode = mCode;
    this.lineNo = lineNo;
    this.deliveryNo = deliveryNo;
    this.expectedDate = expectedDate;
    this.expectedQuantity = expectedQuantity;
    this.arrivedQuantity = arrivedQuantity;
  }


  public PoMaterials(String po, String mCode, double expectedQuantity, double arrivedQuantity) {

    this.po = po;
    this.mCode = mCode;
    this.expectedQuantity = expectedQuantity;
    this.arrivedQuantity = arrivedQuantity;
  }


  public int getRowid() {
    return rowid;
  }

  public void setRowid(int rowid) {
    this.rowid = rowid;
  }


  public String getPo() {
    return po;
  }

  public void setPo(String po) {
    this.po = po;
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


  public int getDeliveryNo() {
    return deliveryNo;
  }

  public void setDeliveryNo(int deliveryNo) {
    this.deliveryNo = deliveryNo;
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


  @Override
  public String toString() {

    return "PoMaterials{" +
           "rowid=" + rowid +
           ", po='" + po + '\'' +
           ", mCode='" + mCode + '\'' +
           ", lineNo=" + lineNo +
           ", deliveryNo=" + deliveryNo +
           ", expectedDate=" + expectedDate +
           ", expectedQuantity=" + expectedQuantity +
           ", arrivedQuantity=" + arrivedQuantity +
           ", proteanEntry=" + proteanEntry +
           '}';
  }



}
