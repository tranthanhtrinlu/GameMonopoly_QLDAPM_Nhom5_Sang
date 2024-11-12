package Events;
import Model.BoardElements.Property;
import Model.GamePlayer.Player;
import java.util.EventObject;

/**
 * Class Events.PropertyEvent đại diện đối tượng tài sản
 */
public class PropertyEvent extends EventObject {

    private final Player player;
    private final int cost;

    public PropertyEvent(Property property, Player p, int rentCost) {
        super(property);
        this.player = p;
        this.cost = rentCost;
    }

    /**
     * Getter lấy người chơi hiện tại
     * @return A MVC.Player player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Getter lấy loại tài sản
     * @return A MVC.Property event.
     */
    public Property getProperty(){
        return (Property) this.getSource();
    }

    /**
     * Overridden Java method lấy object java
     * @return A Java source
     */
    @Override
    public Object getSource() {
        return super.getSource();
    }

    /**
     * Lấy giá của tài sản
     * @return Integer, the cost
     */
    public int getCost() {
        return this.cost;
    }
}
