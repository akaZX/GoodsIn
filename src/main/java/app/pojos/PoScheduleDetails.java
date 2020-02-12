package app.pojos;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

import java.time.LocalDateTime;

public class PoScheduleDetails {

  private int rowid;
  private String po;
  private String bay;
  private int pallets;
  private int duration;
  private String haulier;
  private String comments;
  private String registrationNo;
  private LocalDateTime eta;
  private LocalDateTime arrived;
  private LocalDateTime departed;
  private LocalDateTime bookedIn;
  private int visible;




  public PoScheduleDetails(int rowid, String po,String bay, int pallets, int duration, String haulier, String comments, String registrationNo, LocalDateTime eta, LocalDateTime arrived, LocalDateTime departed, LocalDateTime bookedIn) {
    this.rowid = rowid;
    this.po = po;
    this.bay = bay;
    this.pallets = pallets;
    this.duration = duration;
    this.haulier = haulier;
    this.comments = comments;
    this.registrationNo = registrationNo;


    if(eta != null){
      this.eta = eta;
    }
    if (arrived != null) {
      this.arrived = arrived;
    }
    if (departed != null) {
      this.departed = departed;
    }
    if (bookedIn != null) {
      this.bookedIn = bookedIn;
    }
  }


  public int getRowid() {

    return rowid;
  }


  public String getPo() {
    return po;
  }

  public void setPo(String po) {
    this.po = po;
  }

  public String getBay() {

    return bay;
  }

  public void setBay(String bay) {

    this.bay = bay;
  }

  public int getPallets() {
    return pallets;
  }

  public void setPallets(int pallets) {
    this.pallets = pallets;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }


  public String getHaulier() {
    return haulier;
  }

  public void setHaulier(String haulier) {
    this.haulier = haulier;
  }


  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }


  public String getRegistrationNo() {
    return registrationNo;
  }

  public void setRegistrationNo(String registrationNo) {
    this.registrationNo = registrationNo;
  }


  public LocalDateTime getEta() {
    return eta;
  }

  public void setEta(LocalDateTime eta) {
    this.eta = eta;
  }


  public LocalDateTime getArrived() {
    return arrived;
  }

  public void setArrived(LocalDateTime arrived) {
    this.arrived = arrived;
  }


  public LocalDateTime getDeparted() {
    return departed;
  }

  public void setDeparted(LocalDateTime departed) {
    this.departed = departed;
  }


  public LocalDateTime getBookedIn() {
    return bookedIn;
  }

  public void setBookedIn(LocalDateTime bookedIn) {
    this.bookedIn = bookedIn;
  }


  public int getVisible() {
    return visible;
  }

  public void setVisible(int visible) {
    this.visible = visible;
  }


  public ObservableValue<LocalDateTime> getEtaProperty() {
       ObjectProperty<LocalDateTime> prop =
            new SimpleObjectProperty<>();
       prop.set(this.eta);
       return prop;
  }


  public ObservableValue<LocalDateTime> getArrivedProperty() {

    ObjectProperty<LocalDateTime> prop =
            new SimpleObjectProperty<>();
    prop.set(this.arrived);
    return prop;


  }


  public ObservableValue<LocalDateTime> getDepartedPoperty() {
    ObjectProperty<LocalDateTime> prop =
            new SimpleObjectProperty<>();
    prop.set(this.departed);
    return prop;

  }


  public ObservableValue<LocalDateTime> getBookedinProperty() {
    ObjectProperty<LocalDateTime> prop =
            new SimpleObjectProperty<>();
    prop.set(this.bookedIn);
    return prop;
  }
}
