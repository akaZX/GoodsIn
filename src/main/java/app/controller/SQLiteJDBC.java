package app.controller;

import app.pojos.PoMaterials;
import app.pojos.PoScheduleDetails;
import app.pojos.SupplierOrders;
import app.pojos.Suppliers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;

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
            }else{
                System.out.println("tuscias rs supplier");
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
                + order.getPoNumber() + "' AND order_date = '"
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


            System.out.println("viduje query: " + st.toString());

            return st.executeQuery(query);


        } catch (SQLException e) {
            System.out.println("grazina nuly");
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
            System.out.println("Nepavyko ideti naujo po materialo");
            e.printStackTrace();
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


    public static PoScheduleDetails getDeliveryDetails(int rowid) {
        String query = "SELECT rowid, * FROM PO_SCHEDULE_DETAILS WHERE s_o_rowid = "+ rowid + ";";

        ResultSet rs = query(query);
        try {
            while (rs.next()) {
                PoScheduleDetails temp = new PoScheduleDetails(
                        rs.getInt("rowid"),
                        rs.getString("po"),
                        rs.getString("bay"),
                        rs.getInt("pallets"),
                        rs.getInt("duration"),
                        rs.getString("haulier"),
                        rs.getString("comments"),
                        rs.getString("registration_no"),
                        LocalDateTime.parse(rs.getString("eta")),
                        LocalDateTime.parse(rs.getString("arrived")),
                        LocalDateTime.parse(rs.getString("departed")),
                        LocalDateTime.parse(rs.getString("booked_in")));
                return temp;

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}

