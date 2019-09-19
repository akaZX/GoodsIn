package app.controller;



import app.model.Haulier;
import app.model.PurchaseOrder;
import app.model.Supplier;

import java.sql.*;
import java.time.format.DateTimeFormatter;

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

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        }
        catch(ClassNotFoundException cnfex) {

            System.out.println("Problem in loading or "
                    + "registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }

        try {

            connection = DriverManager.getConnection(dbURL);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            return resultSet;
        }
        catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
        finally {
            try {
                if(null != connection) {
                    statement.close();
                    connection.close();
                }
            }
            catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }
        return resultSet;
    }


    public static void accessConectionInsert(String query){

        try {

            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        }
        catch(ClassNotFoundException cnfex) {

            System.out.println("Problem in loading or "
                    + "registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(dbURL);

            statement = connection.createStatement();
            statement.executeUpdate(query);
        }

        catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
        finally {
            try {
                if(null != connection) {
                    statement.close();
                    connection.close();
                }
            }
            catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }
    }

    public static void insertOrder(PurchaseOrder order){

        final String checkQuery =
                "SELECT * from ORDERS WHERE PO_NUMBER ='"
                        + order.getOrderNumber() + "' AND PROTEAN_ENTRY = 1;";

        String insert =
                "INSERT INTO ORDERS([SUPPLIER],[SUPPLIER_ID],[PO_DATE],[PO_NUMBER]) VALUES('"
                        + order.getSupplierName().toUpperCase() + "','" + order.getSupplierID() + "',#"
                        + order.getPoDate() + "#,'" + order.getOrderNumber() + "')";
        ResultSet existingOrder = accessConnectionSelect(checkQuery);

        try {

            if(!existingOrder.next()){
                accessConectionInsert(insert);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void insertSupplier(Supplier supplier){

        final String checkQuery =
                "SELECT * FROM SUPPLIERS WHERE DESC ='"+ supplier.getName().toUpperCase() + "';";

        String insertSupplier =
                "INSERT INTO SUPPLIERS([DESC], [SUPPLIER_ID]) VALUES('"
                        + supplier.getName().toUpperCase() + "','" + supplier.getSupplierId() + "')";

        ResultSet existingSupplier = accessConnectionSelect(checkQuery);
        try {

            if(!existingSupplier.next()){
                accessConectionInsert(insertSupplier);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertHaulier(String haulier){

        String trimmedHaulier = haulier.trim().toUpperCase();

        final String checkQuery =
                "SELECT * FROM HAULIERS WHERE DESC ='"+ trimmedHaulier.toUpperCase() + "';";

        String insertSupplier =
                "INSERT INTO HAULIERS([DESC]) VALUES('"
                        + trimmedHaulier.toUpperCase() + "')";

        ResultSet existingSupplier = accessConnectionSelect(checkQuery);
        try {

            if(!existingSupplier.next()){
                accessConectionInsert(insertSupplier);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertNewOrderFromForm(PurchaseOrder order){


        try {

            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        }
        catch(ClassNotFoundException cnfex) {

            System.out.println("Problem in loading or "
                    + "registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(dbURL);
            PreparedStatement insertQuery = connection.prepareStatement(" INSERT INTO ORDERS(" +
                    "[SUPPLIER], [PO_NUMBER], [HAULIER], [PALLETS], [UNLOADING_TIME]," +
                    " [PO_DATE], [EXPECTED_ETA], [ARRIVED], [DEPARTED], [BOOKED_IN], " +
                    "[COMMENTS], [TRAILER_NO], [BAY], [PROTEAN_ENTRY]) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
            insertQuery.setString(1, order.getSupplierName());
            insertQuery.setString(2, order.getOrderNumber());
            insertQuery.setString(3, order.getHaulier());
            insertQuery.setInt(4, order.getPallets().getValue());
            insertQuery.setInt(5, order.getUnloadingTime().getValue());


            if (order.getPoDate() != null) {
                insertQuery.setDate(6, order.getPoDate());
            } else {
                insertQuery.setNull(7, Types.DATE);
            }

            if (order.getExpectedEta() != null) {
                insertQuery.setTimestamp(7, Timestamp.valueOf(order.getExpectedEta().getValue()));
            } else {
                insertQuery.setNull(7, Types.TIMESTAMP);
            }

            if (order.getArrived() != null) {
                insertQuery.setTimestamp(8, Timestamp.valueOf(order.getArrived().getValue()));
            } else {
                insertQuery.setNull(8, Types.TIMESTAMP);
            }

            if (order.getDeparted() != null) {
                insertQuery.setTimestamp(9, Timestamp.valueOf(order.getDeparted().getValue()));
            } else {
                insertQuery.setNull(9, Types.TIMESTAMP);
            }

            if (order.getBooked() != null) {
                insertQuery.setTimestamp(10, Timestamp.valueOf(order.getBooked().getValue()));
            } else {
                insertQuery.setNull(10, Types.TIMESTAMP);
            }

            insertQuery.setString(11, order.getComments());
            insertQuery.setString(12, order.getTrailerNo());
            insertQuery.setString(13, order.getBay());
            insertQuery.setInt(14, 0);


            insertQuery.execute();
        }

        catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
        finally {
            try {
                if(null != connection) {
                    statement.close();
                    connection.close();
                }
            }
            catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }

    }


    public static void updateOrder(PurchaseOrder order, int id){


        try {

            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        }
        catch(ClassNotFoundException cnfex) {

            System.out.println("Problem in loading or "
                    + "registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(dbURL);
            PreparedStatement insertQuery = connection.prepareStatement(
                    "UPDATE  ORDERS SET " +
                            "SUPPLIER =?, PO_NUMBER =?," +
                            " HAULIER =?, PALLETS =?, UNLOADING_TIME =?," +
                            " PO_DATE = ?, EXPECTED_ETA = ?, " +
                            "ARRIVED = ?, DEPARTED = ?, BOOKED_IN = ?," +
                            "COMMENTS = ?, TRAILER_NO = ?," +
                            " BAY = ? WHERE ID = ?");

            insertQuery.setString(1, order.getSupplierName());
            insertQuery.setString(2, order.getOrderNumber());

            insertQuery.setString(3, order.getHaulier());
            insertQuery.setInt(4, order.getPallets().getValue());
            insertQuery.setInt(5, order.getUnloadingTime().getValue());


            if (order.getPoDate() != null) {
                insertQuery.setDate(6, order.getPoDate());
            } else {
                insertQuery.setNull(6, Types.DATE);
            }

            if (order.getExpectedEta().getValue() != null) {
                insertQuery.setTimestamp(7, Timestamp.valueOf(order.getExpectedEta().getValue()));
            } else {
                insertQuery.setNull(7, Types.TIMESTAMP);
            }

            if (order.getArrived().getValue() != null) {
                insertQuery.setTimestamp(8, Timestamp.valueOf(order.getArrived().getValue()));
            } else {
                insertQuery.setNull(8, Types.TIMESTAMP);
            }

            if (order.getDeparted().getValue() != null) {
                insertQuery.setTimestamp(9, Timestamp.valueOf(order.getDeparted().getValue()));
            } else {
                insertQuery.setNull(9, Types.TIMESTAMP);
            }

            if (order.getBooked().getValue() != null) {
                insertQuery.setTimestamp(10, Timestamp.valueOf(order.getBooked().getValue()));
            } else {
                insertQuery.setNull(10, Types.TIMESTAMP);
            }

            insertQuery.setString(11, order.getComments());
            insertQuery.setString(12, order.getTrailerNo());
            insertQuery.setString(13, order.getBay());
            insertQuery.setInt(14, id);


            int rowsUpdated = insertQuery.executeUpdate();
            System.out.println("rows updated " + rowsUpdated);
        }

        catch(Exception sqlex){
            sqlex.printStackTrace();
        }
        finally {
            try {
                if(null != connection) {
                    statement.close();
                    connection.close();
                }
            }
            catch (SQLException sqlex) {
                sqlex.printStackTrace();
            }
        }

    }

}