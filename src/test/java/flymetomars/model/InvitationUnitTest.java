package flymetomars.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by greyson on 24/5/17.
 */
public class InvitationUnitTest {
    private Invitation invitation;

    @Before
    public void InvitationUnitTest() {
        invitation = new Invitation();
    }
    @Test
    public void missionNotNull() {
        try {
            invitation.setMission(null);
            fail("No exception thrown for null mission");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("mission contains null", e.getMessage().contains("null"));
        }
    }
    @Test
    public void creatorNotNull() {
        try {
            invitation.setCreator(null);
            fail("No exception thrown for null creator");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("creator contains null", e.getMessage().contains("null"));
        }
    }
    @Test
    public void RecipientNotNull() {
        try {
            invitation.setRecipient(null);
            fail("No exception thrown for null recipient");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("recipient contains null", e.getMessage().contains("null"));
        }
    }
    @Test
    public void updatedNotNull() {
        try {
            invitation.setRecipient(null);
            fail("No exception thrown for null update");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("update contains null", e.getMessage().contains("null"));
        }
    }
    @Test
    public void statusNotNull() {
        try {
            invitation.setStatus(null);
            fail("No exception thrown for null status");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("status contains null", e.getMessage().contains("null"));
        }
    }
    @Test
    public void testSentStatus() {
        //SENT("sent"), CREATED("created"), ACCEPTED("accepted"), DECLINED("declined");
        Invitation.InvitationStatus s = Invitation.InvitationStatus.SENT;
        invitation.setStatus(s);
        assertEquals("sent is sent","sent",invitation.getStatus().toString());
    }
    @Test
    public void testCreatedStatus() {
        Invitation.InvitationStatus s = Invitation.InvitationStatus.CREATED;
        invitation.setStatus(s);
        assertEquals("created is created","created",invitation.getStatus().toString());
    }
    @Test
    public void testAcceptedStatus() {
        Invitation.InvitationStatus s = Invitation.InvitationStatus.ACCEPTED;
        invitation.setStatus(s);
        assertEquals("accepted is accepted","accepted",invitation.getStatus().toString());
    }
    @Test
    public void testDeclinedStatus() {
        Invitation.InvitationStatus s = Invitation.InvitationStatus.DECLINED;
        invitation.setStatus(s);
        assertEquals("declined is declined","declined",invitation.getStatus().toString());
    }
    @Test
    public void testRecipientIdentity() {
        Person creator = new Person("llx.tough@gmail.com");
        Person recipient = new Person("llx.tough@gmail.com");
        try {
            invitation.setCreator(creator);
            invitation.setRecipient(recipient);
            fail();
        }catch (IllegalArgumentException e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }
}
