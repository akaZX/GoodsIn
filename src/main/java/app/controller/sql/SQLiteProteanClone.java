package app.controller.sql;

import java.sql.*;

//replicates small table portion of database used at organization
public class SQLiteProteanClone {
    private static Connection c ;



    private static Connection getConnection(){

        final String DB = "C:\\Users\\Asus\\OneDrive\\Desktop\\SQLite Bakkavor Database\\protean.db";
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DB);
            c.setAutoCommit(false);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );

        }
        return c;
    }



    public static boolean update(String query) {
        try {
            Statement tmpStatement = c.createStatement();
            tmpStatement.executeUpdate(query);
            tmpStatement.close();
            c.commit();
            c.close();
            return true;

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static ResultSet query(String query){
        c = getConnection();
        try {
            Statement tmpStatement = c.createStatement();
            return tmpStatement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void closeSession(){


    }



}
