package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.PoMaterials;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PoMaterialsDAO implements Dao<PoMaterials> {

    @Override
    public PoMaterials get(long id) {

        PoMaterials mat = new PoMaterials();
        @Language("SQLite")
        String sql = "Select * from PO_MATERIALS where rowid=" + id + " Limit 1";
        ResultSet rs = SQLiteJDBC.selectQuery(sql);
        try{
            while (rs.next()) {

                GeneratePoMaterial(mat, rs);
            }
        }catch (NullPointerException | SQLException e){
            e.printStackTrace();
        }

        return mat;
    }


    @Override
    public PoMaterials get(String id) {

        return null;
    }


    @Override
    public List<PoMaterials> getAll() {

        List<PoMaterials> list = new ArrayList<>();
        PoMaterials mat;
        @Language("SQLite")
        String sql = "SELECT * from PO_MATERIALS ORDER BY po";
        return getPoMaterialsList(list, sql);
    }


    @Override
    public List<PoMaterials> getAll(String param) {

        return null;
    }


    public List<PoMaterials> getOrderMaterials(String po){
        List<PoMaterials> list = new ArrayList<>();
        @Language("SQLite")
        String sql = "SELECT * from PO_MATERIALS where po = '" + po + "' ORDER BY po";
        return getPoMaterialsList(list, sql);
    }


    @Nullable
    private List<PoMaterials> getPoMaterialsList(List<PoMaterials> list, String sql) {

        PoMaterials mat;
        ResultSet   rs = SQLiteJDBC.selectQuery(sql);
        try {
            while (rs.next()){
                mat = new PoMaterials();
                GeneratePoMaterial(mat, rs);
                list.add(mat);

            }
            rs.close();
        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }

        return list;
    }


    private void GeneratePoMaterial(PoMaterials mat, ResultSet rs) throws SQLException
    {

        mat.setExpectedQuantity(rs.getDouble("expected_quantity"));
        mat.setArrivedQuantity(rs.getDouble("arrived_quantity"));
        mat.setMCode(rs.getString("m_code"));
        mat.setExpectedDate(LocalDate.parse(rs.getString("expected_date")));
        mat.setLineNo(rs.getLong("line_no"));
        mat.setProteanEntry(rs.getLong("protean_entry"));
        mat.setPo(rs.getString("po"));
        mat.setRowid(rs.getLong("rowid"));
        mat.setDeliveryNo(rs.getLong("delivery_no"));
    }


    @Override
    public void save(PoMaterials poMaterials) {

        String values =
                "'" + poMaterials.getPo() + "', '" + poMaterials.getMCode() + "', " + poMaterials.getLineNo() + ", "
                + poMaterials.getDeliveryNo() + ", '" + poMaterials.getExpectedDate() + "', "
                + poMaterials.getExpectedQuantity() + ", " + poMaterials.getArrivedQuantity();

        @Language("SQLite")
        String  sql = " INSERT INTO PO_MATERIALS " +
                      "(po, m_code, line_no, delivery_no, expected_date, expected_quantity, arrived_quantity)" +
                      " VALUES (" + values + ")";

        boolean update = SQLiteJDBC.update(sql);
        if(!update){
            update(poMaterials);
        }
    }


    @Override
    public void update(PoMaterials poMaterials) {

        String values = "expected_date='" + poMaterials.getExpectedDate() + "', expected_quantity="
                + poMaterials.getExpectedQuantity() + ", arrived_quantity=" + poMaterials.getArrivedQuantity();

        @Language("SQLite")
        String sql = "UPDATE PO_MATERIALS SET " + values + " WHERE po ='" + poMaterials.getPo() + "' AND m_code = '" +
                      poMaterials.getMCode() + "' and line_no=" + poMaterials.getLineNo() + " and delivery_no="+ poMaterials.getDeliveryNo();
        SQLiteJDBC.update(sql);

    }


    @Override
    public void delete(PoMaterials poMaterials) {

        SQLiteJDBC.delete("PO_MATERIALS" , "rowid", poMaterials.getRowid());
    }

}
