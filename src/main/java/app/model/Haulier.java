package app.model;



public class Haulier {
    private String name;
    private final int ID;

    public Haulier(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }
}