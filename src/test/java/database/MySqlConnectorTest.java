package database;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MySqlConnectorTest {

    @Test
    public void getDataSourceTest() {
        MysqlDataSource result = new MysqlDataSource();
        result.setURL("jdbc:mysql://projects-db.ewi.tudelft.nl/projects_SnakeGame5");
        result.setUser("pu_SnakeGame5");
        result.setPassword("p4lBymeokGO2");
        MysqlDataSource mysqlDs = (MysqlDataSource) MySqlConnector.getDataSource();
        Assertions.assertEquals(result.getURL(), mysqlDs.getURL());
        Assertions.assertEquals(result.getUser(), mysqlDs.getUser());
        Assertions.assertEquals(result.getDatabaseName(), mysqlDs.getDatabaseName());
    }

    @Test
    public void constructorTest() {
        MySqlConnector connector = new MySqlConnector();
        Assertions.assertNotNull(connector);
    }
}
