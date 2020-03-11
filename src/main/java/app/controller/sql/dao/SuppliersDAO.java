package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.Suppliers;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuppliersDAO implements Dao<Suppliers> {



    @Override
    public Suppliers get(long id) {

        Suppliers supp = new Suppliers();
        @Language("SQLite")
        String sql = "Select rowid, * from suppliers where supp_code =" + id;
        ResultSet rs = SQLiteJDBC.selectQuery(sql);

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

        return supp;
    }


    @Override
    public Suppliers get(String id) {

        return null;
    }


    @Override
    public List<Suppliers> getAll() {
        List<Suppliers> list = new ArrayList<>();
        Suppliers temp;

        @Language("SQLite")
        String    query = "SELECT rowid, * FROM suppliers ORDER BY supp_name";
        ResultSet rs    = SQLiteJDBC.selectQuery(query);

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

        return list;
    }


    @Override
    public List<Suppliers> getAll(String param) {

        return null;
    }


    @Override
    public void save(Suppliers suppliers) {

        String values = "" + suppliers.getSupplierName() + "', '" + suppliers.getSupplierCode() +"" ;
        @Language("SQLite")
        String sql ="INSERT INTO SUPPLIERS (supp_name, supp_code) VALUES('" + values + "')";
        boolean save = SQLiteJDBC.update(sql);
        if(!save){
            update(suppliers);
        }

    }


    @Override
    public void update(Suppliers suppliers) {

        String values = "supp_name = '" + suppliers.getSupplierName() + "'";
        @Language("SQLite")
        String sql = "Update suppliers set " + values + " Where supp_code= '" + suppliers.getSupplierCode()+ "'";
        SQLiteJDBC.update(sql);

    }


    @Override
    public void delete(Suppliers suppliers) {
        SQLiteJDBC.delete("SUPPLIERS" , "supp_code", suppliers.getSupplierCode());
    }



}
