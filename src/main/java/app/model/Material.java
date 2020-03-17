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
    private List<MaterialCountries> countries;
    private List<MaterialVarieties> varieties;
    private MaterialSpecs specs;


    public Material(Materials material) {

        this.material = material;
        initParams();
    }


    private void initParams(){

        countries = new MaterialCountryDao().getAll(material.getMCode());
        varieties = new MaterialVarietiesDao().getAll(material.getMCode());
        specs = new MaterialSpecsDao().get(material.getMCode());


    }


    public Materials getMaterial() {

        return material;
    }


    public List<MaterialCountries> getCountries() {

        return countries;
    }


    public List<MaterialVarieties> getVarieties() {

        return varieties;
    }


    public MaterialSpecs getSpecs() {

        return specs;
    }
}
