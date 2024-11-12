package Testing;

import Model.BoardElements.Utility;
import Model.GamePlayer.User;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Max Curkovic
 * All necessary tests for the Utility class.
 */
public class UtilityTest {

    /**
     * Kiểm thử phương thức buy().
     */
    @Test
    public void testBuy() {

        Utility utility = new Utility("Electric Company", 100);
        User player = new User("A", 500);
        player.setMoneyAmount(100);
        utility.buy(player);
        assertEquals(1, player.getNumOfProperties());

    }

    /**
     * Kiểm thử phương thức resetOwner().
     */
    @Test
    public void testResetOwner() {
        Utility utility = new Utility("Electric Company", 100);
        User player = new User("A", 500);
        player.setMoneyAmount(100);
        utility.buy(player);
        utility.resetOwner();
        assertNull(utility.getOwner());
    }

    /**
     * Kiểm thử phương thức payment() chỉ dành cho một tiện ích (utility).
     */
    @Test
    public void testPayment() {
        Utility utility = new Utility("Electric Company", 100);
        User player1 = new User("A", 500);
        player1.setMoneyAmount(100);
        utility.buy(player1);
        assertEquals(28, utility.payment(7));
    }

}