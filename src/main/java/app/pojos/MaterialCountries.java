package app.pojos;


import java.util.Objects;

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


    public int getRowid() {

        return rowid;
    }


    public void setRowid(int rowid) {

        this.rowid = rowid;
    }


    @Override
    public int hashCode() {

        return Objects.hash(mCode, country, rowid);
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MaterialCountries that = (MaterialCountries) o;
        return rowid == that.rowid &&
               mCode.equals(that.mCode) &&
               country.equals(that.country);
    }


    @Override
    public String toString() {

        return getCountry();
    }


    public String getCountry() {

        return country;
    }


    public void setCountry(String country) {

        this.country = country;
    }
}
