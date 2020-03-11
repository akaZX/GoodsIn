package app.controller.sql;

import app.pojos.ScheduleDetails;
import org.intellij.lang.annotations.Language;
import sun.management.snmp.jvmmib.JVM_MANAGEMENT_MIBOidTable;

import java.sql.*;



public class SQLiteJDBC {

    private static Connection connection;
    private static PreparedStatement statement;
    private static ResultSet resultSet;


    public static boolean update(String query) {
        close();
        try {
            connection = ApacheConnPool.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
            return true;

        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static ResultSet selectQuery(String query){
        close();
        try {
            connection = ApacheConnPool.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return resultSet;

        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> void delete(String tab, String field, T id){

        close();
        @Language("SQLite")
        String query = "Delete from " + tab + " WHERE " + field + "= ?";
        try{
            connection = ApacheConnPool.getConnection();
            statement = connection.prepareStatement(query);

            if( id instanceof Integer){
                statement.setInt(1, (Integer) id);
            }else{
                statement.setString(1, (String)id);
            }
            statement.executeUpdate();


        }catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }







    public static ScheduleDetails getDeliveryDetails(int rowid) {
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

    public static void close(){

        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

