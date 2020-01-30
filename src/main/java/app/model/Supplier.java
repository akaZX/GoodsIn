package app.model;


import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Supplier extends RecursiveTreeObject<Supplier> {

    private final String name;
    private final String supplierId;
    private int ID;

    public Supplier( int ID,  String name, String supplierId) {
        this.name = name;
        this.supplierId = supplierId;
        this.ID = ID;
    }

    public Supplier(String name, String supplierId) {
        this.name = name;
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public String getSupplierId() {
        return supplierId;
    }


    public int getID() {

        return ID;
    }
}