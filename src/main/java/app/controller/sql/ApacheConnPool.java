package app.controller.sql;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ApacheConnPool {
    private static BasicDataSource ds = new BasicDataSource();

    static {
        final String DB_LOC = "C:\\Users\\akazx\\OneDrive\\Desktop\\SQLite Bakkavor Database\\GI_RMT.db";

        ds.setDriverClassName("org.sqlite.JDBC");
        ds.setUrl("jdbc:sqlite:" + DB_LOC);

        ds.setMinIdle(5);
        ds.setMaxIdle(10);

        ds.setMaxOpenPreparedStatements(100);

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

    private ApacheConnPool(){ }
}
