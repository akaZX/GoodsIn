package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.SupplierOrders;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SupplierOrderDao implements Dao<SupplierOrders> {

    private static final String TABLE = "SUPPLIER_ORDERS";


    @Override
    public <R> SupplierOrders get(R id) {


        ResultSet      rs     = SQLiteJDBC.select(TABLE, "rowid", id);
        SupplierOrders orders = null;
        try {
            if (rs.next()) {
                orders = mapRsToObject(rs);
            }
        }
        catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return orders;
    }



    public <R> SupplierOrders getBy(R id, String field) {


        ResultSet      rs     = SQLiteJDBC.select(TABLE, field, id);
        SupplierOrders orders = null;
        try {
            if (rs.next()) {
                orders = mapRsToObject(rs);
            }
        }
        catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return orders;
    }



    @Override
    public List<SupplierOrders> getAll() {

        List<SupplierOrders> list = new ArrayList<>();

        ResultSet rs = SQLiteJDBC.selectAll(TABLE, "po");
        try {
            while (rs.next()) {

                list.add(mapRsToObject(rs));
            }
        }
        catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return list;
    }


    @Override
    public List<SupplierOrders> getAll(String param) {

        return null;
    }

    public  List<SupplierOrders> getAllBy(LocalDate date,  String field) {

        List<SupplierOrders> list = new ArrayList<>();

        ResultSet rs = SQLiteJDBC.select(TABLE, field, date);
        try {
            while (rs.next()) {

                list.add(mapRsToObject(rs));
            }
        }
        catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return list;
    }


    @Override
    public boolean save(SupplierOrders supplierOrders) {

        @Language("SQLite")
        String sql =
                "INSERT INTO SUPPLIER_ORDERS(supp_code, po, order_date) VALUES('"
                + supplierOrders.getSuppCode() + "','" + supplierOrders.getPoNumber() + "','"
                + supplierOrders.getOrderDate() + "')";
        return SQLiteJDBC.update(sql);
    }


    @Override
    public boolean update(SupplierOrders supplierOrders) {

        return false;
    }


    @Override
    public boolean delete(SupplierOrders supplierOrders) {

        return SQLiteJDBC.delete(TABLE, "rowid", supplierOrders.getRowId());
    }


    private SupplierOrders mapRsToObject(ResultSet rs) throws SQLException {

        SupplierOrders order = new SupplierOrders();
        order.setSuppCode(rs.getString("supp_code"));
        order.setPoNumber(rs.getString("po"));
        order.setOrderDate(LocalDate.parse(rs.getString("order_date")));
        order.setRowId(rs.getInt("rowid"));
        return order;
    }
}
