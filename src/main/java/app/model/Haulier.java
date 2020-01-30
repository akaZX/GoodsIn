package app.model;


import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Haulier extends RecursiveTreeObject<Haulier> {
    private final String name;
    private final int ID;

    public Haulier(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }



    public String getName() {
        return name;
    }
}