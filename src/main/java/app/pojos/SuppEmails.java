package app.pojos;


public class SuppEmails {

  private String suppCode;
  private String email;
  private int rowid;


  public String getSuppCode() {
    return suppCode;
  }

  public void setSuppCode(String suppCode) {
    this.suppCode = suppCode;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public int getRowid() {
    return rowid;
  }

  public void setRowid(int rowid) {
    this.rowid = rowid;
  }


  @Override
  public String toString() {
    return getEmail();
  }
}
