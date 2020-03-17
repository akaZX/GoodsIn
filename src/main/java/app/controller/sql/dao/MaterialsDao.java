package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.Materials;
import org.intellij.lang.annotations.Language;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialsDao implements Dao<Materials> {


    @Override
    public <R> Materials get(R id) {

        Materials supp = new Materials();

        ResultSet rs = SQLiteJDBC.select("MATERIALS", "m_code", id);

        try{
            while (rs.next()) {
                supp.setName(rs.getString("name"));
                supp.setMCode(rs.getString("m_code"));

            }
            rs.close();
        }catch (NullPointerException | SQLException e){
            e.printStackTrace();
        }

        return supp;
    }


    @Override
    public List<Materials> getAll() {

        List<Materials> list = new ArrayList<>();
        Materials temp;
        ResultSet rs    = SQLiteJDBC.selectAll("MATERIALS", "m_code");

        try {
            while (rs.next()){

                temp = new Materials();
                temp.setName(rs.getString("name"));
                temp.setMCode(rs.getString("m_code"));
                temp.setDescription(rs.getString("description"));
                temp.setDocLink(rs.getString("doc_link"));
                list.add(temp);

            }
            rs.close();
        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }

        return list;
    }


    @Override
    public List<Materials> getAll(String param) {

        return null;
    }


    @Override
    public void save(Materials materials) {

        String values = "'" + materials.getMCode() + "', '" + materials.getName() +"', '" +
                        materials.getDescription() + "', '" + materials.getDocLink() +"'";
        String fields = "m_code, name, description, doc_link";
        SQLiteJDBC.insert(fields, values, "MATERIALS");

    }


    @Override
    public void update(Materials materials) {
        @Language("SQLite")
        String values = "name= '" + materials.getName() + "', description='" + materials.getDescription() +
                        "', doc_link='" + materials.getDocLink() + "'";
        @Language("SQLite")
        String sql = "Update MATERIALS set " + values + " Where m_code= '" + materials.getMCode()+ "'";
        SQLiteJDBC.update(sql);
    }


    @Override
    public void delete(Materials materials) {
        SQLiteJDBC.delete("MATERIALS" , "m_code", materials.getMCode());
    }
}
