package flymetomars.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by greyson on 24/5/17.
 */
public class GroupUnitTest {
    private Group group;

    @Before
    public void setUp() {
        group = new Group();
    }

    @Test
    public void groupNameNotNullOrEmpty() {
        try {
            group.setGroupName(null);
            fail("No exception thrown for null group name");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Group name contains null", e.getMessage().contains("null"));
        }

        try {
            group.setGroupName("");
            fail("No exception thrown for empty group name");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Group name contains empty", e.getMessage().contains("empty"));
        }
    }

    @Test
    public void groupDescNotNullOrEmpty() {
        try {
            group.setGroupDescription(null);
            fail("No exception thrown for null group description");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Description contains null", e.getMessage().contains("null"));
        }

        try {
            group.setGroupDescription("");
            fail("No exception thrown for empty group description");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Description contains empty", e.getMessage().contains("empty"));
        }
    }

    @Test
    public void groupMembersNotNull() {
        try {
            group.setGroupMembers(null);
            fail("No exception thrown for null group members");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("members contains null", e.getMessage().contains("null"));
        }
    }

    @Test
    public void addMemberNotNull() {
        try {
            group.addGroupMembers(null);
            fail("No exception thrown for null added members");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("added members contains null", e.getMessage().contains("null"));
        }
    }

    @Test
    public void deleteMemberNotNull() {
        try {
            group.deleteGroupMembers(null);
            fail("No exception thrown for null deleted members");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("deleted members contains null", e.getMessage().contains("null"));
        }
    }
}
