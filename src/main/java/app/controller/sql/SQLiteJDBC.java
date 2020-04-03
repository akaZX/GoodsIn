package app.controller.sql;

import app.pojos.ScheduleDetails;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SQLiteJDBC {

    private static Connection connection;

    private static PreparedStatement statement;

    private static ResultSet resultSet;


    public static boolean insert(String fields, String values, String table) {

        String sql = "INSERT INTO " + table + " (" + fields + ") VALUES (" + values + ")";
        return update(sql);
    }


    public static boolean update(String query) {

        try {
            connection = ApacheConnPool.getConnection();
            assert connection != null;
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
            close();

            return true;

        }
        catch (SQLException | NullPointerException e) {
//            e.printStackTrace();
            close();
            return false;
        }
    }


    public static <T> ResultSet select(String table, String field, T param) {

        close();
        String par;
        if (param instanceof Integer) {
            par = param.toString();
        }
        else {
            par = "'" + param + "'";
        }
        @Language("SQLite")
        String sql = "Select  * from " + table + " where " + field + " =" + par;
        return mainSelect(sql);
    }


    public static <T> ResultSet select(String table, String field, T low, T high) {

        close();
        String par1;
        String par2;
        if (low instanceof Integer) {
            par1 = low.toString();
            par2 = high.toString();
        }
        else {
            par1 = "'" + low + "'";
            par2 = "'" + high + "'";
        }
        @Language("SQLite")
        String sql = "Select  * from " + table + " where " + field +
                     " > DATE(" + par1 + ") AND " + field + " < DATE (" + par2 + ")";
        return mainSelect(sql);
    }


    public static ResultSet selectAll(String table, String order) {

        String sql = "SELECT * FROM " + table + " ORDER BY " + order;
        return mainSelect(sql);
    }


    public static ResultSet mainSelect(String sql) {

        close();
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


    public static <T> boolean delete(String table, String field, T id) {

        @Language("SQLite")
        String query = "Delete from " + table + " WHERE " + field + "= ?";
        try {
            connection = ApacheConnPool.getConnection();
            assert connection != null;
            statement = connection.prepareStatement(query);

            if (id instanceof Integer) {
                statement.setInt(1, (Integer) id);
            }
            else {
                statement.setString(1, (String) id);
            }
            statement.executeUpdate();
            close();

            return true;
        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            close();

            return false;
        }
    }


    public static ScheduleDetails getDeliveryDetails(int rowid) {

        String query = "SELECT rowid, * FROM  WHERE so_rowid = " + rowid + ";";

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


    public static void close() {

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

