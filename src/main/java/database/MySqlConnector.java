package database;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;

public class MySqlConnector {

    private static final String dbUrl = "jdbc:mysql://projects-db.ewi.tudelft.nl/";
    private static final String dbName = "projects_SnakeGame5";
    private static final String dbUser = "pu_SnakeGame5";
    private static final String dbPass = "p4lBymeokGO2";

    /**
     * Creates a MySQL DataSource to get connection to the database.
     * @return DataSource for MySQL database
     */
    public static DataSource getDataSource() {
        MysqlDataSource mysqlDS = new MysqlDataSource();
        mysqlDS.setURL(dbUrl + dbName);
        mysqlDS.setUser(dbUser);
        mysqlDS.setPassword(dbPass);
        return mysqlDS;
    }
}
