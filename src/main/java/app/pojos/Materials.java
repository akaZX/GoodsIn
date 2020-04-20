package app.pojos;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Materials extends RecursiveTreeObject<Materials> {

  private String mCode;
  private String name;
  private String description;
  private String docLink;
  private String imagePath;
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


  public String getImagePath() {

    return imagePath;
  }


  public void setImagePath(String imagePath) {

    this.imagePath = imagePath;
  }


  @Override
  public String toString() {

    return getName();
  }



  public String toStrings() {

    return "Materials{" +
           "mCode='" + mCode + '\'' +
           ", name='" + name + '\'' +
           ", description='" + description + '\'' +
           ", docLink='" + docLink + '\'' +
           ", imagePath='" + imagePath + '\'' +
           ", visible=" + visible +
           '}';
  }
}
