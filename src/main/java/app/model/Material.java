package app.model;

import app.controller.sql.dao.MaterialCountryDao;
import app.controller.sql.dao.MaterialSpecsDao;
import app.controller.sql.dao.MaterialVarietiesDao;
import app.pojos.MaterialCountries;
import app.pojos.MaterialSpecs;
import app.pojos.MaterialVarieties;
import app.pojos.Materials;

import java.util.List;

public class Material {

    private Materials material;
    private MaterialSpecs specs;


    public Material(Materials material) {

        this.material = material;
        initParams();
    }


    public Material() {

    }


    private void initParams(){

        specs = new MaterialSpecsDao().get(material.getMCode());


    }


    public Materials getMaterial() {

        return material;
    }



    public MaterialSpecs getSpecs() {

        return specs;
    }


    public void setMaterial(Materials material) {

        this.material = material;
    }


    public void setSpecs(MaterialSpecs specs) {

        this.specs = specs;
    }
}
