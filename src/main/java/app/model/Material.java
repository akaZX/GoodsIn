package app.model;

import app.controller.sql.dao.MaterialSpecsDao;
import app.pojos.MaterialSpecs;
import app.pojos.Materials;

public class Material {

    private Materials material;

    private MaterialSpecs specs;


    public Material(Materials material) {

        this.material = material;
        initParams();
    }


    private void initParams() {

        specs = new MaterialSpecsDao().get(material.getMCode());


    }


    public Material() {

    }


    public Materials getMaterial() {

        return material;
    }


    public void setMaterial(Materials material) {

        this.material = material;
    }


    public MaterialSpecs getSpecs() {

        return specs;
    }


    public void setSpecs(MaterialSpecs specs) {

        this.specs = specs;
    }
}
