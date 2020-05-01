package app.controller.sql;

import app.pojos.ScheduleDetails;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class SQLiteJDBC {

    private static Connection connection;

    private static PreparedStatement statement;

    private static ResultSet resultSet;


    public static boolean insert(String fields, String values, String table) {

        String sql = "INSERT INTO " + table + " (" + fields + ") VALUES (" + values + ")";
        return update(sql);
//        return false;
    }


    public static boolean update(String query) {

        try {
            connection = ApacheConnPool.getConnection();
            assert connection != null;
            statement = connection.prepareStatement(query);
            int  updated = statement.executeUpdate();
//            System.out.println("update : " + updated);
            close();
            return (updated == 1);
        }
        catch (SQLException | NullPointerException e) {
            System.out.println("Error at: SQLiteJDBC.update():");
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
            System.out.println("Error at: SQLiteJDBC.mainSelect():");
//            e.printStackTrace();
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
            int deleted = statement.executeUpdate();
            close();

            return deleted == 1;
        }
        catch (SQLException | NullPointerException e) {
            System.out.println("Error at: SQLiteJDBC.delete():");
//            e.printStackTrace();
            close();

            return false;
        }
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

