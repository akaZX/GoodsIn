package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.SupplierOrders;
import org.intellij.lang.annotations.Language;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SupplierOrderDAO implements Dao<SupplierOrders> {

    @Override
    public SupplierOrders get(long id) {
        SupplierOrders order = new SupplierOrders();

        @Language("SQLite")
        String sql = "Select * from SUPPLIER_ORDERS where rowid=" + id + " Limit 1";
        ResultSet rs = SQLiteJDBC.selectQuery(sql);

        try{
            while (rs.next()) {
                order.setSuppCode(rs.getString("supp_code"));
                order.setPoNumber(rs.getString("po"));
                order.setOrderDate(LocalDate.parse(rs.getString("order_date")));
                order.setRowId(rs.getInt("rowid"));
            }
        }catch (NullPointerException | SQLException e){
            e.printStackTrace();
        }

        return order;
    }


    @Override
    public SupplierOrders get(String id) {

        return null;
    }


    @Override
    public List<SupplierOrders> getAll() {
        List<SupplierOrders> list = new ArrayList<>();
        SupplierOrders order = new SupplierOrders();
        @Language("SQLite")
        String sql = "SELECT * from SUPPLIER_ORDERS ORDER BY po";

        ResultSet rs = SQLiteJDBC.selectQuery(sql);
        try{
            while (rs.next()) {
                order.setSuppCode(rs.getString("supp_code"));
                order.setPoNumber(rs.getString("po"));
                order.setOrderDate(LocalDate.parse(rs.getString("order_date")));
                order.setRowId(rs.getInt("rowid"));

                list.add(order);
            }
        }catch (NullPointerException | SQLException e){
            e.printStackTrace();
        }

        return list;
    }


    @Override
    public List<SupplierOrders> getAll(String param) {

        return null;
    }


    @Override
    public void save(SupplierOrders supplierOrders) {
        @Language("SQLite")
        String sql =
                "INSERT INTO SUPPLIER_ORDERS(supp_code, po, order_date) VALUES('"
                + supplierOrders.getSuppCode() + "','" + supplierOrders.getPoNumber() + "','"
                + supplierOrders.getOrderDate() + "')";
        SQLiteJDBC.update(sql);
    }

    @Override
    public void update(SupplierOrders supplierOrders) {

    }


    @Override
    public void delete(SupplierOrders supplierOrders) {
        SQLiteJDBC.delete("SUPPLIER_ORDERS" , "rowid", supplierOrders.getRowId());

    }
}
