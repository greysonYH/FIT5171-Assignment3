package flymetomars.core.mining;

import flymetomars.data.ExpertiseDAO;
import flymetomars.data.MissionDAO;
import flymetomars.data.PersonDAO;
import flymetomars.model.Expertise;
import flymetomars.model.Person;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by greyson on 24/5/17.
 */
public class Hotshots {
    MissionDAO missionDAO;
    ExpertiseDAO expertiseDAO;
    PersonDAO personDAO;

    public Hotshots(MissionDAO missionDAO, ExpertiseDAO expertiseDAO, PersonDAO personDAO){
        this.missionDAO = missionDAO;
        this.expertiseDAO = expertiseDAO;
        this.personDAO = personDAO;
    }


    public Map<Person, Integer> getPersonByMostDemandedExpertise() throws SQLException {
        Map<Expertise, Integer> expertisesCountSort = missionDAO.getMostDemandedExpertise();
        HashMap<Person, Integer> personByExpertisePoints = new HashMap<>();
        for(Expertise e : expertisesCountSort.keySet()){
            Set<Person> persons = personDAO.getPersonByExpertise(e);
            for (Person p: persons) {
                int expertisePoint = expertisesCountSort.get(e);
                if(personByExpertisePoints.containsKey(p)) {
                    int currentPoint = personByExpertisePoints.get(p);
                    personByExpertisePoints.put(p, expertisePoint + currentPoint);
                }else {
                    personByExpertisePoints.put(p, expertisePoint);
                }
            }
        }

        HashMap<Person, Integer> personByExpertisePointsSort =  EntityMiner.sortByValues(personByExpertisePoints);

        return personByExpertisePointsSort;
    }

    public List<Person> getHotshots(int k) throws SQLException {
        Map<Person, Integer> personByExpertisePointsSort = getPersonByMostDemandedExpertise();
        Set<Person> set = personByExpertisePointsSort.keySet();
        List<Person> person = new ArrayList<Person>();
        if(k > set.size()) k = set.size();
        int idx = 0;
        Iterator<Person> itr = set.iterator();
        while (itr.hasNext() && idx < k){
            person.add((Person) itr.next());
            idx++;
        }

        return  person;
    }


}

