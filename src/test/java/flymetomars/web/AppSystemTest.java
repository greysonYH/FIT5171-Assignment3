package flymetomars.web;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import flymetomars.data.PersonDAO;
import flymetomars.data.ormlite.PersonDAOImpl;
import flymetomars.model.Person;
import net.sourceforge.jwebunit.junit.JWebUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;
import spark.resource.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import static org.h2.util.IOUtils.closeSilently;

/**
 * @author Yuan-Fang Li
 * @version $Id: $
 */
public class AppSystemTest {

    private static String dbURL;
    private static PersonDAO personDAO;

    @BeforeClass
    public static void setUp() throws Exception {
        ClassPathResource resource = new ClassPathResource( "app.properties" );

        Properties properties = new Properties();
        InputStream stream = null;
        try {
            stream = resource.getInputStream();
            properties.load(stream);
            int port = Integer.parseInt(properties.getProperty("spark.port"));
            JWebUnit.setBaseUrl("http://localhost:" + port);

            dbURL = "jdbc:h2:./" + properties.getProperty("h2.dir");
            setupDB(dbURL);
        } finally {
            closeSilently(stream);
        }

        App.main(null);

        try {
            Thread.sleep(500);
        } catch (Exception ignored) {
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        Spark.stop();
        cleanDataBase(dbURL);
    }

    private static void setupDB(String dbURL) throws SQLException, IOException {
        ConnectionSource connectionSource = new JdbcConnectionSource(dbURL);

        personDAO = new PersonDAOImpl(connectionSource);
        TableUtils.createTableIfNotExists(connectionSource, Person.class);


        addDummyUsers();
    }

    /**
     * Adds dummy users to the system. These users are used to display the information on the userpage.
     *
     * @throws SQLException
     */
    private static void addDummyUsers() throws SQLException {
        Person newUser = new Person();
        String userInfostr = "demo";
        for (int index = 0; index < 3; index++) {
            newUser.setFirstName(userInfostr);
            newUser.setLastName("Name" + index);
            newUser.setEmail(userInfostr + index + "@" + userInfostr + ".com");
            newUser.setPassword(userInfostr + index);
            personDAO.save(newUser);
        }
    }

    /**
     * Removes all the tables from the database once the test is implemented. This method should not be called form
     * any other location.
     *
     * @throws SQLException
     */
    private static void cleanDataBase(String dbURL) throws SQLException, IOException {
        ConnectionSource connectionSource = new JdbcConnectionSource(dbURL);

        TableUtils.dropTable(connectionSource, Person.class, true);
    }

    @Test
    public void basePageShouldContainWelcome() {
        String path = "/";
        JWebUnit.beginAt(path);
        JWebUnit.assertTextPresent("Welcome");
    }

    @Test
    public void loginPageShouldContainUsernameField() {
        String path = "/login";
        JWebUnit.beginAt(path);
        JWebUnit.assertTitleEquals("Fly me to Mars: a mission registration system.");
        JWebUnit.assertFormPresent("user_login");
        JWebUnit.assertElementPresent("user_name");
        JWebUnit.assertTextInElement("user_name", "");
        JWebUnit.assertSubmitButtonPresent();
    }
}
