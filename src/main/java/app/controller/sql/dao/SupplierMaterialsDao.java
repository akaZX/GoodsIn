package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.SupplierMaterials;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierMaterialsDao implements Dao<SupplierMaterials> {

    private static final String TABLE = "SUPPLIER_MATERIALS";


    @Override
    public <R> SupplierMaterials get(R id) {

        ResultSet         rs        = SQLiteJDBC.select(TABLE, "rowid", id);
        SupplierMaterials materials = null;
        try {
            if (rs.next()) {
                materials = mapRsToObject(rs);
            }
        }
        catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return materials;
    }


    @Override
    public List<SupplierMaterials> getAll() {

        List<SupplierMaterials> list = new ArrayList<>();
        ResultSet               rs   = SQLiteJDBC.selectAll(TABLE, "supp_code");

        try {
            while (rs.next()) {
                list.add(mapRsToObject(rs));
            }
        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return list;
    }


    @Override
    public List<SupplierMaterials> getAll(String param) {

        List<SupplierMaterials> list = new ArrayList<>();

        ResultSet rs = SQLiteJDBC.select(TABLE, "supp_code", param);

        try {
            while (rs.next()) {
                list.add(mapRsToObject(rs));
            }
        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return list;
    }


    @Override
    public boolean save(SupplierMaterials supplierMaterials) {

        String values = "" + supplierMaterials.getSuppCode() + "', '" + supplierMaterials.getmCode();
        @Language("SQLite")
        String sql = "INSERT INTO SUPPLIER_MATERIALS (supp_code, m_code) VALUES('" + values + "')";
        return SQLiteJDBC.update(sql);

    }


    @Override
    public boolean update(SupplierMaterials supplierMaterials) {

        String values = "pallet_weight=" + supplierMaterials.getPalletWeight();
        @Language("SQLite")
        String sql = "UPDATE SUPPLIER_MATERIALS SET " + values + " WHERE rowid=" + supplierMaterials.getRowid();
        return SQLiteJDBC.update(sql);

    }


    @Override
    public boolean delete(SupplierMaterials supplierMaterials) {

        return SQLiteJDBC.delete(TABLE, "rowid", supplierMaterials.getRowid());
    }


    private SupplierMaterials mapRsToObject(ResultSet rs) throws SQLException {

        SupplierMaterials material = new SupplierMaterials();
        material.setRowid(rs.getInt("rowid"));
        material.setmCode(rs.getString("m_code"));
        material.setSuppCode(rs.getString("supp_code"));
        material.setPalletWeight(rs.getInt("pallet_weight"));
        return material;
    }

}
