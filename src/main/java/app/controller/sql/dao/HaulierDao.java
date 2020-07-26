package app.controller.sql.dao;

import app.controller.sql.SQLiteJDBC;
import app.pojos.Hauliers;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HaulierDao implements Dao<Hauliers> {

    private static final String TABLE = "HAULIERS";


    @Override
    public <R> Hauliers get(R id) {

        return null;
    }


    @Override
    public List<Hauliers> getAll() {

        List<Hauliers> list = new ArrayList<>();
        ResultSet      rs   = SQLiteJDBC.selectAll(TABLE, "name");
        try {
            while (rs.next()) {
                list.add(new Hauliers(rs.getString("name")));
            }
            rs.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        SQLiteJDBC.close();
        return list;
    }


    @Override
    public List<Hauliers> getAll(String param) {

        return null;
    }


    @Override
    public boolean save(Hauliers hauliers) {

        @Language("SQLite")
        String sql =
                "INSERT INTO HAULIERS(name) VALUES('"
                + hauliers.getName().toUpperCase() + "')";
        return SQLiteJDBC.update(sql);
    }


    @Override
    public boolean update(Hauliers hauliers) {

        return false;
    }


    @Override
    public boolean delete(Hauliers hauliers) {

        return SQLiteJDBC.delete(TABLE, "name", hauliers.getName());
    }
}
