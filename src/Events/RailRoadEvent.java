package Events;


import Model.BoardElements.RailRoad;
import Model.GamePlayer.Player;

import java.util.EventObject;

/**
 * @author Kareem El-Hajjar
=======
import Model.BoardElements.RailRoad;
import Model.GamePlayer.Player;
import java.util.EventObject;

/**

 * Class Events.RailRoadEvent đại diện sự kiện vào ô  đường
 */
public class RailRoadEvent extends EventObject {

    private final Player player;
    private final int rentCost;


    public RailRoadEvent(RailRoad rail, Player p, int rentCost) {
        super(rail);
        this.player = p;
        this.rentCost = rentCost;
    }

    /**
     * Lấy người chơi hiện tại ở ô
     * @return A MVC.Player player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Getter lấy đối tượng đường
     * @return A Railroad event.
     */
    public RailRoad getRailRoad(){
        return (RailRoad) this.getSource();
    }

    /**
     * Override Java Lấy object java
     * @return A Java source
     */
    @Override
    public Object getSource() {
        return super.getSource();
    }

    /**
     * Lấy ra giá thuê
     * @return Integer, the rent cost
     */
    public int getRentCost() {
        return this.rentCost;
    }
}
