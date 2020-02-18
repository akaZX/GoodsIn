package app.controller.sql;

import app.pojos.PoMaterials;
import app.pojos.PoScheduleDetails;
import app.pojos.SupplierOrders;
import app.pojos.Suppliers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.intellij.lang.annotations.Language;

import java.sql.*;



public class SQLiteJDBC {

    static Connection c = null;


    public static ObservableList<Suppliers> getSuppliers(){

        ObservableList<Suppliers> suppliers = FXCollections.observableArrayList();

        String    query = "SELECT rowid, * FROM SUPPLIERS order by supp_name";
        ResultSet rs    = query(query);

        try {
            if (rs != null) {
                while (rs.next()) {
                    Suppliers temp = new Suppliers();
                    temp.setRowID(rs.getInt("rowid"));
                    temp.setSupplierName(rs.getString("supp_name"));
                    temp.setSupplierCode("supp_code");
                    suppliers.add(temp);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        return suppliers;
    }

    public static void insertOrder(SupplierOrders order) {
        final String checkQuery =
                "SELECT rowid,* FROM SUPPLIER_ORDERS WHERE PO ='"
                + order.getPoNumber() + "' AND ORDER_DATE = '"
                + order.getOrderDate().toString() + "';";

        String insertNewOrder =
                "INSERT INTO SUPPLIER_ORDERS(supp_code, po, order_date) VALUES('"
                + order.getSuppCode() + "','" + order.getPoNumber() + "','"
                + order.getOrderDate() + "')";


        ResultSet existingOrder = query(checkQuery);

        try {

            if(!existingOrder.next()){
                update(insertNewOrder);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertSupplier(Suppliers supplier){

        String pstmt =
                "INSERT INTO SUPPLIERS (supp_name, supp_code) VALUES(?, ?)";
                updateTwoColumns(pstmt, supplier.getSupplierName(), supplier.getSupplierCode());

    }

    private static Connection getConnection(){
        final String DB = "C:\\Users\\Asus\\OneDrive\\Desktop\\SQLite Bakkavor Database\\GI_RMT.db";

        if (c == null) {

            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:" + DB);

            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );

            }
        }else{

            try {
                c.close();
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:" + DB);

            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );

            }
        }

        return c;
    }

    public static void update(String query) {
        Statement st = null;
        try {
            c = getConnection();
            st = c.createStatement();
            st.executeUpdate(query);

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateTwoColumns(String query, String s1, String s2) {
        PreparedStatement st = null;

        try {
            c = getConnection();
            st = c.prepareStatement(query);
            st.setString(1, s1);
            st.setString(2, s2);
            st.executeUpdate();

        } catch (java.sql.SQLException e) {
        }
    }

    public static ResultSet query(String query){

        Statement st ;
        try {
            Connection c = getConnection();

            st = c.createStatement();

            return st.executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void insertPoMaterials(String query, PoMaterials material){
        PreparedStatement st = null;

        try {
            c = getConnection();
            st = c.prepareStatement(query);
            st.setString(1, material.getPoNumber());
            st.setString(2, material.getMCode());
            st.setString(3, material.getExpectedDate().toString());
            st.setDouble(4, material.getExpectedQuantity());
            st.setDouble(5, material.getArrivedQuantity());
            st.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PoScheduleDetails getDeliveryDetails(int rowid) {
        String query = "SELECT rowid, * FROM PO_SCHEDULE_DETAILS WHERE so_rowid = "+ rowid + ";";

        ResultSet rs = query(query);
        try {
            while (rs.next()) {
//                PoScheduleDetails temp = new PoScheduleDetails(
//                        rs.getInt("rowid"),
//                        rs.getString("po"),
//                        rs.getString("bay"),
//                        rs.getInt("pallets"),
//                        rs.getInt("duration"),
//                        rs.getString("haulier"),
//                        rs.getString("comments"),
//                        rs.getString("registration_no"),
//                        LocalDateTime.parse(rs.getString("eta")),
//                        LocalDateTime.parse(rs.getString("arrived")),
//                        LocalDateTime.parse(rs.getString("departed")),
//                        LocalDateTime.parse(rs.getString("booked_in")));
//                return temp;

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}

