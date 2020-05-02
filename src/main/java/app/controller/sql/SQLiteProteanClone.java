package app.controller.sql;

import app.controller.sql.dao.*;
import app.pojos.*;
import org.intellij.lang.annotations.Language;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//replicates small table portion of database used at organization
public class SQLiteProteanClone {

    private static Connection c;


    private static Connection getConnection() {

        try {
            final String DB = new File(".").getCanonicalPath() + "/database/protean.db";
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DB);
            c.setAutoCommit(false);
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
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

        }
        catch (java.sql.SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static ResultSet query(String query) {

        c = getConnection();
        try {
            Statement tmpStatement = c.createStatement();
            return tmpStatement.executeQuery(query);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void getOrderDetailsFromProtean(LocalDate date1) {

        List<ScheduleDetails> list = new ScheduleDetailsDao().getAllByDate(date1);
        @Language("SQLite") final String proteanQuery =
                "SELECT Distinct PO, date(DATE) as date, SUPP_NAME, SUPP_CODE FROM PROTEAN WHERE PO LIKE 'B%' AND  DATE ='" +
                date1 + "' AND M_CODE LIKE 'M%' GROUP BY PO ORDER BY supp_name";

        ResultSet rs = SQLiteProteanClone.query(proteanQuery);
        try {

            List<String> orders = new ArrayList<>();
            while (rs.next()) {
                String po = rs.getString("po");

                orders.add(po);
                insertScheduleDetails(rs, list);
                insertSuppliers(rs);
                insertOrders(rs);

            }
            insertNewMaterials(orders);
            insertNewSupplierMaterials(orders);
            insertNewPoMaterials(orders);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    //  Originally it should get data from Protean SQL database as it is located in company's VPN
    //  Testing data was stored in separate SQLite database for demonstration purposes


    private static void insertSuppliers(ResultSet rs) {

        try {
            String supplierName = rs.getString("SUPP_NAME").replaceAll("'", " ");

            Suppliers tempSupp = new Suppliers();
            tempSupp.setSupplierCode(rs.getString("supp_code"));
            tempSupp.setSupplierName(supplierName);

            new SuppliersDao().save(tempSupp);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void insertScheduleDetails(ResultSet rs, List<ScheduleDetails> list) {

        ScheduleDetailsDao detailsDao = new ScheduleDetailsDao();
        try {
            ScheduleDetails temp = new ScheduleDetails(rs.getString("po"), rs.getString("date"));
            if (! list.contains(temp)) {
                boolean s = detailsDao.saveFromProtean(temp);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void insertOrders(ResultSet rs) {

        Dao<SupplierOrders> dao = new SupplierOrderDao();
        try {
            dao.save(new SupplierOrders(rs.getString("supp_code"), rs.getString("po"), LocalDate.parse(rs.getString("date"))));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private static void insertNewMaterials(List<String> orders) {

        Materials      mat = new Materials();
        Dao<Materials> dao = new MaterialsDao();
        orders.forEach(po -> {
            @Language("SQLite")
            String query = "select m_code, material_name from protean where po ='" + po + "';";
            ResultSet rs = SQLiteProteanClone.query(query);

            try {
                assert rs != null;
                while (rs.next()) {
                    String name = rs.getString("material_name").replaceAll("'", " ");
                    mat.setMCode(rs.getString("m_code"));
                    mat.setName(name);
                    dao.save(mat);
                }

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        });


    }


    private static void insertNewSupplierMaterials(List<String> orders) {

        Dao<SupplierMaterials> dao      = new SupplierMaterialsDao();
        SupplierMaterials      material = new SupplierMaterials();

        orders.forEach(po -> {
            @Language("SQLite")
            String query = "select m_code, supp_code from protean where po ='" + po + "';";
            ResultSet rs = SQLiteProteanClone.query(query);

            try {
                assert rs != null;
                while (rs.next()) {
                    material.setmCode(rs.getString("m_code"));
                    material.setSuppCode(rs.getString("supp_code"));
                    dao.save(material);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        });

    }


    private static void insertNewPoMaterials(List<String> orders) {

        Dao<PoMaterials> dao  = new PoMaterialsDao();
        PoMaterials      temp = new PoMaterials();

        orders.forEach(po -> {
            @Language("SQLite")
            String query =
                    "select po, m_code, date, Round(expected_quantity, 2) as expected_quantity, round(booked_in, 2) as booked_in, line, delivery_no from protean where po ='" +
                    po + "';";
            ResultSet rs = SQLiteProteanClone.query(query);

            try {
                assert rs != null;
                while (rs.next()) {

                    temp.setPo(rs.getString("po"));
                    temp.setMCode(rs.getString("m_code"));
                    temp.setArrivedQuantity(rs.getDouble("booked_in"));
                    temp.setExpectedQuantity(rs.getDouble("expected_quantity"));
                    temp.setExpectedDate(LocalDate.parse(rs.getString("date")));
                    temp.setDeliveryNo(rs.getInt("delivery_no"));
                    temp.setLineNo(rs.getInt("line"));
                    dao.save(temp);

                }
                rs.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });

    }


    private void closeSession() {


    }


}
