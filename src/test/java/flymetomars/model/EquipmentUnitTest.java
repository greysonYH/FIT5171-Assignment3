package flymetomars.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yuan-Fang Li
 * @version $Id: $
 */
public class EquipmentUnitTest {
    private Equipment e, e1, e2;

    @Before
    public void setUp(){  e = new Equipment(); }

    ///
    @Test
    public void setNameNotNullOrEmpty() {
        try {
            e.setName(null);
            fail();
        }catch (Exception e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
        try {
            e.setName("");
            fail();
        }catch (Exception e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }

    @Test
    public void setCostNotNegative() {
        try {
            e.setWeight(-10);
            fail();
        }catch (Exception e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }

    @Test
    public void setWeightNotNegative() {
        try {
            e.setWeight(-10);
            fail();
        }catch (Exception e){
            assertTrue(e instanceof  IllegalArgumentException);
        }
    }

    @Test
    public void setVolumeNotNegative() {
        try {
            e.setVolume(-10);
            fail();
        }catch (Exception e){
            assertTrue(e instanceof  IllegalArgumentException);
        }

    }

    ///

    @Test
    public void sameEquipmentNeedsToHaveSameAttributesAndName() {
        e1 = new Equipment("Laser", 10, 2, 20);
        e2 = new Equipment("Laser", 10, 2, 20);

        assertEquals(e1, e2);
    }

    @Test
    public void differentEquipmentCanHaveDifferentAttributes() {
        e1 = new Equipment("Laser", 10, 1, 20);
        e2 = new Equipment("Laser", 10, 2, 20);

        assertNotEquals(e1, e2);
    }

    @Test
    public void differentEquipmentCanHaveDifferentNames() {
        e1 = new Equipment("Laser", 10, 2, 20);
        e2 = new Equipment("laser", 10, 2, 20);

        assertNotEquals(e1, e2);
    }
}