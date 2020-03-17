package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.MaterialVarieties;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialVarietiesDao implements Dao<MaterialVarieties> {

    @Override
    public MaterialVarieties get(long id) {

        return null;
    }


    @Override
    public MaterialVarieties get(String id) {

        return null;
    }


    @Override
    public List<MaterialVarieties> getAll() {

        return null;
    }

    public List<MaterialVarieties> getAll(String mCode){

        List<MaterialVarieties> list =  new ArrayList<>();
        @Language("SQLite")
        String query = "Select * from MATERIAL_VARIETIES WHERE m_code ='" + mCode + "'";
        ResultSet rs = SQLiteJDBC.select(query);

        try {
            while (rs.next()) {
                MaterialVarieties variety = new MaterialVarieties(rs.getString("m_code"),
                        rs.getString("variety"), rs.getInt("rowid"));
                list.add(variety);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }


    @Override
    public void save(MaterialVarieties materialVarieties) {

        String values = "'" + materialVarieties.getMCode() + "', '" + materialVarieties.getVariety() +"'" ;
        @Language("SQLite")
        String sql ="INSERT INTO MATERIAL_VARIETIES (m_code, variety) VALUES(" + values + ")";
        boolean save = SQLiteJDBC.update(sql);

    }


    @Override
    public void update(MaterialVarieties materialVarieties) {

    }


    @Override
    public void delete(MaterialVarieties materialVarieties) {
        SQLiteJDBC.delete("MATERIAL_VARIETIES", "rowid", materialVarieties.getRowid());

    }
}
