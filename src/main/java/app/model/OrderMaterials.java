package app.model;

import app.pojos.Materials;
import app.pojos.PoMaterials;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;


public class OrderMaterials extends RecursiveTreeObject<OrderMaterials> {

    private PoMaterials poMaterials;
    private Materials material;


    public OrderMaterials() {



    }


    public PoMaterials getPoMaterials() {

        return poMaterials;
    }


    public void setPoMaterials(PoMaterials poMaterials) {

        this.poMaterials = poMaterials;
    }


    public Materials getMaterial() {

        return material;
    }


    public void setMaterial(Materials material) {

        this.material = material;
    }


    @Override
    public String toString() {

        return "OrderMaterials{" +
               "poMaterials=" + poMaterials +
               ", material=" + material.toStrings() +
               '}';
    }
}
