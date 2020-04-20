package app.pojos;


import java.time.LocalDate;

public class RmtQaRecords {

  private int rowid;
  private String po;
  private String mCode;
  private String author;
  private LocalDate date;
  private RmtQaIntakeDetails details;
  private String decision;
  private double lorryTemp;
  private double materialTemp;


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


  public String getmCode() {

    return mCode;
  }


  public void setmCode(String mCode) {

    this.mCode = mCode;
  }


  public String getAuthor() {

    return author;
  }


  public void setAuthor(String author) {

    this.author = author;
  }


  public LocalDate getDate() {

    return date;
  }


  public void setDate(LocalDate date) {

    this.date = date;
  }


  public RmtQaIntakeDetails getDetails() {

    return details;
  }


  public void setDetails(RmtQaIntakeDetails details) {

    this.details = details;
  }


  public String getDecision() {

    return decision;
  }


  public void setDecision(String decision) {

    this.decision = decision;
  }


  public double getLorryTemp() {

    return lorryTemp;
  }


  public void setLorryTemp(double lorryTemp) {

    this.lorryTemp = lorryTemp;
  }


  public double getMaterialTemp() {

    return materialTemp;
  }


  public void setMaterialTemp(double materialTemp) {

    this.materialTemp = materialTemp;
  }


  @Override
  public String toString() {

    return "RmtQaRecords{" +
           "rowid=" + rowid +
           ", po='" + po + '\'' +
           ", mCode='" + mCode + '\'' +
           ", author='" + author + '\'' +
           ", date=" + date +
           ", details=" + details +
           ", decision='" + decision + '\'' +
           ", lorryTemp=" + lorryTemp +
           ", materialTemp=" + materialTemp +
           '}';
  }
}
