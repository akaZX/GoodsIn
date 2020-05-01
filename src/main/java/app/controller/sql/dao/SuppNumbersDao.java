package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.SuppNumbers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuppNumbersDao implements Dao<SuppNumbers> {

    private static final String TABLE = "SUPP_NUMBERS";


    @Override
    public <R> SuppNumbers get(R id) {

        return null;
    }


    @Override
    public List<SuppNumbers> getAll() {

        List<SuppNumbers> list = new ArrayList<>();
        ResultSet         rs   = SQLiteJDBC.selectAll(TABLE, "phone_no");

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
    public List<SuppNumbers> getAll(String param) {

        List<SuppNumbers> list = new ArrayList<>();
        ResultSet         rs   = SQLiteJDBC.select(TABLE, "supp_code", param);

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
    public boolean save(SuppNumbers suppNumbers) {

        String values =
                "'" + suppNumbers.getPhoneNo() + "', '" + suppNumbers.getSuppCode() + "', '" + suppNumbers.getDetails() +
                "'";
        String fields = "phone_no, supp_code, details";
        return SQLiteJDBC.insert(fields, values, TABLE);
    }


    @Override
    public boolean update(SuppNumbers suppNumbers) {

        return false;
    }


    @Override
    public boolean delete(SuppNumbers suppNumbers) {

        return SQLiteJDBC.delete(TABLE, "rowid", suppNumbers.getRowid());
    }


    private SuppNumbers mapRsToObject(ResultSet rs) throws SQLException {

        SuppNumbers num = new SuppNumbers();
        num.setSuppCode(rs.getString("supp_code"));
        num.setDetails(rs.getString("details"));
        num.setPhoneNo(rs.getString("phone_no"));
        num.setRowid(rs.getInt("rowid"));
        return num;
    }
}
