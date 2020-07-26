package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.MaterialCountries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialCountryDao implements Dao<MaterialCountries> {

    private static final String TABLE = "MATERIAL_COUNTRIES";


    @Override
    public <R> MaterialCountries get(R id) {

        ResultSet         resultSet = SQLiteJDBC.select(TABLE, "rowid", id);
        MaterialCountries country   = null;
        try {
            if (resultSet.next()) {
                country = mapRsToObject(resultSet);
            }
            resultSet.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return country;
    }


    @Override
    public List<MaterialCountries> getAll() {

        return null;
    }


    public List<MaterialCountries> getAll(String mCode) {

        List<MaterialCountries> list = new ArrayList<>();

        ResultSet rs = SQLiteJDBC.select(TABLE, "m_code", mCode);

        try {
            while (rs.next()) {

                list.add(mapRsToObject(rs));
            }
            rs.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return list;
    }


    @Override
    public boolean save(MaterialCountries materialCountries) {

        String values = "'" + materialCountries.getMCode() + "', '" + materialCountries.getCountry() + "'";
        String sql    = "INSERT INTO MATERIAL_COUNTRIES (m_code, country) VALUES(" + values + ")";
        return SQLiteJDBC.update(sql);

    }


    @Override
    public boolean update(MaterialCountries materialCountries) {

        return false;
    }


    @Override
    public boolean delete(MaterialCountries materialCountries) {

        return SQLiteJDBC.delete(TABLE, "rowid", materialCountries.getRowid());
    }


    private MaterialCountries mapRsToObject(ResultSet rs) throws SQLException {

        return new MaterialCountries(rs.getString("m_code"),
                rs.getString("country"), rs.getInt("rowid"));
    }
}
