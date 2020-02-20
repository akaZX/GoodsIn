package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.Materials;
import app.pojos.Suppliers;
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


    public Materials get(String id) {
        Materials supp = new Materials();
        @Language("SQLite")
        String sql = "Select  * from MATERIALS where m_code ='" + id + "'";
        ResultSet rs = SQLiteJDBC.selectQuery(sql);

        try{
            while (rs.next()) {
                supp.setName(rs.getString("material_name"));
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
                temp.setName(rs.getString("material_name"));
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
    public void save(Materials materials) {

    }


    @Override
    public void update(Materials materials) {

    }


    @Override
    public void delete(Materials materials) {
        SQLiteJDBC.delete("MATERIALS" , "m_code", materials.getMCode());
    }
}
