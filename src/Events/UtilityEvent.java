package Events;
import Model.BoardElements.Utility;
import Model.GamePlayer.Player;
import java.util.EventObject;

public class UtilityEvent extends EventObject {

    private final Player player;
    private final int totalDiceRoll;
    private final int payment;

    public UtilityEvent(Utility utility, Player p, int totalDiceRoll, int payment) {
        super(utility);
        this.player = p;
        this.totalDiceRoll = totalDiceRoll;
        this.payment = payment;
    }

    /**
    * Tính tổng điểm xúc xắc
    * @return An integer totalDiceRoll
    */
    public int getTotalDiceRoll() {
        return this.totalDiceRoll;
    }

    /**
    * Lấy người chơi
    * @return A MVC.Player player
    */
    public Player getPlayer() {
        return this.player;
    }

    /**
    * Lấy ra phần tử tiện ích
    * @return A MVC.Utility
    */
    public Utility getUtility(){
        return (Utility) this.getSource();
    }

    /**
    * Java method for lấy object java
    */
    @Override
    public Object getSource() {
        return super.getSource();
    }

    /**
     * Getter lấy tiền chi trả
     * @return An integer payment.
     */
    public int getPayment() {
        return this.payment;
    }
}
