package app.controller.sql;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class ProteanJDBConnection {

    //Real connection to Bakkavor database
    final private static String connectionUrl = "jdbc:sqlserver://******;database=ERP_STD_BOURNE;user=******;password=******;";

    private static ResultSet resultSet = null;


    public static ResultSet querySQL(String query) {


        try {
            Connection connection = DriverManager.getConnection(connectionUrl);
            Statement  statement  = connection.createStatement();

            String selectSql = query;
            resultSet = statement.executeQuery(selectSql);


        }
        catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("transfer result set");
        return resultSet;
    }

}