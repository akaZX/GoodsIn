package app.controller;

import app.pojos.SupplierOrders;
import app.pojos.Suppliers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.Nullable;
import java.sql.*;

public class SQLiteJDBC {


    public static ObservableList<Suppliers> getSuppliers(){

        ObservableList<Suppliers> suppliers = FXCollections.observableArrayList();

        String    query = "SELECT rowid, * FROM SUPPLIERS order by supplier_name";
        ResultSet rs    = SQLiteJDBC.query(query);

        try {
            if (rs != null) {
                while (rs.next()) {
                    Suppliers temp = new Suppliers();
                    temp.setRowID(rs.getInt("rowid"));
                    temp.setSupplierName(rs.getString("supplier_name"));
                    temp.setSupplierCode("supplier_code");
                    suppliers.add(temp);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        return suppliers;
    }

    public static boolean insertOrder(SupplierOrders order) {
        final String checkQuery =
                "SELECT * FROM SUPPLIER_ORDERS WHERE PO_NUMBER ='"
                + order.getPoNumber() + "' AND DATE = '"
                + order.getOrderDate().toString() + "';";

        String insert =
                "INSERT INTO SUPPLIER_ORDERS(supp_code, po_number, order_date) VALUES('"
                + order.getSuppCode() + "','" + order.getPoNumber() + "','"
                + order.getOrderDate() + "')";
        ResultSet existingOrder = query(checkQuery);

        try {

            if(!existingOrder.next()){
                update(insert);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void insertSupplier(Suppliers supplier){

        String insertSupplier =
                "INSERT INTO SUPPLIERS (supplier_name, supplier_code) VALUES(?, ?)";
                update(insertSupplier, supplier);

    }

    private static Connection getConnection(){

        final String DB = "C:\\Users\\Asus\\OneDrive\\Desktop\\SQLite Bakkavor Database\\GI_RMT.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + DB);

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );

        }
        return conn;
    }

    public static void update(String query) {
        Connection c = null;
        Statement st = null;

        try {
            c = getConnection();
            st = c.createStatement();
            st.executeUpdate(query);




        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }finally {
            close(null,null, st, c);
        }
    }

    public static void update(String query, Suppliers supp) {
        Connection c = null;
        PreparedStatement st = null;

        try {
            c = getConnection();
            st = c.prepareStatement(query);
            st.setString(1,supp.getSupplierName());
            st.setString(2,supp.getSupplierCode());
            st.executeUpdate();




        } catch (java.sql.SQLException e) {
            System.out.println();
        }finally {
            close(null, st, null, c);
        }
    }

    @Nullable
    public static ResultSet query(String query){
        Connection c = null;
        Statement st = null;

        ResultSet rs = null;
        try {
            c = getConnection();
            st = c.createStatement();


            rs = st.executeQuery(query);
            return rs;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void close(ResultSet rs, PreparedStatement pstmt, Statement st, Connection conn){

        try {
            if(st != null){
                st.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e3) {
            System.out.println(e3.getMessage());
        }
    }


    }

