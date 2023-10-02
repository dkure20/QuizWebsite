package DatabaseManagerTests;
import Database.DatabaseManager;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.SQLException;
public class DatabaseManagerMailTests {
    @Test
    public void test1() throws SQLException {
        DatabaseManager.runDatabaseCreationScript();
        assertNull(DatabaseManager.getUser(12));
    }
}

