package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.MaterialSpecs;
import org.intellij.lang.annotations.Language;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialSpecsDao implements Dao<MaterialSpecs> {

    private static final String TABLE = "MATERIAL_SPECS";
    MaterialSpecs specs = null;
    @Override
    public <R> MaterialSpecs get(R id) {

        ResultSet resultSet = SQLiteJDBC.select(TABLE, "m_code", id);
        try {
            if (resultSet.next()) {
                specs =  mapRsToObject(resultSet);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return specs;
    }


    @Override
    public List<MaterialSpecs> getAll() {

        List<MaterialSpecs> list = new ArrayList<>();

        ResultSet rs    = SQLiteJDBC.selectAll(TABLE, "m_code");

        try {
            while (rs.next()){
                MaterialSpecs specs = mapRsToObject(rs);
                list.add(specs);
            }
            rs.close();
        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return list;
    }


    @Override
    public List<MaterialSpecs> getAll(String param) {

        return null;
    }


    @Override
    public boolean save(MaterialSpecs materialSpecs) {


        String fields = "m_code, density, min_density, max_density, lorry_temp, min_lorry_temp, max_lorry_temp, material_temp, min_material_temp, max_material_temp, brix, min_brix, max_brix, pressure, min_pressure, max_pressure, length, min_length, max_length, width, min_width, max_width, color_stage, min_colour_stage, max_colour_stage, head_weight, min_head_weight, max_head_weight, yield, min_yield, max_yield, max_major, max_critical, max_minor, variety, country, grower_id, harvest_date, like_for_like, lot_number, day, room, rta_number, ggn, twa, health_mark, expiry_date ";
        return SQLiteJDBC.insert(fields, materialSpecs.saveString(), TABLE);
    }


    @Override
    public boolean update(MaterialSpecs materialSpecs) {
        @Language("SQLite")
        String sql = "UPDATE MATERIAL_SPECS SET " + materialSpecs.toString() + " WHERE m_code='" + materialSpecs.getMCode() + "'";
        return SQLiteJDBC.update(sql);
    }


    @Override
    public boolean delete(MaterialSpecs materialSpecs) {
        return SQLiteJDBC.delete(TABLE, "m_code", materialSpecs.getMCode());
    }

    private MaterialSpecs mapRsToObject(ResultSet rs) throws SQLException {

        System.out.println("viduje Dao " + rs.getString(1));
        MaterialSpecs s = new MaterialSpecs();
        s.setMCode(rs.getString("m_code"));
        s.setDensity(rs.getInt("density"));
        s.setMinDensity(rs.getInt("min_density"));
        s.setMaxDensity(rs.getInt("max_density"));
        s.setLorryTemp(rs.getInt("lorry_temp"));
        s.setMinLorryTemp(rs.getDouble("min_lorry_temp"));
        s.setMaxLorryTemp(rs.getDouble("max_lorry_temp"));
        s.setMaterialTemp(rs.getInt("material_temp"));
        s.setMinMaterialTemp(rs.getDouble("min_material_temp"));
        s.setMaxMaterialTemp(rs.getDouble("max_material_temp"));
        s.setBrix(rs.getInt("brix"));
        s.setMinBrix(rs.getDouble("min_brix"));
        s.setMaxBrix(rs.getDouble("max_brix"));
        s.setPressure(rs.getInt("pressure"));
        s.setMinPressure(rs.getDouble("min_pressure"));
        s.setMaxPressure(rs.getDouble("max_pressure"));
        s.setLength(rs.getInt("length"));
        s.setMinLength(rs.getInt("min_length"));
        s.setMaxLength(rs.getInt("max_length"));
        s.setWidth(rs.getInt("width"));
        s.setMinWidth(rs.getInt("min_width"));
        s.setMaxWidth(rs.getInt("max_width"));
        s.setColorStage(rs.getInt("color_stage"));
        s.setMinColorStage(rs.getInt("min_colour_stage"));
        s.setMaxColorStage(rs.getInt("max_colour_stage"));
        s.setHeadWeight(rs.getInt("head_weight"));
        s.setMinHeadWeight(rs.getInt("min_head_weight"));
        s.setMaxHeadWeight(rs.getInt("max_head_weight"));
        s.setYield(rs.getInt("yield"));
        s.setMinYield(rs.getInt("min_yield"));
        s.setMaxYield(rs.getInt("max_yield"));
        s.setMaxMajor(rs.getInt("max_major"));
        s.setMaxMinor(rs.getInt("max_minor"));
        s.setMaxCritical(rs.getInt("max_critical"));
        s.setVariety(rs.getInt("variety"));
        s.setCountry(rs.getInt("country"));
        s.setGrowerId(rs.getInt("grower_id"));
        s.setHarvestDate(rs.getInt("harvest_date"));
        s.setLikeForLike(rs.getInt("like_for_like"));
        s.setLotNumber(rs.getInt("lot_number"));
        s.setDay(rs.getInt("day"));
        s.setRoom(rs.getInt("room"));
        s.setRtaNumber(rs.getInt("rta_number"));
        s.setGgn(rs.getInt("ggn"));
        s.setTwa(rs.getInt("twa"));
        s.setHealthMark(rs.getInt("health_mark"));
        s.setExpiryDate(rs.getInt("expiry_date"));

        return s;
    }
}
