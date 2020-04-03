package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.SuppEmails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuppEmailsDao implements Dao<SuppEmails> {

    private static final String TABLE = "SUPP_EMAILS";


    @Override
    public <R> SuppEmails get(R id) {

        return null;
    }


    @Override
    public List<SuppEmails> getAll() {

        List<SuppEmails> list = new ArrayList<>();
        ResultSet        rs   = SQLiteJDBC.selectAll(TABLE, "email");

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
    public List<SuppEmails> getAll(String param) {

        List<SuppEmails> list = new ArrayList<>();
        ResultSet        rs   = SQLiteJDBC.select(TABLE, "supp_code", param);

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
    public boolean save(SuppEmails suppEmails) {

        String values = "'" + suppEmails.getSuppCode() + "', '" + suppEmails.getEmail() + "'";
        String fields = "supp_code, email";

        return SQLiteJDBC.insert(fields, values, TABLE);
    }


    @Override
    public boolean update(SuppEmails suppEmails) {

        return false;
    }


    @Override
    public boolean delete(SuppEmails suppEmails) {

        return SQLiteJDBC.delete(TABLE, "rowid", suppEmails.getRowid());
    }


    private SuppEmails mapRsToObject(ResultSet rs) throws SQLException {

        SuppEmails email = new SuppEmails();
        email = new SuppEmails();
        email.setRowid(rs.getInt("rowid"));
        email.setEmail(rs.getString("email"));
        email.setSuppCode(rs.getString("supp_code"));
        return email;
    }
}
