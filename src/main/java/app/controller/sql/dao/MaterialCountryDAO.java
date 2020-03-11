package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.MaterialCountries;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialCountryDAO implements Dao<MaterialCountries> {

    @Override
    public MaterialCountries get(long id) {

        return null;
    }


    @Override
    public MaterialCountries get(String id) {

        return null;
    }


    @Override
    public List<MaterialCountries> getAll() {

        return null;
    }


    public List<MaterialCountries> getAll(String mCode) {
        List<MaterialCountries> list=  new ArrayList<>();
        @Language("SQLite")
        String query = "Select * from MATERIAL_COUNTRIES WHERE m_code ='" + mCode + "'";
        ResultSet rs = SQLiteJDBC.selectQuery(query);

        try {
            while (rs.next()) {
                MaterialCountries country = new MaterialCountries(rs.getString("m_code"),
                        rs.getString("country"), rs.getInt("rowid"));
                list.add(country);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(MaterialCountries materialCountries) {

        String values = "'" + materialCountries.getMCode() + "', '" + materialCountries.getCountry() + "'";
        String sql ="INSERT INTO MATERIAL_COUNTRIES (m_code, country) VALUES(" + values + ")";
        SQLiteJDBC.update(sql);

    }


    @Override
    public void update(MaterialCountries materialCountries) {

    }


    @Override
    public void delete(MaterialCountries materialCountries) {
        SQLiteJDBC.delete("MATERIAL_COUNTRIES", "rowid", materialCountries.getRowid());
    }
}
