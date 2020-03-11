package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.ScheduleDetails;
import com.sun.istack.internal.localization.NullLocalizable;
import org.intellij.lang.annotations.Language;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.sql.Types.NULL;

public class ScheduleDetailsDAO implements Dao<ScheduleDetails> {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    @Override
    public ScheduleDetails get(long id) {

        return null;
    }


    @Override
    public ScheduleDetails get(String id) {

        return null;
    }


    @Override
    public List<ScheduleDetails> getAll() {

        return null;
    }


    @Override
    public List<ScheduleDetails> getAll(String param) {

        return null;
    }


    @Override
    public void save(ScheduleDetails scheduleDetails) {

        String values =
                "" + scheduleDetails.getOrderRowId() +
                ", '" + scheduleDetails.getBay() +
                "', " + scheduleDetails.getPallets() +
                ", " + scheduleDetails.getDuration() +
                ", '" + scheduleDetails.getHaulier() +
                "', '" + scheduleDetails.getComments() +
                "', '" + scheduleDetails.getRegistrationNo() +
                dateStrings(scheduleDetails);

        @Language("SQLite")
        String  sql = " INSERT INTO SCHEDULE_DETAILS " +
                      "(order_rowid, bay, pallets, duration, haulier, comments, reg_no, eta, arrived, departed, booked_in)" +
                      " VALUES (" + values + ")";

        SQLiteJDBC.update(sql);


    }


    @Override
    public void update(ScheduleDetails scheduleDetails) {


        @Language("SQLite")
        String values =
                "bay='" + scheduleDetails.getBay() +
                "', pallets=" + scheduleDetails.getPallets()
                + ", duration=" + scheduleDetails.getDuration() +
                ", haulier='" + scheduleDetails.getHaulier() +
                "', comments='" + scheduleDetails.getComments() +
                "', reg_no='"+scheduleDetails.getRegistrationNo() + dateStrings(scheduleDetails)
                ;

        @Language("SQLite")
        String sql = "UPDATE SCHEDULE_DETAILS SET " + values + " WHERE rowid =" + scheduleDetails.getRowid() + ";";
        boolean b = SQLiteJDBC.update(sql);
        System.out.println(b);

    }


    @Override
    public void delete(ScheduleDetails scheduleDetails) {
        SQLiteJDBC.delete("SCHEDULE_DETAILS" , "rowid", scheduleDetails.getRowid());
    }


    // works out if date/time variables are null and assigns appropriate strings to be used for SQL query
    private String dateStrings(ScheduleDetails scheduleDetails){

        String eta = (scheduleDetails.getEta() == null ? "NULL" : "'" + scheduleDetails.getEta() + "'" );
        String arrived = (scheduleDetails.getArrived() == null ? "NULL" : "'" + scheduleDetails.getArrived() + "'" );
        String departed = (scheduleDetails.getDeparted() == null ? "NULL" : "'" + scheduleDetails.getDeparted() + "'" );
        String bookedIn = (scheduleDetails.getBookedIn() == null ? "NULL" : "'" + scheduleDetails.getBookedIn() + "'" );

        return "', eta=" + eta +
               ", arrived=" + arrived +
               ", booked_in=" + bookedIn +
               ", departed=" + departed + "";
    }


}
