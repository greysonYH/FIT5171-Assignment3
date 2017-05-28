package flymetomars.core.mining;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import flymetomars.data.*;
import flymetomars.model.Equipment;
import flymetomars.model.Expertise;
import flymetomars.model.Mission;
import flymetomars.model.Person;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Yuan-Fang Li
 * @version $Id: $
 */
public class EntityMinerTest {
    private Person p1;
    private Person p2;
    private Person p3;
    private Person p4;
    private Person p5;
    private Person p6;
    private Mission m1;
    private Mission m2;
    private Mission m3;
    private Expertise e1;
    private Expertise e2;
    private Expertise e3;
    private Expertise e4;
    private Expertise e5;
    private Expertise e6;
    private Expertise e7;

    private PersonDAO pDao;
    private MissionDAO mDao;
    private ExpertiseDAO eDao;
    private InvitationDAO iDao;
    private EquipmentDAO qDao;

    private EntityMiner miner;

    @Before
    public void setUp() {
        p1 = new Person();
        p1.setEmail("p1@example.com");
        p2 = new Person("p2@example.com");
        p3 = new Person("p3@example.com");
        p4 = new Person("p4@example.com");
        p5 = new Person("p5@example.com");
        p6 = new Person("p6@example.com");

        m1 = new Mission();
        m2 = new Mission();
        m3 = new Mission();

        e1 = new Expertise();
        e1.setDescription("Levitation");
        e2 = new Expertise("cook");
        e3 = new Expertise("drive");
        e4 = new Expertise("calculation");
        e5 = new Expertise("programming");
        e6 = new Expertise("testing");
        e7 = new Expertise("design");

        pDao = mock(PersonDAO.class);
        mDao = mock(MissionDAO.class);
        eDao = mock(ExpertiseDAO.class);
        iDao = mock(InvitationDAO.class);
        qDao = mock(EquipmentDAO.class);

        miner = new EntityMiner(pDao, mDao, eDao, iDao, qDao);
    }

    @Test
    public void onePersonIsAlsoMostPopularPerson() throws SQLException {
        // create a single person
        Person p = new Person();
        p.setEmail("abc@abc.net.au");
        ArrayList<Person> list = new ArrayList<>();
        list.add(p);

        // mock the behaviour of pDao
        when(pDao.getAll()).thenReturn(list);

        Person mostPopularPerson = miner.getMostPopularPerson();
        assertEquals("One person is also most popular", p, mostPopularPerson);
    }

    /**
     * Return the top-k most expensive missions. The cost of a mission is the sum
     * of the costs of its required equipments.
     * @return
     */
    public List<Mission> getExorbitance(int k) throws SQLException {
        // TODO: implement and test this!
        Exorbitance ex = new Exorbitance(mDao);
        return ex.getTopKMostExpensiveMissions(k);

    }

    /**
     * Return the top-k persons with the most demanded expertises (required by missions).
     */
    public List<Person> getHotshots(int k) throws SQLException {
        // TODO: implement and test this!
        Hotshots hs = new Hotshots(mDao,eDao,pDao);
        return hs.getHotshots(k);
    }

    /**
     * Return the top-k power brokers (people who connect to many people, the connectivity is measured by the number of person in the missions in which the person involve.
     */
    public List<Person> getPowerBrokersBase() throws SQLException {
        PowerBrokers powerBrokers = new PowerBrokers(mDao,pDao);
        return powerBrokers.getPowerBrokers();
    }

    public List<Date> getOpportuneTimes(Person person, Date start, Date end) throws SQLException {
        OpportuneTimes opportuneTimes = new OpportuneTimes(mDao,pDao);
        return opportuneTimes.getOpportuneTimes(person,start, end);
    }

    /**
     * Return a list of persons with expertise that satisfy the roles of a new mission.
     */
    public List<Person> getRostering(List<Expertise> expertiseList, int k) throws SQLException {
        Rostering ros = new Rostering(mDao);
        return ros.generateRosterList(expertiseList, k);
    }

    /**
     * Return m other persons that this person is not a friend with up to (and including) k connections
     * */
    public List<Person> getFrontiers(Person p, int k, int m) throws SQLException {
        NewFrontiers nf = new NewFrontiers(mDao);
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

    @Test
    public void getK3Hotshots() throws SQLException {
        p1.addExpertise(e1);
        p2.addExpertise(e1);
        p2.addExpertise(e2);
        p3.addExpertise(e3);

        m1.getParticipantSet().add(p1);
        m1.getParticipantSet().add(p2);
        m2.getParticipantSet().add(p1);
        m2.getParticipantSet().add(p3);
        m3.getParticipantSet().add(p1);
        m3.getParticipantSet().add(p2);

        m1.getExpertiseRequired().add(e1);
        m1.getExpertiseRequired().add(e2);

        m2.getExpertiseRequired().add(e1);
        m2.getExpertiseRequired().add(e3);

        m3.getExpertiseRequired().add(e1);
        m3.getExpertiseRequired().add(e2);

        HashMap<Expertise, Integer> mostDemandedExpertise = new HashMap<Expertise, Integer>();
        mostDemandedExpertise.put(e1,3);
        mostDemandedExpertise.put(e2,2);
        mostDemandedExpertise.put(e3,1);

        when(mDao.getAll()).thenReturn(Lists.newArrayList(m1,m2,m3));
        when(mDao.getMostDemandedExpertise()).thenReturn(mostDemandedExpertise);
        when(pDao.getPersonByExpertise(e1)).thenReturn(Sets.newHashSet(p1,p2));
        when(pDao.getPersonByExpertise(e2)).thenReturn(Sets.newHashSet(p2));
        when(pDao.getPersonByExpertise(e3)).thenReturn(Sets.newHashSet(p3));
        when(eDao.getAll()).thenReturn(Lists.newArrayList(e1,e2,e3));
        when(pDao.getAll()).thenReturn(Lists.newArrayList(p1,p2,p3));

        List<Person> hotshots = miner.getHotshots(2);
        assertEquals(2, hotshots.size());
        assertTrue(p2.equals(hotshots.get(0)));
        assertTrue(p1.equals(hotshots.get(1)));

        List<Person> hotshots2 = miner.getHotshots(3);
        assertTrue(p3.equals(hotshots2.get(2)));

    }


    @Test
    public void oneSinglePersonIsAHotshot() throws SQLException {
        p1.addExpertise(e1);
        m1.getParticipantSet().add(p1);
        m1.getExpertiseRequired().add(e1);

        HashMap<Expertise, Integer> mostDemandedExpertise = new HashMap<Expertise, Integer>();
        mostDemandedExpertise.put(e1,1);

        when(mDao.getAll()).thenReturn(Lists.newArrayList(m1));
        when(mDao.getMostDemandedExpertise()).thenReturn(mostDemandedExpertise);
        when(pDao.getPersonByExpertise(e1)).thenReturn(Sets.newHashSet(p1));
        when(eDao.getAll()).thenReturn(Lists.newArrayList(e1));
        when(pDao.getAll()).thenReturn(Lists.newArrayList(p1));


        List<Person> hotshots = miner.getHotshots(2);
        assertEquals(1, hotshots.size());
        assertEquals(p1, hotshots.get(0));
    }

    @Test
    public void getK1MostExpensiveMission() throws SQLException {
        Equipment eq1 = new Equipment("laptop", 1, 1, 200);
        Equipment eq2 = new Equipment("phone", 1, 1, 100);
        Equipment eq3 = new Equipment("bike", 1, 1, 150);
        Equipment eq4 = new Equipment("knife", 1, 1, 50);

        m1.addEquipment(eq1);  //200
        m1.addEquipment(eq2);  //100
        m1.addEquipment(eq2);  //100
        m1.addEquipment(eq3);  //150     total cost for m1 should be 550. but the result is 750
        m2.addEquipment(eq3);
        m2.addEquipment(eq4);
        m1.setName("m1");
        m2.setName("m2");
        m3.setName("m3");
        when(mDao.getAll()).thenReturn(Lists.newArrayList(m1, m2, m3));
        List<Mission> exorbitanceList = miner.getExorbitance(1);
        assertEquals(1, exorbitanceList.size());
    }

    @Test
    public void getPowerBrokers() throws SQLException {
        m1.getParticipantSet().add(p1);
        m1.getParticipantSet().add(p2);

        m2.getParticipantSet().add(p1);
        m2.getParticipantSet().add(p3);

        m3.getParticipantSet().add(p1);
        m3.getParticipantSet().add(p2);

        when(mDao.getAll()).thenReturn(Lists.newArrayList(m1,m2,m3));
        when(pDao.getAll()).thenReturn(Lists.newArrayList(p1,p2,p3 ));

        List<Person> powerbrokers = miner.getPowerBrokers();

        assertEquals(3, powerbrokers.size());
        assertTrue(powerbrokers.get(0).equals(p1));
        assertTrue(powerbrokers.get(1).equals(p2));
        assertTrue(powerbrokers.get(2).equals(p3));
    }

    @Test
    public  void getOpportuneTimes() throws SQLException {
        m1.setTime(new Date("01/01/2000"));
        m2.setTime(new Date("01/01/2005"));
        m3.setTime(new Date("01/01/2010"));

        m1.setParticipantSet(Sets.newHashSet(p4,p2,p3));
        m2.setParticipantSet(Sets.newHashSet(p1,p2,p3,p4));
        m3.setParticipantSet(Sets.newHashSet(p1,p2,p4));

        Date start = new Date("01/01/1995");
        Date end = new Date("01/01/2020");
        when(mDao.getMissionsBetweenDate(start,end)).thenReturn(Sets.newHashSet(m1,m2,m3));

        List<Date> opportuneTimes = miner.getOpportuneTimes(p1, start, end);
        assertEquals(2, opportuneTimes.size());
        assertTrue(opportuneTimes.get(0).equals(new Date("01/01/2005")));
        assertTrue(opportuneTimes.get(1).equals(new Date("01/01/2010")));
    }


    @Test
    public void generatedPersonListShouldGreaterThanK() throws SQLException {
        p1.addExpertise(e1, e3, e7);
        p2.addExpertise(e2, e3, e6, e7);
        p3.addExpertise(e3, e5);
        p4.addExpertise(e2, e3, e4, e6);
        p5.addExpertise(e1, e6, e7);
        p6.addExpertise(e4, e5, e6);

        m1.setParticipantSet(Sets.newHashSet(p1, p2, p4)); // e1, e2, e3, e4, e6, e7
        m2.setParticipantSet(Sets.newHashSet(p3, p4, p5)); // e1, e2, ... e6, e7
        m3.setParticipantSet(Sets.newHashSet(p1, p3, p6)); // e1, e3, e4, e5, e6, e7
        m1.setName("m1");
        m2.setName("m2");
        m3.setName("m3");
        m1.setStatus(Mission.Status.finalised);
        m2.setStatus(Mission.Status.finalised);
        m3.setStatus(Mission.Status.finalised);

        when(mDao.getAll()).thenReturn(Lists.newArrayList(m1, m2, m3));
        List<Expertise> roles = Arrays.asList(e1, e2, e6, e7);

        List<Person> roster = miner.getRostering(roles, 2);
        // it means, auto generated recommend role list should contain more than 2 person
        assertTrue(roles.size() >= 2);
    }

    @Test
    public void getTwoConnectionAndTwoFriends() throws SQLException {
        m1.setParticipantSet(Sets.newHashSet(p1, p3, p4));
        m2.setParticipantSet(Sets.newHashSet(p3, p5));
        m3.setParticipantSet(Sets.newHashSet(p2, p5, p6));
        m1.setName("m1");
        m2.setName("m2");
        m3.setName("m3");
        m1.setStatus(Mission.Status.finalised);
        m2.setStatus(Mission.Status.finalised);
        m3.setStatus(Mission.Status.finalised);

        when(mDao.getAll()).thenReturn(Lists.newArrayList(m1, m2, m3));
        List<Person> newFrontier = miner.getFrontiers(p1, 2, 2);
        assertEquals(2, newFrontier.size());
    }


}