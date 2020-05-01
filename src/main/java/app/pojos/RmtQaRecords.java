package app.pojos;


import app.controller.sql.dao.MaterialsDao;
import com.google.gson.Gson;

import java.time.LocalDateTime;

public class RmtQaRecords {

  private int rowid ;
  private String po  = "";
  private String mCode = "";
  private String author = "";
  private String decision = "";
  private LocalDateTime date;
  private RmtQaIntakeDetails details;



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


  public LocalDateTime getDate() {

    return date;
  }


  public void setDate(LocalDateTime date) {

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


  @Override
  public String toString() {
    return getmCode() + " - " + new MaterialsDao().get(getmCode()) + "\nDecision: " + getDecision();
  }

  public String toUpdateString(){
    return "po='" + po +
           "', m_code='" + mCode +
           "', author='" + author +
           "', date='" + date +
           "', details_JSON='" + new Gson().toJson(details) +
           "', decision='" + decision + "'";
  }

  public String saveString(){

    return "'" + po +
           "', '" + mCode +
           "','" + author +
           "', '" + date.toString() +
           "', '" + new Gson().toJson(details) +
           "', '" + decision + "'";


  }
}
