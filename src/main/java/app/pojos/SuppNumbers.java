package app.pojos;



public class SuppNumbers {

  private int rowid;
  private String phoneNo;
  private String suppCode;
  private String details;


  public int getRowid() {
    return rowid;
  }

  public void setRowid(int rowid) {
    this.rowid = rowid;
  }


  public String getPhoneNo() {
    return phoneNo;
  }

  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }


  public String getSuppCode() {
    return suppCode;
  }

  public void setSuppCode(String suppCode) {
    this.suppCode = suppCode;
  }


  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }


  @Override
  public String toString() {

    return getDetails() + " - " + getPhoneNo();
  }
}
