package flymetomars.core.check;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import flymetomars.model.Equipment;
import flymetomars.model.Expertise;
import flymetomars.model.Mission;
import flymetomars.model.Person;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * @author Yuan-Fang Li
 * @version $Id: $
 */
public class ValidatorUnitTest {

    private Validator validator;
    Expertise e1, e2, e3, e4;
    Equipment eq1, eq2;
    Person p1, p2;
    Map<Equipment.Attribute, Integer> max;

    @Before
    public void setUp() {
        validator = new Validator();

        eq1 = new Equipment("Computer",10,10,10);
        eq2 = new Equipment("GPS", 10,10,10);

        e1 = new Expertise("navigation");
        e2 = new Expertise("cooking");
        e3 = new Expertise("IT");
        e4 = new Expertise("medical");

        p1 = new Person("p1@abc.com");
        p2 = new Person("p2@abc.com");

        max = new HashMap<>();
        max.put(Equipment.Attribute.volume, 20);
        max.put(Equipment.Attribute.cost, 200);
        max.put(Equipment.Attribute.weight,20);
    }

    //@Ignore
    @Test
    public void missionNeedsToSatisfyRequiredExpertise1() {
        Expertise e1 = new Expertise("navigation");
        Expertise e2 = new Expertise("cooking");
        Expertise e3 = new Expertise("IT");
        Expertise e4 = new Expertise("medical");

        Person p1 = new Person("p1@abc.com");
        Person p2 = new Person("p2@abc.com");
        Person p3 = new Person("p3@abc.com");

        p1.addExpertise(e1);
        p2.addExpertise(e2, e3);

        Mission mission = new Mission();
        mission.setName("initial");
        mission.setExpertiseRequired(Sets.newHashSet(e1, e2, e3, e4));

        try {
            validator.finalise(mission);
            fail("No exception thrown");
        } catch (ValidationException e) {
            Set<ValidationError> validationErrors = e.getValidationErrors();
            assertFalse(validationErrors.isEmpty());
        }
    }

    @Test
    public void missionNeedsToSatisfyTime() {
        Multiset<Equipment> equipment =  HashMultiset.create();
        equipment.add(eq1);
        equipment.add(eq2);

        p1.setEquipmentOwned(Sets.newHashSet(eq1));
        p2.setEquipmentOwned(Sets.newHashSet(eq2));
        p1.addExpertise(e1, e4);
        p2.addExpertise(e2, e3);

        //No date
        Mission mission1 = new Mission();
        mission1.setName("initial");
        mission1.setDuration(10);
        mission1.setLocation("Nevada");
        mission1.setDescription("New mission");
        mission1.setMaxAttributes(max);
        mission1.setEquipmentsRequired(equipment);
        mission1.setExpertiseRequired(Sets.newHashSet(e1, e2, e3, e4));
        mission1.setParticipantSet(Sets.newHashSet(p1,p2));
        try {
            validator.finalise(mission1);
            fail("No exception thrown");
        } catch (ValidationException e) {
            Set<ValidationError> validationErrors = e.getValidationErrors();
            assertFalse(validationErrors.isEmpty());
        }
    }

    @Test
    public void missionNeedsToSatisfyName() {
        Multiset<Equipment> equipment =  HashMultiset.create();
        equipment.add(eq1);
        equipment.add(eq2);

        p1.setEquipmentOwned(Sets.newHashSet(eq1));
        p2.setEquipmentOwned(Sets.newHashSet(eq2));
        p1.addExpertise(e1, e4);
        p2.addExpertise(e2, e3);

        //No Name
        Mission mission2 = new Mission();
        mission2.setLocation("Nevada");
        mission2.setDuration(10);
        mission2.setDescription("New mission");
        mission2.setMaxAttributes(max);
        mission2.setTime(new Date("10/10/2040"));
        mission2.setEquipmentsRequired(equipment);
        mission2.setExpertiseRequired(Sets.newHashSet(e1, e2, e3, e4));
        mission2.setParticipantSet(Sets.newHashSet(p1,p2));
        try {
            validator.finalise(mission2);
            fail("No exception thrown");
        } catch (ValidationException e) {
            Set<ValidationError> validationErrors = e.getValidationErrors();
            assertFalse(validationErrors.isEmpty());
        }

    }

    @Test
    public void missionNeedsToSatisfyDuration() {
        Multiset<Equipment> equipment =  HashMultiset.create();
        equipment.add(eq1);
        equipment.add(eq2);

        p1.setEquipmentOwned(Sets.newHashSet(eq1));
        p2.setEquipmentOwned(Sets.newHashSet(eq2));
        p1.addExpertise(e1, e4);
        p2.addExpertise(e2, e3);

        //Duration = 0
        Mission mission3 = new Mission();
        mission3.setName("initial");
        mission3.setLocation("Nevada");
        mission3.setDescription("New mission");
        mission3.setMaxAttributes(max);
        mission3.setTime(new Date("10/10/2040"));
        mission3.setEquipmentsRequired(equipment);
        mission3.setExpertiseRequired(Sets.newHashSet(e1, e2, e3, e4));
        mission3.setParticipantSet(Sets.newHashSet(p1,p2));
        try {
            validator.finalise(mission3);
            fail("No exception thrown");
        } catch (ValidationException e) {
            Set<ValidationError> validationErrors = e.getValidationErrors();
            assertFalse(validationErrors.isEmpty());
        }
    }

    @Test
    public void missionNeedsToSatisfyLocation() {
        Multiset<Equipment> equipment =  HashMultiset.create();
        equipment.add(eq1);
        equipment.add(eq2);

        p1.setEquipmentOwned(Sets.newHashSet(eq1));
        p2.setEquipmentOwned(Sets.newHashSet(eq2));
        p1.addExpertise(e1, e4);
        p2.addExpertise(e2, e3);


        //No location
        Mission mission4 = new Mission();
        mission4.setName("initial");
        mission4.setDuration(10);
        mission4.setDescription("New mission");
        mission4.setMaxAttributes(max);
        mission4.setTime(new Date("10/10/2040"));
        mission4.setEquipmentsRequired(equipment);
        mission4.setExpertiseRequired(Sets.newHashSet(e1, e2, e3, e4));
        mission4.setParticipantSet(Sets.newHashSet(p1,p2));
        try {
            validator.finalise(mission4);
            fail("No exception thrown");
        } catch (ValidationException e) {
            Set<ValidationError> validationErrors = e.getValidationErrors();
            assertFalse(validationErrors.isEmpty());
        }
    }

    @Test
    public void missionNeedsToSatisfyDescription() {
        Multiset<Equipment> equipment =  HashMultiset.create();
        equipment.add(eq1);
        equipment.add(eq2);

        p1.setEquipmentOwned(Sets.newHashSet(eq1));
        p2.setEquipmentOwned(Sets.newHashSet(eq2));
        p1.addExpertise(e1, e4);
        p2.addExpertise(e2, e3);

        //No description
        Mission mission5 = new Mission();
        mission5.setName("initial");
        mission5.setLocation("Nevada");
        mission5.setMaxAttributes(max);
        mission5.setTime(new Date("10/10/2040"));
        mission5.setEquipmentsRequired(equipment);
        mission5.setExpertiseRequired(Sets.newHashSet(e1, e2, e3, e4));
        mission5.setParticipantSet(Sets.newHashSet(p1,p2));
        try {
            validator.finalise(mission5);
            fail("No exception thrown");
        } catch (ValidationException e) {
            Set<ValidationError> validationErrors = e.getValidationErrors();
            assertFalse(validationErrors.isEmpty());
        }

    }

    @Test
    public void missionNeedsToSatisfyMaxAttribute() {
        Multiset<Equipment> equipment =  HashMultiset.create();
        equipment.add(eq1);
        equipment.add(eq2);

        p1.setEquipmentOwned(Sets.newHashSet(eq1));
        p2.setEquipmentOwned(Sets.newHashSet(eq2));
        p1.addExpertise(e1, e4);
        p2.addExpertise(e2, e3);

        //No MaxAttribute
        Mission mission6 = new Mission();
        mission6.setName("initial");
        mission6.setDuration(10);
        mission6.setLocation("Nevada");
        mission6.setDescription("New mission");
        mission6.setTime(new Date("10/10/2040"));
        mission6.setEquipmentsRequired(equipment);
        mission6.setExpertiseRequired(Sets.newHashSet(e1, e2, e3, e4));
        mission6.setParticipantSet(Sets.newHashSet(p1,p2));
        try {
            validator.finalise(mission6);
            fail("No exception thrown");
        } catch (ValidationException e) {
            Set<ValidationError> validationErrors = e.getValidationErrors();
            assertFalse(validationErrors.isEmpty());
        }
    }

    @Test
    public void missionNeedsToSatisfyRequiredExpertise() {
        Multiset<Equipment> equipment =  HashMultiset.create();
        equipment.add(eq1);
        equipment.add(eq2);

        p1.setEquipmentOwned(Sets.newHashSet(eq1));
        p2.setEquipmentOwned(Sets.newHashSet(eq2));
        p1.addExpertise(e1);
        p2.addExpertise(e2, e3);

        Mission mission = new Mission();
        mission.setName("initial");
        mission.setLocation("Nevada");
        mission.setDescription("New mission");
        mission.setMaxAttributes(max);
        mission.setTime(new Date("10/10,2020"));
        mission.setEquipmentsRequired(equipment);
        mission.setExpertiseRequired(Sets.newHashSet(e1, e2, e3, e4));
        mission.setParticipantSet(Sets.newHashSet(p1,p2));
        try {
            validator.finalise(mission);
            fail("No exception thrown");
        } catch (ValidationException e) {
            Set<ValidationError> validationErrors = e.getValidationErrors();
            assertFalse(validationErrors.isEmpty());
        }
    }

    @Test
    public void missionNeedsToSatisfyRequiredEquipment() {
        Multiset<Equipment> equipment =  HashMultiset.create();
        equipment.add(eq1);
        equipment.add(eq2);

        p1.setEquipmentOwned(Sets.newHashSet(eq1));
        p2.setEquipmentOwned(Sets.newHashSet(eq1));
        p1.addExpertise(e1, e4);
        p2.addExpertise(e2, e3);

        Mission mission = new Mission();
        mission.setName("initial");
        mission.setLocation("Nevada");
        mission.setDescription("New mission");
        mission.setMaxAttributes(max);
        mission.setTime(new Date("10/10,2020"));
        mission.setEquipmentsRequired(equipment);
        mission.setExpertiseRequired(Sets.newHashSet(e1, e2, e3, e4));
        mission.setParticipantSet(Sets.newHashSet(p1,p2));
        try {
            validator.finalise(mission);
            fail("No exception thrown");
        } catch (ValidationException e) {
            Set<ValidationError> validationErrors = e.getValidationErrors();
            assertFalse(validationErrors.isEmpty());
        }
    }

    /*
    Testing the weight, change eq1.weight = 20
    total weight = eq1 + eq2 = 20 + 10 = 30 > max weight (20)
     */
    @Test
    public void missionNeedsToSatisfyMaxWeightConstraint() {

        eq1.setWeight(20);
        Multiset<Equipment> equipment =  HashMultiset.create();
        equipment.add(eq1);
        equipment.add(eq2);

        p1.setEquipmentOwned(Sets.newHashSet(eq1));
        p2.setEquipmentOwned(Sets.newHashSet(eq2));
        p1.addExpertise(e1, e4);
        p2.addExpertise(e2, e3);

        Mission mission = new Mission();
        mission.setName("initial");
        mission.setLocation("Nevada");
        mission.setDescription("New mission");
        mission.setMaxAttributes(max);
        mission.setTime(new Date("10/10,2020"));
        mission.setEquipmentsRequired(equipment);
        mission.setExpertiseRequired(Sets.newHashSet(e1, e2, e3, e4));
        mission.setParticipantSet(Sets.newHashSet(p1,p2));
        try {
            validator.finalise(mission);
            fail("No exception thrown");
        } catch (ValidationException e) {
            Set<ValidationError> validationErrors = e.getValidationErrors();
            assertFalse(validationErrors.isEmpty());
        }
    }

    /*
   Testing the volume, change eq1.volume = 20
   total volume = eq1 + eq2 = 20 + 10 = 30 > max volume (20)
    */
    @Test
    public void missionNeedsToSatisfyMaxVolumeConstraint() {

        eq1.setVolume(20);
        Multiset<Equipment> equipment =  HashMultiset.create();
        equipment.add(eq1);
        equipment.add(eq2);

        p1.setEquipmentOwned(Sets.newHashSet(eq1));
        p2.setEquipmentOwned(Sets.newHashSet(eq2));
        p1.addExpertise(e1, e4);
        p2.addExpertise(e2, e3);

        Mission mission = new Mission();
        mission.setName("initial");
        mission.setLocation("Nevada");
        mission.setDescription("New mission");
        mission.setMaxAttributes(max);
        mission.setTime(new Date("10/10,2020"));
        mission.setEquipmentsRequired(equipment);
        mission.setExpertiseRequired(Sets.newHashSet(e1, e2, e3, e4));
        mission.setParticipantSet(Sets.newHashSet(p1,p2));
        try {
            validator.finalise(mission);
            fail("No exception thrown");
        } catch (ValidationException e) {
            Set<ValidationError> validationErrors = e.getValidationErrors();
            assertFalse(validationErrors.isEmpty());
        }
    }

    /*
   Testing the cost, change eq1.cost = 300
   total cost = eq1 + eq2 = 300 + 10 = 310 > max volume (200)
    */
    @Test
    public void missionNeedsToSatisfyMaxCostConstraint() {

        eq1.setCost(300);
        Multiset<Equipment> equipment =  HashMultiset.create();
        equipment.add(eq1);
        equipment.add(eq2);

        p1.setEquipmentOwned(Sets.newHashSet(eq1));
        p2.setEquipmentOwned(Sets.newHashSet(eq2));
        p1.addExpertise(e1, e4);
        p2.addExpertise(e2, e3);

        Mission mission = new Mission();
        mission.setName("initial");
        mission.setLocation("Nevada");
        mission.setDescription("New mission");
        mission.setMaxAttributes(max);
        mission.setTime(new Date("10/10,2020"));
        mission.setEquipmentsRequired(equipment);
        mission.setExpertiseRequired(Sets.newHashSet(e1, e2, e3, e4));
        mission.setParticipantSet(Sets.newHashSet(p1,p2));
        try {
            validator.finalise(mission);
            fail("No exception thrown");
        } catch (ValidationException e) {
            Set<ValidationError> validationErrors = e.getValidationErrors();
            assertFalse(validationErrors.isEmpty());
        }
    }

    // Test person's email with special constraint
    @Test
    public void emailNeedsToSatisfyRequiredFormat() {
        Person person = new Person();
        try {
            validator.setPersonEmail(person, "llx.google@");
            fail("There is no exception about email!!");
        } catch (ValidationException ep) {
            Set<ValidationError> validationErrors = ep.getValidationErrors();
            assertFalse(validationErrors.isEmpty());
        }
    }

    // Test person's password with special constraint
    @Test
    public void passwordNeedsToSatisfyRequiredFormat() {
        Person person = new Person();
        try {
            validator.setPersonPassword(person, "abc123");
            fail("no exception of password!");
        } catch (ValidationException e) {
            Set<ValidationError> validationErrors = e.getValidationErrors();
            assertFalse(validationErrors.isEmpty());
        }
    }
}