package flymetomars.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by greyson on 24/5/17.
 */
public class ExpertiseUnitTest {
    private Expertise expertise;

    @Before
    public void ExpertiseUnitTest() {
        expertise = new Expertise();
    }

    @Test
    public void descriptionNotNull() {
        try {
            expertise.setDescription(null);
            fail("No exception thrown for null description");
        } catch (Exception e) {
            assertTrue("Throws IAE", e instanceof IllegalArgumentException);
            assertTrue("Message contains null", e.getMessage().contains("null"));
        }
    }
}
