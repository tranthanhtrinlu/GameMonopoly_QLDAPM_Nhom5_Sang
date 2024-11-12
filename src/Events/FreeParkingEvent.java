package Events;

import Model.BoardElements.FreeParking;
import Model.GamePlayer.Player;

import java.util.EventObject;


public class FreeParkingEvent extends EventObject {
    private final Player player;
    private final int centerMoney;

    public FreeParkingEvent(FreeParking source, Player p, int centerMoney) {
        super(source);
        this.player = p;
        this.centerMoney = centerMoney;
    }

    /**
     * Getter lấy người chơi
     * @return A MVC.Player player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Getter lấy FreeParking
     * @return A MVC.Tax_FreeParking event.
     */
    public FreeParking getLocation(){
        return (FreeParking) this.getSource();
    }

    /**
     * Java method for lấy Object java
     * @return A Java source
     */
    @Override
    public Object getSource() {
        return super.getSource();
    }

    /**
     * Getter lấy center money
     * @return An integer center money.
     */
    public int getCenterMoney() {
        return this.centerMoney;
    }
}
