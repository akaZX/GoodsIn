package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.PoMaterials;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PoMaterialsDao implements Dao<PoMaterials> {

    private final static String TABLE = "PO_MATERIALS";


    @Override
    public <R> PoMaterials get(R id) {

        PoMaterials mat = new PoMaterials();

        ResultSet rs = SQLiteJDBC.select(TABLE, "rowid", id);
        try {
            if (rs.next()) {
                return mapRsToObject(rs);
            }
        }
        catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return mat;
    }


    @Override
    public List<PoMaterials> getAll() {

        List<PoMaterials> list = new ArrayList<>();
        ResultSet         rs   = SQLiteJDBC.selectAll(TABLE, "po");
        getPoMaterialsList(list, rs);
        SQLiteJDBC.close();
        return list;
    }


    @Override
    public List<PoMaterials> getAll(String param) {

        List<PoMaterials> list = new ArrayList<>();
        ResultSet         rs   = SQLiteJDBC.select(TABLE, "po", param);
        getPoMaterialsList(list, rs);
        SQLiteJDBC.close();
        return list;
    }


    @Override
    public boolean save(PoMaterials poMaterials) {

        String values =
                "'" + poMaterials.getPo() + "', '" + poMaterials.getMCode() + "', " + poMaterials.getLineNo() + ", "
                + poMaterials.getDeliveryNo() + ", '" + poMaterials.getExpectedDate() + "', "
                + poMaterials.getExpectedQuantity() + ", " + poMaterials.getArrivedQuantity();

        @Language("SQLite")
        String sql = " INSERT INTO PO_MATERIALS " +
                     "(po, m_code, line_no, delivery_no, expected_date, expected_quantity, arrived_quantity)" +
                     " VALUES (" + values + ")";

        boolean update = SQLiteJDBC.update(sql);
        if (! update) {
            return  update(poMaterials);
        }
        return update;
    }


    @Override
    public boolean update(PoMaterials poMaterials) {

        String values = "expected_date='" + poMaterials.getExpectedDate() + "', expected_quantity="
                        + poMaterials.getExpectedQuantity() + ", arrived_quantity=" + poMaterials.getArrivedQuantity();

        @Language("SQLite")
        String sql = "UPDATE PO_MATERIALS SET " + values + " WHERE po ='" + poMaterials.getPo() + "' AND m_code = '" +
                     poMaterials.getMCode() + "' and line_no=" + poMaterials.getLineNo() + " and delivery_no=" +
                     poMaterials.getDeliveryNo();
        return SQLiteJDBC.update(sql);

    }


    @Override
    public boolean delete(PoMaterials poMaterials) {

        return SQLiteJDBC.delete(TABLE, "rowid", poMaterials.getRowid());
    }


    private void getPoMaterialsList(List<PoMaterials> list, ResultSet rs) {

        try {
            while (rs.next()) {
                list.add(mapRsToObject(rs));
            }
            SQLiteJDBC.close();
        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }


    private PoMaterials mapRsToObject(ResultSet rs) throws SQLException {

        PoMaterials mat = new PoMaterials();
        mat.setExpectedQuantity(rs.getDouble("expected_quantity"));
        mat.setArrivedQuantity(rs.getDouble("arrived_quantity"));
        mat.setMCode(rs.getString("m_code"));
        mat.setExpectedDate(LocalDate.parse(rs.getString("expected_date")));
        mat.setLineNo(rs.getInt("line_no"));
        mat.setProteanEntry(rs.getInt("protean_entry"));
        mat.setPo(rs.getString("po"));
        mat.setRowid(rs.getInt("rowid"));
        mat.setDeliveryNo(rs.getInt("delivery_no"));
        return mat;
    }
}
