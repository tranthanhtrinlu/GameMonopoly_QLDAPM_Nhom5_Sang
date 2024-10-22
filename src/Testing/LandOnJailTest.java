package Testing;

import Model.BoardElements.LandOnJail;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Max Curkovic
 * All necessary tests for the LandOnJail class.
 */
public class LandOnJailTest {

    /**
     * Test to test the cost of the LandOnJail element.
     */
    @Test
    public void testCost(){
        LandOnJail landOnJail= new LandOnJail("Land on Jail", 0);
        assertEquals(0, landOnJail.getCost());
    }
}