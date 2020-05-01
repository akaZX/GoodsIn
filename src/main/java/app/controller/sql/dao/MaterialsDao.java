package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.Materials;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialsDao implements Dao<Materials> {

    private static final String TABLE = "MATERIALS";


    @Override
    public <R> Materials get(R id) {

        Materials supp = null;

        ResultSet rs = SQLiteJDBC.select(TABLE, "m_code", id);

        try {
            while (rs.next()) {
               supp = mapRsToObject(rs);

            }
            rs.close();
        }
        catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return supp;
    }


    @Override
    public List<Materials> getAll() {

        List<Materials> list = new ArrayList<>();
        ResultSet       rs   = SQLiteJDBC.selectAll(TABLE, "name");

        try {
            while (rs.next()) {

                list.add(mapRsToObject(rs));

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
    public List<Materials> getAll(String param) {

        return null;
    }


    @Override
    public boolean save(Materials materials) {

        String values = "'" + materials.getMCode() + "', '" + materials.getName() + "', " +
                        stringOrNull(materials.getDescription()) + ", " + stringOrNull(materials.getDocLink()) + ", " +
                        stringOrNull(materials.getImagePath()) + "";
        String fields = "m_code, name, description, doc_link, image_path";
        return SQLiteJDBC.insert(fields, values, TABLE);

    }


    @Override
    public boolean update(Materials materials) {

        @Language("SQLite")
        String values = "name= '" + materials.getName() + "', description=" + stringOrNull(materials.getDescription()) +
                        ", doc_link=" + stringOrNull(materials.getDocLink()) + ", image_path=" + stringOrNull(materials.getImagePath()) + "";
        @Language("SQLite")
        String sql = "Update MATERIALS set " + values + " Where m_code= '" + materials.getMCode() + "'";
        return SQLiteJDBC.update(sql);
    }


    @Override
    public boolean delete(Materials materials) {

       return SQLiteJDBC.delete(TABLE, "m_code", materials.getMCode());
    }


    private Materials mapRsToObject(ResultSet rs) throws SQLException {

        Materials temp = new Materials();
        temp.setName(rs.getString("name"));
        temp.setMCode(rs.getString("m_code"));
        temp.setDescription(rs.getString("description"));
        temp.setDocLink(rs.getString("doc_link"));
        temp.setImagePath(rs.getString("image_path"));
        return temp;
    }


    private String stringOrNull(String string) {
        return (string == null || (string.trim().equalsIgnoreCase("null") || string.isEmpty()) ? "NULL" : ("'" + string + "'"));

    }
}
