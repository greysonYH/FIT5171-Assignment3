package flymetomars.data;

import flymetomars.model.Expertise;
import flymetomars.model.Person;

import java.sql.SQLException;
import java.util.Set;

/**
 * Created by yli on 10/03/15.
 */
public interface PersonDAO extends DAO<Person> {
    Person getPersonByEmail(String email) throws SQLException;

    Set<Person> getPersonByExpertise(Expertise expertise) throws SQLException;
}
