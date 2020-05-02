package app.pojos;


import app.controller.sql.dao.MaterialsDao;

public class SupplierMaterials {

    private int rowid;

    private String suppCode;

    private String mCode;

    private int palletWeight;


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


    public int getPalletWeight() {

        return palletWeight;
    }


    public void setPalletWeight(int palletWeight) {

        this.palletWeight = palletWeight;
    }


    @Override
    public String toString() {

        return new MaterialsDao().get(getmCode()).getName();
    }


    public String getmCode() {

        return mCode;
    }


    public void setmCode(String mCode) {

        this.mCode = mCode;
    }
}
