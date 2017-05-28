package flymetomars.core.mining;

import flymetomars.data.MissionDAO;
import flymetomars.data.PersonDAO;
import flymetomars.model.Mission;
import flymetomars.model.Person;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by greyson on 24/5/17.
 */
public class PowerBrokers {
    MissionDAO missionDAO;
    PersonDAO personDAO;

    public PowerBrokers(MissionDAO missionDAO, PersonDAO personDAO){
        this.missionDAO = missionDAO;
        this.personDAO = personDAO;
    }

    public List<Person> getPowerBrokers() throws SQLException {
        List<Mission> missions = missionDAO.getAll();
        HashMap<Person, Integer> personMissionParticipations = new HashMap<Person, Integer>();
        for (Mission m: missions){
            Set<Person> participants = m.getParticipantSet();
            int nParticipants = participants.size();
            for(Person p : participants){
                if(personMissionParticipations.containsKey(p)){
                    int value = personMissionParticipations.get(p);
                    value += nParticipants;
                    personMissionParticipations.put(p, value);
                }else{
                    personMissionParticipations.put(p,nParticipants);
                }
            }
        }
        HashMap<Person, Integer> personMissionParticipationsSorted = EntityMiner.sortByValues(personMissionParticipations);
        return new ArrayList<>(personMissionParticipationsSorted.keySet());
    }

}

