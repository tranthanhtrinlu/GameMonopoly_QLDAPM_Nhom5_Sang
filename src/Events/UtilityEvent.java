package Events;
import Model.BoardElements.Utility;
import Model.GamePlayer.Player;
import java.util.EventObject;

public class UtilityEvent extends EventObject {

    private final Player player;
    private final int totalDiceRoll;
    private final int payment;
    /**
     * Constructs a prototypical Event.
     *
     * @param utility the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public UtilityEvent(Utility utility, Player p, int totalDiceRoll, int payment) {
        super(utility);
        this.player = p;
        this.totalDiceRoll = totalDiceRoll;
        this.payment = payment;
    }

    /**
    * Method for getting the total dice roll
    * @return An integer totalDiceRoll
    */
    public int getTotalDiceRoll() {
        return this.totalDiceRoll;
    }

    /**
    * Method for getting the player
    * @return A MVC.Player player
    */
    public Player getPlayer() {
        return this.player;
    }

    /**
    * Method for getting a utility element
    * @return A MVC.Utility
    */
    public Utility getUtility(){
        return (Utility) this.getSource();
    }

    /**
    * Java method for getting a source
    */
    @Override
    public Object getSource() {
        return super.getSource();
    }

    /**
     * Getter for the payment.
     * @return An integer payment.
     */
    public int getPayment() {
        return this.payment;
    }
}
