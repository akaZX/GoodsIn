package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.SuppEmails;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuppEmailsDao implements Dao<SuppEmails> {


    @Override
    public <R> SuppEmails get(R id) {

        return null;
    }


    @Override
    public List<SuppEmails> getAll() {

        return null;
    }


    @Override
    public List<SuppEmails> getAll(String param) {

        List<SuppEmails> list = new ArrayList<>();
        SuppEmails temp;

        ResultSet rs = SQLiteJDBC.selectAll("SUPP_EMAILS", "email");

        try {
            while (rs.next()){

                temp = new SuppEmails();
                temp.setRowid(rs.getInt("rowid"));
                temp.setEmail(rs.getString("email"));
                temp.setSuppCode(rs.getString("supp_code"));
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
    public void save(SuppEmails suppEmails) {

        String values = "'" + suppEmails.getSuppCode() + "', '" + suppEmails.getEmail() + "'";
        String fields = "supp_code, email";
        SQLiteJDBC.insert(fields, values, "SUPP_EMAILS");

    }


    @Override
    public void update(SuppEmails suppEmails) {

    }


    @Override
    public void delete(SuppEmails suppEmails) {
        SQLiteJDBC.delete("SUPP_EMAILS", "rowid", suppEmails.getRowid());
    }
}
