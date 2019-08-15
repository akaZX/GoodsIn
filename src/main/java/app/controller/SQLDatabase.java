package app.controller;



import java.sql.*;


public class SQLDatabase {

    final private static String connectionUrl = "jdbc:sqlserver://UKH1-PNSQLC01;database=ERP_STD_BOURNE;user=Userapps_BO;password=h1Ghness;";
    private static ResultSet resultSet = null;

    public static ResultSet querySQL(String query) {

        try {
            Connection connection = DriverManager.getConnection(connectionUrl);
            Statement statement = connection.createStatement();

            String selectSql = query;
            resultSet = statement.executeQuery(selectSql);


        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("transfer result set");
        return resultSet;
    }

}