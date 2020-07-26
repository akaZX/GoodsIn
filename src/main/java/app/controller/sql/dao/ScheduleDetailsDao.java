package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.ScheduleDetails;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDetailsDao implements Dao<ScheduleDetails> {

    private static final String TABLE = "SCHEDULE_DETAILS";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    @Override
    public <R> ScheduleDetails get(R id) {

        return null;
    }


    @Override
    public List<ScheduleDetails> getAll() {

        return null;
    }


    @Override
    public List<ScheduleDetails> getAll(String param) {

        List<ScheduleDetails> list = new ArrayList<>();
        ResultSet             rs   = SQLiteJDBC.select(TABLE, "po", param);
        try {
            while (rs.next()) {
                list.add(mapRsToObject(rs));
            }
        }
        catch (SQLException ignored) {
        }
        SQLiteJDBC.close();
        return list;
    }


    @Override
    public boolean save(ScheduleDetails scheduleDetails) {

        String values =
                "'" + scheduleDetails.getPo() +
                "', " + (scheduleDetails.getOrderDate() == null ? "NULL" : "'" + scheduleDetails.getOrderDate() + "'") +
                ", " + stringOrNull(scheduleDetails.getBay()) +
                ", " + scheduleDetails.getPallets() +
                ", " + scheduleDetails.getDuration() +
                ", " + stringOrNull(scheduleDetails.getHaulier()).toUpperCase() +
                ", " + stringOrNull(scheduleDetails.getComments()) +
                ", " + stringOrNull(scheduleDetails.getRegistrationNo()) +
                ", " + saveDateStrings(scheduleDetails);

        @Language("SQLite")
        String sql = " INSERT INTO SCHEDULE_DETAILS " +
                     "(po, order_date, bay, pallets, duration, haulier, comments, reg_no, eta, arrived, order_date, departed, booked_in)" +
                     " VALUES (" + values.toUpperCase() + ");";

        return SQLiteJDBC.update(sql);
    }


    @Override
    public boolean update(ScheduleDetails scheduleDetails) {

        @Language("SQLite")
        String values =
                "bay='" + scheduleDetails.getBay() +
                "', pallets=" + scheduleDetails.getPallets() +
                ", duration=" + scheduleDetails.getDuration() +
                ", haulier=" + stringOrNull(scheduleDetails.getHaulier()).toUpperCase() +
                ", comments=" + stringOrNull(scheduleDetails.getComments()) +
                ", reg_no=" + stringOrNull(scheduleDetails.getRegistrationNo()) +
                ", " + updateDateStrings(scheduleDetails);


        @Language("SQLite")
        String sql = "UPDATE SCHEDULE_DETAILS SET " + values + " WHERE rowid =" + scheduleDetails.getRowid();

        return SQLiteJDBC.update(sql);

    }


    @Override
    public boolean delete(ScheduleDetails scheduleDetails) {

        return SQLiteJDBC.delete(TABLE, "rowid", scheduleDetails.getRowid());
    }


    // works out if date/time variables are null and assigns appropriate strings to be used for SQL query
    private String updateDateStrings(ScheduleDetails scheduleDetails) {

        String eta      = (scheduleDetails.getEta() == null ? "NULL" : "'" + scheduleDetails.getEta() + "'");
        String arrived  = (scheduleDetails.getArrived() == null ? "NULL" : "'" + scheduleDetails.getArrived() + "'");
        String departed = (scheduleDetails.getDeparted() == null ? "NULL" : "'" + scheduleDetails.getDeparted() + "'");
        String bookedIn = (scheduleDetails.getBookedIn() == null ? "NULL" : "'" + scheduleDetails.getBookedIn() + "'");
        String orderDate = (
                scheduleDetails.getEta() == null ? "NULL" : "'" + scheduleDetails.getEta().toLocalDate() + "'");

        return " eta=" + eta +
               ", arrived=" + arrived +
               ", booked_in=" + bookedIn +
               ", departed=" + departed +
               ", order_date=" + orderDate;
    }


    private String saveDateStrings(ScheduleDetails scheduleDetails) {

        String eta = (scheduleDetails.getEta() == null ? "NULL, " : "'" + scheduleDetails.getEta() + "', ");
        String arrived = (
                scheduleDetails.getArrived() == null ? "NULL, " : "'" + scheduleDetails.getArrived() + "', ");
        String orderDate = (
                scheduleDetails.getEta() == null ? "NULL, " : "'" + scheduleDetails.getEta().toLocalDate() + "', ");
        String departed = (
                scheduleDetails.getDeparted() == null ? "NULL, " : "'" + scheduleDetails.getDeparted() + "', ");
        String bookedIn = (scheduleDetails.getBookedIn() == null ? "NULL" : "'" + scheduleDetails.getBookedIn() + "'");

        return eta + arrived + orderDate + departed + bookedIn;
    }


    private String stringOrNull(String s) {

        return (s == null ? "NULL" : "'" + s + "'");
    }


    private ScheduleDetails mapRsToObject(ResultSet rs) throws SQLException {

        return new ScheduleDetails(

                rs.getInt("rowid"), rs.getString("po"), rs.getString("bay"),
                rs.getInt("pallets"), rs.getInt("duration"), rs.getString("haulier"),
                rs.getString("comments"), rs.getString("reg_no"), rs.getString("eta"),
                rs.getString("arrived"), rs.getString("departed"), rs.getString("booked_in"),
                rs.getString("order_date"));

    }


    public List<ScheduleDetails> getAllByDate(LocalDate param) {

        List<ScheduleDetails> list = new ArrayList<>();
        ResultSet             rs   = SQLiteJDBC.select(TABLE, "order_date", param);
        try {
            while (rs.next()) {
                list.add(mapRsToObject(rs));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return list;
    }


    public List<ScheduleDetails> getAllByDates(LocalDate high, LocalDate low) {

        List<ScheduleDetails> list = new ArrayList<>();
        ResultSet             rs   = SQLiteJDBC.select(TABLE, "eta", high, low);
        try {
            while (rs.next()) {
                list.add(mapRsToObject(rs));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return list;
    }


    public boolean saveFromProtean(ScheduleDetails scheduleDetails) {

        String values =
                "'" + scheduleDetails.getPo() +
                "', '" + (scheduleDetails.getOrderDate() == null ? "" : scheduleDetails.getOrderDate()) +
                "'";

        @Language("SQLite")
        String sql = " INSERT INTO SCHEDULE_DETAILS " +
                     "(po, order_date)" +
                     " VALUES (" + values.toUpperCase() + ");";

        return SQLiteJDBC.update(sql);

    }


}
