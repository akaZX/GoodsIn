package app.pojos;


public class Hauliers {

  private String name;


  public Hauliers(String name) {

    this.name = name;
  }


  public String getName() {
    return name;
  }



  public void setName(String name) {
    this.name = name;
  }


  @Override
  public String toString() {

    return getName();
  }
}
