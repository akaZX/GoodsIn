package app.pojos;


public class MaterialVarieties {

    private String mCode;

    private String variety;

    private int rowid;


    public MaterialVarieties() {

    }


    public MaterialVarieties(String mCode, String variety) {

        this.mCode = mCode;
        this.variety = variety;
    }


    public MaterialVarieties(String mCode, String variety, int rowid) {

        this.mCode = mCode;
        this.variety = variety;
        this.rowid = rowid;
    }


    public String getMCode() {

        return mCode;
    }


    public void setMCode(String mCode) {

        this.mCode = mCode;
    }


    public String getVariety() {

        return variety;
    }


    public void setVariety(String variety) {

        this.variety = variety;
    }


    public int getRowid() {

        return rowid;
    }


    public void setRowid(int rowid) {

        this.rowid = rowid;
    }


    @Override
    public String toString() {

        return variety;
    }
}
