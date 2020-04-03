package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.MaterialVarieties;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialVarietiesDao implements Dao<MaterialVarieties> {


    private static final String TABLE = "MATERIAL_VARIETIES";
    MaterialVarieties variety = null;
    @Override
    public <R> MaterialVarieties get(R id) {
        ResultSet rs = SQLiteJDBC.select(TABLE, "rowid", id);
        try {
            if(rs.next()){
                variety =  mapRsToObject(rs);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return variety;
    }


    @Override
    public List<MaterialVarieties> getAll() {
        List<MaterialVarieties> list =  new ArrayList<>();
        ResultSet rs = SQLiteJDBC.selectAll(TABLE, "m_code");
        try {
            while (rs.next()) {
                list.add(mapRsToObject(rs));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return list;

    }

    public List<MaterialVarieties> getAll(String mCode){

        List<MaterialVarieties> list =  new ArrayList<>();

        ResultSet rs = SQLiteJDBC.select(TABLE, "m_code", mCode);

        try {
            while (rs.next()) {
                list.add(mapRsToObject(rs));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return list;

    }


    @Override
    public boolean save(MaterialVarieties materialVarieties) {

        String values = "'" + materialVarieties.getMCode() + "', '" + materialVarieties.getVariety() +"'" ;
        @Language("SQLite")
        String sql ="INSERT INTO MATERIAL_VARIETIES (m_code, variety) VALUES(" + values + ")";
        return  SQLiteJDBC.update(sql);

    }


    @Override
    public boolean update(MaterialVarieties materialVarieties) {
        return false;
    }


    @Override
    public boolean delete(MaterialVarieties materialVarieties) {
       return SQLiteJDBC.delete(TABLE, "rowid", materialVarieties.getRowid());

    }

    private MaterialVarieties mapRsToObject(ResultSet rs) throws SQLException{
        return new MaterialVarieties(rs.getString("m_code"),
                rs.getString("variety"), rs.getInt("rowid"));
    }
}
