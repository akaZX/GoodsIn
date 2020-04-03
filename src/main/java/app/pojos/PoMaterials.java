package app.pojos;


import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.time.LocalDate;

public class PoMaterials extends RecursiveTreeObject<PoMaterials> {

  private long rowid;
  private String po;
  private String mCode;
  private long lineNo;
  private long deliveryNo;
  private LocalDate expectedDate;
  private double expectedQuantity;
  private double arrivedQuantity;
  private long proteanEntry;


  public PoMaterials() {

  }


  public PoMaterials(long rowid, String po, String mCode, long lineNo, long deliveryNo, LocalDate expectedDate, double expectedQuantity, double arrivedQuantity) {

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


  public long getRowid() {
    return rowid;
  }

  public void setRowid(long rowid) {
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


  public long getLineNo() {
    return lineNo;
  }

  public void setLineNo(long lineNo) {
    this.lineNo = lineNo;
  }


  public long getDeliveryNo() {
    return deliveryNo;
  }

  public void setDeliveryNo(long deliveryNo) {
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


  public long getProteanEntry() {
    return proteanEntry;
  }

  public void setProteanEntry(long proteanEntry) {
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
