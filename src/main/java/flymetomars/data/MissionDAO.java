package flymetomars.data;

import flymetomars.model.Expertise;
import flymetomars.model.Mission;
import flymetomars.model.Person;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by yli on 10/03/15.
 */
public interface MissionDAO extends DAO<Mission>{
    Set<Mission> getMissionsByCreator(Person person) throws SQLException;

    Mission getMissionsByCreatorAndName(Person person, String name) throws SQLException;

    HashMap<Expertise, Integer> getMostDemandedExpertise() throws SQLException;//////////

    Set<Mission> getMissionsByParticipant(Person person) throws SQLException;

    Set<Mission> getMissionsBetweenDate(Date start, Date end) throws SQLException;
}
