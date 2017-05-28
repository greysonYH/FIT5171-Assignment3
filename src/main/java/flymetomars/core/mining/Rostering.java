package flymetomars.core.mining;

import flymetomars.data.MissionDAO;
import flymetomars.model.Expertise;
import flymetomars.model.Mission;
import flymetomars.model.Person;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by greyson on 24/5/17.
 */
public class Rostering {
    MissionDAO missionDAO;

    public Rostering(MissionDAO missionDAO) {
        this.missionDAO = missionDAO;
    }

    public List<Person> generateRosterList(List<Expertise> roles, int k) throws SQLException {
        List<Mission> finishedMissions = missionDAO.getAll();
        Set<Person> allFormerParticipants = new HashSet<>();
        Set<Expertise> allExistenceExpertise = new HashSet<>();
        Map<Expertise, List<Person>> expertiseWithRelevantPeople = new HashMap<>();

        List<Person> requiredPersonForNewMission = new ArrayList<>();

        // find all the persons from finished missions, former participants
        for (Mission m : finishedMissions) {
            allFormerParticipants.addAll(m.getParticipantSet());
            for (Person p : m.getParticipantSet()) {
                allExistenceExpertise.addAll(p.getExpertise());
            }
        }

        /**
         * Expertise classification
         * Create a Map expertiseWithRelevantPeople, store all the existence expertise and relevant people
         * For example
         *
         * navigation: {p1, p2, p6, p6}   // p1 denotes person 1
         * It        : {p2, p5, p8}
         * medical   : {p3, p7}
         * Cooking   : {p1, p8, p9}
         *
         * In this way, each expertise is connected with a list of people.
         * And, this information is calculate from all finished missions
         **/
        for (Expertise e : allExistenceExpertise) {
            List<Person> personList = new ArrayList<>();
            for (Person p : allFormerParticipants) {
                if (p.getExpertise().contains(e))
                    personList.add(p);
            }
            expertiseWithRelevantPeople.put(e, personList);
        }

        /**
         * Since we have got all the expertise and relevant people:  expertiseWithRelevantPeople
         * For each role, we just pick person from each expertise category.
         *
         * For example, i need 2 people can cooking.
         * Then just move to "Cooking" data set, and pick 2 person
         *
         * pick/ recommend people to satisfy the role list for a new mission.
         * Constraint: The minimum size of the returned list is k
         **/


        // If the size of required person list,  not meet the minimum number k.   generate the list again!!!!
        requiredPersonForNewMission = pickStrategy (roles, expertiseWithRelevantPeople);
        while (requiredPersonForNewMission.size() < k) {
            requiredPersonForNewMission = pickStrategy (roles, expertiseWithRelevantPeople);
        }

        return requiredPersonForNewMission;
    }

    // Needed person pick strategy, we use random num as index, randomly pick suitable person
    private static List<Person> pickStrategy (List<Expertise> roles, Map<Expertise, List<Person>> expertiseWithPeople) {
        int indexNum = 0;
        Random rand = new Random();
        List<Expertise> expertiseList = roles;
        Map<Expertise, List<Person>> expertiseWithPeoList = expertiseWithPeople;

        List<Person> candidateResult = new ArrayList<>();

        for(Expertise e : roles) {
            if (expertiseWithPeoList.get(e).isEmpty()) {
                System.out.println("Cannot find person with this role/expertise" + e.getDescription());
                break;
            } else {
                // generate a random index from: 0 to expertiseWithRelevantPeople.get(it.next()).size() -1
                indexNum = rand.nextInt((expertiseWithPeoList.get(e).size() -1) - 0 + 1) + 0;
                //System.out.println(indexNum);
                candidateResult.add(expertiseWithPeoList.get(e).get(indexNum));
            }
        }
        return candidateResult;
    }

}

