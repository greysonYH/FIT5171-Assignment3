package flymetomars.data;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import flymetomars.model.Invitation;
import flymetomars.model.Mission;
import flymetomars.model.Person;

import java.sql.SQLException;
import java.util.Set;

/**
 * Created by yli on 10/03/15.
 */
public interface InvitationDAO extends DAO<Invitation> {


    Set<Invitation> getInvitationsByCreator(Person person) throws SQLException;

    Set<Invitation> getInvitationsByRecipient(Person person) throws SQLException;

    Set<Invitation> getInvitationsByMission(Mission mission) throws SQLException;
}
