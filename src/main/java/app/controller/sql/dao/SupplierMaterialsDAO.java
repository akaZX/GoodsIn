package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.SupplierMaterials;
import app.pojos.Suppliers;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierMaterialsDAO implements Dao<SupplierMaterials> {

    @Override
    public SupplierMaterials get(long id) {

        SupplierMaterials material = new SupplierMaterials();

        @Language("SQLite")
        String sql = "Select * from SUPPLIER_MATERIALS where rowid= " + id;
        ResultSet rs = SQLiteJDBC.selectQuery(sql);

        try{
            while (rs.next()) {

                material.setRowid(rs.getInt("rowid"));
                material.setmCode(rs.getString("m_code"));
                material.setSuppCode(rs.getString("supp_code"));
                material.setPalletWeight(rs.getInt("pallet_weight"));

            }
        }catch (NullPointerException | SQLException e){
            e.printStackTrace();
        }

        return material;

    }

    @Override
    public List<SupplierMaterials> getAll() {

        List<SupplierMaterials> list = new ArrayList<>();
        SupplierMaterials        temp;

        @Language("SQLite")
        String    query = "SELECT * FROM SUPPLIER_MATERIALS ORDER BY supp_code";
        return getSupplierMaterialsList(list, query);
    }


    public List<SupplierMaterials> getAllSupplierMaterials(String supplierCode){

        List<SupplierMaterials> list = new ArrayList<>();
        SupplierMaterials        temp;

        @Language("SQLite")
        String    query = "SELECT * FROM SUPPLIER_MATERIALS WHERE supp_code ='" + supplierCode + "' ORDER BY supp_code";
        return getSupplierMaterialsList(list, query);

    }


    @Nullable
    private List<SupplierMaterials> getSupplierMaterialsList(List<SupplierMaterials> list, String query) {

        SupplierMaterials temp;
        ResultSet         rs = SQLiteJDBC.selectQuery(query);

        try {
            while (rs.next()){

                temp = new SupplierMaterials();
                temp.setRowid(rs.getInt("rowid"));
                temp.setmCode(rs.getString("m_code"));
                temp.setSuppCode(rs.getString("supp_code"));
                temp.setPalletWeight(rs.getInt("pallet_weight"));

                list.add(temp);

            }
        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }

        return list;
    }


    @Override
    public void save(SupplierMaterials supplierMaterials) {

        String values = "" + supplierMaterials.getSuppCode() + "', '" + supplierMaterials.getmCode();
        @Language("SQLite")
        String sql ="INSERT INTO SUPPLIER_MATERIALS (supp_code, m_code) VALUES('" + values + "')";
        SQLiteJDBC.update(sql);

    }


    @Override
    public void update(SupplierMaterials supplierMaterials) {
        String values = "pallet_weight="  + supplierMaterials.getPalletWeight() ;
        @Language("SQLite")
        String sql = "UPDATE SUPPLIER_MATERIALS SET " + values + " WHERE rowid=" + supplierMaterials.getRowid() ;
        SQLiteJDBC.update(sql);

    }


    @Override
    public void delete(SupplierMaterials supplierMaterials) {
        SQLiteJDBC.delete("SUPPLIER_MATERIALS", "rowid", supplierMaterials.getRowid());
    }



}
