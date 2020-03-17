package app.controller.sql;

import app.pojos.ScheduleDetails;
import org.intellij.lang.annotations.Language;

import javax.swing.*;
import java.sql.*;



public class SQLiteJDBC {

    private static Connection connection;
    private static PreparedStatement statement;
    private static ResultSet resultSet;


    public static void insert(String fields, String values, String table){

        String sql ="INSERT INTO" + table + " (" + fields + ") VALUES(" + values + ")";
        update(sql);
    }


    public static boolean update(String query) {
        close();
        try {
            connection = ApacheConnPool.getConnection();
            assert connection != null;
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
            return true;

        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static <T> ResultSet select(String table, String field, T param){
        close();
        String par;
        if (param instanceof Integer) {
            par = param.toString();
        }else{
            par = "'" + param + "'";
        }
        @Language("SQLite")
        String sql = "Select  * from " + table + " where " + field + " =" + par;
        try {
            connection = ApacheConnPool.getConnection();
            assert connection != null;
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            return resultSet;

        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet selectAll(String table, String order){
        close();
        try {
            String    query = "SELECT * FROM " + table + " ORDER BY " + order;
            connection = ApacheConnPool.getConnection();
            assert connection != null;
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return resultSet;

        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }


    }

    public static <T> void delete(String table, String field, T id){

        close();
        @Language("SQLite")
        String query = "Delete from " + table + " WHERE " + field + "= ?";
        try{
            connection = ApacheConnPool.getConnection();
            assert connection != null;
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
        String query = "SELECT rowid, * FROM  WHERE so_rowid = "+ rowid + ";";

        ResultSet rs = select("PO_SCHEDULE_DETAILS", "so_rowid", rowid);
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

