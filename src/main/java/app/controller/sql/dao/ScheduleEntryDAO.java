package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.model.ScheduleEntry;
import app.pojos.ScheduleDetails;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

public class ScheduleEntryDAO implements Dao<ScheduleEntry> {

    @Override
    public ScheduleEntry get(long id) {

        return null;
    }


    @Override
    public ScheduleEntry get(String id) {

        return null;
    }


    @Override
    public List<ScheduleEntry> getAll() {

        return null;
    }


    @Override
    public List<ScheduleEntry> getAll(String param) {

        return null;
    }


    public List<ScheduleEntry> getAll(LocalDate date) {

        List<ScheduleEntry> list = new ArrayList<>();

        @Language("SQLite")
        String query = "SELECT " +
                       " so.rowid," +
                       " so.po," +
                       " sp.supp_name," +
                       " so.supp_code, " +
                       " so.order_date, " +
                       " sd.rowid as sched_rowid," +
                       " sd.bay," +
                       " sd.pallets," +
                       " sd.duration," +
                       " sd.haulier ," +
                       " sd.comments ," +
                       " sd.reg_no ," +
                       " sd.eta ," +
                       " sd.arrived ," +
                       " sd.departed ," +
                       " sd.booked_in " +
                       "FROM SUPPLIER_ORDERS so " +
                       "INNER JOIN SUPPLIERS sp  using(supp_code) " +
                       "Left join SCHEDULE_DETAILS sd ON  so.rowid = sd.order_rowid " +
                       "where  (so.order_date ='"+ date.toString() + "' " +
                       "AND so.visible = 1 AND sd.visible = 1) OR (so.order_date ='"+ date.toString() + "' " +
                       "AND so.visible = 1 AND sd.visible is null)";

        ResultSet rs = SQLiteJDBC.selectQuery(query);

        try {

            while (rs.next()) {

                ScheduleEntry temp = new ScheduleEntry(
                        rs.getInt("rowid"), rs.getString("supp_name"), rs.getString("supp_code"),
                        rs.getString("po"), rs.getString("order_date"));

                ScheduleDetails temp2 = new ScheduleDetails(
                        rs.getInt("sched_rowid"),
                        rs.getString("bay"),
                        rs.getInt("pallets"),
                        rs.getInt("duration"),
                        rs.getString("haulier"),
                        rs.getString("comments"),
                        rs.getString("reg_no"),
                        rs.getString("eta"),
                        rs.getString("arrived"),
                        rs.getString("departed"),
                        rs.getString("booked_in"));


                temp.setScheduleDetails(temp2);
                list.add(temp);
            }
            rs.close();
        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }

        SQLiteJDBC.close();
        return list;

    }


    @Override
    public void save(ScheduleEntry scheduleEntry) {

    }


    @Override
    public void update(ScheduleEntry scheduleEntry) {

    }


    @Override
    public void delete(ScheduleEntry scheduleEntry) {

        if (scheduleEntry.getScheduleDetails().getRowid() > 0) {
            new ScheduleDetailsDAO().delete(scheduleEntry.getScheduleDetails());
        }else{
           String query = "UPDATE SUPPLIER_ORDERS SET visible = 0 WHERE rowid = " + scheduleEntry.getRowId() + ";";
            SQLiteJDBC.update(query);
        }


    }
}
