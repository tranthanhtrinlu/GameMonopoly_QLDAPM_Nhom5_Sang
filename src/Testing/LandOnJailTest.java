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
     * Kiểm tra chi phí của phần tử LandOnJail.
     */
    @Test
    public void testCost(){
        LandOnJail landOnJail= new LandOnJail("Land on Jail", 0);
        assertEquals(0, landOnJail.getCost());
    }
}