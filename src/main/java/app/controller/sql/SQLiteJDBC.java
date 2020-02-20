package app.controller.sql;

import app.pojos.PoMaterials;
import app.pojos.PoScheduleDetails;
import app.pojos.SupplierOrders;
import org.intellij.lang.annotations.Language;

import java.sql.*;



public class SQLiteJDBC {

    static Connection c = null;


    private static Connection getConnection(){

        if (c == null) {

            try {
              c = ApacheConnPool.getConnection();

            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            }
        }else{

            try {
                c.close();
                c = ApacheConnPool.getConnection();
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            }
        }

        return c;
    }


    public static boolean update(String query) {
        Statement st = null;
        try {
            c = getConnection();

            st = c.createStatement();
            st.executeUpdate(query);
            return true;

        } catch (Exception e) {
            return false;
        }
    }


    public static ResultSet selectQuery(String query){

        Statement st = null;
        try {
            c = getConnection();

            st = c.createStatement();

            return st.executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> void delete(String tab, String field, T id){
        @Language("SQLite")
        String query = "Delete from " + tab + " WHERE " + field + "= ?";
        PreparedStatement preparedStatement = null;
        try{
            c = getConnection();
            preparedStatement = c.prepareStatement(query);
            if( id instanceof Integer){
                preparedStatement.setInt(1, (Integer) id);
            }else{
                preparedStatement.setString(1, (String)id);
            }

            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


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


        ResultSet existingOrder = selectQuery(checkQuery);

        try {

            if(!existingOrder.next()){
                update(insertNewOrder);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void updateTwoColumns(String query, String s1, String s2) {
        PreparedStatement st;

        try {
            c = getConnection();

            st = c.prepareStatement(query);
            st.setString(1, s1);
            st.setString(2, s2);
            st.executeUpdate();

        } catch (java.sql.SQLException e) {
        }
    }



    public static PoScheduleDetails getDeliveryDetails(int rowid) {
        String query = "SELECT rowid, * FROM PO_SCHEDULE_DETAILS WHERE so_rowid = "+ rowid + ";";

        ResultSet rs = selectQuery(query);
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

