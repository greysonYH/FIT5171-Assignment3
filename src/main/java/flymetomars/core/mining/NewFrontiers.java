package flymetomars.core.mining;

import flymetomars.data.MissionDAO;
import flymetomars.model.Mission;
import flymetomars.model.Person;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by greyson on 24/5/17.
 */
public class NewFrontiers {
    MissionDAO missionDAO;
    public NewFrontiers(MissionDAO missionDAO) {
        this.missionDAO = missionDAO;
    }

    public List<Person> getNewFrontiers(Person onePerson, int numOfK, int numOfM) throws SQLException {
        List<Mission> finishedMissions = missionDAO.getAll();
        Set<Person> allFormerParticipants = new HashSet<>();
        Map<Person, List<Person>> onePersonWithHisFriendList = new HashMap<>();
        Map<Person, List<Person>> onePersonWithNonFriendList = new HashMap<>();

        List<Person> nonFriendList = new ArrayList<>();

        // find all the persons from finished missions, former participants
        for (Mission m : finishedMissions) {
            allFormerParticipants.addAll(m.getParticipantSet());
        }

        // Map each person (Key) and his friends List (value)
        for (Person p : allFormerParticipants) {
            List<Person> friendList = new ArrayList<>();
            for (Mission m : finishedMissions) {
                if (m.getParticipantSet().contains(p)) {
                    for (Person sp : m.getParticipantSet()) {
                        if (sp != p) {
                            friendList.add(sp);
                        }
                    }
                }
            }
            onePersonWithHisFriendList.put(p, friendList);
        }

        for (Map.Entry<Person, List<Person>> entry : onePersonWithHisFriendList.entrySet())
        {
            List<Person> allFormerParticipantsList = new ArrayList(allFormerParticipants);
            allFormerParticipantsList.removeAll(entry.getValue());
            onePersonWithNonFriendList.put(entry.getKey(), allFormerParticipantsList);
        }

        List<Person> onePersonNonFriendList = onePersonWithNonFriendList.get(onePerson);

        if (numOfM <= onePersonNonFriendList.size()) {
            nonFriendList = onePersonNonFriendList.subList(0, numOfM);
        }

        return nonFriendList;
    }
}

