package flymetomars.core.mining;


import flymetomars.data.*;
import flymetomars.model.Expertise;
import flymetomars.model.Mission;
import flymetomars.model.Person;

import java.sql.SQLException;
import java.util.*;

/**
 * @author Yuan-Fang Li
 * @version $Id: $
 */
public class EntityMiner {
    private PersonDAO personDAO;
    private MissionDAO missionDAO;
    private ExpertiseDAO expertiseDAO;
    private InvitationDAO invitationDAO;
    private EquipmentDAO equipmentDAO;

    public EntityMiner(PersonDAO personDAO, MissionDAO missionDAO, ExpertiseDAO expertiseDAO, InvitationDAO invitationDAO,EquipmentDAO equipmentDAO) {
        this.personDAO = personDAO;
        this.missionDAO = missionDAO;
        this.expertiseDAO = expertiseDAO;
        this.invitationDAO = invitationDAO;
        this.equipmentDAO = equipmentDAO;
    }

    /**
     * Return the most popular person by the count of invitations received.
     *
     * @return The most popular person.
     */
    public Person getMostPopularPerson() throws SQLException {
        List<Person> persons = personDAO.getAll();
        int maxInvites = 0;
        Person result = null;
        for (Person p : persons) {
            int noInvites = p.getInvitationsReceived().size();
            if (maxInvites <= noInvites) {
                maxInvites = noInvites;
                result = p;
            } 
        }
        return result;
    }


    /**
     * Get a list of persons of the given size with the most invitations received.
     *
     * @param size the size of the set to be returned.
     * @return the set of the most popular person by invitations received.
     */
    public List<Person> getCelebrarity(int size) {
        // TODO: implement it!
        return null;
    }

    /**
     * Get the list of the given size of persons with the most missions registered.
     *
     * @param person the person of the buddies to be returned.
     * @param size the size of the set to be returned.
     * @return the set of the busiest persons.
     */
    public List<Person> getBuddies(Person person, int size) {
        // TODO: implement it!
        return null;
    }

    /**
     * Given a person, return the largest group of persons (including this person)
     * such that each pair of persons are connected by some mission (i.e., they all
     * know each other through these missions).
     *
     * @param person the person of the social circle.
     * @Return the social circle of the given person.
     */
    public Set<Person> getSocialCircle(Person person) {
        // TODO: implement it!
        return null;
    }

    /**
     * Given a person, return the top-k upcoming missions that his/her friends
     * have been invited to but he/she hasn’t been (ranked by the number of
     * his/her friends who have been invited).
     *
     * @param person the person of the sour grape.
     * @param size the max number of upcoming missions that the person is not invited to.
     * @return the ranked list of the person's friends who are invited to
     * a mission that the person is not invited to.
     */
    public List<Mission> getSourGrapes(Person person, int size) {
        // TODO: implement it!
        return null;
    }

    /**
     * Return the top-k most expensive missions. The cost of a mission is the sum
     * of the costs of its required equipments.
     * @return
     */
    public List<Mission> getExorbitance(int k) throws SQLException {
        // TODO: implement and test this!
        Exorbitance ex = new Exorbitance(missionDAO);
        return ex.getTopKMostExpensiveMissions(k);

    }

    /**
     * Return the top-k persons with the most demanded expertises (required by missions).
     */
    public List<Person> getHotshots(int k) throws SQLException {
        // TODO: implement and test this!
        Hotshots hs = new Hotshots(missionDAO,expertiseDAO,personDAO);
        return hs.getHotshots(k);
    }

    /**
     * Return the top-k power brokers (people who connect to many people, the connectivity is measured by the number of person in the missions in which the person involve.
     */
    public List<Person> getPowerBrokers() throws SQLException {
        PowerBrokers powerBrokers = new PowerBrokers(missionDAO,personDAO);
        return powerBrokers.getPowerBrokers();
    }

    public List<Date> getOpportuneTimes(Person person, Date start, Date end) throws SQLException {
        OpportuneTimes opportuneTimes = new OpportuneTimes(missionDAO,personDAO);
        return opportuneTimes.getOpportuneTimes(person,start, end);
    }

    /**
     * Return a list of persons with expertise that satisfy the roles of a new mission.
     */
    public List<Person> getRostering(List<Expertise> expertiseList, int k) throws SQLException {
        Rostering ros = new Rostering(missionDAO);
        return ros.generateRosterList(expertiseList, k);
    }

    /**
     * Return m other persons that this person is not a friend with up to (and including) k connections
     * */
    public List<Person> getFrontiers(Person p, int k, int m) throws SQLException {
        NewFrontiers nf = new NewFrontiers(missionDAO);
        return nf.getNewFrontiers(p, k, m);
    }

    /*
   source: http://beginnersbook.com/2013/12/how-to-sort-hashmap-in-java-by-keys-and-values/
    */
    public  static HashMap sortByValues(Map map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {   // Here should return o2, o1 as DESC, if return
                return ((Comparable) ((Map.Entry) (o2)).getValue())  // o1, o2, the order is ASC
                        .compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }
}
