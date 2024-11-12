package Testing;

import Model.BoardElements.Tax;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Max Curkovic
 * All necessary tests for the Tax class.
 */
public class TaxTest {

    /**
     * Kiểm tra chi phí của phần tử Tax.
     */
    @Test
    public void testCost(){
        Tax tax = new Tax("Testing Tax", 100);
        assertEquals(100, tax.getCost());
    }
}
