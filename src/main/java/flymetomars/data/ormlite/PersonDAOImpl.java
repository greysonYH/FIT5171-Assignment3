package flymetomars.data.ormlite;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import flymetomars.data.PersonDAO;
import flymetomars.model.Expertise;
import flymetomars.model.Person;

import java.sql.SQLException;
import java.util.Set;

/**
 * @author Yuan-Fang Li
 * @version $Id: $
 */
public class PersonDAOImpl extends AbstractEntityDAOImpl<Person> implements PersonDAO {

    public PersonDAOImpl(ConnectionSource connectionSource) throws SQLException {
        dao = DaoManager.createDao(connectionSource, Person.class);
    }

    @Override
    public Person getPersonByEmail(String email) throws SQLException {
        QueryBuilder<Person, Long> queryBuilder = dao.queryBuilder();
        queryBuilder.where().eq("email", email);
        return dao.queryForFirst(queryBuilder.prepare());
    }

    @Override
    public Set<Person> getPersonByExpertise(Expertise expertise) throws SQLException {
        return null;
    }
}
