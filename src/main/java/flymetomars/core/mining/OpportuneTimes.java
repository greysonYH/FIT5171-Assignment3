package flymetomars.core.mining;

import flymetomars.data.MissionDAO;
import flymetomars.data.PersonDAO;
import flymetomars.model.Mission;
import flymetomars.model.Person;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by greyson on 24/5/17.
 */
public class OpportuneTimes {
    MissionDAO missionDAO;
    PersonDAO personDAO;
    public OpportuneTimes(MissionDAO missionDAO, PersonDAO personDAO){
        this.personDAO = personDAO;
        this.missionDAO = missionDAO;
    }

    List<Date> getOpportuneTimes(Person person, Date startDate, Date endDate) throws SQLException {
        Set<Mission> missions = missionDAO.getMissionsBetweenDate(startDate, endDate);
        Map<Date, Integer> friends = new HashMap<Date,Integer>();
        for(Mission mission: missions){
            if(mission.getParticipantSet().contains(person) && mission.getTime() != null){
                friends.put(mission.getTime(), mission.getParticipantSet().size() - 1);
            }
        }
        friends = EntityMiner.sortByValues(friends);
        return new ArrayList<>(friends.keySet());
    }

}
