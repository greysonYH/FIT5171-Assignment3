package flymetomars.model;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by greyson on 24/5/17.
 */
public class MissionUnitTest {
    private Mission m;

    @Before
    public void setUp() {
        m = new Mission();
    }


    @Test
    public void setNameNotNullOrEmpty() {
        try {
            m.setName(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
        try {
            m.setName("");
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }

    @Test
    public void setDateNotNull() {
        try {
            m.setTime(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }

    @Test
    public void setLocationNotNull() {
        try {
            m.setLocation(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }


    @Test
    public void setDescriptionNotNull() {
        try {
            m.setDescription(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }

    @Test
    public void setInvitationSetNotNull() {
        try {
            m.setInvitationSet(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }

    @Test
    public void setParticipantSetNotNull() {
        try {
            m.setParticipantSet(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }

    @Test
    public void setCaptainNotNull() {
        try {
            m.setCaptain(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }

    @Test
    public void setEquipmentRequiredNotNull() {
        try {
            m.setEquipmentsRequired(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }

    @Test
    public void setDurationNotNegative() {
        try {
            m.setDuration(-10);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }

    @Test
    public void registeredGroupsNotNull() {
        try {
            m.setRegisteredGroup(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }

    @Test
    public void addGroupNotNull() {
        try {
            m.addGroup(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }

    @Test
    public void addGroupNotEmpty() {
        try {
            Group g = new Group("Fit5171", "This is a testing unit");
            m.addGroup(g);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(e instanceof  IllegalArgumentException);
        }

    }



    @Test
    public void sameEquipmentStoredInSameElement(){
        Equipment eq1 = new Equipment("Laser",10,10,10);
        Equipment eq2 = new Equipment("Laser",10,10,10);
        Multiset<Equipment> bag = HashMultiset.create();
        bag.add(eq1);
        bag.add(eq2);
        m.setEquipmentsRequired(bag);

        assertEquals(2, m.getEquipmentsRequired().count(eq1));
    }

    @Test
    public void differentEquipmentStoredInDifferentElement(){
        Equipment eq1 = new Equipment("Laser",10,10,10);
        Equipment eq2 = new Equipment("Laser",1,5,10);
        Multiset<Equipment> bag = HashMultiset.create();
        bag.add(eq1);
        bag.add(eq2);
        m.setEquipmentsRequired(bag);

        assertEquals(1, m.getEquipmentsRequired().count(eq1));
        assertEquals(1, m.getEquipmentsRequired().count(eq2));

    }

    @Test
    public void testPersonIsInParticipantSet() {
        Person person = new Person();
        person.setEmail("kasat2@monash.edu");
        person.setFirstName("Kadek");
        person.setLastName("Ananta");

        m.addParticipant(person);
        Assert.assertTrue("Person is in participant set", m.isPersonInParticipantSet(person));
    }

    @Test
    public void testPersonIsNotInParticipantSet() {
        Person person1 = new Person();
        person1.setEmail("kasat2@monash.edu");
        person1.setFirstName("Kadek");
        person1.setLastName("Ananta");

        Person person2 = new Person();
        person2.setEmail("ling@monash.edu");
        person2.setFirstName("Lingxioa");
        person2.setLastName("Li");

        m.addParticipant(person1);
        Assert.assertFalse("Person is not in participant set", m.isPersonInParticipantSet(person2));
    }
}
