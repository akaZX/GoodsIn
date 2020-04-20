package app.pojos;


public class SupplierMaterials {

    private int rowid;

    private String suppCode;

    private String mCode;

    private long palletWeight;


    public int getRowid() {

        return rowid;
    }


    public void setRowid(int rowid) {

        this.rowid = rowid;
    }


    public String getSuppCode() {

        return suppCode;
    }


    public void setSuppCode(String suppCode) {

        this.suppCode = suppCode;
    }


    public String getmCode() {

        return mCode;
    }


    public void setmCode(String mCode) {

        this.mCode = mCode;
    }


    public long getPalletWeight() {

        return palletWeight;
    }


    public void setPalletWeight(long palletWeight) {

        this.palletWeight = palletWeight;
    }
}
