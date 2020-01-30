package app.pojos;

public class Materials {

  private String mCode;
  private String name;
  private String description;
  private String docLink;
  private long visible;


  public String getMCode() {
    return mCode;
  }

  public void setMCode(String mCode) {
    this.mCode = mCode;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public String getDocLink() {
    return docLink;
  }

  public void setDocLink(String docLink) {
    this.docLink = docLink;
  }


  public long getVisible() {
    return visible;
  }

  public void setVisible(long visible) {
    this.visible = visible;
  }

}
