package app.pojos;


import java.time.LocalDate;

public class SupplierOrders {

  private String suppCode;
  private String poNumber;
  private LocalDate orderDate;


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


  public LocalDate getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDate orderDate) {
    this.orderDate = orderDate;
  }

}
