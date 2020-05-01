package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.RmtQaIntakeDetails;
import app.pojos.RmtQaRecords;
import com.google.gson.Gson;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RmtQaRecordsDao implements Dao<RmtQaRecords> {

    private static final String TABLE = "RMT_QA_RECORDS";

    @Override
    public <R> RmtQaRecords get(R id) {
        RmtQaRecords record = null;
        ResultSet resultSet = SQLiteJDBC.select(TABLE, "rowid", id);
        try {
            if (resultSet.next()) {
                record =  mapRsToObject(resultSet);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();

        return record;
    }


    @Override
    public List<RmtQaRecords> getAll() {

        List<RmtQaRecords> list = new ArrayList<>();

        ResultSet rs    = SQLiteJDBC.selectAll(TABLE, "po");

        return getRecords(list, rs);
    }


    @Override
    public List<RmtQaRecords> getAll(String param) {
        List<RmtQaRecords> list = new ArrayList<>();

        ResultSet rs = SQLiteJDBC.select(TABLE, "po", param);

        return getRecords(list, rs);

    }


    @Nullable
    private List<RmtQaRecords> getRecords(List<RmtQaRecords> list, ResultSet rs) {

        try {
            while (rs.next()){
                RmtQaRecords specs = mapRsToObject(rs);
                list.add(specs);
            }
            rs.close();
        }
        catch (SQLException | NullPointerException e) {
           return null;
        }
        SQLiteJDBC.close();
        return list;
    }


    @Override
    public boolean save(RmtQaRecords rmtQaRecords) {

        String fields = " po, m_code, author, date, details_JSON, decision ";
        return SQLiteJDBC.insert(fields, rmtQaRecords.saveString(), TABLE );
    }


    @Override
    public boolean update(RmtQaRecords rmtQaRecords) {

        @Language("SQLite")
        String sql = "UPDATE RMT_QA_RECORDS SET " + rmtQaRecords.toUpdateString() + " WHERE rowid=" + rmtQaRecords.getRowid() + "";
        return SQLiteJDBC.update(sql);
    }


    @Override
    public boolean delete(RmtQaRecords rmtQaRecords) {

        return SQLiteJDBC.delete(TABLE, "rowid", rmtQaRecords.getRowid());
    }

    private RmtQaRecords mapRsToObject(ResultSet rs) throws SQLException {
        RmtQaRecords record = new RmtQaRecords();
        record.setRowid(rs.getInt("rowid"));
        record.setPo(rs.getString("po"));
        record.setmCode(rs.getString("m_code"));
        record.setAuthor(rs.getString("author"));
        record.setDecision(rs.getString("decision"));
        record.setDate(LocalDateTime.parse(rs.getString("date")));
        record.setDetails(new Gson().fromJson(rs.getString("details_JSON"), RmtQaIntakeDetails.class));
//        System.out.println("Viduje: " + rs.getString("details_JSON"));

        return record;
    }
}
