package app.controller.sql;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ApacheConnPool {

    private static final BasicDataSource ds = new BasicDataSource();

    static {
        final String DB_LOC;
        try {
            DB_LOC = new File(".").getCanonicalPath() + "/database/GI_RMT.db";
            ds.setDriverClassName("org.sqlite.JDBC");
            ds.setUrl("jdbc:sqlite:" + DB_LOC);
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(150);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {

        try {
            return ds.getConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
