package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.Materials;
import org.intellij.lang.annotations.Language;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialsDAO implements Dao<Materials> {


    @Override
    public Materials get(long id) {
       return null;
    }

    @Override
    public Materials get(String id) {
        Materials supp = new Materials();
        @Language("SQLite")
        String sql = "Select  * from MATERIALS where m_code ='" + id + "'";
        ResultSet rs = SQLiteJDBC.selectQuery(sql);

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

        @Language("SQLite")
        String    query = "SELECT * FROM MATERIALS ORDER BY m_code";
        ResultSet rs    = SQLiteJDBC.selectQuery(query);

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

        String values = "" + materials.getMCode() + "', '" + materials.getName() +"', '" +
                        materials.getDescription() + "', '" + materials.getDocLink();
        @Language("SQLite")
        String sql ="INSERT INTO MATERIALS (m_code, name, description, doc_link) VALUES('" + values + "')";
        SQLiteJDBC.update(sql);

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
