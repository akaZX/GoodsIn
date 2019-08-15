package app.model;



public class Supplier {

    private String name;
    private String supplierId;
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
}