package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.QaRecordWeight;
import app.pojos.Suppliers;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QaRecordWeightDao implements  Dao<QaRecordWeight> {

    private final String TABLE = "QA_RECORD_WEIGHT";


    @Override
    public <R> QaRecordWeight get(R id) {

        QaRecordWeight rec = new QaRecordWeight();

        ResultSet rs = SQLiteJDBC.select(TABLE, "rowid", id);

        try{
            while (rs.next()) {
                rec = mapRSToObject(rs);
            }
        }catch (NullPointerException | SQLException e){
            e.printStackTrace();
            SQLiteJDBC.close();

        }
        SQLiteJDBC.close();

        return rec;

    }


    @Override
    public List<QaRecordWeight> getAll() {

        return null;
    }


    @Override
    public List<QaRecordWeight> getAll(String param) {

        return null;
    }


    @Override
    public boolean save(QaRecordWeight qaRecordWeight) {
        String values = qaRecordWeight.getRowid() + ", " + qaRecordWeight.getBoxes() + ", " + qaRecordWeight.getWeight() +"" ;

        @Language("SQLite")
        String sql ="INSERT INTO " + TABLE + " (rowid, boxes, weight) VALUES(" + values + ")";
        boolean save = SQLiteJDBC.update(sql);

        return save;

    }


    @Override
    public boolean update(QaRecordWeight qaRecordWeight) {

        String values = "boxes = " + qaRecordWeight.getBoxes() + ", weight =" + qaRecordWeight.getWeight();
        @Language("SQLite")
        String sql = "Update " + TABLE + " set " + values + " Where rowid= " + qaRecordWeight.getRowid()+ "";

        if (!SQLiteJDBC.update(sql)){
            return save(qaRecordWeight);
        }else{
            return true;
        }

    }


    @Override
    public boolean delete(QaRecordWeight qaRecordWeight) {

        return SQLiteJDBC.delete(TABLE , "rowid", qaRecordWeight.getRowid());
    }


    private QaRecordWeight mapRSToObject(ResultSet rs){
        QaRecordWeight rec = new QaRecordWeight();

        try {
            rec.setRowid(rs.getInt("rowid"));
            rec.setBoxes(rs.getInt("boxes"));
            rec.setWeight(rs.getDouble("weight"));
        }
        catch (SQLException throwables) {
//            throwables.printStackTrace();
        }


        return rec;
    }
}
