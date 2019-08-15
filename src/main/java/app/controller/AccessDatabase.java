package app.controller;



import java.sql.*;

public class AccessDatabase {

        static String msAccDB = "C:\\Users\\Asus\\OneDrive\\Desktop\\testDB.mdb";
//    static String msAccDB = "S:\\Factory\\Goods In\\testDB.mdb";
    static String dbURL = "jdbc:ucanaccess://"
            + msAccDB;

    // variables
    static Connection connection = null;
    static  Statement statement = null;
    static ResultSet resultSet = null;


    public static ResultSet accessConnectionSelect(String query){
        // Step 1: Loading or
        // registering Oracle JDBC driver class
        try {

            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        }
        catch(ClassNotFoundException cnfex) {

            System.out.println("Problem in loading or "
                    + "registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }

        // Step 2: Opening database connection
        try {
            // Step 2.A: Create and
            // get connection using DriverManager class
            connection = DriverManager.getConnection(dbURL);

            // Step 2.B: Creating JDBC Statement
            statement = connection.createStatement();

            // Step 2.C: Executing SQL and
            // retrieve data into ResultSet
            resultSet = statement.executeQuery(query);
            return resultSet;


        }
        catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
        finally {
            // Step 3: Closing database connection
            try {
                if(null != connection) {
                    // cleanup resources, once after processing

                    statement.close();

                    // and then finally close connection
                    connection.close();
                }
            }
            catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }
        return resultSet;
    }


    public static void accessConectionIsertUpdate(String query){



        // Step 1: Loading or
        // registering Oracle JDBC driver class
        try {

            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        }
        catch(ClassNotFoundException cnfex) {

            System.out.println("Problem in loading or "
                    + "registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }

        // Step 2: Opening database connection
        try {


            // Step 2.A: Create and
            // get connection using DriverManager class
            connection = DriverManager.getConnection(dbURL);

            // Step 2.B: Creating JDBC Statement
            statement = connection.createStatement();

            // Step 2.C: Executing SQL and
            // retrieve data into ResultSet
            statement.executeUpdate(query);
        }

        catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
        finally {
            // Step 3: Closing database connection
            try {
                if(null != connection) {
                    // cleanup resources, once after processing

                    statement.close();

                    // and then finally close connection
                    connection.close();
                }
            }
            catch (SQLException sqlex) {
                System.out.println("********** Entry exists ******************");
            }
        }

    }



}