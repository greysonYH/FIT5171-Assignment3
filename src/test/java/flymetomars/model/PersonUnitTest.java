package flymetomars.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by yli on 16/03/15.
 */
public class PersonUnitTest {
    private Person p;

    @Before
    public void setUp() {
        p = new Person();
    }


    ///

    @Test
    public void firstNameNotNullOrEmpty() {
        try {
            p.setFirstName(null);
            fail("No exception thrown for null first name");
        }catch (IllegalArgumentException e){
            assertTrue(e instanceof  IllegalArgumentException);
        }

        try {
            p.setFirstName("");
            fail("No exception thrown for empty first name");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("FirstName contains empty", e.getMessage().contains("empty"));
        }
    }

    @Test
    public void lastNameNotNullOrEmpty() {
        try {
            p.setLastName(null);
            fail("No exception thrown for null last name");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("LastName contains null", e.getMessage().contains("null"));
        }

        try {
            p.setLastName("");
            fail("No exception thrown for null last name");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("LastName contains empty", e.getMessage().contains("empty"));
        }
    }


    @Test
    public void emailNotNullOrEmpty() {
        try {
            p.setEmail(null);
            fail("No exception thrown for null email");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Email contains null", e.getMessage().contains("null"));
        }

        try {
            p.setEmail("");
            fail("No exception thrown for empty password");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Email contains empty", e.getMessage().contains("empty"));
        }
    }

    @Test
    public void setMissionRegisteredNotNull() {
        try {
            p.setMissionRegistered(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }
    @Test
    public void setExpertiseNotNull() {
        try {
            p.setExpertise(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }
    @Test
    public void setInvitationReceivedNotNull() {
        try {
            p.setInvitationsReceived(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }
    @Test
    public void setEquipmentOwnedNotNull() {
        try {
            p.setEquipmentOwned(null);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }
    ///


    @Test
    public void expertiseNotNull() {
        assertNotNull("expertise not null", p.getExpertise());
    }

    @Test
    public void passwordNotNullOrEmpty() {
        try {
            p.setPassword(null);
            fail("No exception thrown for null password");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains null", e.getMessage().contains("null"));
        }

        try {
            p.setPassword("");
            fail("No exception thrown for empty password");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains empty", e.getMessage().contains("empty"));
        }
    }

    @Test
    public void differentPersonNotEqual() {
        p.setEmail("abc@abc.net.au");
        Object o = "abc@abc.net.au";
        assertNotEquals("String not Person", p, o);

        Person q = new Person();
        assertNotEquals("q doesn't have an email", p, q);

        q.setEmail("abc@abc.net");
        assertNotEquals("Different emails", p, q);
    }

    @Test
    public void sameEmailSamePerson() {
        String email = "abc@abc.net.au";
        p.setEmail(email);

        Person q = new Person();
        q.setEmail(email);

        assertEquals("Same person", p, q);

        p.setLastName("Foo");
        q.setLastName("Bar");

        assertEquals("Names don't matter", p, q);
    }

    @Test
    public void newNullExpertiseThrowsIAE() {
        try {
            p.addExpertise(null);
            fail("No exception thrown for null expertise");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains null", e.getMessage().contains("null"));
        }
    }

    @Test
    public void newExpertiseNullDescriptionThrowsIAE() {
        // mock an Expertise object with expected behaviour
        Expertise exp = mock(Expertise.class);
        when(exp.getDescription()).thenReturn(null);

        try {
            p.addExpertise(exp);
            fail("No exception thrown for null expertise description");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains null", e.getMessage().contains("null"));
            assertTrue("Message contains description", e.getMessage().contains("description"));
        }
    }

    @Test
    public void newExpertiseWithNonemptyDescription() {
        // mock an Expertise object with expected behaviour
        Expertise exp = mock(Expertise.class);
        when(exp.getDescription()).thenReturn("fishing");

        assertEquals("Empty collection", 0, p.getExpertise().size());
        p.addExpertise(exp);
        assertEquals("1 expertise", 1, p.getExpertise().size());
    }
}