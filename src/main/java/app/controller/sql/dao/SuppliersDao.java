package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.Suppliers;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuppliersDao implements Dao<Suppliers> {

    @Override
    public <R> Suppliers get(R id) {
        Suppliers supp = new Suppliers();

        ResultSet rs = SQLiteJDBC.select("SUPPLIERS", "supp_code", id);

        try{
            while (rs.next()) {
                supp.setRowID(rs.getInt("rowid"));
                supp.setSupplierCode(rs.getString("supp_code"));
                supp.setSupplierName(rs.getString("supp_name"));

            }
            rs.close();
        }catch (NullPointerException | SQLException e){
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return supp;
    }



    @Override
    public List<Suppliers> getAll() {
        List<Suppliers> list = new ArrayList<>();
        Suppliers temp;

        ResultSet rs    = SQLiteJDBC.selectAll("SUPPLIERS", "supp_name");

        try {
            while (rs.next()){

                temp = new Suppliers();
                temp.setRowID(rs.getInt("rowid"));
                temp.setSupplierCode(rs.getString("supp_code"));
                temp.setSupplierName(rs.getString("supp_name"));
                list.add(temp);

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
    public List<Suppliers> getAll(String param) {

        return null;
    }


    @Override
    public boolean save(Suppliers suppliers) {

        String values = "" + suppliers.getSupplierName() + "', '" + suppliers.getSupplierCode() +"" ;

        @Language("SQLite")
        String sql ="INSERT INTO SUPPLIERS (supp_name, supp_code) VALUES('" + values.toUpperCase() + "')";
        boolean save = SQLiteJDBC.update(sql);
        if(!save){
            update(suppliers);
        }
        return save;
    }


    @Override
    public boolean update(Suppliers suppliers) {

        String values = "supp_name = '" + suppliers.getSupplierName() + "'";
        @Language("SQLite")
        String sql = "Update suppliers set " + values + " Where supp_code= '" + suppliers.getSupplierCode()+ "'";
        return SQLiteJDBC.update(sql);

    }


    @Override
    public boolean delete(Suppliers suppliers) {
       return SQLiteJDBC.delete("SUPPLIERS" , "supp_code", suppliers.getSupplierCode());
    }



}
