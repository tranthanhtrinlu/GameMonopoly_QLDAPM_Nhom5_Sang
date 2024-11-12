package Events;
import Model.GamePlayer.Player;
import Model.BoardElements.Tax;
import java.util.EventObject;
/*
Đại diện sự kiện vào ô thuês
 */
public class TaxEvent extends EventObject {

    private final Player player;


    public TaxEvent(Tax source, Player p) {
        super(source);
        this.player = p;
    }

    /**
     * Getter lấy người chơi hiện tại ở ô
     * @return A MVC.Player player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Getter lấy địa chỉ ô
     * @return A MVC.Tax_FreeParking event.
     */
    public Tax getLocation(){
        return (Tax) this.getSource();
    }

    /**
     * Overriden Java method lấy object java
     * @return A Java source
     */
    @Override
    public Object getSource() {
        return super.getSource();
    }
}
