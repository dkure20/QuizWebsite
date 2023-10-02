import Database.DatabaseManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener(value = "/HTML/AuthorizationPage.html")
public class Initializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent even) {
        DatabaseManager.runDatabaseCreationScript();
    }
}