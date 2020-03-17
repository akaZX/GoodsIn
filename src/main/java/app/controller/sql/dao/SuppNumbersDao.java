package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.SuppNumbers;

import java.util.List;

public class SuppNumbersDao implements Dao<SuppNumbers> {

    @Override
    public <R> SuppNumbers get(R id) {

        return null;
    }


    @Override
    public List<SuppNumbers> getAll() {
        return null;
    }


    @Override
    public List<SuppNumbers> getAll(String param) {

        return null;
    }


    @Override
    public void save(SuppNumbers suppNumbers) {
        String values = "'" + suppNumbers.getPhoneNo() + "', " + suppNumbers.getSuppCode() + "', " + suppNumbers.getDetails() + "'";
        String fields = "phone_no, supp_code, details";
        SQLiteJDBC.insert(fields, values, "SUPP_NUMBERS");
    }


    @Override
    public void update(SuppNumbers suppNumbers) {

    }


    @Override
    public void delete(SuppNumbers suppNumbers) {

        SQLiteJDBC.delete("SUPP_NUMBERS", "rowid", suppNumbers.getRowid());
    }
}
