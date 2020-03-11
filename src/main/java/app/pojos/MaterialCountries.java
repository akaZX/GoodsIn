package app.pojos;


public class MaterialCountries {

  private String mCode;
  private String country;
  private int rowid;


  public MaterialCountries() {

  }


  public MaterialCountries(String mCode, String country) {

    this.mCode = mCode;
    this.country = country;
  }


  public MaterialCountries(String mCode, String country, int rowid) {

    this.mCode = mCode;
    this.country = country;
    this.rowid = rowid;
  }


  public String getMCode() {
    return mCode;
  }

  public void setMCode(String mCode) {
    this.mCode = mCode;
  }


  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }


  public int getRowid() {
    return rowid;
  }

  public void setRowid(int rowid) {
    this.rowid = rowid;
  }


  @Override
  public String toString() {
    return getCountry();
  }
}
